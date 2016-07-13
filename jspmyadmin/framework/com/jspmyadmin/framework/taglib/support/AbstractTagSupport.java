/**
 * 
 */
package com.jspmyadmin.framework.taglib.support;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.jsp.tagext.TagSupport;

import com.jspmyadmin.framework.constants.FrameworkConstants;

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
		Object value = null;
		try {
			Method method = bean.getClass().getMethod(
					FrameworkConstants.GET + name.substring(0, 1).toUpperCase()
							+ name.substring(1));
			if (method == null) {
				method = bean.getClass().getMethod(name);
			}
			value = method.invoke(bean);
			method = null;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
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
					FrameworkConstants.SET + name.substring(0, 1).toUpperCase()
							+ name.substring(1));
			if (method == null) {
				method = bean.getClass().getMethod(
						FrameworkConstants.SET + name.substring(2));
			}
			method.invoke(bean, value);
			method = null;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
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
