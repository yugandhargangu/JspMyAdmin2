/**
 * 
 */
package com.jspmyadmin.app.view.structure.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jspmyadmin.app.view.structure.beans.ColumnInfo;
import com.jspmyadmin.app.view.structure.beans.ColumnListBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/04/20
 *
 */
public class StructureLogic extends AbstractLogic {

	private final String _view;

	/**
	 * 
	 * @param view
	 * @param messages
	 * @throws NullPointerException
	 */
	public StructureLogic(String view) throws NullPointerException {
		if (view == null) {
			throw new NullPointerException();
		}
		_view = view;
	}

	/**
	 * 
	 * @param bean
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	public void fillStructureBean(Bean bean) throws ClassNotFoundException, SQLException, Exception {
		ColumnListBean columnListBean = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		List<ColumnInfo> columnInfoList = null;
		ColumnInfo columnInfo = null;

		StringBuilder builder = null;
		try {
			columnListBean = (ColumnListBean) bean;
			apiConnection = super.getConnection(bean.getRequest_db());

			builder = new StringBuilder();
			builder.append("SHOW FULL COLUMNS FROM `");
			builder.append(_view);
			builder.append(FrameworkConstants.SYMBOL_TEN);
			statement = apiConnection.getStmtSelect(builder.toString());
			resultSet = statement.executeQuery();
			columnInfoList = new ArrayList<ColumnInfo>();
			while (resultSet.next()) {
				columnInfo = new ColumnInfo();
				columnInfo.setField_name(resultSet.getString(1));
				columnInfo.setField_type(resultSet.getString(2));
				columnInfo.setCollation(resultSet.getString(3));
				columnInfo.setNull_yes(resultSet.getString(4));
				columnInfo.setKey(resultSet.getString(5));
				columnInfo.setDef_val(resultSet.getString(6));
				columnInfo.setExtra(resultSet.getString(7));
				columnInfo.setPrivileges(resultSet.getString(8));
				columnInfo.setComments(resultSet.getString(9));
				columnInfoList.add(columnInfo);
			}
			columnListBean.setColumn_list(columnInfoList);
		} finally {
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
	}
}
