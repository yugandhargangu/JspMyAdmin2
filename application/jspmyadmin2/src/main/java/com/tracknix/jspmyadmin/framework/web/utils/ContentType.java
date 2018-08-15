package com.tracknix.jspmyadmin.framework.web.utils;

/**
 * @author Yugandhar Gangu
 *
 */
public enum ContentType {
	TEXT_PLAIN("text/plain"), APPLICATION_JSON("application/json"), TEXT_HTML("text/html");

	private final String type;

	/**
	 * Constructor
	 * 
	 * @param type
	 *            String
	 */
	private ContentType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}
