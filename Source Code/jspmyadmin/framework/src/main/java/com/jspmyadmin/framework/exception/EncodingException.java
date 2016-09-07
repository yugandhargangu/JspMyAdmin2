/**
 * 
 */
package com.jspmyadmin.framework.exception;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/08/31
 *
 */
public class EncodingException extends Exception {

	private static final long serialVersionUID = 3952529595092203014L;

	public EncodingException() {
		super();
	}

	public EncodingException(String message) {
		super(message);
	}

	public EncodingException(String message, Throwable cause) {
		super(message, cause);
	}

	public EncodingException(Throwable cause) {
		super(cause);
	}

}
