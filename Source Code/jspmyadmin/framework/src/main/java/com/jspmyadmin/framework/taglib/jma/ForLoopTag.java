/**
 * 
 */
package com.jspmyadmin.framework.taglib.jma;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.taglib.support.AbstractTagSupport;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/28
 *
 */
public class ForLoopTag extends AbstractTagSupport {

	private static final long serialVersionUID = 1L;
	private static final int _LIST = 1;
	private static final int _MAP = 2;
	private static final int _ARRAY = 3;

	// list of items
	private String items = null;
	// name of current variable
	private String name = null;
	// scope of items
	private String scope = null;
	// name of index variable
	private String index = null;
	// key of map
	private String key = null;

	/**
	 * @param name
	 *            the name to set
	 */
	public synchronized void setName(String name) {
		this.name = name;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public synchronized void setItems(String items) {
		this.items = items;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public synchronized void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public synchronized void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public synchronized void setKey(String key) {
		this.key = key;
	}

	// type of items List or Map
	private int _type = 0;
	// index of the items
	private int _index = -1;
	// checks for next items
	private boolean _continue = true;
	// items iterator
	private transient Iterator<?> _iterator = null;
	private transient Map<?, ?> _mapItems = null;
	// array object
	private Object _array = null;
	private int _length = 0;

	@Override
	public int doStartTag() throws JspException {
		synchronized (this) {
			Object temp = null;
			boolean skip = false;
			// fetch items from specified scope
			if (items.startsWith(Constants.SYMBOL_HASH)) {
				String[] parts = items.substring(1).split(Constants.SYMBOL_DOT_EXPR);
				if (scope == null || Constants.PAGE.equals(scope)) {
					for (int i = 0; i < parts.length; i++) {
						if (temp == null) {
							temp = pageContext.getAttribute(parts[i]);
						} else {
							temp = super.getReflectValue(temp, parts[i]);
						}
					}
				} else if (Constants.COMMAND.equals(scope)) {
					for (int i = 0; i < parts.length; i++) {
						if (temp == null) {
							temp = pageContext.getRequest().getAttribute(Constants.COMMAND);
							temp = super.getReflectValue(temp, parts[i]);
						} else {
							temp = super.getReflectValue(temp, parts[i]);
						}
					}
				} else if (Constants.REQUEST.equals(scope)) {
					for (int i = 0; i < parts.length; i++) {
						if (temp == null) {
							temp = pageContext.getRequest().getAttribute(parts[i]);
						} else {
							temp = super.getReflectValue(temp, parts[i]);
						}
					}
				}
			}

			// check kind of items
			if (temp != null) {
				if (temp instanceof List) {
					List<?> _listItems = (List<?>) temp;
					_iterator = _listItems.iterator();
					_type = _LIST;
				} else if (temp instanceof Map) {
					_mapItems = (Map<?, ?>) temp;
					_iterator = _mapItems.keySet().iterator();
					if (key == null || isEmpty(key)) {
						key = "jnnoiasjdiosadhsbdsb";
					}
					_type = _MAP;
				} else if (temp.getClass().isArray()) {
					_array = temp;
					_length = Array.getLength(_array);
					_type = _ARRAY;
				} else {
					skip = true;
				}
			} else {
				skip = true;
			}
			if (skip) {
				return SKIP_BODY;
			}
			// go to next item
			_next();
			if (_continue) {
				return EVAL_BODY_INCLUDE;
			} else {
				return SKIP_BODY;
			}
		}
	}

	@Override
	public int doAfterBody() throws JspException {
		_next();
		synchronized (this) {
			if (_continue) {
				return EVAL_BODY_AGAIN;
			}
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		synchronized (this) {
			items = null;
			name = null;
			scope = null;
			index = null;
			key = null;
			_type = 0;
			_index = -1;
			_continue = true;
			_iterator = null;
			_array = null;
			_length = 0;
			_mapItems = null;
		}
		return super.doEndTag();
	}

	/**
	 * 
	 */
	private synchronized void _next() {
		_index++;
		switch (_type) {
		case _LIST:
			if (_iterator.hasNext()) {
				pageContext.setAttribute(name, _iterator.next(), PageContext.PAGE_SCOPE);
				if (index != null) {
					pageContext.setAttribute(index, _index, PageContext.PAGE_SCOPE);
				}
			} else {
				_continue = false;
			}
			break;
		case _MAP:
			if (_iterator.hasNext()) {
				Object key = _iterator.next();
				pageContext.setAttribute(name, _mapItems.get(key), PageContext.PAGE_SCOPE);
				pageContext.setAttribute(this.key, key, PageContext.PAGE_SCOPE);
				if (index != null) {
					pageContext.setAttribute(index, _index, PageContext.PAGE_SCOPE);
				}
			} else {
				_continue = false;
			}
			break;
		case _ARRAY:
			if (_index < _length) {
				pageContext.setAttribute(name, Array.get(_array, _index), PageContext.PAGE_SCOPE);
				if (index != null) {
					pageContext.setAttribute(index, _index, PageContext.PAGE_SCOPE);
				}
			} else {
				_continue = false;
			}
			break;
		default:
			_continue = false;
			break;
		}
	}
}
