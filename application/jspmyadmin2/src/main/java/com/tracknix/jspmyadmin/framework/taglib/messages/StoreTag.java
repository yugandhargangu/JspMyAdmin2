/**
 * 
 */
package com.tracknix.jspmyadmin.framework.taglib.messages;

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
public class StoreTag extends AbstractSimpleTagSupport {

	private String key = null;
	private String name = null;
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

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void doTag() throws JspException {
		PageContext pageContext = (PageContext) super.getJspContext();
		MessageReader messageReader = (MessageReader) pageContext.getAttribute(Constants.PAGE_CONTEXT_MESSAGES);
		Object temp = null;
		if (scope == null) {
			key = messageReader.getMessage(key);
			pageContext.setAttribute(name, key, PageContext.PAGE_SCOPE);
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
				pageContext.setAttribute(name, key, PageContext.PAGE_SCOPE);
			}
		}
	}
}
