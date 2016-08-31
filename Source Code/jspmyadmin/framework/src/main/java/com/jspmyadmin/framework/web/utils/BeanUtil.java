/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.jspmyadmin.framework.constants.FrameworkConstants;

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
		} finally {
			param = null;
		}
	}

	@SuppressWarnings("unchecked")
	public void populateMultipart(HttpServletRequest request, Bean bean, int i) throws IOException {
		_bean = bean;
		String boundaryStart = _getBoundryStart(request.getContentType());
		ServletInputStream inputStream = request.getInputStream();

		Map<String, List<?>> paramMap = new HashMap<String, List<?>>();
		FileInputImpl fileInputImpl = null;
		try {
			byte[] buf = new byte[1024];
			String name = null, fileName = null;
			boolean isText = false;
			for (int len = 1; len > 0; len = inputStream.readLine(buf, 0, buf.length)) {
				String temp = new String(buf, 0, len);
				if (boundaryStart.equalsIgnoreCase(temp.trim()) && fileInputImpl != null) {
					List<Object> tempList = null;
					if (paramMap.containsKey(name)) {
						tempList = (List<Object>) paramMap.get(name);
					} else {
						tempList = new ArrayList<Object>();
					}
					tempList.add(fileInputImpl);
					paramMap.put(name, tempList);
					fileInputImpl = null;
				} else if (temp.startsWith(Utils._CONTENT_DISPOSITION) && temp.contains(Utils._FILENAME)) {
					name = _getName(temp);
					fileName = _getFileName(temp);
					isText = false;
					if (null != fileName && !FrameworkConstants.BLANK.equals(fileName)) {
						fileInputImpl = new FileInputImpl(fileName);
					}
				} else if (temp.startsWith(Utils._CONTENT_TYPE)) {
					inputStream.readLine(buf, 0, len);
				} else if (null != fileInputImpl) {
					byte[] bytes = new byte[len];
					System.arraycopy(buf, 0, bytes, 0, len);
					fileInputImpl.write(bytes);
				} else if (temp.startsWith(Utils._CONTENT_DISPOSITION) && !temp.contains(Utils._FILENAME)) {
					name = _getName(temp);
					isText = true;
				} else if (!temp.startsWith(boundaryStart) && null != name && isText
						&& !FrameworkConstants.BLANK.equals(temp.trim())) {

					List<Object> tempList = null;
					if (paramMap.containsKey(name)) {
						tempList = (List<Object>) paramMap.get(name);
					} else {
						tempList = new ArrayList<Object>();
					}
					tempList.add(temp.trim());
					paramMap.put(name, tempList);
				}
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		if (paramMap.size() > 0) {
			for (String key : paramMap.keySet()) {
				_setFileInput(key, paramMap.get(key));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void populateMultipart(HttpServletRequest request, Bean bean) throws IOException {
		_bean = bean;
		String boundaryStart = _getBoundryStart(request.getContentType());
		String boundaryEnd = boundaryStart + Utils._BOUNDARY_PREFIX;
		ServletInputStream inputStream = request.getInputStream();

		Map<String, List<?>> paramMap = new HashMap<String, List<?>>();
		FileInputImpl fileInputImpl = null;
		try {
			byte[] buf = new byte[1024];
			String name = null;
			String fileName = null;
			boolean isText = false;
			boolean isSet = true;
			for (int len = 1; len > 0; len = inputStream.readLine(buf, 0, buf.length)) {
				String temp = new String(buf, 0, len);

				if (boundaryEnd.equalsIgnoreCase(temp.trim())) {
				} else if (boundaryStart.equalsIgnoreCase(temp.trim()) && fileInputImpl != null) {
					List<Object> tempList = null;
					if (paramMap.containsKey(name)) {
						tempList = (List<Object>) paramMap.get(name);
					} else {
						tempList = new ArrayList<Object>();
					}
					tempList.add(fileInputImpl);
					paramMap.put(name, tempList);
					fileInputImpl = null;
					isSet = true;
				} else if (temp.startsWith(Utils._CONTENT_DISPOSITION) && temp.contains(Utils._FILENAME)) {
					name = _getName(temp);
					fileName = _getFileName(temp);
					isText = false;
					if (null != fileName && !FrameworkConstants.BLANK.equals(fileName)) {
						fileInputImpl = new FileInputImpl(fileName);
					}
				} else if (temp.startsWith(Utils._CONTENT_TYPE)) {
					inputStream.readLine(buf, 0, len);
				} else if (null != fileInputImpl) {
					byte[] bytes = new byte[len];
					System.arraycopy(buf, 0, bytes, 0, len);
					fileInputImpl.write(bytes);
				} else if (temp.startsWith(Utils._CONTENT_DISPOSITION) && !temp.contains(Utils._FILENAME)) {
					name = _getName(temp);
					isText = true;
				} else if (!temp.startsWith(boundaryStart) && null != name && isText
						&& !FrameworkConstants.BLANK.equals(temp.trim())) {
					List<Object> tempList = null;
					if (paramMap.containsKey(name)) {
						tempList = (List<Object>) paramMap.get(name);
					} else {
						tempList = new ArrayList<Object>();
					}
					tempList.add(temp.trim());
					paramMap.put(name, tempList);
					isSet = true;
				}

				if (boundaryEnd.equalsIgnoreCase(temp.trim()) && name != null && !isSet) {
					List<Object> tempList = null;
					if (paramMap.containsKey(name)) {
						tempList = (List<Object>) paramMap.get(name);
					} else {
						tempList = new ArrayList<Object>();
					}
					if (fileInputImpl != null) {
						tempList.add(fileInputImpl);
					} else {
						tempList.add(null);
					}
					paramMap.put(name, tempList);
				} else if (boundaryStart.equalsIgnoreCase(temp.trim())) {
					if (name != null && !isSet) {
						List<Object> tempList = null;
						if (paramMap.containsKey(name)) {
							tempList = (List<Object>) paramMap.get(name);
						} else {
							tempList = new ArrayList<Object>();
						}
						tempList.add(null);
						paramMap.put(name, tempList);
					}
					isSet = false;
					name = null;
				}
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		if (paramMap.size() > 0) {
			for (String key : paramMap.keySet()) {
				_setFileInput(key, paramMap.get(key));
			}
		}
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	private void _setValue(String name, Object value) {
		Method method = null;
		try {
			name = FrameworkConstants.SET + new String(name.substring(0, 1)).toUpperCase()
					+ new String(name.substring(1));
			try {
				method = _bean.getClass().getMethod(name, String[].class);
				if (method != null) {
					method.invoke(_bean, value);
					method = null;
				}
				return;
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
			if (method == null) {
				method = _bean.getClass().getMethod(name, String.class);
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

	/**
	 * 
	 * @param name
	 * @param value
	 */
	private void _setFileInput(String name, List<?> value) {
		Method method = null;
		try {

			if (value == null) {
				return;
			}
			name = FrameworkConstants.SET + new String(name.substring(0, 1)).toUpperCase()
					+ new String(name.substring(1));
			// check for string arrays
			try {
				method = _bean.getClass().getMethod(name, String[].class);
				if (method != null) {
					String[] array = new String[value.size()];
					array = value.toArray(array);
					Object obj = array;
					method.invoke(_bean, obj);
					method = null;
				}
				return;
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}

			// check for string value
			try {
				method = _bean.getClass().getMethod(name, String.class);
				if (method != null) {
					method.invoke(_bean, value.get(0));
					method = null;
				}
				return;
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}

			// check for file input arrays
			try {
				method = _bean.getClass().getMethod(name, FileInput[].class);
				if (method != null) {
					FileInput[] array = new FileInput[value.size()];
					array = value.toArray(array);
					Object obj = array;
					method.invoke(_bean, obj);
					method = null;
				}
				return;
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}

			// check for file input
			try {
				method = _bean.getClass().getMethod(name, FileInput.class);
				if (method != null) {
					method.invoke(_bean, value.get(0));
					method = null;
				}
				return;
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}

			// check for object arrays
			try {
				method = _bean.getClass().getMethod(name, Object[].class);
				if (method != null) {
					Object[] array = new Object[value.size()];
					array = value.toArray(array);
					Object obj = array;
					method.invoke(_bean, obj);
					method = null;
				}
				return;
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}

			// check for object
			try {
				method = _bean.getClass().getMethod(name, Object.class);
				if (method != null) {
					method.invoke(_bean, value.get(0));
					method = null;
				}
				return;
			} catch (SecurityException e) {
			} catch (NoSuchMethodException e) {
			}
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}

	/**
	 * 
	 * @param temp
	 * @return
	 */
	private String _getBoundryStart(String contentType) {
		return Utils._BOUNDARY_PREFIX
				+ new String(contentType.substring(contentType.indexOf(Utils._BOUNDARY) + Utils._BOUNDARY.length()));
	}

	/**
	 * 
	 * @param temp
	 * @return
	 */
	private String _getName(String temp) {
		temp = new String(temp.substring(temp.indexOf(Utils._NAME) + Utils._NAME.length() + 1));
		return new String(temp.substring(0, temp.indexOf(Utils._DOUBLE_QUOTE)));
	}

	/**
	 * 
	 * @param temp
	 * @return
	 */
	private String _getFileName(String temp) {
		temp = new String(temp.substring(temp.indexOf(Utils._FILENAME) + Utils._FILENAME.length() + 1));
		return new String(temp.substring(0, temp.indexOf(Utils._DOUBLE_QUOTE)));
	}

	/**
	 * 
	 * @author Yugandhar Gangu
	 * @created_at 2016/07/12
	 *
	 */
	private static class Utils {
		private static final String _BOUNDARY_PREFIX = "--";
		private static final String _BOUNDARY = "boundary=";
		private static final String _NAME = "name=";
		private static final String _FILENAME = "filename=";
		private static final String _CONTENT_DISPOSITION = "Content-Disposition";
		private static final String _CONTENT_TYPE = "Content-Type";
		private static final char _DOUBLE_QUOTE = '"';
	}
}
