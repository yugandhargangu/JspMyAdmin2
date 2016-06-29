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
public class SwitchTag extends AbstractTagSupport {

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

	// local usage
	private boolean _isSubDone = false;
	private Object _value = null;

	/**
	 * @return the isSubDone
	 */
	public synchronized boolean isSubDone() {
		return _isSubDone;
	}

	/**
	 * @param isSubDone
	 *            the isSubDone to set
	 */
	public synchronized void setSubDone(boolean isSubDone) {
		this._isSubDone = isSubDone;
	}

	/**
	 * @return the value
	 */
	public synchronized Object getValue() {
		return _value;
	}

	@Override
	public int doStartTag() {
		if (name != null && name.startsWith(FrameworkConstants.SYMBOL_HASH)) {
			if (scope == null || FrameworkConstants.PAGE.equals(scope)) {
				_value = pageContext.getAttribute(name.substring(1));
			} else if (FrameworkConstants.COMMAND.equals(scope)) {
				_value = pageContext.getRequest().getAttribute(
						FrameworkConstants.COMMAND);
				_value = super.getReflectValue(_value, name.substring(1));
			} else if (FrameworkConstants.REQUEST.equals(scope)) {
				_value = pageContext.getRequest().getAttribute(
						name.substring(1));
			} else if (FrameworkConstants.SESSION.equals(scope)) {
				_value = pageContext.getSession().getAttribute(
						name.substring(1));
			}
		} else {
			_value = name;
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		_isSubDone = false;
		_value = null;
		return EVAL_PAGE;
	}
}
