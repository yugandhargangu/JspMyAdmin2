/**
 * 
 */
package com.jspmyadmin.app.server.database.beans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Comparator;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.framework.constants.BeanConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.logic.EncodeHelper;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/09
 *
 */
public class DatabaseInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private final DecimalFormat _format = new DecimalFormat("0.00");

	private String database = null;
	private String collation = null;
	private String tables = null;
	private String rows = null;
	private String data = null;
	private String indexes = null;
	private String total = null;
	private String action = null;

	/**
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * @param database
	 *            the database to set
	 */
	public void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * @return the collation
	 */
	public String getCollation() {
		return collation;
	}

	/**
	 * @param collation
	 *            the collation to set
	 */
	public void setCollation(String collation) {
		this.collation = collation;
	}

	/**
	 * @return the tables
	 */
	public String getTables() {
		return tables;
	}

	/**
	 * @param tables
	 *            the tables to set
	 */
	public void setTables(String tables) {
		this.tables = tables;
	}

	/**
	 * @return the rows
	 */
	public String getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(String rows) {
		this.rows = rows;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		try {
			double temp = Double.parseDouble(data);
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
				return data + BeanConstants._B;
			}
		} catch (Exception e) {
		}
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the indexes
	 */
	public String getIndexes() {
		try {
			double temp = Double.parseDouble(indexes);
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
				return indexes + BeanConstants._B;
			}
		} catch (Exception e) {
		}
		return indexes;
	}

	/**
	 * @param indexes
	 *            the indexes to set
	 */
	public void setIndexes(String indexes) {
		this.indexes = indexes;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		try {
			double temp = Double.parseDouble(total);
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
				return total + BeanConstants._B;
			}
		} catch (Exception e) {
		}
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
	 * 
	 * @author Yugandhar Gangu
	 * @created_at 2016/02/09
	 *
	 */
	public static class DatabaseInfoComparator implements Comparator<DatabaseInfo>, Serializable {

		private static final long serialVersionUID = 1L;

		private boolean _type = true;
		private int _field = 0;

		private DatabaseInfo _sortInfo = null;

		/**
		 * 
		 * @return
		 */
		public DatabaseInfo getSortInfo(final EncodeHelper encodeObj) {
			if (_sortInfo == null) {
				_sortInfo = new DatabaseInfo();
				JSONObject jsonObject = null;
				try {
					switch (_field) {
					case 1:
						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 1);
						jsonObject.put(Constants.TYPE, !_type);
						_sortInfo.setCollation(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 2);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setTables(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 3);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setRows(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 4);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setData(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 5);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setIndexes(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 6);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setTotal(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 0);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setDatabase(encodeObj.encode(jsonObject.toString()));
						break;
					case 2:
						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 1);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setCollation(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 2);
						jsonObject.put(Constants.TYPE, !_type);
						_sortInfo.setTables(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 3);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setRows(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 4);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setData(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 5);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setIndexes(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 6);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setTotal(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 0);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setDatabase(encodeObj.encode(jsonObject.toString()));
						break;
					case 3:
						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 1);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setCollation(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 2);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setTables(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 3);
						jsonObject.put(Constants.TYPE, !_type);
						_sortInfo.setRows(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 4);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setData(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 5);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setIndexes(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 6);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setTotal(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 0);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setDatabase(encodeObj.encode(jsonObject.toString()));
						break;
					case 4:
						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 1);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setCollation(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 2);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setTables(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 3);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setRows(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 4);
						jsonObject.put(Constants.TYPE, !_type);
						_sortInfo.setData(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 5);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setIndexes(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 6);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setTotal(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 0);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setDatabase(encodeObj.encode(jsonObject.toString()));
						break;
					case 5:
						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 1);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setCollation(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 2);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setTables(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 3);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setRows(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 4);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setData(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 5);
						jsonObject.put(Constants.TYPE, !_type);
						_sortInfo.setIndexes(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 6);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setTotal(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 0);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setDatabase(encodeObj.encode(jsonObject.toString()));
						break;
					case 6:
						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 1);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setCollation(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 2);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setTables(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 3);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setRows(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 4);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setData(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 5);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setIndexes(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 6);
						jsonObject.put(Constants.TYPE, !_type);
						_sortInfo.setTotal(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 0);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setDatabase(encodeObj.encode(jsonObject.toString()));
						break;
					default:
						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 1);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setCollation(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 2);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setTables(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 3);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setRows(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 4);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setData(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 5);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setIndexes(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 6);
						jsonObject.put(Constants.TYPE, false);
						_sortInfo.setTotal(encodeObj.encode(jsonObject.toString()));

						jsonObject = new JSONObject();
						jsonObject.put(Constants.NAME, 0);
						jsonObject.put(Constants.TYPE, !_type);
						_sortInfo.setDatabase(encodeObj.encode(jsonObject.toString()));
						break;
					}
				} catch (JSONException e) {
				} catch (EncodingException e) {
				} finally {

				}
			}
			return _sortInfo;
		}

		/**
		 * 
		 * @return
		 */
		public boolean getType() {
			return _type;
		}

		/**
		 * 
		 * @return
		 */
		public int getField() {
			return _field;
		}

		/**
		 * 
		 * @param sort
		 */
		public DatabaseInfoComparator(String sort, final EncodeHelper encodeObj) {
			if (sort != null) {
				try {
					sort = encodeObj.decode(sort);
					JSONObject jsonObject = new JSONObject(sort);
					if (jsonObject.has(Constants.TYPE)) {
						_type = jsonObject.getBoolean(Constants.TYPE);
					}
					if (jsonObject.has(Constants.NAME)) {
						_field = jsonObject.getInt(Constants.NAME);
					}
				} catch (JSONException e) {
				} catch (Exception e) {
				}
			}
		}

		public int compare(DatabaseInfo info1, DatabaseInfo info2) {
			switch (_field) {
			case 1:
				if (_type) {
					return info1.collation.compareTo(info2.collation);
				} else {
					return info2.collation.compareTo(info1.collation);
				}
			case 2:
				if (_type) {
					if (Integer.parseInt(info1.tables) > Integer.parseInt(info2.tables)) {
						return 1;
					} else if (Integer.parseInt(info1.tables) < Integer.parseInt(info2.tables)) {
						return -1;
					} else {
						return 0;
					}
				} else {
					if (Integer.parseInt(info1.tables) > Integer.parseInt(info2.tables)) {
						return -1;
					} else if (Integer.parseInt(info1.tables) < Integer.parseInt(info2.tables)) {
						return 1;
					} else {
						return 0;
					}
				}
			case 3:
				if (_type) {
					if (Integer.parseInt(info1.rows) > Integer.parseInt(info2.rows)) {
						return 1;
					} else if (Integer.parseInt(info1.rows) < Integer.parseInt(info2.rows)) {
						return -1;
					} else {
						return 0;
					}
				} else {
					if (Integer.parseInt(info1.rows) > Integer.parseInt(info2.rows)) {
						return -1;
					} else if (Integer.parseInt(info1.rows) < Integer.parseInt(info2.rows)) {
						return 1;
					} else {
						return 0;
					}
				}
			case 4:
				if (_type) {
					if (Double.parseDouble(info1.data) > Double.parseDouble(info2.data)) {
						return 1;
					} else if (Double.parseDouble(info1.data) < Double.parseDouble(info2.data)) {
						return -1;
					} else {
						return 0;
					}
				} else {
					if (Double.parseDouble(info1.data) > Double.parseDouble(info2.data)) {
						return -1;
					} else if (Double.parseDouble(info1.data) < Double.parseDouble(info2.data)) {
						return 1;
					} else {
						return 0;
					}
				}
			case 5:
				if (_type) {
					if (Double.parseDouble(info1.indexes) > Double.parseDouble(info2.indexes)) {
						return 1;
					} else if (Double.parseDouble(info1.indexes) < Double.parseDouble(info2.indexes)) {
						return -1;
					} else {
						return 0;
					}
				} else {
					if (Double.parseDouble(info1.indexes) > Double.parseDouble(info2.indexes)) {
						return -1;
					} else if (Double.parseDouble(info1.indexes) < Double.parseDouble(info2.indexes)) {
						return 1;
					} else {
						return 0;
					}
				}
			case 6:
				if (_type) {
					if (Double.parseDouble(info1.total) > Double.parseDouble(info2.total)) {
						return 1;
					} else if (Double.parseDouble(info1.total) < Double.parseDouble(info2.total)) {
						return -1;
					} else {
						return 0;
					}
				} else {
					if (Double.parseDouble(info1.total) > Double.parseDouble(info2.total)) {
						return -1;
					} else if (Double.parseDouble(info1.total) < Double.parseDouble(info2.total)) {
						return 1;
					} else {
						return 0;
					}
				}
			default:
				if (_type) {
					return info1.database.compareTo(info2.database);
				} else {
					return info2.database.compareTo(info1.database);
				}
			}

		}

	}
}
