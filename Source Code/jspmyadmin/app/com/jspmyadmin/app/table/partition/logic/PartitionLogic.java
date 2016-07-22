/**
 * 
 */
package com.jspmyadmin.app.table.partition.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jspmyadmin.app.table.partition.beans.PartinitionBean;
import com.jspmyadmin.app.table.partition.beans.PartitionInfo;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/11
 *
 */
public class PartitionLogic extends AbstractLogic {

	private final String _table;

	/**
	 * 
	 * @param table
	 */
	public PartitionLogic(String table) {
		_table = table;
	}

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void fillBean(Bean bean) throws ClassNotFoundException, SQLException {
		PartinitionBean partinitionBean = (PartinitionBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			apiConnection = getConnection(true);
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT partition_name, subpartition_name, partition_method,");
			builder.append("subpartition_method, partition_expression, subpartition_expression,");
			builder.append("partition_description, table_rows, avg_row_length, data_length");
			builder.append(" FROM information_schema.partitions WHERE table_schema = ? AND table_name = ?");
			statement = apiConnection.getStmtSelect(builder.toString());
			statement.setString(1, apiConnection.getDatabase());
			statement.setString(2, _table);
			resultSet = statement.executeQuery();
			List<PartitionInfo> partitionInfoList = new ArrayList<PartitionInfo>();
			PartitionInfo partitionInfo = null;
			while (resultSet.next()) {
				partitionInfo = new PartitionInfo();
				partitionInfo.setName(resultSet.getString(1));
				if (!isEmpty(partitionInfo.getName())) {
					partitionInfo.setSubname(resultSet.getString(2));
					partitionInfo.setMethod(resultSet.getString(3));
					partitionInfo.setSub_method(resultSet.getString(4));
					partitionInfo.setExpression(resultSet.getString(5));
					partitionInfo.setSub_expression(resultSet.getString(6));
					partitionInfo.setDescription(resultSet.getString(7));
					partitionInfo.setTable_rows(resultSet.getString(8));
					partitionInfo.setAvg_row_length(resultSet.getString(9));
					partitionInfo.setData_length(resultSet.getString(10));
					partitionInfoList.add(partitionInfo);
				}
			}
			partinitionBean.setPartition_list(partitionInfoList);
		} finally {
			close(resultSet);
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
	public void addPartition(Bean bean) throws ClassNotFoundException, SQLException {
		PartinitionBean partinitionBean = (PartinitionBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		try {
			if (!isEmpty(partinitionBean.getPartition())) {
				apiConnection = getConnection(true);
				StringBuilder builder = new StringBuilder();
				builder.append("ALTER TABLE `");
				builder.append(_table);
				builder.append("` PARTITION BY ");
				builder.append(partinitionBean.getPartition());
				builder.append(FrameworkConstants.SPACE);
				builder.append(partinitionBean.getPartition_val());
				builder.append(FrameworkConstants.SPACE);
				if (!isEmpty(partinitionBean.getPartitions())) {
					builder.append("PARTITIONS ");
					builder.append(partinitionBean.getPartitions());
				}
				statement = apiConnection.getStmtSelect(builder.toString());
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
	public void dropPartitions(Bean bean) throws ClassNotFoundException, SQLException {
		PartinitionBean partinitionBean = (PartinitionBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		try {
			if (partinitionBean.getNames() != null) {
				apiConnection = getConnection(true);
				StringBuilder builder = new StringBuilder();
				builder.append("ALTER TABLE `");
				builder.append(_table);
				builder.append("` DROP PARTITION ");
				boolean alreadyEntered = false;
				for (String partition : partinitionBean.getNames()) {
					if (alreadyEntered) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
					} else {
						alreadyEntered = true;
					}
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(partition);
					builder.append(FrameworkConstants.SYMBOL_TEN);
				}
				statement = apiConnection.getStmtSelect(builder.toString());
				statement.execute();
				apiConnection.commit();
			}
		} finally {
			close(statement);
			close(apiConnection);
		}
	}

}
