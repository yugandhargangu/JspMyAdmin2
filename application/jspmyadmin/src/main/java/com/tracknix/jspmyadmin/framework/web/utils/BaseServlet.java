package com.tracknix.jspmyadmin.framework.web.utils;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tracknix.jspmyadmin.framework.connection.ConnectionFactory;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.logic.EncodeHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Yugandhar Gangu
 */
public abstract class BaseServlet extends HttpServlet {

    private static final Logger _LOGGER = Logger.getLogger(BaseServlet.class.getName());
    protected static final EncodeHelper ENCODE_HELPER = new EncodeHelperImpl();

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
        BaseServlet._web_inf_path = _web_inf_path;
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

            // read queries
            new Thread() {
                @Override
                public void run() {
                    try {
                        QueryReader.read();
                        _LOGGER.log(Level.INFO, "Successfully Read all queries.");
                    } catch (IOException e) {
                        _LOGGER.log(Level.WARNING, "Unable to read queries.", e);
                    }
                }
            }.start();

            ConnectionFactory.init();
        } catch (UnknownHostException ignored) {
        }
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
     * To check session is already created or not. If not it will create session
     * and set basic settings.
     *
     * @param request   HttpServletRequest object
     * @param encodeObj EncodeHelper object
     */
    protected void _checkSession(HttpServletRequest request, EncodeHelper encodeObj) {
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
    protected PathInfo _getPathInfo(String path, MethodType methodType) {
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
    protected boolean _isValidUser(HttpServletRequest request) {
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
    protected RedirectParams _checkRedirectParams(HttpServletRequest request) {
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
    protected Messages _getMessages(HttpServletRequest request) {
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
    protected void _setNewAdd(HttpServletRequest request, final EncodeHelper encodeObj) {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        try {
            objectNode.put(Constants.NEW_ADD, Constants.NEW_ADD);
            request.setAttribute(Constants.NEW_ADD, encodeObj.encode(objectNode.toString()));
        } catch (EncodingException ignored) {
        }
    }
}
