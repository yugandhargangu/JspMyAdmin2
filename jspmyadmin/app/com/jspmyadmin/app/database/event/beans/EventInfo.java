/**
 * 
 */
package com.jspmyadmin.app.database.event.beans;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/16
 *
 */
public class EventInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name = null;
	private String definer = null;
	private String type = null;
	private String status = null;
	private String create_date = null;
	private String alter_date = null;
	private String comments = null;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the definer
	 */
	public String getDefiner() {
		return definer;
	}

	/**
	 * @param definer
	 *            the definer to set
	 */
	public void setDefiner(String definer) {
		this.definer = definer;
	}

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

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the create_date
	 */
	public String getCreate_date() {
		return create_date;
	}

	/**
	 * @param create_date
	 *            the create_date to set
	 */
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	/**
	 * @return the alter_date
	 */
	public String getAlter_date() {
		return alter_date;
	}

	/**
	 * @param alter_date
	 *            the alter_date to set
	 */
	public void setAlter_date(String alter_date) {
		this.alter_date = alter_date;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
