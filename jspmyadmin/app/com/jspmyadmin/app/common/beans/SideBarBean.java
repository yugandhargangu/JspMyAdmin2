/**
 * 
 */
package com.jspmyadmin.app.common.beans;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 *
 */
public class SideBarBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String type = null;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
