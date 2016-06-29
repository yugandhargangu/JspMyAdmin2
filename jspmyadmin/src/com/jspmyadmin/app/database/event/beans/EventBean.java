/**
 * 
 */
package com.jspmyadmin.app.database.event.beans;

import java.util.ArrayList;
import java.util.List;

import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/16
 *
 */
public class EventBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String definer = null;
	private String definer_name = null;
	private String not_exists = null;
	private String event_name = null;
	private String schedule_type = null;
	private String interval_quantity = null;
	private String interval = null;
	private String start_date_type = null;
	private String start_date = null;
	private String[] start_date_interval_quantity = null;
	private String[] start_date_interval = null;
	private String end_date_type = null;
	private String end_date = null;
	private String[] end_date_interval_quantity = null;
	private String[] end_date_interval = null;
	private String preserve = null;
	private String status = null;
	private String comment = null;
	private String body = null;

	private String start_interval = null;
	private String end_interval = null;
	private String action = null;

	private List<String> interval_list = null;
	private List<String> definer_list = null;

	@Override
	public void init() {
		interval_list = new ArrayList<String>(15);
		interval_list.add("DAY");
		interval_list.add("DAY_HOUR");
		interval_list.add("DAY_MINUTE");
		interval_list.add("DAY_SECOND");
		interval_list.add("HOUR");
		interval_list.add("HOUR_MINUTE");
		interval_list.add("HOUR_SECOND");
		interval_list.add("MINUTE");
		interval_list.add("MINUTE_SECOND");
		interval_list.add("MONTH");
		interval_list.add("QUARTER");
		interval_list.add("SECOND");
		interval_list.add("WEEK");
		interval_list.add("YEAR ");
		interval_list.add("YEAR_MONTH");

		definer_list = new ArrayList<String>(2);
		definer_list.add(FrameworkConstants.CURRENT_USER);
		definer_list.add("OTHER");
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
	 * @return the not_exists
	 */
	public String getNot_exists() {
		return not_exists;
	}

	/**
	 * @param not_exists
	 *            the not_exists to set
	 */
	public void setNot_exists(String not_exists) {
		this.not_exists = not_exists;
	}

	/**
	 * @return the event_name
	 */
	public String getEvent_name() {
		return event_name;
	}

	/**
	 * @param event_name
	 *            the event_name to set
	 */
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	/**
	 * @return the schedule_type
	 */
	public String getSchedule_type() {
		return schedule_type;
	}

	/**
	 * @param schedule_type
	 *            the schedule_type to set
	 */
	public void setSchedule_type(String schedule_type) {
		this.schedule_type = schedule_type;
	}

	/**
	 * @return the interval_quantity
	 */
	public String getInterval_quantity() {
		return interval_quantity;
	}

	/**
	 * @param interval_quantity
	 *            the interval_quantity to set
	 */
	public void setInterval_quantity(String interval_quantity) {
		this.interval_quantity = interval_quantity;
	}

	/**
	 * @return the interval
	 */
	public String getInterval() {
		return interval;
	}

	/**
	 * @param interval
	 *            the interval to set
	 */
	public void setInterval(String interval) {
		this.interval = interval;
	}

	/**
	 * @return the start_date_type
	 */
	public String getStart_date_type() {
		return start_date_type;
	}

	/**
	 * @param start_date_type
	 *            the start_date_type to set
	 */
	public void setStart_date_type(String start_date_type) {
		this.start_date_type = start_date_type;
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
	 * @return the start_date_interval_quantity
	 */
	public String[] getStart_date_interval_quantity() {
		return start_date_interval_quantity;
	}

	/**
	 * @param start_date_interval_quantity
	 *            the start_date_interval_quantity to set
	 */
	public void setStart_date_interval_quantity(String[] start_date_interval_quantity) {
		this.start_date_interval_quantity = start_date_interval_quantity;
	}

	/**
	 * @return the start_date_interval
	 */
	public String[] getStart_date_interval() {
		return start_date_interval;
	}

	/**
	 * @param start_date_interval
	 *            the start_date_interval to set
	 */
	public void setStart_date_interval(String[] start_date_interval) {
		this.start_date_interval = start_date_interval;
	}

	/**
	 * @return the end_date_type
	 */
	public String getEnd_date_type() {
		return end_date_type;
	}

	/**
	 * @param end_date_type
	 *            the end_date_type to set
	 */
	public void setEnd_date_type(String end_date_type) {
		this.end_date_type = end_date_type;
	}

	/**
	 * @return the end_date
	 */
	public String getEnd_date() {
		return end_date;
	}

	/**
	 * @param end_date
	 *            the end_date to set
	 */
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	/**
	 * @return the end_date_interval_quantity
	 */
	public String[] getEnd_date_interval_quantity() {
		return end_date_interval_quantity;
	}

	/**
	 * @param end_date_interval_quantity
	 *            the end_date_interval_quantity to set
	 */
	public void setEnd_date_interval_quantity(String[] end_date_interval_quantity) {
		this.end_date_interval_quantity = end_date_interval_quantity;
	}

	/**
	 * @return the preserve
	 */
	public String getPreserve() {
		return preserve;
	}

	/**
	 * @param preserve
	 *            the preserve to set
	 */
	public void setPreserve(String preserve) {
		this.preserve = preserve;
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
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the end_date_interval
	 */
	public String[] getEnd_date_interval() {
		return end_date_interval;
	}

	/**
	 * @param end_date_interval
	 *            the end_date_interval to set
	 */
	public void setEnd_date_interval(String[] end_date_interval) {
		this.end_date_interval = end_date_interval;
	}

	/**
	 * @return the start_interval
	 */
	public String getStart_interval() {
		return start_interval;
	}

	/**
	 * @param start_interval
	 *            the start_interval to set
	 */
	public void setStart_interval(String start_interval) {
		this.start_interval = start_interval;
	}

	/**
	 * @return the end_interval
	 */
	public String getEnd_interval() {
		return end_interval;
	}

	/**
	 * @param end_interval
	 *            the end_interval to set
	 */
	public void setEnd_interval(String end_interval) {
		this.end_interval = end_interval;
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
	 * @return the interval_list
	 */
	public List<String> getInterval_list() {
		return interval_list;
	}

	/**
	 * @param interval_list
	 *            the interval_list to set
	 */
	public void setInterval_list(List<String> interval_list) {
		this.interval_list = interval_list;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
