/**
 * 
 */
package com.jspmyadmin.framework.web.logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;

import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.DefaultServlet;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/29
 *
 */
public class EncDecLogic {

	private static final char[] _ALPHA_DIGITS = "ABCDEFGHIJKLMNOPQRASTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
			.toCharArray();
	private static final char[] _ALPHABETS = "ABCDEFGHIJKLMNOPQRASTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	private static final char[] _ENCODE_CHARS = "ABCDEF".toCharArray();

	private static final int _KEY_LENGTH = 16;
	private static final int _TOKEN_LENGTH = 20;

	private static final String _ALGO = "AES";
	private static final byte[] _ENCRYPTION_KEY = new byte[16];
	private static final char _ENCODE_CHAR;

	static {
		Random random = new Random();
		_ENCODE_CHAR = _ENCODE_CHARS[random.nextInt(_ENCODE_CHARS.length)];
		for (int i = 0; i < _KEY_LENGTH; i++) {
			_ENCRYPTION_KEY[i] = (byte) _ALPHABETS[random.nextInt(_ALPHABETS.length)];
		}
		random = null;
	}

	/**
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String data) throws Exception {
		HttpSession httpSession = DefaultServlet.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
		Base64 base64 = new Base64();
		String key = httpSession.getAttribute(FrameworkConstants.SESSION_KEY).toString();
		byte[] byteKey = base64.decode(key);
		byte[] byteData = data.getBytes(FrameworkConstants.ENCODE_UTF8);
		byte[] encData = _encrypt(byteData, byteKey);
		data = base64.encodeAsString(encData);
		base64 = null;
		return data;
	}

	/**
	 * 
	 * @param encryptedData
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String encryptedData) throws Exception {
		HttpSession httpSession = DefaultServlet.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
		Base64 base64 = new Base64();
		String key = httpSession.getAttribute(FrameworkConstants.SESSION_KEY).toString();
		byte[] byteKey = base64.decode(key);
		byte[] byteData = base64.decode(encryptedData);
		byteData = _decrypt(byteData, byteKey);
		base64 = null;
		return new String(byteData, FrameworkConstants.ENCODE_UTF8);
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String generateToken(HttpServletRequest request) throws Exception {

		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < _TOKEN_LENGTH; i++) {
			builder.append(_ALPHA_DIGITS[random.nextInt(_ALPHA_DIGITS.length)]);
		}
		random = null;
		HttpSession session = request.getSession();
		session.setAttribute(FrameworkConstants.SESSION_TOKEN, builder.toString());
		return encode(builder.toString());
	}

	/**
	 * 
	 * @param request
	 * @param token
	 * @return
	 */
	public boolean isValidToken(HttpServletRequest request, String token) {

		HttpSession session = request.getSession();
		Object temp = session.getAttribute(FrameworkConstants.SESSION_TOKEN);
		if (temp == null) {
			return false;
		}
		try {
			token = decode(token);
			if (!token.equals(temp)) {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			temp = null;
		}
		return true;
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	public boolean isValidToken(String token) {
		HttpSession session = DefaultServlet.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
		if (session != null) {
			Object key = session.getAttribute(FrameworkConstants.SESSION_TOKEN);
			try {
				if (key != null && key.equals(decode(token))) {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param session
	 * @throws EncoderException
	 */
	public void generateKey(HttpSession session) {
		try {
			StringBuilder builder = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < _KEY_LENGTH; i++) {
				builder.append(_ALPHABETS[random.nextInt(_ALPHABETS.length)]);
			}
			random = null;
			Base64 base64 = new Base64();
			session.setAttribute(FrameworkConstants.SESSION_KEY, base64.encodeAsString(builder.toString().getBytes()));
			base64 = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String encode(String data) throws Exception {
		byte[] text = data.getBytes(FrameworkConstants.ENCODE_UTF8);
		text = _encrypt(text, _ENCRYPTION_KEY);
		text = _encode(text);
		return new String(text, FrameworkConstants.ENCODE_UTF8);
	}

	/**
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String decode(String data) throws Exception {
		byte[] text = data.getBytes(FrameworkConstants.ENCODE_UTF8);
		text = _decode(text);
		text = _decrypt(text, _ENCRYPTION_KEY);
		return new String(text, FrameworkConstants.ENCODE_UTF8);
	}

	/**
	 * 
	 * @param data
	 * @param encKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private byte[] _encrypt(byte[] data, byte[] encKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Key key = new SecretKeySpec(encKey, _ALGO);
		Cipher c = Cipher.getInstance(_ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(data);
		return encVal;
	}

	/**
	 * 
	 * @param encryptedData
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private byte[] _decrypt(byte[] data, byte[] encKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Key key = new SecretKeySpec(encKey, _ALGO);
		Cipher c = Cipher.getInstance(_ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decValue = c.doFinal(data);
		return decValue;
	}

	/**
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	private byte[] _encode(byte[] data) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		for (byte b : data) {
			output.write((b & 0x0F) + _ENCODE_CHAR);
			output.write((((b >>> 4) & 0x0F) + _ENCODE_CHAR));
		}
		byte[] text = output.toByteArray();
		output.close();
		return text;
	}

	/**
	 * 
	 * @param encodedData
	 * @return
	 */
	private byte[] _decode(byte[] encodedData) {
		int l = encodedData.length / 2;
		byte[] result = new byte[l];
		for (int i = 0; i < l; i++)
			result[i] = (byte) (encodedData[i * 2] - _ENCODE_CHAR + ((encodedData[i * 2 + 1] - _ENCODE_CHAR) << 4));
		return result;
	}

}
