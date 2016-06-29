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
import com.jspmyadmin.framework.db.AbstractLogic;
import com.jspmyadmin.framework.db.ApiConnection;
import com.jspmyadmin.framework.util.AppConstants;
import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;
import com.jspmyadmin.framework.web.utils.Messages;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/12
 *
 */
public class StatusLogic extends AbstractLogic {

	private static final int _LIMIT = 1024;
	private static final String _B = " B";
	private static final String _KIB = " KiB";
	private static final String _MIB = " MiB";
	private static final String _GIB = " GiB";
	private static final DecimalFormat _FORMAT = new DecimalFormat("0.00");

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
		StringBuilder builder = null;
		List<String[]> data_list = null;
		String[] variables = null;
		double temp = 0;
		double total = 0;
		long time = 0L;
		try {
			statusBean = (StatusBean) bean;
			apiConnection = getConnection(false);
			builder = new StringBuilder();
			builder.append("SELECT *, CASE VARIABLE_NAME WHEN ? THEN 1 ");
			builder.append("WHEN ? THEN 2 WHEN ? THEN 3 WHEN ? then 4 ");
			builder.append("ELSE 0 END AS typ FROM information_schema.GLOBAL_STATUS");
			statement = apiConnection.preparedStatementSelect(builder.toString());
			statement.setString(1, "BYTES_RECEIVED");
			statement.setString(2, "BYTES_SENT");
			statement.setString(3, "UPTIME");
			statement.setString(4, "UPTIME_SINCE_FLUSH_STATUS");
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
				int type = resultSet.getInt(3);
				switch (type) {
				case 1:
					temp = Double.parseDouble(variables[1]);
					total += temp;
					variables[1] = _getValue(temp);
					statusBean.setReceived(variables[1]);
					break;
				case 2:
					temp = Double.parseDouble(variables[1]);
					total += temp;
					variables[1] = _getValue(temp);
					statusBean.setSent(variables[1]);
					break;
				case 3:
					time = Long.parseLong(variables[1]);
					variables[1] = _getInDays(time);
					statusBean.setRun_time(variables[1]);
					statusBean.setStart_date(_getDate(time));
					break;
				case 4:
					time = Long.parseLong(variables[1]);
					variables[1] = _getInDays(time);
					break;
				default:
					break;
				}
				data_list.add(variables);
			}
			statusBean.setData_list(data_list);
			statusBean.setTotal(_getValue(total));
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
			builder = null;
		}
	}

	/**
	 * 
	 * @param temp
	 * @return
	 */
	private String _getValue(double temp) {
		if (temp > _LIMIT) {
			temp = temp / _LIMIT;
			if (temp > _LIMIT) {
				temp = temp / _LIMIT;
				if (temp > _LIMIT) {
					temp = temp / _LIMIT;
					return _FORMAT.format(temp) + _GIB;
				} else {
					return _FORMAT.format(temp) + _MIB;
				}
			} else {
				return _FORMAT.format(temp) + _KIB;
			}
		} else {
			return temp + _B;
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
