package com.tracknix.jspmyadmin.framework.web.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracknix.jspmyadmin.framework.constants.Constants;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Yugandhar Gangu
 */
class BeanUtil {

    private List<Object> _beans = null;

    /**
     * @param request {@link HttpServletRequest]}
     * @param beans   {@link List}
     */
    void populate(HttpServletRequest request, List<Object> beans) {
        _beans = beans;
        try {
            Map<?, ?> paramMap = request.getParameterMap();
            for (Object o : paramMap.keySet()) {
                String param = o.toString();
                _setValue(param, paramMap.get(param));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param request {@link HttpServletRequest}
     * @param beans   {@link List}
     */
    void populateJson(HttpServletRequest request, List<Object> beans) {
        _beans = beans;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = request.getReader();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(bufferedReader);
            Iterator<Entry<String, JsonNode>> jsonIterator = jsonNode.fields();
            while (jsonIterator.hasNext()) {
                Entry<String, JsonNode> entry = jsonIterator.next();
                Object[] values;
                if (entry.getValue().isArray()) {
                    values = new String[entry.getValue().size()];
                    int i = 0;
                    for (JsonNode objNode : entry.getValue()) {
                        values[i++] = objNode.asText();
                    }
                } else {
                    values = new Object[1];
                    values[0] = entry.getValue().asText();
                }
                _setValue(entry.getKey(), values);
            }
            bufferedReader.close();
            bufferedReader = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    void populateMultipart(HttpServletRequest request, List<Object> beans) throws IOException {
        _beans = beans;
        String boundaryStart = _getBoundryStart(request.getContentType());
        String boundaryEnd = boundaryStart + Utils._BOUNDARY_PREFIX;
        ServletInputStream inputStream = request.getInputStream();

        Map<String, List<?>> paramMap = new HashMap<String, List<?>>();
        FileInputImpl fileInputImpl = null;
        try {
            byte[] buf = new byte[1024];
            String name = null;
            String fileName;
            boolean isText = false;
            boolean isSet = true;
            for (int len = 1; len > 0; len = inputStream.readLine(buf, 0, buf.length)) {
                String temp = new String(buf, 0, len);

                if (!boundaryEnd.equalsIgnoreCase(temp.trim())) {
                    if (boundaryStart.equalsIgnoreCase(temp.trim()) && fileInputImpl != null) {
                        List<Object> tempList;
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
                        if (!Constants.BLANK.equals(fileName)) {
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
                            && !Constants.BLANK.equals(temp.trim())) {
                        List<Object> tempList;
                        if (paramMap.containsKey(name)) {
                            tempList = (List<Object>) paramMap.get(name);
                        } else {
                            tempList = new ArrayList<Object>();
                        }
                        tempList.add(temp.trim());
                        paramMap.put(name, tempList);
                        isSet = true;
                    }
                }

                if (boundaryEnd.equalsIgnoreCase(temp.trim()) && name != null && !isSet) {
                    List<Object> tempList;
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
                        List<Object> tempList;
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
            for (Entry<String, List<?>> entry : paramMap.entrySet()) {
                _setFileInput(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * @param name  name
     * @param value Object
     */
    private void _setValue(String name, Object value) {
        name = Constants.SET + name.substring(0, 1).toUpperCase() + name.substring(1);
        for (Object bean : _beans) {
            Method method = null;
            try {
                try {
                    method = bean.getClass().getMethod(name, String[].class);
                    if (method != null) {
                        method.invoke(bean, value);
                        method = null;
                    }
                    return;
                } catch (SecurityException ignored) {
                } catch (NoSuchMethodException ignored) {
                }
                if (method == null) {
                    method = bean.getClass().getMethod(name, String.class);
                    if (method != null) {
                        method.invoke(bean, ((Object[]) value)[0]);
                    }
                }
            } catch (SecurityException ignored) {
            } catch (IllegalArgumentException ignored) {
            } catch (IllegalAccessException ignored) {
            } catch (InvocationTargetException ignored) {
            } catch (NoSuchMethodException ignored) {
            }
        }
    }

    /**
     * @param name  string
     * @param value {@link List}
     */
    private void _setFileInput(String name, List<?> value) {
        if (value == null) {
            return;
        }
        name = Constants.SET + name.substring(0, 1).toUpperCase() + name.substring(1);
        for (Object bean : _beans) {
            Method method;
            try {
                // check for string arrays
                try {
                    method = bean.getClass().getMethod(name, String[].class);
                    if (method != null) {
                        String[] array = new String[value.size()];
                        array = value.toArray(array);
                        Object obj = array;
                        method.invoke(bean, obj);
                    }
                    return;
                } catch (SecurityException ignored) {
                } catch (NoSuchMethodException ignored) {
                }

                // check for string value
                try {
                    method = bean.getClass().getMethod(name, String.class);
                    if (method != null) {
                        method.invoke(bean, value.get(0));
                    }
                    return;
                } catch (SecurityException ignored) {
                } catch (NoSuchMethodException ignored) {
                }

                // check for file input arrays
                try {
                    method = bean.getClass().getMethod(name, FileInput[].class);
                    if (method != null) {
                        FileInput[] array = new FileInput[value.size()];
                        array = value.toArray(array);
                        Object obj = array;
                        method.invoke(bean, obj);
                    }
                    return;
                } catch (SecurityException ignored) {
                } catch (NoSuchMethodException ignored) {
                }

                // check for file input
                try {
                    method = bean.getClass().getMethod(name, FileInput.class);
                    if (method != null) {
                        method.invoke(bean, value.get(0));
                    }
                    return;
                } catch (SecurityException ignored) {
                } catch (NoSuchMethodException ignored) {
                }

                // check for object arrays
                try {
                    method = bean.getClass().getMethod(name, Object[].class);
                    if (method != null) {
                        Object[] array = new Object[value.size()];
                        array = value.toArray(array);
                        Object obj = array;
                        method.invoke(bean, obj);
                    }
                    return;
                } catch (SecurityException ignored) {
                } catch (NoSuchMethodException ignored) {
                }

                // check for object
                try {
                    method = bean.getClass().getMethod(name, Object.class);
                    if (method != null) {
                        method.invoke(bean, value.get(0));
                    }
                } catch (SecurityException ignored) {
                } catch (NoSuchMethodException ignored) {
                }
            } catch (IllegalArgumentException ignored) {
            } catch (IllegalAccessException ignored) {
            } catch (InvocationTargetException ignored) {
            }
        }
    }

    /**
     * @param contentType string
     * @return string
     */
    private String _getBoundryStart(String contentType) {
        return Utils._BOUNDARY_PREFIX
                + contentType.substring(contentType.indexOf(Utils._BOUNDARY) + Utils._BOUNDARY.length());
    }

    /**
     * @param temp string
     * @return string
     */
    private String _getName(String temp) {
        temp = temp.substring(temp.indexOf(Utils._NAME) + Utils._NAME.length() + 1);
        return temp.substring(0, temp.indexOf(Utils._DOUBLE_QUOTE));
    }

    /**
     * @param temp string
     * @return string
     */
    private String _getFileName(String temp) {
        temp = temp.substring(temp.indexOf(Utils._FILENAME) + Utils._FILENAME.length() + 1);
        return temp.substring(0, temp.indexOf(Utils._DOUBLE_QUOTE));
    }

    /**
     * @author Yugandhar Gangu
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
