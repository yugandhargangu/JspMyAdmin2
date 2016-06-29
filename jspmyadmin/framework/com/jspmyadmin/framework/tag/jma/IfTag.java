/**
 * 
 */
package com.jspmyadmin.framework.tag.jma;

import javax.servlet.jsp.JspException;

import com.jspmyadmin.framework.tag.support.AbstractTagSupport;
import com.jspmyadmin.framework.util.FrameworkConstants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/28
 *
 */
public class IfTag extends AbstractTagSupport {

	private static final long serialVersionUID = 1L;

	private String name = null;
	private String value = null;
	private String scope = null;

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public int doStartTag() throws JspException {

		String[] scopes = new String[2];
		if (!isEmpty(scope)) {
			scopes = scope.split(FrameworkConstants.SYMBOL_COMMA);
		}
		Object temp1 = null;
		if (name.startsWith(FrameworkConstants.SYMBOL_HASH)) {

			if (FrameworkConstants.COMMAND.equals(scopes[0])) {
				temp1 = pageContext.getRequest().getAttribute(
						FrameworkConstants.COMMAND);
				temp1 = super.getReflectValue(temp1, name.substring(1));
			} else if (FrameworkConstants.REQUEST.equals(scopes[0])) {
				temp1 = pageContext.getRequest()
						.getAttribute(name.substring(1));
			} else if (FrameworkConstants.PAGE.equals(scopes[0])) {
				temp1 = pageContext.getAttribute(name.substring(1));
			} else if (FrameworkConstants.SESSION.equals(scopes[0])) {
				temp1 = pageContext.getSession()
						.getAttribute(name.substring(1));
			}
		} else {
			temp1 = name;
		}

		Object temp2 = null;
		if (value.startsWith(FrameworkConstants.SYMBOL_HASH)) {

			if (FrameworkConstants.COMMAND.equals(scopes[1])) {
				temp2 = pageContext.getRequest().getAttribute(
						FrameworkConstants.COMMAND);
				temp2 = super.getReflectValue(temp2, value.substring(1));
			} else if (FrameworkConstants.REQUEST.equals(scopes[1])) {
				temp2 = pageContext.getRequest().getAttribute(
						value.substring(1));
			} else if (FrameworkConstants.PAGE.equals(scopes[1])) {
				temp2 = pageContext.getAttribute(value.substring(1));
			} else if (FrameworkConstants.SESSION.equals(scopes[1])) {
				temp2 = pageContext.getSession().getAttribute(
						value.substring(1));
			}
		} else {
			temp2 = value;
		}

		scopes = null;
		if ((temp1 == null && temp2 == null)
				|| (temp1 != null && temp2 != null && temp1.equals(temp2))) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}
}
