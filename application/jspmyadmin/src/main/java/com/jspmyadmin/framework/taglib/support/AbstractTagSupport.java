/**
 * 
 */
package com.jspmyadmin.framework.taglib.support;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.jsp.tagext.TagSupport;

import com.jspmyadmin.framework.constants.Constants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/28
 *
 */
public class AbstractTagSupport extends TagSupport {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param bean
	 * @param name
	 * @return
	 */
	protected Object getReflectValue(Object bean, String name) {
		Method method = null;
		try {
			method = bean.getClass()
					.getMethod(Constants.GET + name.substring(0, 1).toUpperCase() + name.substring(1));

		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
		if (method == null) {
			try {
				method = bean.getClass().getMethod(name);
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
		}

		Object value = null;
		if (method != null) {
			try {
				value = method.invoke(bean);
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
		return value;
	}

	/**
	 * 
	 * @param bean
	 * @param name
	 * @return
	 */
	protected void setReflectValue(Object bean, String name, Serializable value) {
		Method method = null;
		try {
			method = bean.getClass()
					.getMethod(Constants.SET + name.substring(0, 1).toUpperCase() + name.substring(1));
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}

		if (method == null) {
			try {
				method = bean.getClass().getMethod(Constants.SET + name.substring(2));
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
		}

		if (method != null) {
			try {
				method.invoke(bean, value);
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	protected boolean isEmpty(String val) {
		if (val == null || Constants.BLANK.equals(val.trim())) {
			return true;
		}
		return false;
	}

}
