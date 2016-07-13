/**
 * 
 */
package com.jspmyadmin.app.table.structure.logic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.jspmyadmin.app.table.structure.beans.AlterColumnBean;
import com.jspmyadmin.app.table.structure.beans.ColumnInfo;
import com.jspmyadmin.app.table.structure.beans.ColumnListBean;
import com.jspmyadmin.app.table.structure.beans.IndexInfo;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.utils.Bean;
import com.jspmyadmin.framework.web.utils.Messages;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/04/20
 *
 */
public class StructureLogic extends AbstractLogic {

	private final String _table;
	private final Messages _messages;

	/**
	 * 
	 * @param table
	 */
	public StructureLogic(String table, Messages messages) throws NullPointerException {
		if (table == null) {
			throw new NullPointerException();
		}
		_table = table;
		_messages = messages;
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
		List<IndexInfo> indexInfoList = null;
		IndexInfo indexInfo = null;

		StringBuilder builder = null;
		try {
			columnListBean = (ColumnListBean) bean;
			apiConnection = super.getConnection(true);

			builder = new StringBuilder();
			builder.append("SHOW FULL COLUMNS FROM `");
			builder.append(_table);
			builder.append(FrameworkConstants.SYMBOL_TEN);
			statement = apiConnection.preparedStatementSelect(builder.toString());
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
			close(resultSet);
			close(statement);

			builder.delete(0, builder.length());
			builder.append("SHOW INDEXES FROM `");
			builder.append(_table);
			builder.append(FrameworkConstants.SYMBOL_TEN);
			statement = apiConnection.preparedStatementSelect(builder.toString());
			resultSet = statement.executeQuery();
			indexInfoList = new ArrayList<IndexInfo>();
			while (resultSet.next()) {
				indexInfo = new IndexInfo();
				indexInfo.setNon_unique(resultSet.getString(2));
				indexInfo.setKey_name(resultSet.getString(3));
				indexInfo.setColumn_name(resultSet.getString(5));
				indexInfo.setCollation(resultSet.getString(6));
				indexInfo.setCardinality(resultSet.getString(7));
				indexInfo.setNull_yes(resultSet.getString(10));
				indexInfo.setIndex_type(resultSet.getString(11));
				indexInfo.setComment(resultSet.getString(12));
				indexInfoList.add(indexInfo);
			}
			columnListBean.setIndex_list(indexInfoList);
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
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public void dropColums(Bean bean) throws SQLException, ClassNotFoundException, Exception {
		ColumnListBean columnListBean = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;

		StringBuilder builder = null;
		try {
			columnListBean = (ColumnListBean) bean;
			if (columnListBean.getColumns() != null && columnListBean.getColumns().length > 0) {
				builder = new StringBuilder();
				builder.append("ALTER TABLE `");
				builder.append(_table);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(" DROP ");
				for (int i = 0; i < columnListBean.getColumns().length; i++) {
					if (i != 0) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
					}
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(columnListBean.getColumns()[i]);
					builder.append(FrameworkConstants.SYMBOL_TEN);
				}
				apiConnection = getConnection(true);
				statement = apiConnection.preparedStatement(builder.toString());
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
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public void dropKeys(Bean bean) throws SQLException, ClassNotFoundException, Exception {
		ColumnListBean columnListBean = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;

		StringBuilder builder = null;
		try {
			columnListBean = (ColumnListBean) bean;
			if (columnListBean.getKeys() != null && columnListBean.getKeys().length > 0) {
				builder = new StringBuilder();
				builder.append("ALTER TABLE `");
				builder.append(_table);
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(" DROP ");
				for (int i = 0; i < columnListBean.getKeys().length; i++) {
					if (i != 0) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
					}
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(columnListBean.getKeys()[i]);
					builder.append(FrameworkConstants.SYMBOL_TEN);
				}
				apiConnection = getConnection(true);
				statement = apiConnection.preparedStatement(builder.toString());
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
	 * @throws Exception
	 */
	public void fillAlterBean(Bean bean) throws ClassNotFoundException, SQLException, Exception {

		AlterColumnBean alterColumnBean = (AlterColumnBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		StringBuilder builder = null;

		String[] old_columns = null;
		String[] columns = null;
		String[] datatypes = null;
		String[] lengths = null;
		String[] defaults = null;
		String[] collations = null;
		String[] pks = null;
		String[] nns = null;
		String[] uqs = null;
		String[] uns = null;
		String[] zfs = null;
		String[] ais = null;
		String[] comments = null;
		try {
			apiConnection = getConnection(true);
			alterColumnBean.setOld_table_name(_table);
			alterColumnBean.setNew_table_name(_table);

			statement = apiConnection.preparedStatementSelect("SHOW TABLE STATUS WHERE name = ?");
			statement.setString(1, _table);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				alterColumnBean.setOld_collation(resultSet.getString("collation"));
				alterColumnBean.setNew_collation(alterColumnBean.getOld_collation());
				alterColumnBean.setOld_engine(resultSet.getString("engine"));
				alterColumnBean.setNew_engine(alterColumnBean.getOld_engine());
				alterColumnBean.setOld_comments(resultSet.getString("comment"));
				alterColumnBean.setNew_comments(alterColumnBean.getOld_comments());
			}

			String uniqueQuery = "SHOW KEYS FROM `" + _table + "` WHERE key_name <> ? AND non_unique = ?";
			statement = apiConnection.preparedStatementSelect(uniqueQuery);
			statement.setString(1, " PRIMARY");
			statement.setInt(2, 0);
			resultSet = statement.executeQuery();
			List<String> oldUniqueList = new ArrayList<String>();
			while (resultSet.next()) {
				oldUniqueList.add(resultSet.getString("column_name"));
			}

			builder = new StringBuilder();
			builder.append("SHOW FULL COLUMNS FROM `");
			builder.append(_table);
			builder.append(FrameworkConstants.SYMBOL_TEN);
			statement = apiConnection.preparedStatementSelect(builder.toString());
			resultSet = statement.executeQuery();
			if (resultSet.last()) {
				int size = resultSet.getRow();
				old_columns = new String[size];
				columns = new String[size];
				datatypes = new String[size];
				lengths = new String[size];
				defaults = new String[size];
				collations = new String[size];
				pks = new String[size];
				nns = new String[size];
				uqs = new String[size];
				uns = new String[size];
				zfs = new String[size];
				ais = new String[size];
				comments = new String[size];

				resultSet.beforeFirst();
				int i = 0;
				while (resultSet.next()) {
					old_columns[i] = resultSet.getString("field");
					columns[i] = old_columns[i];
					String temp = resultSet.getString("type");
					String[] tempType = temp.split(" ");
					if (tempType.length > 1) {
						int index = tempType[0].indexOf(FrameworkConstants.SYMBOL_BRACKET_OPEN);
						if (index != -1) {
							datatypes[i] = tempType[0].substring(0, index);
							lengths[i] = tempType[0].substring(index + 1, tempType[0].length() - 1);
						} else {
							datatypes[i] = temp;
						}
						for (int j = 1; j < tempType.length; j++) {
							if ("unsigned".equalsIgnoreCase(tempType[j])) {
								uns[i] = "1";
							} else if ("zerofill".equalsIgnoreCase(tempType[j])) {
								zfs[i] = "1";
							}
						}
					} else {
						int index = temp.indexOf(FrameworkConstants.SYMBOL_BRACKET_OPEN);
						if (index != -1) {
							datatypes[i] = temp.substring(0, index);
							lengths[i] = temp.substring(index + 1, temp.length() - 1);
						} else {
							datatypes[i] = temp;
						}
					}
					datatypes[i] = datatypes[i].toUpperCase();
					defaults[i] = resultSet.getString("default");
					collations[i] = resultSet.getString("collation");
					temp = resultSet.getString("Key");
					if (temp != null && "PRI".equalsIgnoreCase(temp)) {
						pks[i] = "1";
					}
					temp = resultSet.getString("null");
					if (temp != null && "NO".equalsIgnoreCase(temp)) {
						nns[i] = "1";
					}
					if (oldUniqueList.contains(old_columns[i])) {
						uqs[i] = "1";
					}
					temp = resultSet.getString("extra");
					if (temp != null && "auto_increment".equalsIgnoreCase(temp)) {
						ais[i] = "1";
					}
					comments[i] = resultSet.getString("comment");
					i++;
				}
				alterColumnBean.setOld_columns(old_columns);
				alterColumnBean.setColumns(columns);
				alterColumnBean.setDatatypes(datatypes);
				alterColumnBean.setLengths(lengths);
				alterColumnBean.setDefaults(defaults);
				alterColumnBean.setCollations(collations);
				alterColumnBean.setPks(pks);
				alterColumnBean.setNns(nns);
				alterColumnBean.setUqs(uqs);
				alterColumnBean.setUns(uns);
				alterColumnBean.setZfs(zfs);
				alterColumnBean.setAis(ais);
				alterColumnBean.setComments(comments);
			}

		} finally {
			close(resultSet);
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
	 * @throws Exception
	 */
	public String alterColumns(Bean bean) throws ClassNotFoundException, SQLException, Exception {

		String result = null;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		StringBuilder builder = null;
		AlterColumnBean alterColumnBean = (AlterColumnBean) bean;
		List<String> uniqueList = null;
		Iterator<String> uniqueIterator = null;
		String zerofill = "ZEROFILL";
		String unsigned = "UNSIGNED";
		String binary = "BINARY";
		String null_ = "NULL";
		String not_null = "NOT NULL";
		String collate = "COLLATE";
		String default_ = "DEFAULT";
		String auto_increment = "AUTO_INCREMENT";
		String comment = "COMMENT";
		String primary_key = null;
		String previous_column = null;
		boolean alreadyEntered = false;
		try {

			uniqueList = new ArrayList<String>();
			builder = new StringBuilder();
			builder.append("ALTER TABLE ");
			builder.append(FrameworkConstants.SYMBOL_TEN);
			builder.append(_table);
			builder.append(FrameworkConstants.SYMBOL_TEN);

			String table_start = builder.toString();

			if (!alterColumnBean.getOld_collation().equalsIgnoreCase(alterColumnBean.getNew_collation())) {
				builder.append(" COLLATE = ");
				builder.append(alterColumnBean.getNew_collation());
				alreadyEntered = true;
			}

			if (!alterColumnBean.getOld_engine().equalsIgnoreCase(alterColumnBean.getNew_engine())) {
				if (alreadyEntered) {
					builder.append(FrameworkConstants.SYMBOL_COMMA);
				}
				builder.append(" ENGINE = ");
				builder.append(alterColumnBean.getNew_engine());
				if (!alreadyEntered) {
					alreadyEntered = true;
				}
			}

			for (int i = 0; i < alterColumnBean.getColumns().length; i++) {
				if (!isEmpty(alterColumnBean.getOld_columns()[i]) || !isEmpty(alterColumnBean.getColumns()[i])) {
					// check for changes
					if (FrameworkConstants.ONE.equals(alterColumnBean.getChanges()[i])) {

						if (isEmpty(alterColumnBean.getOld_columns()[i])
								&& !FrameworkConstants.ONE.equals(alterColumnBean.getDeletes()[i])) {
							// add new column
							if (alreadyEntered) {
								builder.append(FrameworkConstants.SYMBOL_COMMA);
							}
							if (!alreadyEntered) {
								alreadyEntered = true;
							}

							builder.append(" ADD COLUMN ");
							builder.append(FrameworkConstants.SYMBOL_TEN);
							builder.append(alterColumnBean.getColumns()[i]);
							builder.append(FrameworkConstants.SYMBOL_TEN);
							builder.append(FrameworkConstants.SPACE);
							builder.append(alterColumnBean.getDatatypes()[i]);
							if (!isEmpty(alterColumnBean.getLengths()[i])) {
								builder.append(FrameworkConstants.SYMBOL_BRACKET_OPEN);
								builder.append(alterColumnBean.getLengths()[i]);
								builder.append(FrameworkConstants.SYMBOL_BRACKET_CLOSE);
							}
							builder.append(FrameworkConstants.SPACE);
							if (FrameworkConstants.ONE.equals(alterColumnBean.getZfs()[i])) {
								builder.append(zerofill);
								builder.append(FrameworkConstants.SPACE);
							}
							if (FrameworkConstants.ONE.equals(alterColumnBean.getUns()[i])) {
								builder.append(unsigned);
								builder.append(FrameworkConstants.SPACE);
							}
							if (FrameworkConstants.ONE.equals(alterColumnBean.getBins()[i])) {
								builder.append(binary);
								builder.append(FrameworkConstants.SPACE);
							}
							if (!isEmpty(alterColumnBean.getCollations()[i])) {
								builder.append(collate);
								builder.append(FrameworkConstants.SPACE);
								builder.append(FrameworkConstants.SYMBOL_QUOTE);
								builder.append(alterColumnBean.getCollations()[i]);
								builder.append(FrameworkConstants.SYMBOL_QUOTE);
								builder.append(FrameworkConstants.SPACE);
							}
							if (FrameworkConstants.ONE.equals(alterColumnBean.getNns()[i])) {
								builder.append(not_null);
								builder.append(FrameworkConstants.SPACE);
							} else {
								builder.append(null_);
								builder.append(FrameworkConstants.SPACE);
							}
							if (!isEmpty(alterColumnBean.getDefaults()[i])) {
								builder.append(default_);
								builder.append(FrameworkConstants.SPACE);
								if (FrameworkConstants.CURRENT_TIMESTAMP.equals(alterColumnBean.getDefaults()[i])) {
									builder.append(alterColumnBean.getDefaults()[i]);
								} else {
									builder.append(FrameworkConstants.SYMBOL_QUOTE);
									builder.append(alterColumnBean.getDefaults()[i]);
									builder.append(FrameworkConstants.SYMBOL_QUOTE);
								}
								builder.append(FrameworkConstants.SPACE);
							}
							if (FrameworkConstants.ONE.equals(alterColumnBean.getAis()[i])) {
								builder.append(auto_increment);
								builder.append(FrameworkConstants.SPACE);
							}

							builder.append(FrameworkConstants.SPACE);
							builder.append(comment);
							builder.append(FrameworkConstants.SPACE);
							builder.append(FrameworkConstants.SYMBOL_QUOTE);
							if (!isEmpty(alterColumnBean.getComments()[i])) {
								builder.append(alterColumnBean.getComments()[i]);
							}
							builder.append(FrameworkConstants.SYMBOL_QUOTE);

							if (FrameworkConstants.ONE.equals(alterColumnBean.getPks()[i])) {
								primary_key = alterColumnBean.getColumns()[i];
							}
							if (FrameworkConstants.ONE.equals(alterColumnBean.getUqs()[i])) {
								uniqueList.add(alterColumnBean.getColumns()[i]);
							}
							if (previous_column != null) {
								builder.append(" AFTER ");
								builder.append(FrameworkConstants.SYMBOL_TEN);
								builder.append(previous_column);
								builder.append(FrameworkConstants.SYMBOL_TEN);
							} else {
								builder.append(" FIRST");
							}

							previous_column = alterColumnBean.getColumns()[i];
						} else if (!isEmpty(alterColumnBean.getOld_columns()[i])
								&& FrameworkConstants.ONE.equals(alterColumnBean.getDeletes()[i])) {
							// drop column
							if (alreadyEntered) {
								builder.append(FrameworkConstants.SYMBOL_COMMA);
							}
							if (!alreadyEntered) {
								alreadyEntered = true;
							}
							builder.append(" DROP COLUMN ");
							builder.append(FrameworkConstants.SYMBOL_TEN);
							builder.append(alterColumnBean.getOld_columns()[i]);
							builder.append(FrameworkConstants.SYMBOL_TEN);
						} else if (!isEmpty(alterColumnBean.getOld_columns()[i])) {
							// alter column
							if (alreadyEntered) {
								builder.append(FrameworkConstants.SYMBOL_COMMA);
							}
							if (!alreadyEntered) {
								alreadyEntered = true;
							}

							if (alterColumnBean.getOld_columns()[i] != null && !alterColumnBean.getOld_columns()[i]
									.trim().equals(alterColumnBean.getColumns()[i].trim())) {
								// change column
								builder.append(" CHANGE COLUMN ");
								builder.append(FrameworkConstants.SYMBOL_TEN);
								builder.append(alterColumnBean.getOld_columns()[i]);
								builder.append(FrameworkConstants.SYMBOL_TEN);
								builder.append(FrameworkConstants.SPACE);
								builder.append(FrameworkConstants.SYMBOL_TEN);
								builder.append(alterColumnBean.getColumns()[i]);
								builder.append(FrameworkConstants.SYMBOL_TEN);
							} else {
								// modify column
								builder.append(" MODIFY COLUMN ");
								builder.append(FrameworkConstants.SYMBOL_TEN);
								builder.append(alterColumnBean.getColumns()[i]);
								builder.append(FrameworkConstants.SYMBOL_TEN);
							}
							builder.append(FrameworkConstants.SPACE);
							builder.append(alterColumnBean.getDatatypes()[i]);
							if (!isEmpty(alterColumnBean.getLengths()[i])) {
								builder.append(FrameworkConstants.SYMBOL_BRACKET_OPEN);
								builder.append(alterColumnBean.getLengths()[i]);
								builder.append(FrameworkConstants.SYMBOL_BRACKET_CLOSE);
							}
							builder.append(FrameworkConstants.SPACE);
							if (FrameworkConstants.ONE.equals(alterColumnBean.getZfs()[i])) {
								builder.append(zerofill);
								builder.append(FrameworkConstants.SPACE);
							}
							if (FrameworkConstants.ONE.equals(alterColumnBean.getUns()[i])) {
								builder.append(unsigned);
								builder.append(FrameworkConstants.SPACE);
							}
							if (FrameworkConstants.ONE.equals(alterColumnBean.getBins()[i])) {
								builder.append(binary);
								builder.append(FrameworkConstants.SPACE);
							}
							if (!isEmpty(alterColumnBean.getCollations()[i])) {
								builder.append(collate);
								builder.append(FrameworkConstants.SPACE);
								builder.append(FrameworkConstants.SYMBOL_QUOTE);
								builder.append(alterColumnBean.getCollations()[i]);
								builder.append(FrameworkConstants.SYMBOL_QUOTE);
								builder.append(FrameworkConstants.SPACE);
							}
							if (FrameworkConstants.ONE.equals(alterColumnBean.getNns()[i])) {
								builder.append(not_null);
								builder.append(FrameworkConstants.SPACE);
							} else {
								builder.append(null_);
								builder.append(FrameworkConstants.SPACE);
							}
							if (!isEmpty(alterColumnBean.getDefaults()[i])) {
								builder.append(default_);
								builder.append(FrameworkConstants.SPACE);
								if (FrameworkConstants.CURRENT_TIMESTAMP.equals(alterColumnBean.getDefaults()[i])) {
									builder.append(alterColumnBean.getDefaults()[i]);
								} else {
									builder.append(FrameworkConstants.SYMBOL_QUOTE);
									builder.append(alterColumnBean.getDefaults()[i]);
									builder.append(FrameworkConstants.SYMBOL_QUOTE);
								}
								builder.append(FrameworkConstants.SPACE);
							}
							if (FrameworkConstants.ONE.equals(alterColumnBean.getAis()[i])) {
								builder.append(auto_increment);
								builder.append(FrameworkConstants.SPACE);
							}

							builder.append(FrameworkConstants.SPACE);
							builder.append(comment);
							builder.append(FrameworkConstants.SPACE);
							builder.append(FrameworkConstants.SYMBOL_QUOTE);
							if (!isEmpty(alterColumnBean.getComments()[i])) {
								builder.append(alterColumnBean.getComments()[i]);
							}
							builder.append(FrameworkConstants.SYMBOL_QUOTE);

							if (FrameworkConstants.ONE.equals(alterColumnBean.getPks()[i])) {
								primary_key = alterColumnBean.getColumns()[i].trim();
							}
							if (FrameworkConstants.ONE.equals(alterColumnBean.getUqs()[i])) {
								uniqueList.add(alterColumnBean.getColumns()[i].trim());
							}
							if (previous_column != null) {
								builder.append(" AFTER ");
								builder.append(FrameworkConstants.SYMBOL_TEN);
								builder.append(previous_column);
								builder.append(FrameworkConstants.SYMBOL_TEN);
							} else {
								builder.append(" FIRST");
							}

							previous_column = alterColumnBean.getColumns()[i];
						} else {
							// non effect cases
						}
					} else {
						previous_column = alterColumnBean.getColumns()[i];
						if (FrameworkConstants.ONE.equals(alterColumnBean.getPks()[i])) {
							primary_key = alterColumnBean.getColumns()[i].trim();
						}
						if (FrameworkConstants.ONE.equals(alterColumnBean.getUqs()[i])) {
							uniqueList.add(alterColumnBean.getColumns()[i].trim());
						}
					}
				}
			}

			apiConnection = getConnection(true);
			String primaryQuery = "SHOW KEYS FROM `" + _table + "` WHERE key_name = ?";
			statement = apiConnection.preparedStatementSelect(primaryQuery);
			statement.setString(1, "PRIMARY");
			resultSet = statement.executeQuery();
			String old_primary_key = null;
			if (resultSet.next()) {
				old_primary_key = resultSet.getString("column_name");
			}

			boolean oldPKStatus = false;
			for (int i = 0; i < alterColumnBean.getOld_columns().length; i++) {
				if (!isEmpty(alterColumnBean.getOld_columns()[i])
						&& alterColumnBean.getOld_columns()[i].equalsIgnoreCase(old_primary_key)) {
					oldPKStatus = true;
				}
			}

			if (alreadyEntered) {
				if (primary_key == null && oldPKStatus) {
					// do nothing
				} else if (old_primary_key != null && primary_key != null) {
					if (!old_primary_key.equalsIgnoreCase(primary_key)) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
						builder.append(FrameworkConstants.SPACE);
						builder.append(" DROP PRIMARY KEY");
						builder.append(FrameworkConstants.SYMBOL_COMMA);
						builder.append(" ADD PRIMARY KEY");
						builder.append(FrameworkConstants.SYMBOL_BRACKET_OPEN);
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(primary_key);
						builder.append(FrameworkConstants.SYMBOL_TEN);
						builder.append(FrameworkConstants.SYMBOL_BRACKET_CLOSE);
						if (!alreadyEntered) {
							alreadyEntered = true;
						}
					}
				} else if (old_primary_key != null) {
					builder.append(FrameworkConstants.SYMBOL_COMMA);
					builder.append(FrameworkConstants.SPACE);
					builder.append(" DROP PRIMARY KEY");
					if (!alreadyEntered) {
						alreadyEntered = true;
					}
				} else if (primary_key != null) {
					builder.append(FrameworkConstants.SYMBOL_COMMA);
					builder.append(" ADD PRIMARY KEY");
					builder.append(FrameworkConstants.SYMBOL_BRACKET_OPEN);
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(primary_key);
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(FrameworkConstants.SYMBOL_BRACKET_CLOSE);
					if (!alreadyEntered) {
						alreadyEntered = true;
					}
				}
			}

			String uniqueQuery = "SHOW KEYS FROM `" + _table + "` WHERE key_name <> ? AND non_unique = ?";
			statement = apiConnection.preparedStatementSelect(uniqueQuery);
			statement.setString(1, "PRIMARY");
			statement.setInt(2, 0);
			resultSet = statement.executeQuery();
			Map<String, String> oldUniqueList = new HashMap<String, String>();
			while (resultSet.next()) {
				oldUniqueList.put(resultSet.getString("column_name"), resultSet.getString("key_name"));
			}

			uniqueIterator = uniqueList.iterator();
			while (uniqueIterator.hasNext()) {
				String item = uniqueIterator.next();
				if (oldUniqueList.containsKey(item)) {
					oldUniqueList.remove(item);
				} else {
					if (alreadyEntered) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
					}
					builder.append(" ADD UNIQUE ");
					builder.append(FrameworkConstants.SYMBOL_BRACKET_OPEN);
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(item);
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(FrameworkConstants.SYMBOL_BRACKET_CLOSE);
					if (!alreadyEntered) {
						alreadyEntered = true;
					}
				}
			}
			if (oldUniqueList.size() > 0) {
				for (String item : oldUniqueList.values()) {
					if (alreadyEntered) {
						builder.append(FrameworkConstants.SYMBOL_COMMA);
					}
					builder.append(" DROP KEY ");
					builder.append(FrameworkConstants.SYMBOL_TEN);
					builder.append(item);
					builder.append(FrameworkConstants.SYMBOL_TEN);
					if (!alreadyEntered) {
						alreadyEntered = true;
					}
				}
			}

			if (!alterColumnBean.getOld_comments().equalsIgnoreCase(alterColumnBean.getNew_comments())) {
				if (alreadyEntered) {
					builder.append(FrameworkConstants.SYMBOL_COMMA);
				}
				builder.append(" COMMENT = ");
				builder.append(FrameworkConstants.SYMBOL_QUOTE);
				builder.append(alterColumnBean.getNew_comments());
				builder.append(FrameworkConstants.SYMBOL_QUOTE);
				if (!alreadyEntered) {
					alreadyEntered = true;
				}
			}

			if (!alterColumnBean.getOld_table_name().equalsIgnoreCase(alterColumnBean.getNew_table_name())) {
				if (alreadyEntered) {
					builder.append(FrameworkConstants.SYMBOL_COMMA);
				}
				builder.append(" RENAME TO ");
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(alterColumnBean.getNew_table_name());
				builder.append(FrameworkConstants.SYMBOL_TEN);
			}

			if (FrameworkConstants.YES.equalsIgnoreCase(alterColumnBean.getAction())) {
				String temp = builder.toString();
				if (!table_start.equals(temp)) {
					statement = apiConnection.preparedStatement(builder.toString());
					statement.execute();
					apiConnection.commit();
				}
			} else {
				result = builder.toString();
				if (table_start.equals(result)) {
					result = "// No Changes Found";
				}
			}
		} finally

		{
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return result;

	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public String validate(Bean bean) throws Exception {

		String result = null;
		AlterColumnBean alterColumnBean = null;
		int count = 0;
		JSONObject jsonObject = null;
		JSONObject tempJsonObject = null;
		String a = "a";
		String b = "b";
		String[] tempArr = null;
		try {
			alterColumnBean = (AlterColumnBean) bean;
			jsonObject = new JSONObject(FrameworkConstants.Utils.DATA_TYPES_INFO);
			for (int i = 0; i < alterColumnBean.getColumns().length; i++) {
				if (!FrameworkConstants.ONE.equals(alterColumnBean.getDeletes()[i])
						&& !isEmpty(alterColumnBean.getColumns()[i])) {
					count++;
					// check column is duplicate or not
					if (!isColumnNameValid(alterColumnBean.getColumns(), alterColumnBean.getDeletes(),
							alterColumnBean.getColumns()[i])) {
						result = _messages.getMessage("msg.duplicate_columna_name") + alterColumnBean.getColumns()[i];
						break;
					}

					// get validation data
					tempJsonObject = jsonObject.getJSONObject(alterColumnBean.getDatatypes()[i]);
					int aVal = tempJsonObject.getInt(a);
					if (aVal > 0) {
						// has list
						if (isEmpty(alterColumnBean.getLengths()[i])) {
							result = _messages.getMessage("msg.length_value_blank_column")
									+ alterColumnBean.getColumns()[i];
							break;
						}

						// validate list values
						tempArr = alterColumnBean.getLengths()[i].split(FrameworkConstants.SYMBOL_COMMA);
						boolean isInvalid = false;
						for (int j = 0; j < tempArr.length; j++) {
							if (!isValidSqlString(tempArr[j], true)) {
								isInvalid = true;
								break;
							}
						}
						if (isInvalid) {
							result = _messages.getMessage("msg.length_value_blank_column")
									+ alterColumnBean.getColumns()[i];
							break;
						}
					} else {
						// no list
						int bVal = tempJsonObject.getInt(b);
						boolean isInvalid = false;
						switch (bVal) {
						case 0:
							// length = 1 and mandatory
							if (isEmpty(alterColumnBean.getLengths()[i])) {
								isInvalid = true;
								break;
							}
							if (!isInteger(alterColumnBean.getLengths()[i])) {
								isInvalid = true;
								break;
							}
							break;
						case 1:
							// length = 2 and mandatory
							if (isEmpty(alterColumnBean.getLengths()[i])) {
								isInvalid = true;
								break;
							}
							tempArr = alterColumnBean.getLengths()[i].split(FrameworkConstants.SYMBOL_COMMA);
							if (tempArr == null || tempArr.length != 2) {
								isInvalid = true;
								break;
							} else if (!isInteger(tempArr[0]) || !isInteger(tempArr[1])) {
								isInvalid = true;
								break;
							}
							break;
						case 2:
							// length = 1 and not mandatory
							if (!isEmpty(alterColumnBean.getLengths()[i])
									&& !isInteger(alterColumnBean.getLengths()[i])) {
								isInvalid = true;
								break;
							}
							break;
						case 3:
							// length = 2 and not mandatory
							if (!isEmpty(alterColumnBean.getLengths()[i])) {
								tempArr = alterColumnBean.getLengths()[i].split(FrameworkConstants.SYMBOL_COMMA);
								if (tempArr == null || tempArr.length != 2) {
									isInvalid = true;
									break;
								} else if (!isInteger(tempArr[0]) || !isInteger(tempArr[1])) {
									isInvalid = true;
									break;
								}
							}
							break;
						default:
							break;
						}

						if (isInvalid) {
							result = _messages.getMessage("msg.length_value_blank_column")
									+ alterColumnBean.getColumns()[i];
							break;
						}
					}

					// validate default value
					if (!isEmpty(alterColumnBean.getDefaults()[i])
							&& !isValidSqlString(alterColumnBean.getDefaults()[i], false)) {
						result = _messages.getMessage("msg.default_value_invalid_column")
								+ alterColumnBean.getColumns()[i];
						break;
					}

					// validate comment value
					if (!isEmpty(alterColumnBean.getComments()[i])
							&& !isValidSqlString(alterColumnBean.getComments()[i], false)) {
						result = _messages.getMessage("msg.comment_invalid_column") + alterColumnBean.getColumns()[i];
						break;
					}
				}
			}
			if (count == 0) {
				result = _messages.getMessage("msg.all_columns_blank");
			}
		} finally {

		}
		return result;
	}

	/**
	 * 
	 * @param columns
	 * @param column
	 * @return
	 * @throws Exception
	 */
	public boolean isColumnNameValid(String[] columns, String[] deletes, String column) throws Exception {
		int count = 0;
		try {
			for (int i = 0; i < columns.length; i++) {
				if (!isEmpty(columns[i]) && !FrameworkConstants.ONE.equals(deletes[i])
						&& columns[i].trim().equalsIgnoreCase(column.trim())) {
					count++;
					if (count > 1) {
						return false;
					}
				}
			}
		} finally {
			columns = null;
			deletes = null;
			column = null;
		}
		return true;
	}
}
