package com.tracknix.jspmyadmin.framework.web.utils;

import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.logic.EncodeHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ControllerUtil class is to scan all the controllers and mappings.
 *
 * @author Yugandhar Gangu
 */
class ControllerUtil {

    private static final String _APP_PACKAGE = "com.tracknix.jspmyadmin.application";
    private static final String _CLASS = ".class";
    static final Map<String, List<PathInfo>> PATH_MAP = new ConcurrentHashMap<String, List<PathInfo>>();
    static final Map<Class<?>, Map<Field, DetectType>> LOGIC_MAP = new ConcurrentHashMap<Class<?>, Map<Field, DetectType>>();

    private static boolean isInitialized = false;
    private static List<String> _classList = new ArrayList<String>();
    private static ClassFileFilter _fileFilter = new ClassFileFilter();

    /**
     * @throws Exception e
     */
    synchronized static void scan() throws Exception {
        if (!isInitialized) {
            isInitialized = true;
            _scanAllClasses();
            _scanControllers();
        }
    }

    /**
     *
     */
    synchronized static void destroy() {
        isInitialized = false;
        PATH_MAP.clear();
        LOGIC_MAP.clear();
    }

    /**
     * @throws URISyntaxException e
     */
    private static void _scanAllClasses() throws URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL packageURL = classLoader.getResource(_APP_PACKAGE.replace(Constants.SYMBOL_DOT, Constants.SYMBOL_BACK_SLASH));
        if (packageURL != null) {
            URI uri = new URI(packageURL.getPath());
            File folder = new File(uri.getPath());
            File[] files = folder.listFiles();
            if (files != null) {
                String pkg = _APP_PACKAGE + Constants.SYMBOL_DOT;
                for (File file : files) {
                    if (file.isDirectory()) {
                        _scanCurrent(pkg, file);
                    }
                }
            }
        }
    }

    /**
     * @param pkg    package name
     * @param folder folder name
     */
    private static void _scanCurrent(String pkg, File folder) {
        if (!pkg.endsWith(Constants.SYMBOL_DOT)) {
            pkg = pkg + Constants.SYMBOL_DOT;
        }
        String currentPkg = pkg + folder.getName() + Constants.SYMBOL_DOT;
        File[] files = folder.listFiles(_fileFilter);
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
    }

    /**
     * @throws Exception e
     */
    private static void _scanControllers() throws Exception {
        if (_classList.size() < 1) {
            return;
        }
        Iterator<String> classIterator = _classList.iterator();
        String strClass;
        Class<?> klass;
        PathInfo pathInfo;
        try {
            while (classIterator.hasNext()) {
                strClass = classIterator.next();
                klass = Class.forName(strClass);

                if (klass.isAnnotationPresent(WebController.class)) {
                    WebController webController = klass.getAnnotation(WebController.class);
                    for (Method method : klass.getDeclaredMethods()) {

                        if (method.isAnnotationPresent(Handle.class)) {
                            pathInfo = new PathInfo();
                            pathInfo.setController(klass);
                            pathInfo.setAuthRequired(webController.authentication());
                            pathInfo.setRequestLevel(webController.requestLevel());
                            Handle handle = method.getAnnotation(Handle.class);
                            pathInfo.setMethod(method);
                            pathInfo.setMethodType(handle.methodType());
                            if (method.isAnnotationPresent(Rest.class)) {
                                Rest rest = method.getAnnotation(Rest.class);
                                pathInfo.setRest(true);
                                pathInfo.setContentType(rest.contentType());
                            }
                            if (method.isAnnotationPresent(Download.class)) {
                                pathInfo.setDownload(true);
                            }
                            if (method.isAnnotationPresent(ValidateToken.class)) {
                                pathInfo.setValidateToken(true);
                            }
                            if (method.isAnnotationPresent(GenerateToken.class)) {
                                pathInfo.setGenerateToken(true);
                            }

                            Class<?>[] params = method.getParameterTypes();
                            if (params != null && params.length > 0) {
                                Annotation[][] paramAnnotations = method.getParameterAnnotations();
                                PathInfo.Param[] parameters = new PathInfo.Param[params.length];
                                for (int i = 0; i < params.length; i++) {
                                    if (EncodeHelper.class == params[i]) {
                                        parameters[i] = new PathInfo.Param(params[i], DetectType.ENCODE_HELPER, null);
                                    } else if (RequestAdaptor.class == params[i]) {
                                        parameters[i] = new PathInfo.Param(params[i], DetectType.REQUEST_ADAPTOR, null);
                                    } else if (RedirectParams.class == params[i]) {
                                        parameters[i] = new PathInfo.Param(params[i], DetectType.REDIRECT_PARAMS, null);
                                    } else if (HttpServletRequest.class == params[i]) {
                                        parameters[i] = new PathInfo.Param(params[i], DetectType.REQUEST, null);
                                    } else if (HttpServletResponse.class == params[i]) {
                                        parameters[i] = new PathInfo.Param(params[i], DetectType.RESPONSE, null);
                                    } else if (HttpSession.class == params[i]) {
                                        parameters[i] = new PathInfo.Param(params[i], DetectType.SESSION, null);
                                    } else if (Messages.class == params[i]) {
                                        parameters[i] = new PathInfo.Param(params[i], DetectType.MESSAGES, null);
                                    } else if (View.class == params[i]) {
                                        parameters[i] = new PathInfo.Param(params[i], DetectType.VIEW, null);
                                    } else if (ConnectionHelper.class == params[i]) {
                                        parameters[i] = new PathInfo.Param(params[i], DetectType.CONNECTION, null);
                                    } else if (paramAnnotations != null && paramAnnotations.length > 0) {
                                        for (Annotation annotation : paramAnnotations[i]) {
                                            if (annotation.annotationType() == LogicParam.class) {
                                                parameters[i] = new PathInfo.Param(params[i], DetectType.LOGIC, null);
                                            } else if (annotation.annotationType() == Model.class) {
                                                Model model = (Model) annotation;
                                                if (Bean.class.isAssignableFrom(params[i])) {
                                                    parameters[i] = new PathInfo.Param(params[i], DetectType.BEAN,
                                                            model.name());
                                                } else {
                                                    parameters[i] = new PathInfo.Param(params[i], DetectType.MODEL,
                                                            model.name());
                                                }
                                                break;
                                            } else {
                                                parameters[i] = new PathInfo.Param(params[i], DetectType.UNKNOWN, null);
                                            }
                                        }
                                    } else {
                                        parameters[i] = new PathInfo.Param(params[i], DetectType.UNKNOWN, null);
                                    }
                                }
                                pathInfo.setParameters(parameters);
                            }
                            List<PathInfo> listObj;
                            if (PATH_MAP.containsKey(handle.path())) {
                                listObj = PATH_MAP.get(handle.path());
                            } else {
                                listObj = new ArrayList<PathInfo>(1);
                            }
                            listObj.add(pathInfo);
                            PATH_MAP.put(handle.path(), listObj);
                        }
                    }
                } else if (klass.isAnnotationPresent(LogicService.class)) {
                    Field[] fields = klass.getDeclaredFields();
                    if (fields != null) {
                        Map<Field, DetectType> detectTypeMap = null;
                        for (Field field : fields) {
                            if (field.isAnnotationPresent(Detect.class)) {
                                if (detectTypeMap == null) {
                                    detectTypeMap = new ConcurrentHashMap<Field, DetectType>();
                                }
                                if (EncodeHelper.class == field.getType()) {
                                    detectTypeMap.put(field, DetectType.ENCODE_HELPER);
                                } else if (RequestAdaptor.class == field.getType()) {
                                    detectTypeMap.put(field, DetectType.REQUEST_ADAPTOR);
                                } else if (RedirectParams.class == field.getType()) {
                                    detectTypeMap.put(field, DetectType.REDIRECT_PARAMS);
                                } else if (HttpServletRequest.class == field.getType()) {
                                    detectTypeMap.put(field, DetectType.REQUEST);
                                } else if (HttpServletResponse.class == field.getType()) {
                                    detectTypeMap.put(field, DetectType.RESPONSE);
                                } else if (HttpSession.class == field.getType()) {
                                    detectTypeMap.put(field, DetectType.SESSION);
                                } else if (Messages.class == field.getType()) {
                                    detectTypeMap.put(field, DetectType.MESSAGES);
                                } else if (View.class == field.getType()) {
                                    detectTypeMap.put(field, DetectType.VIEW);
                                } else if (ConnectionHelper.class == field.getType()) {
                                    detectTypeMap.put(field, DetectType.CONNECTION);
                                } else {
                                    detectTypeMap.put(field, DetectType.UNKNOWN);
                                }
                            }
                        }
                        if (detectTypeMap != null) {
                            LOGIC_MAP.put(klass, detectTypeMap);
                        }
                    }
                }
            }
        } finally {
            _fileFilter = null;
            _classList = null;
        }
    }

    /**
     * @author Yugandhar Gangu
     */
    private static class ClassFileFilter implements FileFilter {
        public boolean accept(File pathname) {
            return pathname.isDirectory() || pathname.getName().toLowerCase().endsWith(_CLASS);
        }
    }
}
