/**
 * 
 */
package com.jspmyadmin.app.database.trigger.beans;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/28
 *
 */
public class TriggerInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String trigger_name = null;
	private String event_type = null;
	private String table_name = null;
	private String event_time = null;
	private String definer = null;

	/**
	 * @return the trigger_name
	 */
	public String getTrigger_name() {
		return trigger_name;
	}

	/**
	 * @param trigger_name
	 *            the trigger_name to set
	 */
	public void setTrigger_name(String trigger_name) {
		this.trigger_name = trigger_name;
	}

	/**
	 * @return the event_type
	 */
	public String getEvent_type() {
		return event_type;
	}

	/**
	 * @param event_type
	 *            the event_type to set
	 */
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	/**
	 * @return the table_name
	 */
	public String getTable_name() {
		return table_name;
	}

	/**
	 * @param table_name
	 *            the table_name to set
	 */
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	/**
	 * @return the event_time
	 */
	public String getEvent_time() {
		return event_time;
	}

	/**
	 * @param event_time
	 *            the event_time to set
	 */
	public void setEvent_time(String event_time) {
		this.event_time = event_time;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
