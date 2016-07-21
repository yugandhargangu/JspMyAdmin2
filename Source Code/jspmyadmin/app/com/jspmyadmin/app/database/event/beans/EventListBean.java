/**
 * 
 */
package com.jspmyadmin.app.database.event.beans;

import java.util.List;

import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/16
 *
 */
public class EventListBean extends Bean {

	private static final long serialVersionUID = 1L;

	private List<EventInfo> event_list = null;
	private String total = null;
	private String[] events = null;
	private String new_event = null;

	/**
	 * @return the event_list
	 */
	public List<EventInfo> getEvent_list() {
		return event_list;
	}

	/**
	 * @param event_list
	 *            the event_list to set
	 */
	public void setEvent_list(List<EventInfo> event_list) {
		this.event_list = event_list;
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
	 * @return the events
	 */
	public String[] getEvents() {
		return events;
	}

	/**
	 * @param events
	 *            the events to set
	 */
	public void setEvents(String[] events) {
		this.events = events;
	}

	/**
	 * @return the new_event
	 */
	public String getNew_event() {
		return new_event;
	}

	/**
	 * @param new_event
	 *            the new_event to set
	 */
	public void setNew_event(String new_event) {
		this.new_event = new_event;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
