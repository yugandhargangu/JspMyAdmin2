/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
public abstract class Controller<T extends Bean> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String _METHOD_GET = "GET";
	private static final String _METHOD_POST = "POST";

	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	protected HttpSession session = null;
	protected Messages messages = null;

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
	}

	/**
	 * 
	 * @param messages
	 */
	void setMessages(Messages messages) {
		this.messages = messages;
	}

	/**
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void generateToken(Bean bean) throws Exception {
		EncDecLogic encDecLogic = new EncDecLogic();
		bean.setToken(encDecLogic.generateToken(request));
		encDecLogic = null;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */
	protected void fillBasics(Bean bean) {
		EncDecLogic encDecLogic = new EncDecLogic();
		try {
			JSONObject jsonObject = new JSONObject(encDecLogic.decode(bean.getToken()));
			if (jsonObject.has(FrameworkConstants.ERR)) {
				bean.setErr(jsonObject.getString(FrameworkConstants.ERR));
			}
			if (jsonObject.has(FrameworkConstants.ERR_KEY)) {
				bean.setErr_key(jsonObject.getString(FrameworkConstants.ERR_KEY));
			}
			if (jsonObject.has(FrameworkConstants.MSG)) {
				bean.setMsg(jsonObject.getString(FrameworkConstants.MSG));
			}
			if (jsonObject.has(FrameworkConstants.MSG_KEY)) {
				bean.setMsg_key(jsonObject.getString(FrameworkConstants.MSG_KEY));
			}
		} catch (JSONException e) {
		} catch (Exception e) {
		} finally {
			encDecLogic = null;
		}
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	protected String encode(Object object) {
		EncDecLogic encDecLogic = new EncDecLogic();
		String result = null;
		try {
			result = encDecLogic.encode(object.toString());
		} catch (Exception e) {
		} finally {
			encDecLogic = null;
		}
		return result;
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	protected String encrypt(Object object) {
		return object.toString();
		// disbale for now
		// EncDecLogic encDecLogic = new EncDecLogic();
		// String result = null;
		// try {
		// result = encDecLogic.encrypt(object.toString().trim());
		// } catch (Exception e) {
		// } finally {
		// encDecLogic = null;
		// }
		// return result;

	}

	/**
	 * 
	 * @param bean
	 */
	protected String checkForDb(Bean bean) {
		if (bean.getToken() != null && !FrameworkConstants.BLANK.equals(bean.getToken().trim())) {
			EncDecLogic encDecLogic = new EncDecLogic();
			JSONObject jsonObject = null;
			try {
				String token = encDecLogic.decode(bean.getToken());
				if (token != null) {
					try {
						jsonObject = new JSONObject(token);
						if (jsonObject.has(FrameworkConstants.DATABASE)) {
							session.setAttribute(FrameworkConstants.SESSION_DB,
									jsonObject.getString(FrameworkConstants.DATABASE));
							token = jsonObject.getString(FrameworkConstants.DATABASE);
							return token;
						}
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
			} finally {
				encDecLogic = null;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */
	protected String checkForTable(Bean bean) {
		session.removeAttribute(FrameworkConstants.SESSION_VIEW);
		if (bean.getToken() != null && !FrameworkConstants.BLANK.equals(bean.getToken().trim())) {
			EncDecLogic encDecLogic = new EncDecLogic();
			JSONObject jsonObject = null;
			try {
				String token = encDecLogic.decode(bean.getToken());
				if (token != null) {
					try {
						jsonObject = new JSONObject(token);
						if (jsonObject.has(FrameworkConstants.TABLE)) {
							session.setAttribute(FrameworkConstants.SESSION_TABLE,
									jsonObject.getString(FrameworkConstants.TABLE));
							token = jsonObject.getString(FrameworkConstants.TABLE);
							if (jsonObject.has(FrameworkConstants.DATABASE)) {
								session.setAttribute(FrameworkConstants.SESSION_DB,
										jsonObject.getString(FrameworkConstants.DATABASE));
							}
							return token;
						}
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
			} finally {
				encDecLogic = null;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */
	protected String checkForView(Bean bean) {
		session.removeAttribute(FrameworkConstants.SESSION_TABLE);
		if (bean.getToken() != null && !FrameworkConstants.BLANK.equals(bean.getToken().trim())) {
			EncDecLogic encDecLogic = new EncDecLogic();
			JSONObject jsonObject = null;
			try {
				String token = encDecLogic.decode(bean.getToken());
				if (token != null) {
					try {
						jsonObject = new JSONObject(token);
						if (jsonObject.has(FrameworkConstants.VIEW)) {
							session.setAttribute(FrameworkConstants.SESSION_VIEW,
									jsonObject.getString(FrameworkConstants.VIEW));
							token = jsonObject.getString(FrameworkConstants.VIEW);
							if (jsonObject.has(FrameworkConstants.DATABASE)) {
								session.setAttribute(FrameworkConstants.SESSION_DB,
										jsonObject.getString(FrameworkConstants.DATABASE));
							}
							return token;
						}
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
			} finally {
				encDecLogic = null;
			}
		}
		return null;
	}

	/**
	 * 
	 */
	protected void clearForServer() {
		session.removeAttribute(FrameworkConstants.SESSION_DB);
		session.removeAttribute(FrameworkConstants.SESSION_TABLE);
		session.removeAttribute(FrameworkConstants.SESSION_VIEW);
	}

	/**
	 * 
	 */
	protected void clearForDb() {
		session.removeAttribute(FrameworkConstants.SESSION_TABLE);
		session.removeAttribute(FrameworkConstants.SESSION_VIEW);
	}

	/**
	 * 
	 * @param view
	 */
	protected void handleDefault(View view) {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_HOME);
	}

	/**
	 * 
	 * @param view
	 */
	protected void handleException(View view) {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_HOME);
	}

	/**
	 * 
	 * @param bean
	 * @param view
	 * @throws IOException
	 * @throws ServletException
	 */
	@SuppressWarnings("unchecked")
	public final void service(Bean bean, View view, final PathInfo pathInfo) throws IOException, ServletException {
		try {
			if (_METHOD_GET.equals(request.getMethod().toUpperCase())) {
				BeanUtil beanUtil = new BeanUtil();
				beanUtil.populate(request, bean);
				beanUtil = null;

				if (pathInfo.isGetValidateToken()) {
					EncDecLogic encDecLogic = new EncDecLogic();
					if (!encDecLogic.isValidToken(request, bean.getToken())) {
						response.sendRedirect(request.getContextPath());
						return;
					}
					encDecLogic = null;
				}

				handleGet((T) bean, view);
				if (pathInfo.isGetResponseBody() || pathInfo.isGetDownload()) {
					response.setContentType(FrameworkConstants.CONTENT_TYPE_TEXT_PLAIN);
					return;
				}
				ActualView actualView = (ActualView) view;
				switch (actualView.getType()) {
				case FORWARD:
					request.setAttribute(FrameworkConstants.COMMAND, bean);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher(FrameworkConstants.JSP_ROOT + actualView.getPath());
					dispatcher.forward(request, response);
					break;
				case REDIRECT:
					String path = request.getContextPath() + actualView.getPath();
					if (actualView.getToken() != null) {
						path = path + FrameworkConstants.SYMBOL_TOKEN + actualView.getToken();
					}
					response.sendRedirect(path);
					break;
				default:
					break;
				}
			} else if (_METHOD_POST.equals(request.getMethod().toUpperCase())) {
				BeanUtil beanUtil = new BeanUtil();
				if (request.getContentType() != null && request.getContentType().toLowerCase()
						.indexOf(FrameworkConstants.MULTIPART_FORM_DATA) > -1) {
					beanUtil.populateMultipart(request, bean);
				} else {
					beanUtil.populate(request, bean);
				}
				beanUtil = null;

				if (pathInfo.isPostValidateToken()) {
					EncDecLogic encDecLogic = new EncDecLogic();
					if (!encDecLogic.isValidToken(request, bean.getToken())) {
						response.sendRedirect(request.getContextPath());
						return;
					}
					encDecLogic = null;
				}

				handlePost((T) bean, view);
				if (pathInfo.isPostResponseBody() || pathInfo.isPostDownload()) {
					return;
				}
				ActualView actualView = (ActualView) view;
				switch (actualView.getType()) {
				case FORWARD:
					request.setAttribute(FrameworkConstants.COMMAND, bean);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher(FrameworkConstants.JSP_ROOT + actualView.getPath());
					dispatcher.forward(request, response);
					break;
				case REDIRECT:
					String path = request.getContextPath() + actualView.getPath();
					if (actualView.getToken() != null) {
						path = path + FrameworkConstants.SYMBOL_TOKEN + actualView.getToken();
					}
					response.sendRedirect(path);
					break;
				default:
					break;
				}
			} else {
				response.sendRedirect(request.getContextPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param bean
	 * @param view
	 */
	protected abstract void handleGet(T bean, View view) throws Exception;

	/**
	 * 
	 * @param bean
	 * @param view
	 */
	protected abstract void handlePost(T bean, View view) throws Exception;
}
