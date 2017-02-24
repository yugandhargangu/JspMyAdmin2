/**
 * 
 */
package com.jspmyadmin.app.database.event.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.database.event.beans.EventBean;
import com.jspmyadmin.app.database.event.beans.EventInfo;
import com.jspmyadmin.app.database.event.beans.EventListBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;
import com.jspmyadmin.framework.web.utils.Messages;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/03/16
 *
 */
public class EventLogic extends AbstractLogic {

	private Messages messages = null;

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setMessages(Messages messages) {
		this.messages = messages;
	}

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void fillListBean(Bean bean) throws SQLException {

		EventListBean eventListBean = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		StringBuilder builder = null;
		List<EventInfo> eventInfoList = null;
		EventInfo eventInfo = null;
		int total = 0;
		try {
			eventListBean = (EventListBean) bean;
			apiConnection = super.getConnection(bean.getRequest_db());
			builder = new StringBuilder();
			builder.append("SELECT event_name,definer,event_type,status,");
			builder.append("created,last_altered,event_comment FROM ");
			builder.append("information_schema.events WHERE event_schema = ? ");
			builder.append("ORDER BY event_name ASC");
			statement = apiConnection.getStmtSelect(builder.toString());
			statement.setString(1, bean.getRequest_db());
			resultSet = statement.executeQuery();
			eventInfoList = new ArrayList<EventInfo>();
			while (resultSet.next()) {
				eventInfo = new EventInfo();
				eventInfo.setName(resultSet.getString(1));
				eventInfo.setDefiner(resultSet.getString(2));
				eventInfo.setType(resultSet.getString(3));
				eventInfo.setStatus(resultSet.getString(4));
				eventInfo.setCreate_date(resultSet.getString(5));
				eventInfo.setAlter_date(resultSet.getString(6));
				eventInfo.setComments(resultSet.getString(7));
				eventInfoList.add(eventInfo);
				total++;
			}
			eventListBean.setEvent_list(eventInfoList);
			eventListBean.setTotal(Integer.toString(total));
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param bean
	 * @throws SQLException
	 */
	public void renameEvent(Bean bean) throws SQLException {

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;

		EventListBean eventListBean = null;
		StringBuilder builder = null;
		try {
			eventListBean = (EventListBean) bean;
			if (eventListBean.getEvents() != null && eventListBean.getEvents().length > 0) {
				apiConnection = getConnection(bean.getRequest_db());
				builder = new StringBuilder();
				builder.append("ALTER EVENT ");
				builder.append(Constants.SYMBOL_TEN);
				builder.append(eventListBean.getEvents()[0]);
				builder.append(Constants.SYMBOL_TEN);
				builder.append(" RENAME TO ");
				builder.append(Constants.SYMBOL_TEN);
				builder.append(eventListBean.getNew_event());
				builder.append(Constants.SYMBOL_TEN);
				statement = apiConnection.getStmt(builder.toString());
				statement.execute();
				apiConnection.commit();
			}
		} finally {
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void enableEvent(Bean bean) throws SQLException {

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;

		EventListBean eventListBean = null;
		StringBuilder builder = null;
		try {
			eventListBean = (EventListBean) bean;
			if (eventListBean.getEvents() != null) {
				String alter = "ALTER EVENT ";
				String enable = "ENABLE";
				apiConnection = getConnection(bean.getRequest_db());
				builder = new StringBuilder();
				for (int i = 0; i < eventListBean.getEvents().length; i++) {
					builder.append(alter);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(eventListBean.getEvents()[i]);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(Constants.SPACE);
					builder.append(enable);
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement = null;
					builder.delete(0, builder.length());
				}
				apiConnection.commit();
			}
		} finally {
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void dropEvent(Bean bean) throws SQLException {

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;

		EventListBean eventListBean = null;
		StringBuilder builder = null;
		try {
			eventListBean = (EventListBean) bean;
			if (eventListBean.getEvents() != null) {
				String alter = "DROP EVENT IF EXISTS";
				apiConnection = getConnection(bean.getRequest_db());
				builder = new StringBuilder();
				for (int i = 0; i < eventListBean.getEvents().length; i++) {
					builder.append(alter);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(eventListBean.getEvents()[i]);
					builder.append(Constants.SYMBOL_TEN);
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement = null;
					builder.delete(0, builder.length());
				}
				apiConnection.commit();
			}
		} finally {
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void disableEvent(Bean bean) throws SQLException {

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;

		EventListBean eventListBean = null;
		StringBuilder builder = null;
		try {
			eventListBean = (EventListBean) bean;
			if (eventListBean.getEvents() != null) {
				String alter = "ALTER EVENT ";
				String disable = "DISABLE";
				apiConnection = getConnection(bean.getRequest_db());
				builder = new StringBuilder();
				for (int i = 0; i < eventListBean.getEvents().length; i++) {
					builder.append(alter);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(eventListBean.getEvents()[i]);
					builder.append(Constants.SYMBOL_TEN);
					builder.append(Constants.SPACE);
					builder.append(disable);
					statement = apiConnection.getStmt(builder.toString());
					statement.execute();
					statement = null;
					builder.delete(0, builder.length());
				}
				apiConnection.commit();
			}
		} finally {
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws JSONException
	 */
	public String getShowCreate(Bean bean) throws SQLException, JSONException {

		String result = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		EventListBean eventListBean = null;
		JSONObject jsonObject = null;
		StringBuilder builder = null;
		try {
			eventListBean = (EventListBean) bean;
			if (eventListBean.getEvents() != null) {
				apiConnection = getConnection(bean.getRequest_db());
				String temp = "SHOW CREATE EVENT `";
				builder = new StringBuilder();
				jsonObject = new JSONObject();
				for (int i = 0; i < eventListBean.getEvents().length; i++) {
					builder.append(temp);
					builder.append(eventListBean.getEvents()[i]);
					builder.append(Constants.SYMBOL_TEN);
					statement = apiConnection.getStmtSelect(builder.toString());
					resultSet = statement.executeQuery();
					if (resultSet.next()) {
						jsonObject.put(resultSet.getString(1),
								resultSet.getString(4) + Constants.SYMBOL_SEMI_COLON);
					}
					resultSet.close();
					resultSet = null;
					statement.close();
					statement = null;
					builder.delete(0, builder.length());
				}
				result = jsonObject.toString();
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;
	}

	/**
	 * 
	 * @param name
	 * @param database
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean isExisted(String name, String database) throws ClassNotFoundException, SQLException {
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		StringBuilder builder = null;
		try {
			apiConnection = super.getConnection(database);
			builder = new StringBuilder();
			builder.append("SELECT COUNT(event_name) FROM ");
			builder.append("information_schema.events WHERE ");
			builder.append("event_schema = ? AND event_name = ?");
			statement = apiConnection.getStmtSelect(builder.toString());
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return false;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String saveEvent(Bean bean) throws SQLException {

		String result = null;
		ApiConnection apiConnection = null;
		PreparedStatement statement = null;

		EventBean eventBean = null;
		StringBuilder builder = null;
		String[] temp = null;
		String interval = "+ INTERVAL ";
		try {
			eventBean = (EventBean) bean;
			apiConnection = getConnection(bean.getRequest_db());
			builder = new StringBuilder();
			builder.append("CREATE ");
			if (!isEmpty(eventBean.getDefiner())) {
				if (Constants.CURRENT_USER.equalsIgnoreCase(eventBean.getDefiner())) {
					builder.append("DEFINER = ");
					builder.append(eventBean.getDefiner());
					builder.append(Constants.SPACE);
				} else if (!isEmpty(eventBean.getDefiner_name())) {
					temp = eventBean.getDefiner_name().split(Constants.SYMBOL_AT);
					builder.append("DEFINER = ");
					if (temp.length < 2) {
						builder.append(Constants.SYMBOL_TEN);
						builder.append(temp[0]);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SPACE);
					} else {
						builder.append(Constants.SYMBOL_TEN);
						builder.append(temp[0]);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(Constants.SYMBOL_AT);
						builder.append(Constants.SYMBOL_TEN);
						builder.append(temp[1]);
						if (!temp[1].endsWith(Constants.SYMBOL_TEN)) {
							builder.append(Constants.SYMBOL_TEN);
						}
						builder.append(Constants.SPACE);
					}
				}
			}
			builder.append("EVENT ");
			if (Constants.YES.equalsIgnoreCase(eventBean.getNot_exists())) {
				builder.append("IF NOT EXISTS ");
			}
			builder.append(Constants.SYMBOL_TEN);
			builder.append(eventBean.getEvent_name());
			builder.append(Constants.SYMBOL_TEN);
			builder.append(Constants.SPACE);
			builder.append("ON SCHEDULE ");
			if (Constants.AT.equalsIgnoreCase(eventBean.getSchedule_type())) {
				// one time
				builder.append(eventBean.getSchedule_type());
				builder.append(Constants.SPACE);
				if (Constants.CURRENT_TIMESTAMP.equalsIgnoreCase(eventBean.getStart_date_type())) {
					builder.append(Constants.CURRENT_TIMESTAMP);
				} else {
					builder.append(Constants.SYMBOL_QUOTE);
					builder.append(eventBean.getStart_date());
					builder.append(Constants.SYMBOL_QUOTE);
				}
				builder.append(Constants.SPACE);
				if (eventBean.getStart_date_interval_quantity() != null) {
					for (int i = 0; i < eventBean.getStart_date_interval_quantity().length; i++) {
						if (!isEmpty(eventBean.getStart_date_interval_quantity()[i])) {
							if (isInteger(eventBean.getStart_date_interval_quantity()[i])) {
								builder.append(interval);
								builder.append(eventBean.getStart_date_interval_quantity()[i]);
								builder.append(Constants.SPACE);
								builder.append(eventBean.getStart_date_interval()[i]);
							} else {
								builder.append(interval);
								builder.append(Constants.SYMBOL_QUOTE);
								builder.append(eventBean.getStart_date_interval_quantity()[i]);
								builder.append(Constants.SYMBOL_QUOTE);
								builder.append(Constants.SPACE);
								builder.append(eventBean.getStart_date_interval()[i]);
							}
						}
					}
					builder.append(Constants.SPACE);
				}
			} else if (Constants.EVERY.equalsIgnoreCase(eventBean.getSchedule_type())) {
				// Recursive
				builder.append(eventBean.getSchedule_type());
				builder.append(Constants.SPACE);
				if (isInteger(eventBean.getInterval_quantity())) {
					builder.append(eventBean.getInterval_quantity());
				} else {
					builder.append(Constants.SYMBOL_QUOTE);
					builder.append(eventBean.getInterval_quantity());
					builder.append(Constants.SYMBOL_QUOTE);
				}
				builder.append(Constants.SPACE);
				builder.append(eventBean.getInterval());
				builder.append(Constants.SPACE);
				if (!isEmpty(eventBean.getStart_date_type())) {
					boolean start = false;
					if (Constants.CURRENT_TIMESTAMP.equalsIgnoreCase(eventBean.getStart_date_type())) {
						builder.append("STARTS ");
						builder.append(Constants.CURRENT_TIMESTAMP);
						builder.append(Constants.SPACE);
						start = true;
					} else if (!isEmpty(eventBean.getStart_date())) {
						builder.append("STARTS ");
						builder.append(Constants.SYMBOL_QUOTE);
						builder.append(eventBean.getStart_date());
						builder.append(Constants.SYMBOL_QUOTE);
						builder.append(Constants.SPACE);
						start = true;
					}
					if (start && eventBean.getStart_date_interval_quantity() != null) {
						for (int i = 0; i < eventBean.getStart_date_interval_quantity().length; i++) {
							if (!isEmpty(eventBean.getStart_date_interval_quantity()[i])) {
								if (isInteger(eventBean.getStart_date_interval_quantity()[i])) {
									builder.append(interval);
									builder.append(eventBean.getStart_date_interval_quantity()[i]);
									builder.append(Constants.SPACE);
									builder.append(eventBean.getStart_date_interval()[i]);
								} else {
									builder.append(interval);
									builder.append(Constants.SYMBOL_QUOTE);
									builder.append(eventBean.getStart_date_interval_quantity()[i]);
									builder.append(Constants.SYMBOL_QUOTE);
									builder.append(Constants.SPACE);
									builder.append(eventBean.getStart_date_interval()[i]);
								}
							}
						}
					}
				}
				if (!isEmpty(eventBean.getEnd_date_type())) {
					boolean end = false;
					if (Constants.CURRENT_TIMESTAMP.equalsIgnoreCase(eventBean.getEnd_date_type())) {
						builder.append("ENDS ");
						builder.append(Constants.CURRENT_TIMESTAMP);
						builder.append(Constants.SPACE);
						end = true;
					} else if (!isEmpty(eventBean.getEnd_date())) {
						builder.append("ENDS ");
						builder.append(Constants.SYMBOL_QUOTE);
						builder.append(eventBean.getEnd_date());
						builder.append(Constants.SYMBOL_QUOTE);
						builder.append(Constants.SPACE);
						end = true;
					}
					if (end && eventBean.getEnd_date_interval_quantity() != null) {
						for (int i = 0; i < eventBean.getEnd_date_interval_quantity().length; i++) {
							if (!isEmpty(eventBean.getEnd_date_interval_quantity()[i])) {
								if (isInteger(eventBean.getEnd_date_interval_quantity()[i])) {
									builder.append(interval);
									builder.append(eventBean.getEnd_date_interval_quantity()[i]);
									builder.append(Constants.SPACE);
									builder.append(eventBean.getEnd_date_interval()[i]);
								} else {
									builder.append(interval);
									builder.append(Constants.SYMBOL_QUOTE);
									builder.append(eventBean.getEnd_date_interval_quantity()[i]);
									builder.append(Constants.SYMBOL_QUOTE);
									builder.append(Constants.SPACE);
									builder.append(eventBean.getEnd_date_interval()[i]);
								}
							}
						}
					}
				}
			}
			if (!isEmpty(eventBean.getPreserve())) {
				builder.append("ON COMPLETION ");
				builder.append(eventBean.getPreserve());
				builder.append(Constants.SPACE);
			}
			if (!isEmpty(eventBean.getStatus())) {
				builder.append(eventBean.getStatus());
				builder.append(Constants.SPACE);
			}
			if (!isEmpty(eventBean.getComment())) {
				builder.append(Constants.SYMBOL_QUOTE);
				builder.append(eventBean.getComment());
				builder.append(Constants.SYMBOL_QUOTE);
				builder.append(Constants.SPACE);
			}
			builder.append("DO ");
			builder.append(eventBean.getBody());
			if (Constants.YES.equalsIgnoreCase(eventBean.getAction())) {
				apiConnection = super.getConnection(bean.getRequest_db());
				statement = apiConnection.getStmt(builder.toString());
				statement.execute();
			} else {
				result = builder.toString();
			}
		} finally {
			close(statement);
			close(apiConnection);
		}
		return result;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	public String getStartInterval(List<String> list) {
		StringBuilder builder = new StringBuilder();
		builder.append("<div><div class=\"form-input\">");
		builder.append("<label>+ INTERVAL</label></div>");
		builder.append("<div class=\"form-input\"><label>");
		builder.append(messages.getMessage("lbl.quantity"));
		builder.append("</label> <input type=\"text\" ");
		builder.append("name=\"start_date_interval_quantity\" ");
		builder.append("class=\"form-control\"></div>");
		builder.append("<div class=\"form-input\"><label>");
		builder.append(messages.getMessage("lbl.interval"));
		builder.append("</label> <select name=\"start_date_interval\"");
		builder.append(" class=\"form-control\">");
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String temp = iterator.next();
			builder.append("<option value=\"");
			builder.append(temp);
			builder.append("\">");
			builder.append(temp);
			builder.append("</option>");
		}
		builder.append("</select></div><div ");
		builder.append("style=\"display: inline-block;\">");
		builder.append("<img alt=\"\" class=\"icon\" ");
		builder.append("onclick=\"removeThisInterval(this);\" ");
		builder.append("src=\"");
		builder.append(RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getContextPath());
		builder.append("/components/icons/minus-r.png\"></div></div>");
		return builder.toString();
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	public String getEndInterval(List<String> list) {
		StringBuilder builder = new StringBuilder();
		builder.append("<div><div class=\"form-input\">");
		builder.append("<label>+ INTERVAL</label></div>");
		builder.append("<div class=\"form-input\"><label>");
		builder.append(messages.getMessage("lbl.quantity"));
		builder.append("</label> <input type=\"text\" ");
		builder.append("name=\"end_date_interval_quantity\" ");
		builder.append("class=\"form-control\"></div>");
		builder.append("<div class=\"form-input\"><label>");
		builder.append(messages.getMessage("lbl.interval"));
		builder.append("</label> <select name=\"end_date_interval\"");
		builder.append(" class=\"form-control\">");
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String temp = iterator.next();
			builder.append("<option value=\"");
			builder.append(temp);
			builder.append("\">");
			builder.append(temp);
			builder.append("</option>");
		}
		builder.append("</select></div><div ");
		builder.append("style=\"display: inline-block;\">");
		builder.append("<img alt=\"\" class=\"icon\" ");
		builder.append("onclick=\"removeThisInterval(this);\" ");
		builder.append("src=\"");
		builder.append(RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getContextPath());
		builder.append("/components/icons/minus-r.png\"></div></div>");
		return builder.toString();
	}
}
