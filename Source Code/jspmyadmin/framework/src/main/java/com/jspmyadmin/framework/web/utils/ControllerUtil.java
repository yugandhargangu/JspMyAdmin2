/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.Download;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.HandleGet;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.Rest;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.logic.EncodeHelper;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
class ControllerUtil {

	static final String HANDLEGET = "handleGet";
	static final String HANDLEPOST = "handlePost";

	private static final String _APP_PACKAGE = "com.jspmyadmin.app";
	private static final String _CLASS = ".class";
	static final Map<String, PathInfo> PATH_MAP = new ConcurrentHashMap<String, PathInfo>();

	private static List<String> _classList = new ArrayList<String>();
	private static ClassFileFilter _fileFilter = new ClassFileFilter();

	/**
	 * @throws Exception
	 * @throws ClassNotFoundException
	 * 
	 */
	static void scan() throws ClassNotFoundException, Exception {
		_scanAllClasses();
		_scanControllers();
	}

	/**
	 * 
	 */
	static void destroy() {
		PATH_MAP.clear();
	}

	/**
	 * 
	 * @throws URISyntaxException
	 */
	private static void _scanAllClasses() throws URISyntaxException {

		URL packageURL = null;
		URI uri = null;
		File folder = null;
		File[] files = null;
		ClassLoader classLoader = null;
		String pkg = null;
		try {
			classLoader = Thread.currentThread().getContextClassLoader();
			packageURL = classLoader.getResource(
					_APP_PACKAGE.replace(Constants.SYMBOL_DOT, Constants.SYMBOL_BACK_SLASH));
			uri = new URI(packageURL.toString());
			folder = new File(uri.getPath());
			files = folder.listFiles(_fileFilter);
			if (files != null) {
				pkg = _APP_PACKAGE + Constants.SYMBOL_DOT;
				for (File file : files) {
					if (file.isDirectory()) {
						_scanCurrent(pkg, file);
					}
				}
			}
		} finally {
			pkg = null;
			classLoader = null;
			files = null;
			folder = null;
			uri = null;
			packageURL = null;
		}
	}

	/**
	 * 
	 * @param pkg
	 * @param folder
	 */
	private static void _scanCurrent(String pkg, File folder) {
		if (!pkg.endsWith(Constants.SYMBOL_DOT)) {
			pkg = pkg + Constants.SYMBOL_DOT;
		}
		String currentPkg = pkg + folder.getName() + Constants.SYMBOL_DOT;
		File[] files = null;
		try {
			files = folder.listFiles(_fileFilter);
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						_scanCurrent(currentPkg, file);
					} else {
						pkg = file.getName();
						pkg = currentPkg + pkg.substring(0, pkg.length() - 6);
						_classList.add(pkg);
					}
				}
			}
		} finally {
			currentPkg = null;
			files = null;
			folder = null;
			pkg = null;
		}
	}

	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	private static void _scanControllers() throws ClassNotFoundException, Exception {
		if (_classList.size() < 1) {
			return;
		}
		Iterator<String> classIterator = _classList.iterator();
		String strClass = null;
		Class<?> klass = null;
		PathInfo pathInfo = null;
		WebController webController = null;
		try {
			while (classIterator.hasNext()) {
				strClass = classIterator.next();
				klass = Class.forName(strClass);
				webController = klass.getAnnotation(WebController.class);
				if (webController != null) {
					pathInfo = new PathInfo();
					pathInfo.setController(klass);
					pathInfo.setAuthRequired(webController.authentication());
					pathInfo.setRequestLevel(webController.requestLevel());
					if (klass.isAnnotationPresent(Rest.class)) {
						pathInfo.setResponseBody(true);
					}

					boolean isMapped = false;
					for (Method method : klass.getDeclaredMethods()) {
						if (method.isAnnotationPresent(HandleGetOrPost.class)) {
							isMapped = true;
							pathInfo.setAnyMethod(method);
							if (method.isAnnotationPresent(Download.class)) {
								pathInfo.setDownload(true);
							}
							if (method.isAnnotationPresent(ValidateToken.class)) {
								pathInfo.setValidateToken(true);
							}
						} else if (method.isAnnotationPresent(HandleGet.class)) {
							isMapped = true;
							pathInfo.setGetMethod(method);
							if (method.isAnnotationPresent(Download.class)) {
								pathInfo.setDownload(true);
							}
							if (method.isAnnotationPresent(ValidateToken.class)) {
								pathInfo.setValidateToken(true);
							}
						} else if (method.isAnnotationPresent(HandlePost.class)) {
							isMapped = true;
							pathInfo.setPostMethod(method);
							if (method.isAnnotationPresent(Download.class)) {
								pathInfo.setPostDownload(true);
							}
							if (method.isAnnotationPresent(ValidateToken.class)) {
								pathInfo.setPostValidateToken(true);
							}
						}
					}
					if (isMapped) {

						Map<Field, DetectType> detectMap = new HashMap<Field, DetectType>();
						for (Field field : klass.getDeclaredFields()) {
							if (field.isAnnotationPresent(Detect.class)) {
								Class<?> fieldType = field.getType();
								field.setAccessible(true);
								if (EncodeHelper.class == fieldType) {
									detectMap.put(field, DetectType.ENCODE_HELPER);
								} else if (RequestAdaptor.class == fieldType) {
									detectMap.put(field, DetectType.REQUEST_ADAPTOR);
								} else if (RedirectParams.class == fieldType) {
									detectMap.put(field, DetectType.REDIRECT_PARAMS);
								} else if (HttpServletRequest.class == fieldType) {
									detectMap.put(field, DetectType.REQUEST);
								} else if (HttpServletResponse.class == fieldType) {
									detectMap.put(field, DetectType.RESPONSE);
								} else if (HttpSession.class == fieldType) {
									detectMap.put(field, DetectType.SESSION);
								} else if (Messages.class == fieldType) {
									detectMap.put(field, DetectType.MESSAGES);
								} else if (View.class == fieldType) {
									detectMap.put(field, DetectType.VIEW);
								}
							} else if (field.isAnnotationPresent(Model.class)) {
								field.setAccessible(true);
								pathInfo.setModel(field);
							}

						}
						pathInfo.setDetectMap(detectMap);

						PATH_MAP.put(webController.path(), pathInfo);
					}
				}
			}
		} finally {
			_fileFilter = null;
			_classList = null;
		}
	}

	/**
	 * 
	 * @author Yugandhar Gangu
	 * @created_at 2016/01/29
	 *
	 */
	private static class ClassFileFilter implements FileFilter {

		public boolean accept(File pathname) {
			if (pathname.isDirectory() || pathname.getName().toLowerCase().endsWith(_CLASS)) {
				return true;
			}
			return false;
		}

	}
}
