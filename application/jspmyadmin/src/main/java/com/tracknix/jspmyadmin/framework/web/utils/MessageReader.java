package com.tracknix.jspmyadmin.framework.web.utils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yugandhar Gangu
 */
public class MessageReader implements Messages {

    private static String _properties = ".properties";
    private static final String _DEFAULT = "default";
    private static final String _ENCODE = "UTF-8";
    private static final Map<String, Map<String, String>> _MESSAGEMAP = new ConcurrentHashMap<String, Map<String, String>>();

    /**
     *
     */
    static synchronized void read() {
        if (_MESSAGEMAP.size() > 0) {
            return;
        }
        String dot = "\\.";
        String underscore = "_";
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL location = classLoader.getResource("com/tracknix/jspmyadmin/messages");
            if (location != null) {
                URI uri = new URI(location.getPath());
                File directory = new File(uri.getPath());
                FilenameFilter filenameFilter = new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return name.endsWith(_properties);
                    }
                };
                File[] files = directory.listFiles(filenameFilter);
                if (files != null) {
                    for (File file : files) {
                        String name = file.getName().split(dot)[0];
                        if (name.contains(underscore)) {
                            name = file.getName().split(dot)[0].split(underscore)[1];
                            _readFile(file, name);
                        } else {
                            _readFile(file, _DEFAULT);
                        }
                    }
                }
            }
        } catch (URISyntaxException ignored) {
        } catch (IOException ignored) {
        }
    }

    /**
     * @param file   File
     * @param locale String
     * @throws IOException e
     */
    private static void _readFile(File file, String locale) throws IOException {

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String line;
        String[] data;
        Map<String, String> messageMap;
        try {
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream, _ENCODE);
            bufferedReader = new BufferedReader(inputStreamReader);
            messageMap = new ConcurrentHashMap<String, String>();
            while ((line = bufferedReader.readLine()) != null) {
                String _equals = "=";
                if (line.contains(_equals)) {
                    data = line.split(_equals);
                    messageMap.put(data[0].trim(), data[1].trim());
                }
            }
            _MESSAGEMAP.put(locale, messageMap);
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * @throws IOException e
     */
    public static synchronized void remove() throws IOException {
        for (String key : _MESSAGEMAP.keySet()) {
            _MESSAGEMAP.get(key).clear();
        }
        _MESSAGEMAP.clear();
    }

    private final Map<String, String> _messageMap;

    /**
     * @param locale String
     */
    public MessageReader(String locale) {
        if (locale != null && _MESSAGEMAP.containsKey(locale)) {
            _messageMap = _MESSAGEMAP.get(locale);
        } else {
            _messageMap = _MESSAGEMAP.get(_DEFAULT);
        }
    }

    /**
     * @param key String
     * @return String
     */
    public String getMessage(String key) {
        return _messageMap.get(key);
    }

}
