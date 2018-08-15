package com.tracknix.jspmyadmin.framework.web.utils;

import java.io.File;
import java.io.IOException;

/**
 * @author Yugandhar Gangu
 */
public interface View {

    /**
     * @param type ViewType
     */
    public void setType(ViewType type);

    /**
     * @param path String
     */
    public void setPath(String path);

    /**
     * @param token String
     */
    public void setToken(String token);

    /**
     * @param key   String
     * @param value Object
     */
    public void addAttribute(String key, Object value);

    /**
     * @param file                File
     * @param deleteAfterDownload boolean
     * @param filename            String
     * @throws IOException e
     */
    public void handleDownload(File file, boolean deleteAfterDownload, String filename) throws IOException;

    /**
     *
     */
    public void handleDefault();
}
