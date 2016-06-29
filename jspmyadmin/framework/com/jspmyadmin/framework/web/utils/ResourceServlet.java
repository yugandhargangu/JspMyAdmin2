/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
public class ResourceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String CSS = ".css";
	private static final String JS = ".js";
	private static final String PNG = ".png";
	private static final String JPEG = ".jpeg";
	private static final String JPG = ".jpg";
	private static final String ICO = ".ico";
	private static final String TEXT_CSS = "text/css";
	private static final String TEXT_JAVASCRIPT = "text/javascript";
	private static final String IMAGE_PNG = "image/png";
	private static final String IMAGE_JPEG = "image/jpeg";
	private static final String IMAGE_XICON = "image/x-icon";

	private static String _basePath = null;

	/**
	 * @param _basePath
	 *            the _basePath to set
	 */
	static void setBasePath(String basePath) {
		_basePath = basePath;
	}

	@Override
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		_doProcess(arg0, arg1);
	}

	@Override
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		_doProcess(arg0, arg1);
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

		String path = request.getRequestURI().substring(request.getContextPath().length());
		FileInputStream fileInputStream = null;
		BufferedInputStream bufferedInputStream = null;
		ServletOutputStream servletOutputStream = null;
		try {
			File file = new File(_basePath + path);
			if (!file.exists() || !file.isFile() || !file.canRead()) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			if (path.endsWith(PNG)) {
				response.setContentType(IMAGE_PNG);
			} else if (path.endsWith(JPEG) || path.endsWith(JPG)) {
				response.setContentType(IMAGE_JPEG);
			} else if (path.endsWith(CSS)) {
				response.setContentType(TEXT_CSS);
			} else if (path.endsWith(JS)) {
				response.setContentType(TEXT_JAVASCRIPT);
			} else if (path.endsWith(ICO)) {
				response.setContentType(IMAGE_XICON);
			}

			fileInputStream = new FileInputStream(file);
			bufferedInputStream = new BufferedInputStream(fileInputStream);
			servletOutputStream = response.getOutputStream();
			int bytes;
			while ((bytes = bufferedInputStream.read()) != -1) {
				servletOutputStream.write(bytes);
			}
		} finally {
			if (servletOutputStream != null) {
				servletOutputStream.close();
			}
			if (bufferedInputStream != null) {
				bufferedInputStream.close();
			}
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
	}

}
