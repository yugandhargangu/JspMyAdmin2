/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jspmyadmin.framework.constants.FrameworkConstants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/28
 *
 */
public class FrameworkListener implements ServletContextListener {

	private static final Logger _LOGGER = Logger.getLogger(FrameworkListener.class.getName());

	private static String _root_path = null;

	/**
	 * @return the _root_path
	 */
	public static String getRoot_path() {
		return _root_path;
	}

	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = null;
		try {

			context = event.getServletContext();
			if (context != null) {
				_root_path = context.getRealPath("/");
				_root_path = _root_path + "/uploads";
				File file = new File(_root_path);
				file.mkdirs();
				context.setAttribute(FrameworkConstants.APP_DATA_TYPES_INFO, FrameworkConstants.Utils.DATA_TYPES_INFO);
				context.setAttribute(FrameworkConstants.HOSTNAME, InetAddress.getLocalHost().getHostName());
				DefaultServlet.setContext(context);

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

		} catch (Exception e) {
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		try {
			ControllerUtil.destroy();
			MessageReader.remove();
		} catch (IOException e) {
			_LOGGER.log(Level.WARNING, "Unable to Remove Messages.", e);
		}
	}

}
