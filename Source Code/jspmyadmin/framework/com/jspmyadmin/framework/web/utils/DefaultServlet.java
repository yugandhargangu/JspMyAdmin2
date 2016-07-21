/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
public class DefaultServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static ServletContext _context = null;

	/**
	 * 
	 * @param context
	 */
	static void setContext(ServletContext context) {
		_context = context;
	}

	/**
	 * 
	 * @return
	 */
	public static ServletContext getContext() {
		return _context;
	}

	public static final Map<Long, HttpServletRequest> REQUEST_MAP = Collections
			.synchronizedMap(new WeakHashMap<Long, HttpServletRequest>());

	@Override
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		_doProcess(arg0, arg1);
	}

	@Override
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		_doProcess(arg0, arg1);
	}

	@Override
	public void destroy() {
		REQUEST_MAP.clear();
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	private void _doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding(FrameworkConstants.ENCODE_UTF8);
		response.setCharacterEncoding(FrameworkConstants.ENCODE_UTF8);
		String path = request.getRequestURI().substring(request.getContextPath().length());
		REQUEST_MAP.put(Thread.currentThread().getId(), request);
		if (ControllerUtil.PATH_MAP.containsKey(path)) {
			_checkSession(request);
			PathInfo pathInfo = ControllerUtil.PATH_MAP.get(path);
			if (pathInfo.isAuthRequired() && !_isValidUser(request)) {
				response.sendRedirect(request.getContextPath());
				REQUEST_MAP.remove(Thread.currentThread().getId());
				return;
			}
			try {
				Controller<?> controller = (Controller<?>) pathInfo.getController().newInstance();
				controller.setRequest(request);
				controller.setResponse(response);
				controller.setSession(request.getSession());
				controller.setMessages(_getMessages(request));
				Bean bean = (Bean) pathInfo.getBean().newInstance();
				View view = new ActualView(request);
				controller.service(bean, view, pathInfo);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		REQUEST_MAP.remove(Thread.currentThread().getId());
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private boolean _isValidUser(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		if (httpSession == null) {
			return false;
		}
		Object temp = httpSession.getAttribute(FrameworkConstants.SESSION);
		if (temp == null || !((Boolean) temp)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param request
	 */
	private void _checkSession(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		if (httpSession == null) {
			httpSession = request.getSession(true);
			httpSession.invalidate();
			httpSession = request.getSession(true);
		}
		Object temp = httpSession.getAttribute(FrameworkConstants.SESSION_FONTSIZE);
		if (temp == null) {
			httpSession.setAttribute(FrameworkConstants.SESSION_FONTSIZE, 80);
		}
		temp = httpSession.getAttribute(FrameworkConstants.SESSION_KEY);
		if (temp == null) {
			EncDecLogic encDecLogic = new EncDecLogic();
			encDecLogic.generateKey(httpSession);
			encDecLogic = null;
		}

	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private Messages _getMessages(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		Messages messages = null;
		if (httpSession != null) {
			Object temp = httpSession.getAttribute(FrameworkConstants.SESSION_LOCALE);
			if (temp != null) {
				messages = new MessageReader(temp.toString());
			} else {
				messages = new MessageReader(null);
			}
		} else {
			messages = new MessageReader(null);
		}
		return messages;
	}
}
