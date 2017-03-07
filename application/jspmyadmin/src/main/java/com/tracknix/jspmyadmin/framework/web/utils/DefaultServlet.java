package com.tracknix.jspmyadmin.framework.web.utils;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tracknix.jspmyadmin.framework.connection.BasicConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionFactory;
import com.tracknix.jspmyadmin.framework.connection.ConnectionType;
import com.tracknix.jspmyadmin.framework.connection.ConnectionTypeCheck;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.logic.EncodeHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DefaultServlet is a servlet to handle all the requests mapped with it. It
 * uses the FrontController to map with the scanned controllers.
 *
 * @author Yugandhar Gangu
 */
public class DefaultServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger _LOGGER = Logger.getLogger(DefaultServlet.class.getName());
    private static final EncodeHelper ENCODE_HELPER = new EncodeHelperImpl();
    private static final List<String> EXCLUDE_PATHS = new ArrayList<String>();

    static {
        EXCLUDE_PATHS.add("/install.html");
        EXCLUDE_PATHS.add("/language.text");
        EXCLUDE_PATHS.add("/install.text");
    }

    private static ServletContext _context = null;
    private static String _web_inf_path = null;
    private static String _root_path = null;

    /**
     * @param val string to decode
     * @return decode string
     * @throws UnsupportedEncodingException e
     * @throws EncodingException            e
     */
    public static synchronized String decodeInstall(String val) throws UnsupportedEncodingException, EncodingException {
        return ENCODE_HELPER.decodeInstall(val);
    }

    /**
     * @return the _root_path
     */
    public static String getRoot_path() {
        return _root_path;
    }

    /**
     * @return ServletContext
     */
    public static ServletContext getContext() {
        return _context;
    }

    /**
     * @return the _web_inf_path
     */
    public static String getWebInfPath() {
        return _web_inf_path;
    }

    /**
     * @param _web_inf_path the _web_inf_path to set
     */
    private static void setWebInfPath(String _web_inf_path) {
        DefaultServlet._web_inf_path = _web_inf_path;
    }

    /**
     * @param context ServletContext
     */
    private static void setContext(ServletContext context) {
        _context = context;
    }

    /**
     * @param rootpath _root_path
     */
    private static void setRootPath(String rootpath) {
        _root_path = rootpath;
    }

    @Override
    public void init() throws ServletException {
        try {
            ServletConfig config = getServletConfig();
            setContext(config.getServletContext());
            if (_context != null) {
                //setWebInfPath(_context.getRealPath("/WEB-INF/"));
                setWebInfPath(this.getClass().getResource("/").getPath());
                setRootPath(_web_inf_path + "uploads");
                File file = new File(_root_path);
                file.setExecutable(true, false);
                file.setReadable(true, false);
                file.setWritable(true, false);
                if (file.mkdirs()) {
                    _LOGGER.log(Level.INFO, "Temporary path created. Path:" + _root_path);
                } else {
                    _LOGGER.log(Level.WARNING, "Unable to create temporary path. Path:" + _root_path);
                }
                // TODO
                // _context.setAttribute(Constants.APP_DATA_TYPES_INFO,
                // Constants.Utils.DATA_TYPES_INFO);
                _context.setAttribute(Constants.HOSTNAME, InetAddress.getLocalHost().getHostName());

            }
            // scan controllers
            new Thread() {
                @Override
                public void run() {
                    try {
                        ControllerUtil.scan();
                        _LOGGER.log(Level.INFO, "Successfully Scanned Controllers. Controller Count: "
                                + ControllerUtil.PATH_MAP.size());
                    } catch (Exception e) {
                        _LOGGER.log(Level.WARNING, "Unable to Scan Controllers.", e);
                    }
                }
            }.start();

            // read messages
            new Thread() {
                @Override
                public void run() {
                    MessageReader.read();
                    _LOGGER.log(Level.INFO, "Successfully Read all Messages.");
                }
            }.start();

            ConnectionFactory.init();
        } catch (UnknownHostException ignored) {
        }
    }

    @Override
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        _doProcess(arg0, arg1, MethodType.POST);
    }

    @Override
    protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        _doProcess(arg0, arg1, MethodType.GET);
    }

    @Override
    protected void doPut(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        _doProcess(arg0, arg1, MethodType.PUT);
    }

    @Override
    protected void doDelete(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        _doProcess(arg0, arg1, MethodType.DELETE);
    }

    @Override
    public void destroy() {
        RequestAdaptor.REQUEST_MAP.clear();
        try {
            ControllerUtil.destroy();
            MessageReader.remove();
        } catch (IOException e) {
            _LOGGER.log(Level.WARNING, "Unable to Remove Messages.", e);
        }
        super.destroy();
    }

    /**
     * To handle all the requests with given mapping.
     *
     * @param request    HttpServletRequest object
     * @param response   HttpServletResponse object
     * @param methodType MethodType
     * @throws IOException      e
     * @throws ServletException e
     */
    private void _doProcess(HttpServletRequest request, HttpServletResponse response, MethodType methodType)
            throws ServletException, IOException {

        // encoding
        request.setCharacterEncoding(Constants.ENCODE_UTF8);
        response.setCharacterEncoding(Constants.ENCODE_UTF8);
        // fetch request path
        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println(path);
        // add request to request adaptor map
        RequestAdaptor.REQUEST_MAP.put(Thread.currentThread().getId(), request);

        // check if path present in the map
        if (ControllerUtil.PATH_MAP.containsKey(path)) {

            // check configuration settings are installed or not
            if (!ConnectionFactory.isConfigured() && !EXCLUDE_PATHS.contains(path)) {
                response.sendRedirect(request.getContextPath() + EXCLUDE_PATHS.get(0));
                RequestAdaptor.REQUEST_MAP.remove(Thread.currentThread().getId());
                return;
            }

            // Ignore if already configured and the path is to install again
            if (ConnectionFactory.isConfigured() && EXCLUDE_PATHS.get(0).equals(path)) {
                response.sendRedirect(request.getContextPath());
                RequestAdaptor.REQUEST_MAP.remove(Thread.currentThread().getId());
                return;
            }

            boolean isConfig = false;
            // If connection settings are configured
            if (ConnectionFactory.isConfigured()) {
                // type of connection
                ConnectionType connectionType = ConnectionTypeCheck.check();
                switch (connectionType) {
                    case CONFIG:
                        // check if it is full config
                        if ("/login.html".equals(path)) {
                            // Go to home if all settings are configured.
                            response.sendRedirect(request.getContextPath() + "/index.html");
                            RequestAdaptor.REQUEST_MAP.remove(Thread.currentThread().getId());
                            return;
                        }
                        isConfig = true;
                        break;
                    default:
                        break;
                }
            }

            EncodeHelper encodeObj = new EncodeHelperImpl();
            // check for session settings
            this._checkSession(request, encodeObj);
            // Get path info details
            PathInfo pathInfo = this._getPathInfo(path, methodType);
            // check all the condition are satisfying to proceed
            if (pathInfo == null || (pathInfo.isAuthRequired() && !isConfig && !this._isValidUser(request))) {
                response.sendRedirect(request.getContextPath());
                RequestAdaptor.REQUEST_MAP.remove(Thread.currentThread().getId());
                return;
            }
            HttpSession session = request.getSession();
            View view = new ViewImpl(request, response);
            // check any redirect parameters are present or not
            RedirectParams redirectParams = this._checkRedirectParams(request);
            // create request adaptor object and set required settings
            RequestAdaptorAbstract requestAdaptor = new RequestAdaptorImpl(encodeObj);
            requestAdaptor.setRedirectParams(redirectParams);
            requestAdaptor.setSession(session);

            // create front controller object
            FrontController frontController = new FrontController();
            frontController.setEncodeObj(encodeObj);
            frontController.setRequestAdaptor(requestAdaptor);
            frontController.setRequest(request);
            frontController.setResponse(response);
            frontController.setSession(session);
            frontController.setRedirectParams(redirectParams);

            try {
                Object controller = pathInfo.getController().newInstance();
                Object[] parameters = null;
                List<Object> models = null;
                if (pathInfo.getParameters() != null) {
                    parameters = new Object[pathInfo.getParameters().length];
                    for (int i = 0; i < pathInfo.getParameters().length; i++) {
                        switch (pathInfo.getParameters()[i].getDetectType()) {
                            case ENCODE_HELPER:
                                parameters[i] = encodeObj;
                                break;
                            case REQUEST_ADAPTOR:
                                parameters[i] = requestAdaptor;
                                break;
                            case REDIRECT_PARAMS:
                                parameters[i] = redirectParams;
                                break;
                            case REQUEST:
                                parameters[i] = request;
                                break;
                            case RESPONSE:
                                parameters[i] = response;
                                break;
                            case SESSION:
                                parameters[i] = request.getSession();
                                break;
                            case MESSAGES:
                                parameters[i] = this._getMessages(request);
                                break;
                            case VIEW:
                                parameters[i] = view;
                                break;
                            case CONNECTION:
                                parameters[i] = new BasicConnection();
                            case LOGIC:
                                parameters[i] = pathInfo.getParameters()[i].getKlass().newInstance();
                                if (ControllerUtil.LOGIC_MAP.containsKey(pathInfo.getParameters()[i].getKlass())) {
                                    Map<Field, DetectType> detectMap = ControllerUtil.LOGIC_MAP
                                            .get(pathInfo.getParameters()[i].getKlass());
                                    for (Entry<Field, DetectType> entry : detectMap.entrySet()) {
                                        entry.getKey().setAccessible(true);
                                        switch (entry.getValue()) {
                                            case ENCODE_HELPER:
                                                entry.getKey().set(parameters[i], encodeObj);
                                                break;
                                            case REQUEST_ADAPTOR:
                                                entry.getKey().set(parameters[i], requestAdaptor);
                                                break;
                                            case REDIRECT_PARAMS:
                                                entry.getKey().set(parameters[i], redirectParams);
                                                break;
                                            case REQUEST:
                                                entry.getKey().set(parameters[i], request);
                                                break;
                                            case RESPONSE:
                                                entry.getKey().set(parameters[i], response);
                                                break;
                                            case SESSION:
                                                entry.getKey().set(parameters[i], request.getSession());
                                                break;
                                            case MESSAGES:
                                                entry.getKey().set(parameters[i], this._getMessages(request));
                                                break;
                                            case VIEW:
                                                entry.getKey().set(parameters[i], view);
                                                break;
                                            case CONNECTION:
                                                entry.getKey().set(parameters[i], new BasicConnection());
                                            default:
                                                break;
                                        }
                                    }
                                }
                                break;
                            case MODEL:
                            case BEAN:
                                parameters[i] = pathInfo.getParameters()[i].getKlass().newInstance();
                                if (pathInfo.getParameters()[i].getName() != null) {
                                    if (models == null) {
                                        models = new ArrayList<Object>(1);
                                    }
                                    models.add(parameters[i]);
                                }
                                break;
                            case UNKNOWN:
                                break;
                        }
                    }
                }
                if (frontController.preService(models, pathInfo)) {
                    pathInfo.getMethod().setAccessible(true);
                    Object body = null;
                    if (pathInfo.getMethod().getReturnType() == Void.TYPE) {
                        pathInfo.getMethod().invoke(controller, parameters);
                    } else {
                        body = pathInfo.getMethod().invoke(controller, parameters);
                    }
                    frontController.postService(parameters, view, pathInfo, body);
                    this._setNewAdd(request, encodeObj);
                } else if (pathInfo.isRest()) {
                    frontController.postService(parameters, view, pathInfo, new DefaultBean());
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath());
            } catch (Exception e) {
                e.printStackTrace();
                if (session.getAttribute(Constants.SESSION_CONNECT) != null) {
                    if (ConnectionFactory.isConfigured()) {
                        ConnectionType connectionType = ConnectionTypeCheck.check();
                        switch (connectionType) {
                            case CONFIG:
                                session.setAttribute(Constants.SESSION_REDIRECT_PARAM, redirectParams);
                                response.sendRedirect(request.getContextPath() + "/connection_error.html");
                                break;
                            case HALF_CONFIG:
                            default:
                                session.invalidate();
                                response.sendRedirect(request.getContextPath());
                                break;
                        }
                    }
                } else if (e instanceof SQLException) {
                    redirectParams.put(Constants.ERR, e.getMessage());
                    session.setAttribute(Constants.SESSION_REDIRECT_PARAM, redirectParams);
                    response.sendRedirect(request.getContextPath() + AppConstants.PATH_HOME);
                } else if (e instanceof EncodingException) {
                    redirectParams.put(Constants.ERR_KEY, AppConstants.ERR_INVALID_ACCESS);
                    session.setAttribute(Constants.SESSION_REDIRECT_PARAM, redirectParams);
                    response.sendRedirect(request.getContextPath() + AppConstants.PATH_HOME);
                } else {
                    response.sendRedirect(request.getContextPath());
                }
            }
        } else {
            // redirect to home if path is not present in the map.
            response.sendRedirect(request.getContextPath());
        }
        // remove request from request adaptor map
        RequestAdaptor.REQUEST_MAP.remove(Thread.currentThread().getId());
    }

    /**
     * To check session is already created or not. If not it will create session
     * and set basic settings.
     *
     * @param request   HttpServletRequest object
     * @param encodeObj EncodeHelper object
     */
    private void _checkSession(HttpServletRequest request, EncodeHelper encodeObj) {
        HttpSession httpSession = request.getSession();
        if (httpSession == null) {
            // create new session if not exists
            httpSession = request.getSession(true);
            httpSession.invalidate();
            httpSession = request.getSession(true);
        }

        // check for font size
        Object temp = httpSession.getAttribute(Constants.SESSION_FONTSIZE);
        if (temp == null) {
            httpSession.setAttribute(Constants.SESSION_FONTSIZE, 80);
        }

        // check for current locale
        temp = httpSession.getAttribute(Constants.SESSION_LOCALE);
        if (temp == null) {
            if (Constants.Utils.LANGUAGE_MAP.containsKey(request.getLocale().getLanguage())) {
                httpSession.setAttribute(Constants.SESSION_LOCALE, request.getLocale().getLanguage());
            } else {
                httpSession.setAttribute(Constants.SESSION_LOCALE, Constants.DEFAULT_LOCALE);
            }
        }

        // check for encryption key
        temp = httpSession.getAttribute(Constants.SESSION_KEY);
        if (temp == null) {
            encodeObj.generateKey(httpSession);
        }
    }

    /**
     * To get PathInfo object for given path.
     *
     * @param path       url path
     * @param methodType MethodType
     * @return PathInfo object if exists, other wise Null
     */
    private PathInfo _getPathInfo(String path, MethodType methodType) {
        List<PathInfo> pathInfoList = ControllerUtil.PATH_MAP.get(path);
        for (PathInfo pathInfo : pathInfoList) {
            if (pathInfo.getMethodType() == MethodType.ANY) {
                return pathInfo;
            } else if (methodType == pathInfo.getMethodType()) {
                return pathInfo;
            }
        }
        return null;
    }

    /**
     * To check the user is valid or not.
     *
     * @param request HttpServletRequest object
     * @return true - If valid user, otherwise false
     */
    private boolean _isValidUser(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        if (httpSession == null) {
            return false;
        }
        Object temp = httpSession.getAttribute(Constants.SESSION);
        return !(temp == null || !((Boolean) temp));
    }

    /**
     * @param request HttpServletRequest
     * @return RedirectParams
     */
    private RedirectParams _checkRedirectParams(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        Object temp = httpSession.getAttribute(Constants.SESSION_REDIRECT_PARAM);
        if (temp != null && temp instanceof RedirectParams) {
            httpSession.removeAttribute(Constants.SESSION_REDIRECT_PARAM);
            return (RedirectParams) temp;
        }
        return new RedirectParamsImpl();
    }

    /**
     * To get messages of current locale.
     *
     * @param request HttpServletRequest object
     * @return Messages object
     */
    private Messages _getMessages(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        Messages messages;
        if (httpSession != null) {
            Object temp = httpSession.getAttribute(Constants.SESSION_LOCALE);
            if (temp != null && !Constants.DEFAULT_LOCALE.equals(temp)) {
                messages = new MessageReader(temp.toString());
            } else {
                messages = new MessageReader(null);
            }
        } else {
            messages = new MessageReader(null);
        }
        return messages;
    }

    /**
     * @param request   HttpServletRequest
     * @param encodeObj EncodeHelper
     */
    private void _setNewAdd(HttpServletRequest request, final EncodeHelper encodeObj) {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        try {
            objectNode.put(Constants.NEW_ADD, Constants.NEW_ADD);
            request.setAttribute(Constants.NEW_ADD, encodeObj.encode(objectNode.toString()));
        } catch (EncodingException ignored) {
        }
    }

}
