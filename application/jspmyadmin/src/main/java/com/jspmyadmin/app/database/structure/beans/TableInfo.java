/**
 * 
 */
package com.jspmyadmin.app.database.structure.beans;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.jspmyadmin.framework.constants.BeanConstants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/15
 *
 */
public class TableInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private final DecimalFormat _format = new DecimalFormat("0.00");

	private String name = null;
	private String type = null;
	private String engine = null;
	private String rows = null;
	private String collation = null;
	private String size = null;
	private String auto_inr = null;
	private String create_date = null;
	private String update_date = null;
	private String comment = null;
	private String action = null;

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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the engine
	 */
	public String getEngine() {
		return engine;
	}

	/**
	 * @param engine
	 *            the engine to set
	 */
	public void setEngine(String engine) {
		this.engine = engine;
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
	 * @return the size
	 */
	public String getSize() {
		try {
			double temp = Double.parseDouble(size);
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
				return size + BeanConstants._B;
			}
		} catch (Exception e) {
		}
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the auto_inr
	 */
	public String getAuto_inr() {
		return auto_inr;
	}

	/**
	 * @param auto_inr
	 *            the auto_inr to set
	 */
	public void setAuto_inr(String auto_inr) {
		this.auto_inr = auto_inr;
	}

	/**
	 * @return the create_date
	 */
	public String getCreate_date() {
		return create_date;
	}

	/**
	 * @param create_date
	 *            the create_date to set
	 */
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	/**
	 * @return the update_date
	 */
	public String getUpdate_date() {
		return update_date;
	}

	/**
	 * @param update_date
	 *            the update_date to set
	 */
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
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

}
