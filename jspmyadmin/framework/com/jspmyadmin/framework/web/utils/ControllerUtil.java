/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.Download;
import com.jspmyadmin.framework.web.annotations.Multipart;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;

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
	private static Type _beanType = null;
	private static ClassFileFilter _fileFilter = new ClassFileFilter();

	/**
	 * 
	 */
	static void scan() {
		try {
			_scanAllClasses();
			_scanControllers();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
					_APP_PACKAGE.replace(FrameworkConstants.SYMBOL_DOT, FrameworkConstants.SYMBOL_BACK_SLASH));
			uri = new URI(packageURL.toString());
			folder = new File(uri.getPath());
			files = folder.listFiles(_fileFilter);
			if (files != null) {
				pkg = _APP_PACKAGE + FrameworkConstants.SYMBOL_DOT;
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
		if (!pkg.endsWith(FrameworkConstants.SYMBOL_DOT)) {
			pkg = pkg + FrameworkConstants.SYMBOL_DOT;
		}
		String currentPkg = pkg + folder.getName() + FrameworkConstants.SYMBOL_DOT;
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
		Method method = null;
		WebController webController = null;
		ValidateToken validateToken = null;
		Download download = null;
		Multipart multipart = null;
		ResponseBody responseBody = null;
		String bean = null;
		Class<?> beanClass = null;
		try {
			while (classIterator.hasNext()) {
				strClass = classIterator.next();
				klass = Class.forName(strClass);
				webController = klass.getAnnotation(WebController.class);
				if (webController != null) {
					if (_isController(klass)) {
						pathInfo = new PathInfo();
						pathInfo.setController(klass);
						pathInfo.setAuthRequired(webController.authentication());
						try {
							bean = _beanType.toString();
							bean = bean.substring(6, bean.length());
							beanClass = Class.forName(bean);
							pathInfo.setBean(beanClass);
							method = klass.getDeclaredMethod(HANDLEGET, beanClass, View.class);
							validateToken = method.getAnnotation(ValidateToken.class);
							if (validateToken != null) {
								pathInfo.setGetValidateToken(true);
							}
							download = method.getAnnotation(Download.class);
							if (download != null) {
								pathInfo.setGetDownload(true);
							}
							multipart = method.getAnnotation(Multipart.class);
							if (multipart != null) {
								pathInfo.setGetMultiPart(true);
							}
							responseBody = method.getAnnotation(ResponseBody.class);
							if (responseBody != null) {
								pathInfo.setGetResponseBody(true);
							}

							method = klass.getDeclaredMethod(HANDLEPOST, beanClass, View.class);
							validateToken = method.getAnnotation(ValidateToken.class);
							if (validateToken != null) {
								pathInfo.setPostValidateToken(true);
							}
							download = method.getAnnotation(Download.class);
							if (download != null) {
								pathInfo.setPostDownload(true);
							}
							multipart = method.getAnnotation(Multipart.class);
							if (multipart != null) {
								pathInfo.setPostMultiPart(true);
							}
							responseBody = method.getAnnotation(ResponseBody.class);
							if (responseBody != null) {
								pathInfo.setPostResponseBody(true);
							}
							PATH_MAP.put(webController.path(), pathInfo);
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}

					}
				}
			}
		} finally {
			beanClass = null;
			bean = null;
			multipart = null;
			download = null;
			validateToken = null;
			webController = null;
			method = null;
			pathInfo = null;
			klass = null;
			strClass = null;
			classIterator = null;
			_fileFilter = null;
			_beanType = null;
			_classList = null;
		}
	}

	/**
	 * 
	 * @param klass
	 * @return
	 */
	private static boolean _isController(Class<?> klass) {
		Class<?> superClass = klass.getSuperclass();
		if (Object.class.equals(superClass)) {
			return false;
		} else if (Controller.class.equals(superClass)) {
			Type beanClass = ((ParameterizedType) klass.getGenericSuperclass()).getActualTypeArguments()[0];
			_beanType = beanClass;
			return true;
		} else {
			return _isController(superClass);
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
