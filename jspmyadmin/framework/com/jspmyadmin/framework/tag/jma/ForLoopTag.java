/**
 * 
 */
package com.jspmyadmin.framework.tag.jma;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import com.jspmyadmin.framework.tag.support.AbstractTagSupport;
import com.jspmyadmin.framework.util.FrameworkConstants;

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
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(String items) {
		this.items = items;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	// type of items List or Map
	private int _type = 0;
	// index of the items
	private int _index = -1;
	// checks for next items
	private boolean _continue = true;
	// items iterator
	private Iterator<?> _iterator = null;
	private Map<?, ?> _mapItems = null;
	// array object
	private Object _array = null;
	private int _length = 0;

	@Override
	public int doStartTag() throws JspException {
		Object temp = null;
		boolean skip = false;
		// fetch items from specified scope
		if (items.startsWith(FrameworkConstants.SYMBOL_HASH)) {
			if (scope == null || FrameworkConstants.PAGE.equals(scope)) {
				temp = pageContext.getAttribute(items.substring(1));
			} else if (FrameworkConstants.COMMAND.equals(scope)) {
				temp = pageContext.getRequest().getAttribute(
						FrameworkConstants.COMMAND);
				temp = super.getReflectValue(temp, items.substring(1));
			} else if (FrameworkConstants.REQUEST.equals(scope)) {
				temp = pageContext.getRequest()
						.getAttribute(items.substring(1));
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

	@Override
	public int doAfterBody() throws JspException {
		_next();
		if (_continue) {
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
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
				pageContext.setAttribute(name, _iterator.next(),
						PageContext.PAGE_SCOPE);
				if (index != null) {
					pageContext.setAttribute(index, _index,
							PageContext.PAGE_SCOPE);
				}
			} else {
				_continue = false;
			}
			break;
		case _MAP:
			if (_iterator.hasNext()) {
				Object key = _iterator.next();
				pageContext.setAttribute(name, _mapItems.get(key),
						PageContext.PAGE_SCOPE);
				pageContext.setAttribute(this.key, key, PageContext.PAGE_SCOPE);
				if (index != null) {
					pageContext.setAttribute(index, _index,
							PageContext.PAGE_SCOPE);
				}
			} else {
				_continue = false;
			}
			break;
		case _ARRAY:
			if (_index < _length) {
				pageContext.setAttribute(name, Array.get(_array, _index),
						PageContext.PAGE_SCOPE);
				if (index != null) {
					pageContext.setAttribute(index, _index,
							PageContext.PAGE_SCOPE);
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
