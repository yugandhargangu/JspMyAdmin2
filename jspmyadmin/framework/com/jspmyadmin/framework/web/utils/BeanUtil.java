/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jspmyadmin.framework.util.FrameworkConstants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
class BeanUtil {

	private Bean _bean = null;

	/**
	 * 
	 * @param request
	 * @param bean
	 */
	public void populate(HttpServletRequest request, Bean bean) {
		_bean = bean;
		Map<?, ?> paramMap = null;
		Iterator<?> paramIterator = null;
		String param = null;
		try {
			paramMap = request.getParameterMap();
			paramIterator = paramMap.keySet().iterator();
			while (paramIterator.hasNext()) {
				param = paramIterator.next().toString();
				_setValue(param, paramMap.get(param));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			param = null;
		}
	}

	/**
	 * 
	 * @param request
	 * @param bean
	 */
	public void populateMultipart(HttpServletRequest request, Bean bean) {
		_generateTempFileName("");
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	private void _setValue(String name, Object value) {
		Method method = null;
		try {
			try {
				method = _bean.getClass().getMethod(
						FrameworkConstants.SET
								+ name.substring(0, 1).toUpperCase()
								+ name.substring(1), String[].class);
				if (method != null) {
					method.invoke(_bean, value);
					method = null;
				}
				return;
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
			if (method == null) {
				method = _bean.getClass().getMethod(
						FrameworkConstants.SET
								+ name.substring(0, 1).toUpperCase()
								+ name.substring(1), String.class);
				if (method != null) {
					method.invoke(_bean, ((Object[]) value)[0]);
					method = null;
				}
			}

		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}

	private static synchronized String _generateTempFileName(String name) {
		return FrameworkConstants.TEMP_DIR + System.currentTimeMillis() + name;
	}
}
