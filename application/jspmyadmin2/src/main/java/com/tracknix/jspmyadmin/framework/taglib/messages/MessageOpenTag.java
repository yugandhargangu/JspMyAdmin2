/**
 * 
 */
package com.tracknix.jspmyadmin.framework.taglib.messages;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.taglib.support.AbstractSimpleTagSupport;
import com.tracknix.jspmyadmin.framework.web.utils.MessageReader;

/**
 * 
 * @author Yugandhar Gangu
 * @created_at 2016/01/27
 *
 */
public class MessageOpenTag extends AbstractSimpleTagSupport {

	@Override
	public void doTag() throws JspException {
		PageContext pageContext = (PageContext) super.getJspContext();
		HttpSession httpSession = pageContext.getSession();
		MessageReader messageReader = null;
		if (httpSession != null) {
			Object temp = httpSession.getAttribute(Constants.SESSION_LOCALE);
			if (temp != null) {
				messageReader = new MessageReader(temp.toString());
			} else {
				messageReader = new MessageReader(null);
			}
		} else {
			messageReader = new MessageReader(null);
		}
		pageContext.setAttribute(Constants.PAGE_CONTEXT_MESSAGES, messageReader, PageContext.PAGE_SCOPE);
	}
}
