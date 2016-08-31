/**
 * 
 */
package com.jspmyadmin.framework.taglib.jma;

import javax.servlet.jsp.JspException;

import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.taglib.support.AbstractTagSupport;

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
			String[] split = new String(name.substring(1)).split(FrameworkConstants.SYMBOL_DOT_EXPR);
			if (scope == null || FrameworkConstants.PAGE.equals(scope)) {
				for (int i = 0; i < split.length; i++) {
					if (_value == null) {
						_value = pageContext.getAttribute(split[i]);
					} else {
						_value = super.getReflectValue(_value, split[i]);
					}
				}
			} else if (FrameworkConstants.COMMAND.equals(scope)) {
				for (int i = 0; i < split.length; i++) {
					if (_value == null) {
						_value = pageContext.getRequest().getAttribute(FrameworkConstants.COMMAND);
					}
					_value = super.getReflectValue(_value, split[i]);
				}
			} else if (FrameworkConstants.REQUEST.equals(scope)) {
				for (int i = 0; i < split.length; i++) {
					if (_value == null) {
						_value = pageContext.getRequest().getAttribute(split[i]);
					} else {
						_value = super.getReflectValue(_value, split[i]);
					}
				}
			} else if (FrameworkConstants.SESSION.equals(scope)) {
				for (int i = 0; i < split.length; i++) {
					if (_value == null) {
						_value = pageContext.getSession().getAttribute(split[i]);
					} else {
						_value = super.getReflectValue(_value, split[i]);
					}
				}
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
