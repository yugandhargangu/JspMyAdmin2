/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jspmyadmin.framework.util.FrameworkConstants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/28
 *
 */
public class FrameworkListener implements ServletContextListener {

	private static final Logger _LOGGER = Logger.getLogger(FrameworkListener.class.getName());

	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = null;
		try {

			context = event.getServletContext();
			if (context != null) {
				context.setAttribute(FrameworkConstants.APP_DATA_TYPES_INFO, FrameworkConstants.Utils.DATA_TYPES_INFO);
				context.setAttribute(FrameworkConstants.HOSTNAME, InetAddress.getLocalHost().getHostName());
				DefaultServlet.setContext(context);
				ResourceServlet.setBasePath(context.getRealPath("/"));
			}
			ControllerUtil.scan();
			MessageReader.read();
		} catch (IOException e) {
			_LOGGER.log(Level.WARNING, "Unable to Read Messages.", e);
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
