/**
 * 
 */
package com.tracknix.jspmyadmin.framework.taglib.jma;

import javax.servlet.jsp.JspException;

import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.taglib.support.AbstractTagSupport;

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
	public synchronized void setName(String name) {
		this.name = name;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public synchronized void setScope(String scope) {
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
		synchronized (this) {
			if (name != null && name.startsWith(Constants.SYMBOL_HASH)) {
				String[] split = name.substring(1).split(Constants.SYMBOL_DOT_EXPR);
				if (scope == null || Constants.PAGE.equals(scope)) {
					for (int i = 0; i < split.length; i++) {
						if (_value == null) {
							_value = pageContext.getAttribute(split[i]);
						} else {
							_value = super.getReflectValue(_value, split[i]);
						}
					}
				} else if (Constants.COMMAND.equals(scope)) {
					for (int i = 0; i < split.length; i++) {
						if (_value == null) {
							_value = pageContext.getRequest().getAttribute(Constants.COMMAND);
						}
						_value = super.getReflectValue(_value, split[i]);
					}
				} else if (Constants.REQUEST.equals(scope)) {
					for (int i = 0; i < split.length; i++) {
						if (_value == null) {
							_value = pageContext.getRequest().getAttribute(split[i]);
						} else {
							_value = super.getReflectValue(_value, split[i]);
						}
					}
				} else if (Constants.SESSION.equals(scope)) {
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
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		synchronized (this) {
			_isSubDone = false;
			_value = null;
		}
		return EVAL_PAGE;
	}
}
