/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.jspmyadmin.framework.connection.ConnectionType;
import com.jspmyadmin.framework.connection.ConnectionTypeCheck;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
public class DefaultServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger _LOGGER = Logger.getLogger(DefaultServlet.class.getName());

	private static ServletContext _context = null;
	private static String _root_path = null;

	/**
	 * @return the _root_path
	 */
	public static String getRoot_path() {
		return _root_path;
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
	public void init() throws ServletException {
		try {
			ServletConfig config = getServletConfig();
			_context = config.getServletContext();
			if (_context != null) {
				_root_path = _context.getRealPath("/");
				_root_path = _root_path + "/uploads";
				File file = new File(_root_path);
				file.mkdirs();
				_context.setAttribute(FrameworkConstants.APP_DATA_TYPES_INFO, FrameworkConstants.Utils.DATA_TYPES_INFO);
				_context.setAttribute(FrameworkConstants.HOSTNAME, InetAddress.getLocalHost().getHostName());

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
					try {
						MessageReader.read();
						_LOGGER.log(Level.INFO, "Successfully Read all Messages.");
					} catch (IOException e) {
						_LOGGER.log(Level.WARNING, "Unable to Read Messages.", e);
					}
				}
			}.start();

			String host = null;
			String port = null;
			String user = null;
			String pass = null;
			if (config.getInitParameter("host") != null) {
				host = config.getInitParameter("host");
			}
			if (config.getInitParameter("port") != null) {
				port = config.getInitParameter("port");
			}
			if (config.getInitParameter("user") != null) {
				user = config.getInitParameter("user");
			}
			if (config.getInitParameter("password") != null) {
				pass = config.getInitParameter("password");
			}
			_context.setAttribute("config", new Config(host, port, user, pass));
		} catch (Exception e) {
		}
	}

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
		try {
			ControllerUtil.destroy();
			MessageReader.remove();
		} catch (IOException e) {
			_LOGGER.log(Level.WARNING, "Unable to Remove Messages.", e);
		}
		super.destroy();
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
		String path = new String(request.getRequestURI().substring(request.getContextPath().length()));
		REQUEST_MAP.put(Thread.currentThread().getId(), request);
		if (ControllerUtil.PATH_MAP.containsKey(path)) {
			boolean isConfig = false;
			ConnectionType connectionType = ConnectionTypeCheck.check();
			switch (connectionType) {
			case CONFIG:
				if ("/login.html".equals(path)) {
					response.sendRedirect(request.getContextPath() + "/home.html");
					REQUEST_MAP.remove(Thread.currentThread().getId());
					return;
				}
				isConfig = true;
				break;
			default:
				break;
			}
			_checkSession(request);
			PathInfo pathInfo = ControllerUtil.PATH_MAP.get(path);
			if (pathInfo.isAuthRequired() && !isConfig && !_isValidUser(request)) {
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
				controller.setRedirectParams(_checkRedirectParams(request));
				Bean bean = (Bean) pathInfo.getBean().newInstance();
				View view = new ActualView(request);
				controller.service(bean, view, pathInfo);
				_setNewAdd(request);
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
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

		temp = httpSession.getAttribute(FrameworkConstants.SESSION_LOCALE);
		if (temp == null) {
			if (FrameworkConstants.Utils.LANGUAGE_MAP.containsKey(request.getLocale().getLanguage())) {
				httpSession.setAttribute(FrameworkConstants.SESSION_LOCALE, request.getLocale().getLanguage());
			} else {
				httpSession.setAttribute(FrameworkConstants.SESSION_LOCALE, FrameworkConstants.DEFAULT_LOCALE);
			}
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
	private RedirectParams _checkRedirectParams(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		Object temp = httpSession.getAttribute(FrameworkConstants.SESSION_REDIRECT_PARAM);
		if (temp != null && temp instanceof RedirectParams) {
			httpSession.removeAttribute(FrameworkConstants.SESSION_REDIRECT_PARAM);
			return (RedirectParams) temp;
		}
		return new RedirectParamsImpl();
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
			if (temp != null && !FrameworkConstants.DEFAULT_LOCALE.equals(temp)) {
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
	 * 
	 * @param request
	 */
	private void _setNewAdd(HttpServletRequest request) {
		EncDecLogic encDecLogic = new EncDecLogic();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(FrameworkConstants.NEW_ADD, FrameworkConstants.NEW_ADD);
			request.setAttribute(FrameworkConstants.NEW_ADD, encDecLogic.encode(jsonObject.toString()));
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @return
	 */
	public static ConnectionType geConnectionType() {
		try {
			Config config = (Config) _context.getAttribute("config");
			if (config.getHost() != null && config.getPort() != null && config.getUser() != null) {
				return ConnectionType.CONFIG;
			} else if (config.getHost() != null && config.getPort() != null) {
				return ConnectionType.HALF_CONFIG;
			}
		} catch (Exception e) {
		}
		return ConnectionType.SESSION;
	}

	/**
	 * 
	 * @author Yugandhar Gangu
	 * @created_at 2016/08/29
	 *
	 */
	public static class Config {
		private final String host;
		private final String port;
		private final String user;
		private final String pass;

		/**
		 * 
		 * @param host
		 * @param port
		 * @param user
		 * @param pass
		 */
		public Config(String host, String port, String user, String pass) {
			this.host = host;
			this.port = port;
			this.user = user;
			this.pass = pass;
		}

		/**
		 * @return the host
		 */
		public String getHost() {
			return host;
		}

		/**
		 * @return the port
		 */
		public String getPort() {
			return port;
		}

		/**
		 * @return the user
		 */
		public String getUser() {
			return user;
		}

		/**
		 * @return the pass
		 */
		public String getPass() {
			return pass;
		}

	}
}
