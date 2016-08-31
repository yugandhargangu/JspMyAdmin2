/**
 * 
 */
package com.jspmyadmin.framework.taglib.support;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.jspmyadmin.framework.constants.FrameworkConstants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/28
 *
 */
public class AbstractSimpleTagSupport extends SimpleTagSupport {

	/**
	 * 
	 * @param bean
	 * @param name
	 * @return
	 */
	protected Object getReflectValue(Object bean, String name) {
		Object value = null;
		try {
			Method method = bean.getClass().getMethod(FrameworkConstants.GET
					+ new String(name.substring(0, 1)).toUpperCase() + new String(name.substring(1)));
			if (method == null) {
				method = bean.getClass().getMethod(name);
			}
			value = method.invoke(bean);
			method = null;
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
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
		try {
			Method method = bean.getClass().getMethod(
					FrameworkConstants.SET + new String(name.substring(0, 1)).toUpperCase() + name.substring(1));
			if (method == null) {
				method = bean.getClass().getMethod(FrameworkConstants.SET + new String(name.substring(2)));
			}
			method.invoke(bean, value);
			method = null;
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}

	/**
	 * 
	 * @param bean
	 * @param name
	 * @param value
	 */
	protected Object getReflectValue(Object bean, String name, int index) {
		Object value = null;
		try {
			Method method = bean.getClass().getMethod(name, Integer.TYPE);
			value = method.invoke(bean, index);
			method = null;
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		return value;
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	protected boolean isEmpty(String val) {
		if (val == null || FrameworkConstants.BLANK.equals(val.trim())) {
			return true;
		}
		return false;
	}

}
