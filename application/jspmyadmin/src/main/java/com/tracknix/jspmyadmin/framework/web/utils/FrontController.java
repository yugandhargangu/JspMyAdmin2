package com.tracknix.jspmyadmin.framework.web.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.logic.EncodeHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;

/**
 * FrontController class is to check and manage all config details.
 *
 * @author Yugandhar Gangu
 */
class FrontController implements Serializable {

    private static final long serialVersionUID = 1L;

    private EncodeHelper encodeObj = null;
    private RequestAdaptorAbstract requestAdaptor;
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;
    private HttpSession session = null;
    private RedirectParams redirectParams = null;

    /**
     * @param encodeObj the encodeObj to set
     */
    void setEncodeObj(EncodeHelper encodeObj) {
        this.encodeObj = encodeObj;
    }

    /**
     * @param requestAdaptor the requestAdaptor to set
     */
    void setRequestAdaptor(RequestAdaptorAbstract requestAdaptor) {
        this.requestAdaptor = requestAdaptor;
    }

    /**
     * @param request the request to set
     */
    void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * @param response the response to set
     */
    void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * @param session the session to set
     */
    void setSession(HttpSession session) {
        this.session = session;
        requestAdaptor.setSession(session);
    }

    /**
     * @param redirectParams RedirectParams
     */
    void setRedirectParams(RedirectParams redirectParams) {
        this.redirectParams = redirectParams;
    }

    /**
     * To set all basic configuration and required details.
     *
     * @param params   Object array
     * @param pathInfo PathInfo object
     * @return true - If all settings are proper
     * @throws IOException e
     */
    boolean preService(List<Object> params, final PathInfo pathInfo) throws IOException {

        BeanUtil beanUtil = new BeanUtil();
        boolean canProceed = true;
        // populate request parameters into model objects
        if (params != null) {
            if (request.getContentType() != null) {
                if (request.getContentType().toLowerCase().contains(Constants.MULTIPART_FORM_DATA)) {
                    beanUtil.populateMultipart(request, params);
                } else if (request.getContentType().toLowerCase().contains(ContentType.APPLICATION_JSON.toString())) {
                    beanUtil.populateJson(request, params);
                } else {
                    beanUtil.populate(request, params);
                }
            } else {
                beanUtil.populate(request, params);
            }
        }
        // fill request adaptor details
        if (params != null) {
            for (Object object : params) {
                if (object != null && object instanceof Bean) {
                    Bean bean = (Bean) object;
                    try {
                        if (bean.getToken() != null) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            JsonNode jsonToken = objectMapper.readTree(encodeObj.decode(bean.getToken()));
                            requestAdaptor.setJsonToken(jsonToken);
                        }
                        requestAdaptor.fillRequestBean(bean, pathInfo.getRequestLevel());
                        requestAdaptor.fillBasics(bean);
                    } catch (EncodingException ignored) {
                    } catch (JsonProcessingException ignored) {
                    } catch (IOException ignored) {
                    }
                }
            }
        }

        // validate token if required
        if (pathInfo.isValidateToken()) {
            canProceed = false;
            // basic validation
            if (params != null) {
                for (Object object : params) {
                    if (object != null && object instanceof Bean) {
                        Bean bean = (Bean) object;
                        canProceed = requestAdaptor.isValidToken(bean.getToken());
                        if (canProceed) {
                            break;
                        }
                    }
                }
            }

            // request level validation
            if (canProceed) {
                for (Object object : params) {
                    if (object != null && object instanceof Bean) {
                        Bean bean = (Bean) object;
                        canProceed = requestAdaptor.canProceed(bean, pathInfo.getRequestLevel());
                        if (canProceed) {
                            break;
                        }
                    }
                }

                // stop execution if any required fields are missing
                if (!canProceed) {
                    if (pathInfo.isRest()) {
                        response.setContentType(pathInfo.getContentType().toString());
                    } else {
                        response.sendRedirect(request.getContextPath() + AppConstants.PATH_HOME);
                    }
                }
            }
        }
        return canProceed;
    }

    /**
     * To set post configuration details.
     *
     * @param params   Object array
     * @param view     View object
     * @param pathInfo PathInfo object
     * @throws ServletException  e
     * @throws IOException       e
     * @throws EncodingException e
     */
    void postService(Object[] params, View view, final PathInfo pathInfo, Object body)
            throws ServletException, IOException, EncodingException {

        ViewImpl actualView = (ViewImpl) view;
        // Handle rest services
        if (pathInfo.isRest()) {
            response.setContentType(pathInfo.getContentType().toString());
            if (body instanceof Bean && pathInfo.isGenerateToken()) {
                ((Bean) body).setToken(requestAdaptor.generateToken());
            }
            if (body != null) {
                PrintWriter writer = response.getWriter();
                try {
                    if (pathInfo.getContentType() == ContentType.APPLICATION_JSON) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.setSerializationInclusion(Include.NON_NULL);
                        writer.print(objectMapper.writeValueAsString(body));
                    } else {
                        writer.print(body.toString());
                    }
                } catch (JsonProcessingException e) {
                    writer.print(Constants.Utils.ERROR_JSON);
                } finally {
                    if (writer != null) {
                        writer.close();
                    }
                }
            }
            return;
        } else if (pathInfo.isDownload()) {
            return;
        }

        // Handle forward
        switch (actualView.getType()) {
            case DEFAULT:
                break;
            case FORWARD:
                boolean isTokenSet = false;
                if (params != null) {
                    String token = null;
                    for (int i = 0; i < params.length; i++) {
                        if (params[i] != null && params[i] instanceof Bean) {
                            if (pathInfo.isGenerateToken()) {
                                if (token == null) {
                                    isTokenSet = true;
                                    token = requestAdaptor.generateToken();
                                }
                                Bean bean = (Bean) params[i];
                                bean.setToken(token);
                                requestAdaptor.fillRequestToken(bean);
                            }
                            request.setAttribute(pathInfo.getParameters()[i].getName(), params[i]);
                        }
                    }
                }
                if (!isTokenSet && pathInfo.isGenerateToken()) {
                    DefaultBean defaultBean = new DefaultBean();
                    defaultBean.setToken(requestAdaptor.generateToken());
                    request.setAttribute(Constants.COMMAND, defaultBean);
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.JSP_ROOT + actualView.getPath());
                dispatcher.forward(request, response);
                break;
            case REDIRECT:

                // handle redirect
                if (!redirectParams.isEmpty()) {
                    session.setAttribute(Constants.SESSION_REDIRECT_PARAM, redirectParams);
                }
                String path = request.getContextPath() + actualView.getPath();
                if (params != null) {
                    for (Object object : params) {
                        if (object != null && object instanceof Bean) {
                            Bean bean = (Bean) object;
                            if (actualView.getToken() != null) {
                                String token = requestAdaptor.createRequestToken(bean, actualView.getToken());
                                path = path + Constants.SYMBOL_TOKEN + token;
                            } else {
                                String token = requestAdaptor.createRequestToken(bean);
                                if (token != null) {
                                    path = path + Constants.SYMBOL_TOKEN + token;
                                }
                            }
                        }
                    }
                }
                response.sendRedirect(path);
                break;
            default:
                response.sendRedirect(request.getContextPath());
                break;
        }
    }
}
