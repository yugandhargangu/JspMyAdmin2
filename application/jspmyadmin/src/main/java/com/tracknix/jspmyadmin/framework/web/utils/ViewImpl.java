package com.tracknix.jspmyadmin.framework.web.utils;

import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yugandhar Gangu
 */
public class ViewImpl implements View {

    private final HttpServletResponse _response;
    private ViewType type = ViewType.DEFAULT;
    private String path = null;
    private String token = null;
    private final HttpServletRequest _request;

    ViewImpl(HttpServletRequest request, HttpServletResponse response) {
        _request = request;
        _response = response;
    }

    /**
     * @return the type
     */
    public ViewType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ViewType type) {
        this.type = type;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    @SuppressWarnings("unchecked")
    public void addAttribute(String key, Object value) {
        HttpSession httpSession = _request.getSession();
        if (httpSession != null) {
            Object temp = httpSession.getAttribute(Constants.SESSION_FLASH_MAP);
            Map<String, Object> flashMap;
            if (temp == null) {
                flashMap = new HashMap<String, Object>();
            } else {
                flashMap = (Map<String, Object>) temp;
            }
            flashMap.put(key, value);
        }
    }

    public void handleDefault() {
        type = ViewType.REDIRECT;
        path = AppConstants.PATH_HOME;
    }

    public void handleDownload(File file, boolean deleteAfterDownload, String filename) throws IOException {
        _response.setContentType("APPLICATION/OCTET-STREAM");
        _response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            outputStream = _response.getOutputStream();
            byte[] buffer = new byte[2048];

            int ch;
            while ((ch = inputStream.read(buffer, 0, buffer.length)) > 0) {
                outputStream.write(buffer, 0, ch);
                outputStream.flush();
            }
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (deleteAfterDownload) {
                file.delete();
            }
        }
    }
}
