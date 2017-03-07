package com.tracknix.jspmyadmin.framework.web.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 */
public interface FileInput extends Serializable {

    /**
     * @return name
     */
    String getFileName();

    /**
     * @return size
     */
    long getFileSize();

    /**
     * @param path path
     * @throws IOException e
     */
    void copyTo(String path) throws IOException;

    /**
     * @return InputStream
     * @throws IOException e
     */
    InputStream getInputStream() throws IOException;
}
