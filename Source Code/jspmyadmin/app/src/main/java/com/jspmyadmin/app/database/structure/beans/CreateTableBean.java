/**
 * 
 */
package com.jspmyadmin.app.database.structure.beans;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jspmyadmin.app.common.logic.HomeLogic;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.app.server.common.logic.EngineLogic;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
public class CreateTableBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String table_name = null;
	private String collation = null;
	private String engine = null;
	private String partition = null;
	private String partition_val = null;
	private String partitions = null;
	private String comment = null;
	private String[] columns = null;
	private String[] datatypes = null;
	private String[] lengths = null;
	private String[] defaults = null;
	private String[] collations = null;
	private String[] pks = null;
	private String[] nns = null;
	private String[] uqs = null;
	private String[] bins = null;
	private String[] uns = null;
	private String[] zfs = null;
	private String[] ais = null;
	private String[] comments = null;
	private String action = null;

	private Map<String, List<String>> data_types_map = null;
	private Map<String, List<String>> collation_map = null;
	private List<String> engine_list = null;
	private List<String> partition_list = Constants.Utils.PARTITION_LIST;
	private String partition_support = null;

	/**
	 * 
	 */
	public CreateTableBean() {
		try {
			HomeLogic homeLogic = new HomeLogic();
			collation_map = homeLogic.getCollationMap();
			EngineLogic engineLogic = new EngineLogic();
			engine_list = engineLogic.getEngineList();
			StructureLogic structureLogic = new StructureLogic();
			partition_support = Boolean.toString(structureLogic.isSupportsPartition());
		} catch (SQLException e) {
		}
	}

	/**
	 * @return the table_name
	 */
	public String getTable_name() {
		return table_name;
	}

	/**
	 * @param table_name
	 *            the table_name to set
	 */
	public void setTable_name(String table_name) {
		this.table_name = table_name;
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
	 * @return the partition
	 */
	public String getPartition() {
		return partition;
	}

	/**
	 * @param partition
	 *            the partition to set
	 */
	public void setPartition(String partition) {
		this.partition = partition;
	}

	/**
	 * @return the partition_val
	 */
	public String getPartition_val() {
		return partition_val;
	}

	/**
	 * @param partition_val
	 *            the partition_val to set
	 */
	public void setPartition_val(String partition_val) {
		this.partition_val = partition_val;
	}

	/**
	 * @return the partitions
	 */
	public String getPartitions() {
		return partitions;
	}

	/**
	 * @param partitions
	 *            the partitions to set
	 */
	public void setPartitions(String partitions) {
		this.partitions = partitions;
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
	 * @return the columns
	 */
	public String[] getColumns() {
		return columns;
	}

	/**
	 * @param columns
	 *            the columns to set
	 */
	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	/**
	 * @return the datatypes
	 */
	public String[] getDatatypes() {
		return datatypes;
	}

	/**
	 * @param datatypes
	 *            the datatypes to set
	 */
	public void setDatatypes(String[] datatypes) {
		this.datatypes = datatypes;
	}

	/**
	 * @return the lengths
	 */
	public String[] getLengths() {
		return lengths;
	}

	/**
	 * @param lengths
	 *            the lengths to set
	 */
	public void setLengths(String[] lengths) {
		this.lengths = lengths;
	}

	/**
	 * @return the defaults
	 */
	public String[] getDefaults() {
		return defaults;
	}

	/**
	 * @param defaults
	 *            the defaults to set
	 */
	public void setDefaults(String[] defaults) {
		this.defaults = defaults;
	}

	/**
	 * @return the collations
	 */
	public String[] getCollations() {
		return collations;
	}

	/**
	 * @param collations
	 *            the collations to set
	 */
	public void setCollations(String[] collations) {
		this.collations = collations;
	}

	/**
	 * @return the pks
	 */
	public String[] getPks() {
		return pks;
	}

	/**
	 * @param pks
	 *            the pks to set
	 */
	public void setPks(String[] pks) {
		this.pks = pks;
	}

	/**
	 * @return the nns
	 */
	public String[] getNns() {
		return nns;
	}

	/**
	 * @param nns
	 *            the nns to set
	 */
	public void setNns(String[] nns) {
		this.nns = nns;
	}

	/**
	 * @return the uqs
	 */
	public String[] getUqs() {
		return uqs;
	}

	/**
	 * @param uqs
	 *            the uqs to set
	 */
	public void setUqs(String[] uqs) {
		this.uqs = uqs;
	}

	/**
	 * @return the bins
	 */
	public String[] getBins() {
		return bins;
	}

	/**
	 * @param bins
	 *            the bins to set
	 */
	public void setBins(String[] bins) {
		this.bins = bins;
	}

	/**
	 * @return the uns
	 */
	public String[] getUns() {
		return uns;
	}

	/**
	 * @param uns
	 *            the uns to set
	 */
	public void setUns(String[] uns) {
		this.uns = uns;
	}

	/**
	 * @return the zfs
	 */
	public String[] getZfs() {
		return zfs;
	}

	/**
	 * @param zfs
	 *            the zfs to set
	 */
	public void setZfs(String[] zfs) {
		this.zfs = zfs;
	}

	/**
	 * @return the ais
	 */
	public String[] getAis() {
		return ais;
	}

	/**
	 * @param ais
	 *            the ais to set
	 */
	public void setAis(String[] ais) {
		this.ais = ais;
	}

	/**
	 * @return the comments
	 */
	public String[] getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String[] comments) {
		this.comments = comments;
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
	 * @return the data_types_map
	 */
	public Map<String, List<String>> getData_types_map() {
		return data_types_map;
	}

	/**
	 * @param data_types_map
	 *            the data_types_map to set
	 */
	public void setData_types_map(Map<String, List<String>> data_types_map) {
		this.data_types_map = data_types_map;
	}

	/**
	 * @return the collation_map
	 */
	public Map<String, List<String>> getCollation_map() {
		return collation_map;
	}

	/**
	 * @param collation_map
	 *            the collation_map to set
	 */
	public void setCollation_map(Map<String, List<String>> collation_map) {
		this.collation_map = collation_map;
	}

	/**
	 * @return the engine_list
	 */
	public List<String> getEngine_list() {
		return engine_list;
	}

	/**
	 * @param engine_list
	 *            the engine_list to set
	 */
	public void setEngine_list(List<String> engine_list) {
		this.engine_list = engine_list;
	}

	/**
	 * @return the partition_list
	 */
	public List<String> getPartition_list() {
		return partition_list;
	}

	/**
	 * @param partition_list
	 *            the partition_list to set
	 */
	public void setPartition_list(List<String> partition_list) {
		this.partition_list = partition_list;
	}

	/**
	 * @return the partition_support
	 */
	public String getPartition_support() {
		return partition_support;
	}

	/**
	 * @param partition_support
	 *            the partition_support to set
	 */
	public void setPartition_support(String partition_support) {
		this.partition_support = partition_support;
	}

}
