/**
 * 
 */
package com.jspmyadmin.app.server.common.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jspmyadmin.app.server.common.beans.StatusBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.BeanConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;
import com.jspmyadmin.framework.web.utils.Messages;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/12
 *
 */
public class StatusLogic extends AbstractLogic {

	private static final String _ONE = "BYTES_RECEIVED";
	private static final String _TWO = "BYTES_SENT";
	private static final String _THREE = "UPTIME";
	private static final String _FOUR = "UPTIME_SINCE_FLUSH_STATUS";
	private final DecimalFormat _format = new DecimalFormat("0.00");

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
	public void fillBean(Bean bean) throws ClassNotFoundException, SQLException {
		StatusBean statusBean = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		List<String[]> data_list = null;
		String[] variables = null;
		double temp = 0;
		double total = 0;
		long time = 0L;
		try {
			statusBean = (StatusBean) bean;
			apiConnection = getConnection(false);
			statement = apiConnection.getStmtSelect("SHOW STATUS");
			resultSet = statement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			variables = new String[2];
			variables[0] = resultSetMetaData.getColumnName(1);
			variables[1] = resultSetMetaData.getColumnName(2);
			statusBean.setColumnInfo(variables);
			data_list = new ArrayList<String[]>();
			while (resultSet.next()) {
				variables = new String[2];
				variables[0] = resultSet.getString(1);
				variables[1] = resultSet.getString(2);
				if (_ONE.equalsIgnoreCase(variables[0])) {
					temp = Double.parseDouble(variables[1]);
					total += temp;
					variables[1] = _getValue(temp);
					statusBean.setReceived(variables[1]);
				} else if (_TWO.equalsIgnoreCase(variables[0])) {
					temp = Double.parseDouble(variables[1]);
					total += temp;
					variables[1] = _getValue(temp);
					statusBean.setSent(variables[1]);
				} else if (_THREE.equalsIgnoreCase(variables[0])) {
					time = Long.parseLong(variables[1]);
					variables[1] = _getInDays(time);
					statusBean.setRun_time(variables[1]);
					statusBean.setStart_date(_getDate(time));
				} else if (_FOUR.equalsIgnoreCase(variables[0])) {
					time = Long.parseLong(variables[1]);
					variables[1] = _getInDays(time);
				}
				data_list.add(variables);
			}
			statusBean.setData_list(data_list);
			statusBean.setTotal(_getValue(total));
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}

	/**
	 * 
	 * @param temp
	 * @return
	 */
	private String _getValue(double temp) {
		if (temp > BeanConstants._LIMIT) {
			temp = temp / BeanConstants._LIMIT;
			if (temp > BeanConstants._LIMIT) {
				temp = temp / BeanConstants._LIMIT;
				if (temp > BeanConstants._LIMIT) {
					temp = temp / BeanConstants._LIMIT;
					return _format.format(temp) + BeanConstants._GIB;
				} else {
					return _format.format(temp) + BeanConstants._MIB;
				}
			} else {
				return _format.format(temp) + BeanConstants._KIB;
			}
		} else {
			return temp + BeanConstants._B;
		}
	}

	/**
	 * 
	 * @param temp
	 * @return
	 */
	private String _getInDays(long temp) {
		StringBuilder builder = new StringBuilder();
		builder.append(((temp / 60 / 60 / 24) % 24));
		builder.append(FrameworkConstants.SPACE);
		builder.append(messages.getMessage(AppConstants.VAL_DAYS));
		builder.append(FrameworkConstants.SPACE);
		builder.append(((temp / 60 / 60) % 60));
		builder.append(FrameworkConstants.SPACE);
		builder.append(messages.getMessage(AppConstants.VAL_HOURS));
		builder.append(FrameworkConstants.SPACE);
		builder.append(((temp / 60) % 60));
		builder.append(FrameworkConstants.SPACE);
		builder.append(messages.getMessage(AppConstants.VAL_MINS));
		builder.append(FrameworkConstants.SPACE);
		builder.append(temp % 60);
		builder.append(FrameworkConstants.SPACE);
		builder.append(messages.getMessage(AppConstants.VAL_SECS));
		return builder.toString();
	}

	/**
	 * 
	 * @param temp
	 * @return
	 */
	private String _getDate(long temp) {

		String date = null;
		Calendar calendar = null;
		SimpleDateFormat dateFormat = null;
		try {
			calendar = Calendar.getInstance();
			int seconds = (int) temp % 60;
			calendar.add(Calendar.SECOND, seconds * -1);
			int mins = (int) (temp / 60) % 60;
			calendar.add(Calendar.MINUTE, mins * -1);
			int hours = (int) (temp / 60 / 60) % 60;
			calendar.add(Calendar.HOUR, hours * -1);
			int days = (int) ((temp / 60 / 60 / 24) % 24);
			calendar.add(Calendar.DATE, days * -1);
			dateFormat = new SimpleDateFormat(FrameworkConstants.DATE_FORMAT_FULL);
			date = dateFormat.format(calendar.getTime());
		} finally {
			dateFormat = null;
		}
		return date;
	}

}
