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
public class DefaultServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger _LOGGER = Logger.getLogger(DefaultServlet.class.getName());
    private static final List<String> EXCLUDE_PATHS = new ArrayList<String>();

    static {
        EXCLUDE_PATHS.add("/install.html");
        EXCLUDE_PATHS.add("/language.text");
        EXCLUDE_PATHS.add("/install.text");
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
                                                break;
                                            case QUERY:
                                                entry.getKey().set(parameters[i], new QueryReader.QueryHelperIml(pathInfo.getParameters()[i].getKlass()));
                                                break;
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
}
