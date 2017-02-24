/**
 * 
 */
package com.jspmyadmin.app.table.partition.beans;

import java.text.DecimalFormat;

import com.jspmyadmin.framework.constants.BeanConstants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/08
 *
 */
public class PartitionInfo {

	private final DecimalFormat _format = new DecimalFormat("0.00");

	private String name = null;
	private String subname = null;
	private String method = null;
	private String sub_method = null;
	private String expression = null;
	private String sub_expression = null;
	private String description = null;
	private String table_rows = null;
	private String avg_row_length = null;
	private String data_length = null;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the subname
	 */
	public String getSubname() {
		return subname;
	}

	/**
	 * @param subname
	 *            the subname to set
	 */
	public void setSubname(String subname) {
		this.subname = subname;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the sub_method
	 */
	public String getSub_method() {
		return sub_method;
	}

	/**
	 * @param sub_method
	 *            the sub_method to set
	 */
	public void setSub_method(String sub_method) {
		this.sub_method = sub_method;
	}

	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * @param expression
	 *            the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * @return the sub_expression
	 */
	public String getSub_expression() {
		return sub_expression;
	}

	/**
	 * @param sub_expression
	 *            the sub_expression to set
	 */
	public void setSub_expression(String sub_expression) {
		this.sub_expression = sub_expression;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the table_rows
	 */
	public String getTable_rows() {
		return table_rows;
	}

	/**
	 * @param table_rows
	 *            the table_rows to set
	 */
	public void setTable_rows(String table_rows) {
		this.table_rows = table_rows;
	}

	/**
	 * @return the avg_row_length
	 */
	public String getAvg_row_length() {
		try {
			double temp = Double.parseDouble(avg_row_length);
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
				return avg_row_length + BeanConstants._B;
			}
		} catch (Exception e) {
		}
		return avg_row_length;
	}

	/**
	 * @param avg_row_length
	 *            the avg_row_length to set
	 */
	public void setAvg_row_length(String avg_row_length) {
		this.avg_row_length = avg_row_length;
	}

	/**
	 * @return the data_length
	 */
	public String getData_length() {
		try {
			double temp = Double.parseDouble(data_length);
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
				return data_length + BeanConstants._B;
			}
		} catch (Exception e) {
		}
		return data_length;
	}

	/**
	 * @param data_length
	 *            the data_length to set
	 */
	public void setData_length(String data_length) {
		this.data_length = data_length;
	}

}
