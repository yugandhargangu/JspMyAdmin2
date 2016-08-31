/**
 * 
 */
package com.jspmyadmin.framework.taglib.jma;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.taglib.support.AbstractTagSupport;

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
			String[] parts = new String(name.substring(1)).split(FrameworkConstants.SYMBOL_DOT_EXPR);
			if (FrameworkConstants.COMMAND.equals(scope)) {
				for (int i = 0; i < parts.length; i++) {
					if (temp == null) {
						temp = pageContext.getRequest().getAttribute(FrameworkConstants.COMMAND);
						temp = super.getReflectValue(temp, parts[i]);
					} else {
						temp = super.getReflectValue(temp, parts[i]);
					}
				}
			} else if (FrameworkConstants.REQUEST.equals(scope)) {
				for (int i = 0; i < parts.length; i++) {
					if (temp == null) {
						temp = pageContext.getRequest().getAttribute(parts[i]);
					} else {
						temp = super.getReflectValue(temp, parts[i]);
					}
				}
			} else if (FrameworkConstants.PAGE.equals(scope)) {
				for (int i = 0; i < parts.length; i++) {
					if (temp == null) {
						temp = pageContext.getAttribute(parts[i]);
					} else {
						temp = super.getReflectValue(temp, parts[i]);
					}
				}
			} else if (FrameworkConstants.SESSION.equals(scope)) {
				for (int i = 0; i < parts.length; i++) {
					if (temp == null) {
						temp = pageContext.getSession().getAttribute(parts[i]);
					} else {
						temp = super.getReflectValue(temp, parts[i]);
					}
				}
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
