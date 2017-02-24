/**
 * 
 */
package com.jspmyadmin.app.database.trigger.beans;

import java.util.ArrayList;
import java.util.List;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/28
 *
 */
public class TriggerBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String trigger_name = null;
	private String definer = null;
	private String definer_name = null;
	private String trigger_time = null;
	private String trigger_event = null;
	private String database_name = null;
	private String table_name = null;
	private String trigger_order = null;
	private String other_trigger_name = null;
	private String trigger_body = null;
	private String action = null;

	private List<String> definer_list = new ArrayList<String>(Constants.Utils.DEFINER_LIST);
	private List<String> trigger_time_list = new ArrayList<String>(Constants.Utils.TRIGGER_TIME_LIST);
	private List<String> trigger_event_list = new ArrayList<String>(Constants.Utils.TRIGGER_EVENT_LIST);
	private List<String> trigger_order_list = new ArrayList<String>(Constants.Utils.TRIGGER_ORDER_LIST);
	private List<String> database_name_list = null;
	private List<String> other_trigger_name_list = null;

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
	 * @return the definer_name
	 */
	public String getDefiner_name() {
		return definer_name;
	}

	/**
	 * @param definer_name
	 *            the definer_name to set
	 */
	public void setDefiner_name(String definer_name) {
		this.definer_name = definer_name;
	}

	/**
	 * @return the trigger_time
	 */
	public String getTrigger_time() {
		return trigger_time;
	}

	/**
	 * @param trigger_time
	 *            the trigger_time to set
	 */
	public void setTrigger_time(String trigger_time) {
		this.trigger_time = trigger_time;
	}

	/**
	 * @return the trigger_event
	 */
	public String getTrigger_event() {
		return trigger_event;
	}

	/**
	 * @param trigger_event
	 *            the trigger_event to set
	 */
	public void setTrigger_event(String trigger_event) {
		this.trigger_event = trigger_event;
	}

	/**
	 * @return the database_name
	 */
	public String getDatabase_name() {
		return database_name;
	}

	/**
	 * @param database_name
	 *            the database_name to set
	 */
	public void setDatabase_name(String database_name) {
		this.database_name = database_name;
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
	 * @return the trigger_order
	 */
	public String getTrigger_order() {
		return trigger_order;
	}

	/**
	 * @param trigger_order
	 *            the trigger_order to set
	 */
	public void setTrigger_order(String trigger_order) {
		this.trigger_order = trigger_order;
	}

	/**
	 * @return the trigger_body
	 */
	public String getTrigger_body() {
		return trigger_body;
	}

	/**
	 * @param trigger_body
	 *            the trigger_body to set
	 */
	public void setTrigger_body(String trigger_body) {
		this.trigger_body = trigger_body;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the definer_list
	 */
	public List<String> getDefiner_list() {
		return definer_list;
	}

	/**
	 * @param definer_list
	 *            the definer_list to set
	 */
	public void setDefiner_list(List<String> definer_list) {
		this.definer_list = definer_list;
	}

	/**
	 * @return the trigger_time_list
	 */
	public List<String> getTrigger_time_list() {
		return trigger_time_list;
	}

	/**
	 * @param trigger_time_list
	 *            the trigger_time_list to set
	 */
	public void setTrigger_time_list(List<String> trigger_time_list) {
		this.trigger_time_list = trigger_time_list;
	}

	/**
	 * @return the trigger_event_list
	 */
	public List<String> getTrigger_event_list() {
		return trigger_event_list;
	}

	/**
	 * @param trigger_event_list
	 *            the trigger_event_list to set
	 */
	public void setTrigger_event_list(List<String> trigger_event_list) {
		this.trigger_event_list = trigger_event_list;
	}

	/**
	 * @return the database_name_list
	 */
	public List<String> getDatabase_name_list() {
		return database_name_list;
	}

	/**
	 * @param database_name_list
	 *            the database_name_list to set
	 */
	public void setDatabase_name_list(List<String> database_name_list) {
		this.database_name_list = database_name_list;
	}

	/**
	 * @return the trigger_order_list
	 */
	public List<String> getTrigger_order_list() {
		return trigger_order_list;
	}

	/**
	 * @param trigger_order_list
	 *            the trigger_order_list to set
	 */
	public void setTrigger_order_list(List<String> trigger_order_list) {
		this.trigger_order_list = trigger_order_list;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the other_trigger_name
	 */
	public String getOther_trigger_name() {
		return other_trigger_name;
	}

	/**
	 * @param other_trigger_name
	 *            the other_trigger_name to set
	 */
	public void setOther_trigger_name(String other_trigger_name) {
		this.other_trigger_name = other_trigger_name;
	}

	/**
	 * @return the other_trigger_name_list
	 */
	public List<String> getOther_trigger_name_list() {
		return other_trigger_name_list;
	}

	/**
	 * @param other_trigger_name_list
	 *            the other_trigger_name_list to set
	 */
	public void setOther_trigger_name_list(List<String> other_trigger_name_list) {
		this.other_trigger_name_list = other_trigger_name_list;
	}

}
