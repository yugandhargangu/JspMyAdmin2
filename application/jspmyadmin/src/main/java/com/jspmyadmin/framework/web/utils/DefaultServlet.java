/**
 *
 */
package com.jspmyadmin.framework.web.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.framework.connection.ConnectionFactory;
import com.jspmyadmin.framework.connection.ConnectionType;
import com.jspmyadmin.framework.connection.ConnectionTypeCheck;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.logic.EncodeHelper;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
public class DefaultServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger _LOGGER = Logger.getLogger(DefaultServlet.class.getName());
	private static final EncodeHelper ENCODE_HELPER = new EncodeHelperImpl();
	private static final String INSTALL_URL = "/install.html";

	private static ServletContext _context = null;
	private static String _web_inf_path = null;
	private static String _root_path = null;

	/**
	 *
	 * @param val
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws EncodingException
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
	 *
	 * @return
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
	 * @param _web_inf_path
	 *            the _web_inf_path to set
	 */
	private static void setWebInfPath(String _web_inf_path) {
		DefaultServlet._web_inf_path = _web_inf_path;
	}

	/**
	 *
	 * @param context
	 */
	private static void setContext(ServletContext context) {
		_context = context;
	}

	/**
	 *
	 * @param rootpath
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
				setWebInfPath(_context.getRealPath("/WEB-INF/"));
				setRootPath(_web_inf_path + File.separator + "uploads");
				File file = new File(_root_path);
				if (!file.exists()) {
					file.setExecutable(true, false);
					file.setReadable(true, false);
					file.setWritable(true, false);
					if (file.mkdirs()) {
						_LOGGER.log(Level.INFO, "Temporary path created. Path:" + _root_path);
					} else {
						_LOGGER.log(Level.WARNING, "Unable to create temporary path. Path:" + _root_path);
					}
				} else {
					_LOGGER.log(Level.INFO, "Temporary path already exists. Path:" + _root_path);
				}
				_context.setAttribute(Constants.APP_DATA_TYPES_INFO, Constants.Utils.DATA_TYPES_INFO);
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
		} catch (UnknownHostException e) {
		}
	}

	@Override
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		_doProcess(arg0, arg1, ResolveType.POST);
	}

	@Override
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		_doProcess(arg0, arg1, ResolveType.GET);
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
	 *
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	private void _doProcess(HttpServletRequest request, HttpServletResponse response, ResolveType resolveType)
			throws ServletException, IOException {

		request.setCharacterEncoding(Constants.ENCODE_UTF8);
		response.setCharacterEncoding(Constants.ENCODE_UTF8);
		String path = request.getRequestURI().substring(request.getContextPath().length());
		RequestAdaptor.REQUEST_MAP.put(Thread.currentThread().getId(), request);
		if (ControllerUtil.PATH_MAP.containsKey(path)) {

			if (!ConnectionFactory.isConfigured() && !INSTALL_URL.equals(path)) {
				response.sendRedirect(request.getContextPath() + INSTALL_URL);
				RequestAdaptor.REQUEST_MAP.remove(Thread.currentThread().getId());
				return;
			}

			if (ConnectionFactory.isConfigured() && INSTALL_URL.equals(path)) {
				response.sendRedirect(request.getContextPath());
				RequestAdaptor.REQUEST_MAP.remove(Thread.currentThread().getId());
				return;
			}

			boolean isConfig = false;
			if (ConnectionFactory.isConfigured()) {
				ConnectionType connectionType = ConnectionTypeCheck.check();
				if (connectionType == ConnectionType.CONFIG) {
					if ("/login.html".equals(path)) {
						response.sendRedirect(request.getContextPath() + "/home.html");
						RequestAdaptor.REQUEST_MAP.remove(Thread.currentThread().getId());
						return;
					}
					isConfig = true;
				}
			}
			EncodeHelper encodeObj = new EncodeHelperImpl();
			_checkSession(request, encodeObj);
			PathInfo pathInfo = ControllerUtil.PATH_MAP.get(path);
			if (pathInfo.isAuthRequired() && !isConfig && !_isValidUser(request)) {
				response.sendRedirect(request.getContextPath());
				RequestAdaptor.REQUEST_MAP.remove(Thread.currentThread().getId());
				return;
			}

			Method method = null;
			switch (resolveType) {
			case POST:
				method = pathInfo.getPostMethod();
				break;
			case GET:
				method = pathInfo.getGetMethod();
				break;
			default:
				break;
			}
			if (method == null) {
				method = pathInfo.getAnyMethod();
			}

			if (method == null) {
				response.sendRedirect(request.getContextPath());
				RequestAdaptor.REQUEST_MAP.remove(Thread.currentThread().getId());
				return;
			}

			HttpSession session = request.getSession();
			View view = new ViewImpl(request, response);
			RedirectParams redirectParams = _checkRedirectParams(request);
			RequestAdaptorAbstract requestAdaptor = new RequestAdaptorImpl(encodeObj);
			requestAdaptor.setRedirectParams(redirectParams);
			requestAdaptor.setSession(session);

			FrontController<Bean> frontController = new FrontController<Bean>();
			frontController.setEncodeObj(encodeObj);
			frontController.setRequestAdaptor(requestAdaptor);
			frontController.setRequest(request);
			frontController.setResponse(response);
			frontController.setSession(session);
			frontController.setRedirectParams(redirectParams);

			try {
				Object controller = pathInfo.getController().newInstance();
				if (pathInfo.getDetectMap() != null) {
					for (Entry<Field, DetectType> entry : pathInfo.getDetectMap().entrySet()) {
						Field field = entry.getKey();
						switch (entry.getValue()) {
						case ENCODE_HELPER:
							field.set(controller, encodeObj);
							break;
						case REQUEST_ADAPTOR:
							field.set(controller, requestAdaptor);
							break;
						case REDIRECT_PARAMS:
							field.set(controller, redirectParams);
							break;
						case REQUEST:
							field.set(controller, request);
							break;
						case RESPONSE:
							field.set(controller, response);
							break;
						case SESSION:
							field.set(controller, request.getSession());
							break;
						case MESSAGES:
							field.set(controller, _getMessages(request));
							break;
						case VIEW:
							field.set(controller, view);
							break;
						}
					}
				}

				Object model = null;
				if (pathInfo.getModel() != null) {
					Field field = pathInfo.getModel();
					Class<?> fieldType = field.getType();
					model = fieldType.newInstance();
					field.set(controller, model);
				}

				if (frontController.preService(model, view, pathInfo, resolveType)) {
					method.setAccessible(true);
					Object body = null;
					if (method.getReturnType() == Void.TYPE) {
						method.invoke(controller);
					} else {
						body = method.invoke(controller);
					}
					frontController.postService(model, view, pathInfo, body, resolveType);
					_setNewAdd(request, encodeObj);
				}
			} catch (InstantiationException e) {
				response.sendRedirect(request.getContextPath());
			} catch (IllegalAccessException e) {
				response.sendRedirect(request.getContextPath());
			} catch (IllegalArgumentException e) {
				response.sendRedirect(request.getContextPath());
			} catch (Exception e) {
				session = request.getSession();
				if (session != null && session.getAttribute(Constants.SESSION_CONNECT) != null) {
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
				} else if (e instanceof JSONException || e instanceof EncodingException) {
					redirectParams.put(Constants.ERR_KEY, AppConstants.ERR_INVALID_ACCESS);
					session.setAttribute(Constants.SESSION_REDIRECT_PARAM, redirectParams);
					response.sendRedirect(request.getContextPath() + AppConstants.PATH_HOME);
				} else {
					response.sendRedirect(request.getContextPath());
				}
			}
		} else {
			response.sendRedirect(request.getContextPath());
		}
		RequestAdaptor.REQUEST_MAP.remove(Thread.currentThread().getId());

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
		Object temp = httpSession.getAttribute(Constants.SESSION);
		if (temp == null || !((Boolean) temp)) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * @param request
	 * @param encodeObj
	 */
	private void _checkSession(HttpServletRequest request, EncodeHelper encodeObj) {
		HttpSession httpSession = request.getSession();
		if (httpSession == null) {
			httpSession = request.getSession(true);
			httpSession.invalidate();
			httpSession = request.getSession(true);
		}
		Object temp = httpSession.getAttribute(Constants.SESSION_FONTSIZE);
		if (temp == null) {
			httpSession.setAttribute(Constants.SESSION_FONTSIZE, 80);
		}

		temp = httpSession.getAttribute(Constants.SESSION_LOCALE);
		if (temp == null) {
			if (Constants.Utils.LANGUAGE_MAP.containsKey(request.getLocale().getLanguage())) {
				httpSession.setAttribute(Constants.SESSION_LOCALE, request.getLocale().getLanguage());
			} else {
				httpSession.setAttribute(Constants.SESSION_LOCALE, Constants.DEFAULT_LOCALE);
			}
		}
		temp = httpSession.getAttribute(Constants.SESSION_KEY);
		if (temp == null) {
			encodeObj.generateKey(httpSession);
		}

	}

	/**
	 *
	 * @param request
	 * @return
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
	 *
	 * @param request
	 * @return
	 */
	private Messages _getMessages(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		Messages messages = null;
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
	 *
	 * @param request
	 */
	private void _setNewAdd(HttpServletRequest request, final EncodeHelper encodeObj) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(Constants.NEW_ADD, Constants.NEW_ADD);
			request.setAttribute(Constants.NEW_ADD, encodeObj.encode(jsonObject.toString()));
		} catch (JSONException e) {
		} catch (EncodingException e) {
		}
	}

}
