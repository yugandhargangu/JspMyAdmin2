/**
 * 
 */
package com.tracknix.jspmyadmin.framework.taglib.jma;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import com.tracknix.jspmyadmin.framework.taglib.support.AbstractTagSupport;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/28
 *
 */
public class RedirectTag extends AbstractTagSupport {

	private static final long serialVersionUID = 1L;

	private String url = null;

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
		try {
			response.sendRedirect(request.getContextPath() + url);
		} catch (IOException e) {
		}
		return SKIP_PAGE;
	}

}
