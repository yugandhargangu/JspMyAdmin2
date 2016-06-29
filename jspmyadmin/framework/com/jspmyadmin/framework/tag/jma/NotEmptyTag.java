/**
 * 
 */
package com.jspmyadmin.framework.tag.jma;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.jspmyadmin.framework.tag.support.AbstractTagSupport;
import com.jspmyadmin.framework.util.FrameworkConstants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/28
 *
 */
public class NotEmptyTag extends AbstractTagSupport {

	private static final long serialVersionUID = 1L;

	private String name = null;
	private String scope = null;

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
		boolean isEmpty = true;
		if (name.startsWith(FrameworkConstants.SYMBOL_HASH)) {
			Object temp = null;
			if (FrameworkConstants.COMMAND.equals(scope)) {
				temp = pageContext.getRequest().getAttribute(FrameworkConstants.COMMAND);
				temp = super.getReflectValue(temp, name.substring(1));
			} else if (FrameworkConstants.REQUEST.equals(scope)) {
				temp = pageContext.getRequest().getAttribute(name.substring(1));
			} else if (FrameworkConstants.PAGE.equals(scope)) {
				temp = pageContext.getAttribute(name.substring(1));
			} else if (FrameworkConstants.SESSION.equals(scope)) {
				temp = pageContext.getSession().getAttribute(name.substring(1));
			}
			if (temp != null) {
				if (temp instanceof List && ((List<?>) temp).size() == 0) {
					isEmpty = true;
				} else if (temp instanceof Map && ((Map<?, ?>) temp).size() == 0) {
					isEmpty = true;
				} else {
					isEmpty = false;
				}
			}
		}
		if (isEmpty) {
			return SKIP_BODY;
		} else {
			return EVAL_BODY_INCLUDE;
		}
	}
}
