/**
 * 
 */
package com.jspmyadmin.framework.taglib.jma;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.taglib.support.AbstractTagSupport;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/28
 *
 */
public class CaseTag extends AbstractTagSupport {

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

		Tag parentTag = super.getParent();
		SwitchTag switchTag = null;
		if (parentTag instanceof SwitchTag) {
			switchTag = (SwitchTag) parentTag;
		} else {
			throw new JspException("No switch tag found.");
		}

		if (switchTag.isSubDone()) {
			return SKIP_BODY;
		}

		// checking values from SwitchTag object
		if (name == null) {
			Object temp2 = null;
			if (value.startsWith(FrameworkConstants.SYMBOL_HASH)) {

				String[] split = new String(value.substring(1)).split(FrameworkConstants.SYMBOL_DOT_EXPR);
				if (FrameworkConstants.COMMAND.equals(scope)) {
					for (int i = 0; i < split.length; i++) {
						if (temp2 == null) {
							temp2 = pageContext.getRequest().getAttribute(FrameworkConstants.COMMAND);
						}
						temp2 = super.getReflectValue(temp2, split[i]);
					}
				} else if (FrameworkConstants.REQUEST.equals(scope)) {
					for (int i = 0; i < split.length; i++) {
						if (temp2 == null) {
							temp2 = pageContext.getRequest().getAttribute(split[i]);
						} else {
							temp2 = super.getReflectValue(temp2, split[i]);
						}
					}
				} else if (FrameworkConstants.PAGE.equals(scope)) {
					for (int i = 0; i < split.length; i++) {
						if (temp2 == null) {
							temp2 = pageContext.getAttribute(split[i]);
						} else {
							temp2 = super.getReflectValue(temp2, split[i]);
						}
					}
				} else if (FrameworkConstants.SESSION.equals(scope)) {
					for (int i = 0; i < split.length; i++) {
						if (temp2 == null) {
							temp2 = pageContext.getSession().getAttribute(split[i]);
						} else {
							temp2 = super.getReflectValue(temp2, split[i]);
						}
					}
				}
			} else {
				temp2 = value;
			}

			if (temp2 != null && temp2.equals(switchTag.getValue())) {
				switchTag.setSubDone(true);
				return EVAL_BODY_INCLUDE;
			} else {
				return SKIP_BODY;
			}
		}

		// checking values in same CaseTag object
		String[] scopes = new String[2];
		if (!isEmpty(scope)) {
			scopes = scope.split(FrameworkConstants.SYMBOL_COMMA);
		}
		Object temp1 = null;
		if (name.startsWith(FrameworkConstants.SYMBOL_HASH)) {

			if (FrameworkConstants.COMMAND.equals(scopes[0])) {
				temp1 = pageContext.getRequest().getAttribute(FrameworkConstants.COMMAND);
				temp1 = super.getReflectValue(temp1, new String(name.substring(1)));
			} else if (FrameworkConstants.REQUEST.equals(scopes[0])) {
				temp1 = pageContext.getRequest().getAttribute(new String(name.substring(1)));
			} else if (FrameworkConstants.PAGE.equals(scopes[0])) {
				temp1 = pageContext.getAttribute(new String(name.substring(1)));
			} else if (FrameworkConstants.SESSION.equals(scopes[0])) {
				temp1 = pageContext.getSession().getAttribute(new String(name.substring(1)));
			}
		} else {
			temp1 = name;
		}

		Object temp2 = null;
		if (value.startsWith(FrameworkConstants.SYMBOL_HASH)) {

			if (FrameworkConstants.COMMAND.equals(scopes[1])) {
				temp2 = pageContext.getRequest().getAttribute(FrameworkConstants.COMMAND);
				temp2 = super.getReflectValue(temp2, new String(value.substring(1)));
			} else if (FrameworkConstants.REQUEST.equals(scopes[1])) {
				temp2 = pageContext.getRequest().getAttribute(new String(value.substring(1)));
			} else if (FrameworkConstants.PAGE.equals(scopes[1])) {
				temp2 = pageContext.getAttribute(new String(value.substring(1)));
			} else if (FrameworkConstants.SESSION.equals(scopes[1])) {
				temp2 = pageContext.getSession().getAttribute(new String(value.substring(1)));
			}
		} else {
			temp2 = value;
		}

		scopes = null;
		if ((temp1 == null && temp2 == null) || (temp1 != null && temp2 != null && temp1.equals(temp2))) {
			switchTag.setSubDone(true);
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}

}
