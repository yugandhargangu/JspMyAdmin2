/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	protected final EncDecLogic encodeObj = new EncDecLogic();
	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	protected HttpSession session = null;
	protected Messages messages = null;
	protected RedirectParams redirectParams = null;

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
	 * @param redirectParams
	 */
	void setRedirectParams(RedirectParams redirectParams) {
		this.redirectParams = redirectParams;
	}

	/**
	 * 
	 * @param bean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected String generateToken() throws Exception {
		String token = encodeObj.generateToken();
		Object temp = session.getAttribute(FrameworkConstants.SESSION_TOKEN);
		List<String> tokenList = null;
		if (temp != null && temp instanceof List) {
			tokenList = (List<String>) temp;
		} else {
			tokenList = new CopyOnWriteArrayList<String>();
			session.setAttribute(FrameworkConstants.SESSION_TOKEN, tokenList);
		}
		tokenList.add(token);
		token = encodeObj.encode(token);
		return token;
	}

	@SuppressWarnings("unchecked")
	private boolean _isValidToken(String token) {
		try {
			Object temp = session.getAttribute(FrameworkConstants.SESSION_TOKEN);
			if (temp == null || !(temp instanceof List)) {
				return false;
			}
			token = encodeObj.decode(token);
			List<String> tokenList = (List<String>) temp;
			if (tokenList.contains(token)) {
				tokenList.remove(token);
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */
	protected void fillBasics(Bean bean) {
		try {
			if (redirectParams.has(FrameworkConstants.ERR)) {
				bean.setErr(redirectParams.get(FrameworkConstants.ERR).toString());
			}
			if (redirectParams.has(FrameworkConstants.ERR_KEY)) {
				bean.setErr_key(redirectParams.get(FrameworkConstants.ERR_KEY).toString());
			}
			if (redirectParams.has(FrameworkConstants.MSG)) {
				bean.setMsg(redirectParams.get(FrameworkConstants.MSG).toString());
			}
			if (redirectParams.has(FrameworkConstants.MSG_KEY)) {
				bean.setMsg_key(redirectParams.get(FrameworkConstants.MSG_KEY).toString());
			}
		} catch (Exception e) {
		}
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

	protected String encode(Object object) {
		try {
			return encodeObj.encode(object.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		return null;
	}

	/**
	 * 
	 * @param bean
	 */
	protected String setDatabase(Bean bean) {
		if (bean.getToken() != null && !FrameworkConstants.BLANK.equals(bean.getToken().trim())) {
			JSONObject jsonObject = null;
			try {
				String token = encodeObj.decode(bean.getToken());
				if (token != null) {
					try {
						jsonObject = new JSONObject(token);
						if (jsonObject.has(FrameworkConstants.DATABASE)) {
							bean.setRequest_db(jsonObject.getString(FrameworkConstants.DATABASE));
							token = jsonObject.getString(FrameworkConstants.DATABASE);
							return token;
						}
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */
	protected String setTable(Bean bean) {
		if (bean.getToken() != null && !FrameworkConstants.BLANK.equals(bean.getToken().trim())) {
			JSONObject jsonObject = null;
			try {
				String token = encodeObj.decode(bean.getToken());
				if (token != null) {
					try {
						jsonObject = new JSONObject(token);
						if (jsonObject.has(FrameworkConstants.TABLE)) {
							bean.setRequest_table(jsonObject.getString(FrameworkConstants.TABLE));
							token = jsonObject.getString(FrameworkConstants.TABLE);
							if (jsonObject.has(FrameworkConstants.DATABASE)) {
								bean.setRequest_db(jsonObject.getString(FrameworkConstants.DATABASE));
							}
							return token;
						}
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */
	protected String setView(Bean bean) {
		if (bean.getToken() != null && !FrameworkConstants.BLANK.equals(bean.getToken().trim())) {
			JSONObject jsonObject = null;
			try {
				String token = encodeObj.decode(bean.getToken());
				if (token != null) {
					try {
						jsonObject = new JSONObject(token);
						if (jsonObject.has(FrameworkConstants.VIEW)) {
							bean.setRequest_view(jsonObject.getString(FrameworkConstants.VIEW));
							token = jsonObject.getString(FrameworkConstants.VIEW);
							if (jsonObject.has(FrameworkConstants.DATABASE)) {
								bean.setRequest_db(jsonObject.getString(FrameworkConstants.DATABASE));
							}
							return token;
						}
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
			}
		}
		return null;
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
	 * @param file
	 * @param deleteAfterDownload
	 * @param filename
	 * @throws Exception
	 */
	protected void handleDownload(File file, boolean deleteAfterDownload, String filename) throws Exception {
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			outputStream = response.getOutputStream();
			byte[] buffer = new byte[2048];

			int ch = 0;
			while ((ch = inputStream.read(buffer, 0, buffer.length)) > 0) {
				outputStream.write(buffer, 0, ch);
				outputStream.flush();
			}
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (deleteAfterDownload) {
				file.delete();
			}
		}
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
				RequestTokenAdaptor requestAdaptor = new RequestTokenAdaptor();
				BeanUtil beanUtil = new BeanUtil();
				beanUtil.populate(request, bean);
				requestAdaptor.fillRequestBean(bean, this.encodeObj, pathInfo.getRequestLevel());
				beanUtil = null;

				if (pathInfo.isGetValidateToken()) {
					if (!_isValidToken(bean.getToken())) {
						if (pathInfo.isGetResponseBody()) {
							response.setContentType(FrameworkConstants.CONTENT_TYPE_TEXT_PLAIN);
						} else {
							response.sendRedirect(request.getContextPath());
						}
						return;
					}
				}

				handleGet((T) bean, view);
				if (pathInfo.isGetResponseBody()) {
					response.setContentType(FrameworkConstants.CONTENT_TYPE_TEXT_PLAIN);
					return;
				} else if (pathInfo.isGetDownload()) {
					return;
				}
				ActualView actualView = (ActualView) view;
				switch (actualView.getType()) {
				case FORWARD:
					requestAdaptor.fillRequestToken(bean, encodeObj);
					request.setAttribute(FrameworkConstants.COMMAND, bean);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher(FrameworkConstants.JSP_ROOT + actualView.getPath());
					dispatcher.forward(request, response);
					break;
				case REDIRECT:
					if (!redirectParams.isEmpty()) {
						session.setAttribute(FrameworkConstants.SESSION_REDIRECT_PARAM, redirectParams);
					}
					String path = request.getContextPath() + actualView.getPath();
					if (actualView.getToken() != null) {
						String token = requestAdaptor.createRequestToken(bean, actualView.getToken(), encodeObj);
						path = path + FrameworkConstants.SYMBOL_TOKEN + token;
					} else {
						String token = requestAdaptor.createRequestToken(bean, encodeObj);
						if (token != null) {
							path = path + FrameworkConstants.SYMBOL_TOKEN + token;
						}
					}
					response.sendRedirect(path);
					break;
				default:
					response.sendRedirect(request.getContextPath());
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
					if (!_isValidToken(bean.getToken())) {
						if (pathInfo.isPostResponseBody()) {
							response.setContentType(FrameworkConstants.CONTENT_TYPE_TEXT_PLAIN);
						} else {
							response.sendRedirect(request.getContextPath());
						}
						return;
					}
				}

				handlePost((T) bean, view);
				if (pathInfo.isPostResponseBody()) {
					response.setContentType(FrameworkConstants.CONTENT_TYPE_TEXT_PLAIN);
					return;
				} else if (pathInfo.isPostDownload()) {
					return;
				}
				ActualView actualView = (ActualView) view;
				switch (actualView.getType()) {
				case FORWARD:
					RequestTokenAdaptor requestAdaptor = new RequestTokenAdaptor();
					requestAdaptor.fillRequestToken(bean, encodeObj);
					request.setAttribute(FrameworkConstants.COMMAND, bean);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher(FrameworkConstants.JSP_ROOT + actualView.getPath());
					dispatcher.forward(request, response);
					break;
				case REDIRECT:
					if (!redirectParams.isEmpty()) {
						session.setAttribute(FrameworkConstants.SESSION_REDIRECT_PARAM, redirectParams);
					}
					String path = request.getContextPath() + actualView.getPath();
					if (actualView.getToken() != null) {
						requestAdaptor = new RequestTokenAdaptor();
						String token = requestAdaptor.createRequestToken(bean, actualView.getToken(), encodeObj);
						path = path + FrameworkConstants.SYMBOL_TOKEN + token;
					} else {
						requestAdaptor = new RequestTokenAdaptor();
						String token = requestAdaptor.createRequestToken(bean, encodeObj);
						if (token != null) {
							path = path + FrameworkConstants.SYMBOL_TOKEN + token;
						}
					}
					response.sendRedirect(path);
					break;
				default:
					response.sendRedirect(request.getContextPath());
					break;
				}
			} else {
				response.sendRedirect(request.getContextPath());
			}
		} catch (Exception e) {
			if (pathInfo.isPostResponseBody()) {
				response.setContentType(FrameworkConstants.CONTENT_TYPE_TEXT_PLAIN);
			} else {
				handleDefault(view);
			}
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
