/**
 * 
 */
package com.jspmyadmin.app.table.structure.beans;

import java.io.Serializable;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/04/13
 *
 */
public class IndexInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String column_name = null;
	private String key_name = null;
	private String non_unique = null;
	private String null_yes = null;
	private String index_type = null;
	private String collation = null;
	private String cardinality = null;
	private String comment = null;

	/**
	 * @return the column_name
	 */
	public String getColumn_name() {
		return column_name;
	}

	/**
	 * @param column_name
	 *            the column_name to set
	 */
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	/**
	 * @return the key_name
	 */
	public String getKey_name() {
		return key_name;
	}

	/**
	 * @param key_name
	 *            the key_name to set
	 */
	public void setKey_name(String key_name) {
		this.key_name = key_name;
	}

	/**
	 * @return the non_unique
	 */
	public String getNon_unique() {
		return non_unique;
	}

	/**
	 * @param non_unique
	 *            the non_unique to set
	 */
	public void setNon_unique(String non_unique) {
		this.non_unique = non_unique;
	}

	/**
	 * @return the null_yes
	 */
	public String getNull_yes() {
		return null_yes;
	}

	/**
	 * @param null_yes
	 *            the null_yes to set
	 */
	public void setNull_yes(String null_yes) {
		this.null_yes = null_yes;
	}

	/**
	 * @return the index_type
	 */
	public String getIndex_type() {
		return index_type;
	}

	/**
	 * @param index_type
	 *            the index_type to set
	 */
	public void setIndex_type(String index_type) {
		this.index_type = index_type;
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
	 * @return the cardinality
	 */
	public String getCardinality() {
		return cardinality;
	}

	/**
	 * @param cardinality
	 *            the cardinality to set
	 */
	public void setCardinality(String cardinality) {
		this.cardinality = cardinality;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
