package com.tracknix.jspmyadmin.framework.web.logic;

import com.tracknix.jspmyadmin.framework.exception.EncodingException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Yugandhar Gangu
 */
public interface EncodeHelper {

    /**
     * @param data string
     * @return string
     * @throws EncodingException e
     */
    String encrypt(String data) throws EncodingException;

    /**
     * @param encryptedData string
     * @return string
     * @throws EncodingException e
     */
    String decrypt(String encryptedData) throws EncodingException;

    /**
     * @return string
     */
    String generateToken();

    /**
     * @param session {@link HttpSession}
     */
    void generateKey(HttpSession session);

    /**
     * @param data string
     * @return string
     * @throws EncodingException e
     */
    String encode(String data) throws EncodingException;

    /**
     * @param data string
     * @return string
     * @throws EncodingException e
     */
    String decode(String data) throws EncodingException;

    /**
     * @param data string
     * @return string
     * @throws IOException e
     */
    String encodeInstall(String data) throws IOException;

    /**
     * @param data string
     * @return string
     * @throws EncodingException            e
     * @throws UnsupportedEncodingException e
     */
    String decodeInstall(String data) throws EncodingException, UnsupportedEncodingException;
}
