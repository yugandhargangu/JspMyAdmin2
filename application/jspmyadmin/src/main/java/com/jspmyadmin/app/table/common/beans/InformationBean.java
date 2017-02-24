/**
 * 
 */
package com.jspmyadmin.app.table.common.beans;

import java.util.Map;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/06
 *
 */
public class InformationBean extends Bean {

	private static final long serialVersionUID = 1L;

	private Map<String, String> info_map = null;
	private String create_syn = null;
	private String insert_syn = null;
	private String update_syn = null;
	private String delete_syn = null;

	/**
	 * @return the info_map
	 */
	public Map<String, String> getInfo_map() {
		return info_map;
	}

	/**
	 * @param info_map
	 *            the info_map to set
	 */
	public void setInfo_map(Map<String, String> info_map) {
		this.info_map = info_map;
	}

	/**
	 * @return the create_syn
	 */
	public String getCreate_syn() {
		return create_syn;
	}

	/**
	 * @param create_syn
	 *            the create_syn to set
	 */
	public void setCreate_syn(String create_syn) {
		this.create_syn = create_syn;
	}

	/**
	 * @return the insert_syn
	 */
	public String getInsert_syn() {
		return insert_syn;
	}

	/**
	 * @param insert_syn
	 *            the insert_syn to set
	 */
	public void setInsert_syn(String insert_syn) {
		this.insert_syn = insert_syn;
	}

	/**
	 * @return the update_syn
	 */
	public String getUpdate_syn() {
		return update_syn;
	}

	/**
	 * @param update_syn
	 *            the update_syn to set
	 */
	public void setUpdate_syn(String update_syn) {
		this.update_syn = update_syn;
	}

	/**
	 * @return the delete_syn
	 */
	public String getDelete_syn() {
		return delete_syn;
	}

	/**
	 * @param delete_syn
	 *            the delete_syn to set
	 */
	public void setDelete_syn(String delete_syn) {
		this.delete_syn = delete_syn;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
