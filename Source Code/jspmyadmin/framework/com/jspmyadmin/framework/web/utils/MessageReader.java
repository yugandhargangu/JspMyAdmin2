/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/27
 *
 */
public class MessageReader implements Messages{

	private static String _properties = ".properties";
	private static String _equals = "=";
	private static final String _DEFAULT = "default";
	private static final String _ENCODE = "UTF-8";
	private static final Map<String, Map<String, String>> _MESSAGEMAP = new ConcurrentHashMap<String, Map<String, String>>();

	/**
	 * @throws IOException
	 * 
	 */
	public static synchronized void read() throws IOException {
		if (_MESSAGEMAP.size() > 0) {
			return;
		}
		String dot = "\\.";
		String _ = "_";
		String name = null;
		URL location = null;
		File directory = null;
		File[] files = null;
		FilenameFilter filenameFilter = null;
		URI uri = null;
		try {
			location = MessageReader.class.getClassLoader().getResource("");
			uri = new URI(location.getPath() + "com/jspmyadmin/messages");
			directory = new File(uri.getPath());
			filenameFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(_properties);
				}
			};
			files = directory.listFiles(filenameFilter);
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					name = files[i].getName().split(dot)[0];
					if (name.contains(_)) {
						name = files[i].getName().split(dot)[0].split(_)[1];
						_readFile(files[i], name);
					} else {
						_readFile(files[i], _DEFAULT);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dot = null;
			name = null;
			files = null;
			directory = null;
			location = null;
			filenameFilter = null;
			_properties = null;
			_equals = null;
		}
	}

	/**
	 * 
	 * @param file
	 * @param locale
	 * @throws IOException
	 */
	private static void _readFile(File file, String locale) throws IOException {

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		String line = null;
		String[] data = null;
		Map<String, String> messageMap = null;
		try {
			inputStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(inputStream, _ENCODE);
			bufferedReader = new BufferedReader(inputStreamReader);
			messageMap = new ConcurrentHashMap<String, String>();
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains(_equals)) {
					data = line.split(_equals);
					messageMap.put(data[0].trim(), data[1].trim());
				}
			}
			_MESSAGEMAP.put(locale, messageMap);
		} finally {
			data = null;
			line = null;
			if (bufferedReader != null) {
				bufferedReader.close();
				bufferedReader = null;
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
				inputStreamReader = null;
			}
			if (inputStream != null) {
				inputStream.close();
				inputStream = null;
			}
			file = null;
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public static synchronized void remove() throws IOException {
		for (String key : _MESSAGEMAP.keySet()) {
			_MESSAGEMAP.get(key).clear();
		}
		_MESSAGEMAP.clear();
	}

	private final Map<String, String> _messageMap;

	/**
	 * 
	 * @param locale
	 */
	public MessageReader(String locale) {
		if (locale != null && _MESSAGEMAP.containsKey(locale)) {
			_messageMap = _MESSAGEMAP.get(locale);
		} else {
			_messageMap = _MESSAGEMAP.get(_DEFAULT);
		}
		locale = null;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getMessage(String key) {
		return _messageMap.get(key);
	}

}
