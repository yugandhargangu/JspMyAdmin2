/**
 * 
 */
package com.tracknix.jspmyadmin.framework.taglib.messages;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
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
public class PrintTag extends AbstractSimpleTagSupport {

	private String key = null;
	private String scope = null;

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public void doTag() throws JspException, IOException {

		PageContext pageContext = (PageContext) super.getJspContext();
		MessageReader messageReader = (MessageReader) pageContext.getAttribute(Constants.PAGE_CONTEXT_MESSAGES);
		Object temp = null;
		if (scope == null) {
			key = messageReader.getMessage(key);
			JspWriter jspWriter = pageContext.getOut();
			jspWriter.write(key);
			return;
		} else if (Constants.COMMAND.equals(scope)) {
			temp = pageContext.getRequest().getAttribute(Constants.COMMAND);
			temp = super.getReflectValue(temp, key);
		} else if (Constants.PAGE.equals(scope)) {
			temp = pageContext.getAttribute(key);
		} else if (Constants.REQUEST.equals(scope)) {
			temp = pageContext.getRequest().getAttribute(key);
		}
		if (temp != null) {
			key = messageReader.getMessage(temp.toString());
			if (key != null) {
				JspWriter jspWriter = pageContext.getOut();
				jspWriter.write(key);
			}
		}
	}
}
