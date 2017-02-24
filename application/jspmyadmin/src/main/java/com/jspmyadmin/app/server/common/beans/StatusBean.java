/**
 * 
 */
package com.jspmyadmin.app.server.common.beans;

import java.util.List;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/12
 *
 */
public class StatusBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String received = null;
	private String sent = null;
	private String total = null;
	private String run_time = null;
	private String start_date = null;
	private String[] columnInfo = null;
	private List<String[]> data_list = null;

	/**
	 * @return the received
	 */
	public String getReceived() {
		return received;
	}

	/**
	 * @param received
	 *            the received to set
	 */
	public void setReceived(String received) {
		this.received = received;
	}

	/**
	 * @return the sent
	 */
	public String getSent() {
		return sent;
	}

	/**
	 * @param sent
	 *            the sent to set
	 */
	public void setSent(String sent) {
		this.sent = sent;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the run_time
	 */
	public String getRun_time() {
		return run_time;
	}

	/**
	 * @param run_time
	 *            the run_time to set
	 */
	public void setRun_time(String run_time) {
		this.run_time = run_time;
	}

	/**
	 * @return the start_date
	 */
	public String getStart_date() {
		return start_date;
	}

	/**
	 * @param start_date
	 *            the start_date to set
	 */
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	/**
	 * @return the columnInfo
	 */
	public String[] getColumnInfo() {
		return columnInfo;
	}

	/**
	 * @param columnInfo
	 *            the columnInfo to set
	 */
	public void setColumnInfo(String[] columnInfo) {
		this.columnInfo = columnInfo;
	}

	/**
	 * @return the data_list
	 */
	public List<String[]> getData_list() {
		return data_list;
	}

	/**
	 * @param data_list
	 *            the data_list to set
	 */
	public void setData_list(List<String[]> data_list) {
		this.data_list = data_list;
	}

}
