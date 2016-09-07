/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.logic.EncodeHelper;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
class FrontController<T extends Bean> implements Serializable {

	private static final long serialVersionUID = 1L;

	private EncodeHelper encodeObj = null;
	private RequestAdaptorAbstract requestAdaptor;
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private HttpSession session = null;
	private RedirectParams redirectParams = null;

	/**
	 * @param encodeObj
	 *            the encodeObj to set
	 */
	void setEncodeObj(EncodeHelper encodeObj) {
		this.encodeObj = encodeObj;
	}

	/**
	 * @param requestAdaptor
	 *            the requestAdaptor to set
	 */
	void setRequestAdaptor(RequestAdaptorAbstract requestAdaptor) {
		this.requestAdaptor = requestAdaptor;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	void setSession(HttpSession session) {
		this.session = session;
		requestAdaptor.setSession(session);
	}

	/**
	 * 
	 * @param redirectParams
	 */
	void setRedirectParams(RedirectParams redirectParams) {
		this.redirectParams = redirectParams;
	}

	/**
	 * 
	 * @param file
	 * @param deleteAfterDownload
	 * @param filename
	 * @throws Exception
	 */

	/**
	 * 
	 * @param model
	 * @param view
	 * @param pathInfo
	 * @param resolveType
	 * @return
	 * @throws IOException
	 */
	public boolean preService(Object model, View view, final PathInfo pathInfo, ResolveType resolveType)
			throws IOException {
		Bean bean = null;
		if (model != null && model instanceof Bean) {
			bean = (Bean) model;
		}

		BeanUtil beanUtil = new BeanUtil();
		boolean canProceed = true;

		switch (resolveType) {
		case POST:
			if (model != null) {
				if (request.getContentType() != null && request.getContentType().toLowerCase()
						.indexOf(Constants.MULTIPART_FORM_DATA) > -1) {
					beanUtil.populateMultipart(request, model);
				} else {
					beanUtil.populate(request, model);
				}
			}

			if (pathInfo.isPostValidateToken()) {
				canProceed = false;
				if (bean != null) {
					canProceed = requestAdaptor.isValidToken(bean.getToken());
				}
			}
			if (canProceed) {
				if (bean != null) {
					canProceed = requestAdaptor.canProceed(bean, pathInfo.getRequestLevel());
				}
			}

			if (!canProceed) {
				if (pathInfo.isResponseBody()) {
					response.setContentType(Constants.CONTENT_TYPE_TEXT_PLAIN);
				} else {
					response.sendRedirect(request.getContextPath() + AppConstants.PATH_HOME);
				}
			}
			return canProceed;

		case GET:
			if (model != null) {
				beanUtil.populate(request, model);
			}

			if (bean != null) {
				try {
					if (bean.getToken() != null) {
						JSONObject jsonToken = new JSONObject(encodeObj.decode(bean.getToken()));
						requestAdaptor.setJsonToken(jsonToken);
					}
					requestAdaptor.fillRequestBean(bean, pathInfo.getRequestLevel());
					requestAdaptor.fillBasics(bean);
				} catch (JSONException e) {
				} catch (EncodingException e) {
				}
			}

			if (pathInfo.isValidateToken()) {
				canProceed = false;
				if (bean != null) {
					canProceed = requestAdaptor.isValidToken(bean.getToken());
				}
			}

			if (canProceed) {
				if (bean != null) {
					canProceed = requestAdaptor.canProceed(bean, pathInfo.getRequestLevel());
				}
			}

			if (!canProceed) {
				if (pathInfo.isResponseBody()) {
					response.setContentType(Constants.CONTENT_TYPE_TEXT_PLAIN);
				} else {
					response.sendRedirect(request.getContextPath() + AppConstants.PATH_HOME);
				}
			}
			return canProceed;
		default:
			response.sendRedirect(request.getContextPath());
			return false;
		}

	}

	/**
	 * 
	 * @param model
	 * @param view
	 * @param pathInfo
	 * @throws IOException
	 * @throws ServletException
	 */
	public void postService(Object model, View view, final PathInfo pathInfo, Object body, ResolveType resolveType)
			throws IOException, ServletException {
		Bean bean = null;
		if (model != null && model instanceof Bean) {
			bean = (Bean) model;
		}

		ViewImpl actualView = (ViewImpl) view;
		if (pathInfo.isResponseBody()) {
			response.setContentType(Constants.CONTENT_TYPE_TEXT_PLAIN);
			if (body != null) {
				PrintWriter writer = response.getWriter();
				try {
					writer.print(body.toString());
				} finally {
					if (writer != null) {
						writer.close();
					}
				}
			}
			return;
		} else if ((resolveType.equals(ResolveType.POST) && pathInfo.isPostDownload())
				|| (!resolveType.equals(ResolveType.POST) && pathInfo.isDownload())) {
			return;
		}
		switch (actualView.getType()) {
		case FORWARD:
			if (bean != null) {
				requestAdaptor.fillRequestToken(bean);
				request.setAttribute(Constants.COMMAND, bean);
			}
			RequestDispatcher dispatcher = request
					.getRequestDispatcher(Constants.JSP_ROOT + actualView.getPath());
			dispatcher.forward(request, response);
			break;
		case REDIRECT:
			if (!redirectParams.isEmpty()) {
				session.setAttribute(Constants.SESSION_REDIRECT_PARAM, redirectParams);
			}
			String path = request.getContextPath() + actualView.getPath();
			if (bean != null) {
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
			response.sendRedirect(path);
			break;
		default:
			response.sendRedirect(request.getContextPath());
			break;
		}
	}
}
