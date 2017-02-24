/**
 * 
 */
package com.jspmyadmin.app.table.structure.beans;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jspmyadmin.app.common.logic.HomeLogic;
import com.jspmyadmin.app.server.common.logic.EngineLogic;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
public class AlterColumnBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String old_table_name = null;
	private String new_table_name = null;
	private String old_collation = null;
	private String new_collation = null;
	private String old_engine = null;
	private String new_engine = null;
	private String old_comments = null;
	private String new_comments = null;

	private String[] changes = null;
	private String[] deletes = null;
	private String[] old_columns = null;
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

	/**
	 * 
	 */
	public AlterColumnBean() {
		try {
			HomeLogic homeLogic = new HomeLogic();
			collation_map = homeLogic.getCollationMap();
			EngineLogic engineLogic = new EngineLogic();
			engine_list = engineLogic.getEngineList();
		} catch (SQLException e) {
		}
	}

	/**
	 * @return the old_table_name
	 */
	public String getOld_table_name() {
		return old_table_name;
	}

	/**
	 * @param old_table_name
	 *            the old_table_name to set
	 */
	public void setOld_table_name(String old_table_name) {
		this.old_table_name = old_table_name;
	}

	/**
	 * @return the new_table_name
	 */
	public String getNew_table_name() {
		return new_table_name;
	}

	/**
	 * @param new_table_name
	 *            the new_table_name to set
	 */
	public void setNew_table_name(String new_table_name) {
		this.new_table_name = new_table_name;
	}

	/**
	 * @return the old_collation
	 */
	public String getOld_collation() {
		return old_collation;
	}

	/**
	 * @param old_collation
	 *            the old_collation to set
	 */
	public void setOld_collation(String old_collation) {
		this.old_collation = old_collation;
	}

	/**
	 * @return the new_collation
	 */
	public String getNew_collation() {
		return new_collation;
	}

	/**
	 * @param new_collation
	 *            the new_collation to set
	 */
	public void setNew_collation(String new_collation) {
		this.new_collation = new_collation;
	}

	/**
	 * @return the old_engine
	 */
	public String getOld_engine() {
		return old_engine;
	}

	/**
	 * @param old_engine
	 *            the old_engine to set
	 */
	public void setOld_engine(String old_engine) {
		this.old_engine = old_engine;
	}

	/**
	 * @return the new_engine
	 */
	public String getNew_engine() {
		return new_engine;
	}

	/**
	 * @param new_engine
	 *            the new_engine to set
	 */
	public void setNew_engine(String new_engine) {
		this.new_engine = new_engine;
	}

	/**
	 * @return the old_comments
	 */
	public String getOld_comments() {
		return old_comments;
	}

	/**
	 * @param old_comments
	 *            the old_comments to set
	 */
	public void setOld_comments(String old_comments) {
		this.old_comments = old_comments;
	}

	/**
	 * @return the new_comments
	 */
	public String getNew_comments() {
		return new_comments;
	}

	/**
	 * @param new_comments
	 *            the new_comments to set
	 */
	public void setNew_comments(String new_comments) {
		this.new_comments = new_comments;
	}

	/**
	 * @return the changes
	 */
	public String[] getChanges() {
		return changes;
	}

	/**
	 * @param changes
	 *            the changes to set
	 */
	public void setChanges(String[] changes) {
		this.changes = changes;
	}

	/**
	 * @return the deletes
	 */
	public String[] getDeletes() {
		return deletes;
	}

	/**
	 * @param deletes
	 *            the deletes to set
	 */
	public void setDeletes(String[] deletes) {
		this.deletes = deletes;
	}

	/**
	 * @return the old_columns
	 */
	public String[] getOld_columns() {
		return old_columns;
	}

	/**
	 * @param old_columns
	 *            the old_columns to set
	 */
	public void setOld_columns(String[] old_columns) {
		this.old_columns = old_columns;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// extra methods
	public String columns(int i) {
		if (i < columns.length) {
			return columns[i];
		}
		return null;
	}

	public String datatypes(int i) {
		if (i < datatypes.length) {
			return datatypes[i];
		}
		return null;
	}

	public String lengths(int i) {
		if (i < lengths.length) {
			return lengths[i];
		}
		return null;
	}

	public String defaults(int i) {
		if (i < defaults.length) {
			return defaults[i];
		}
		return null;
	}

	public String collations(int i) {
		if (i < collations.length) {
			return collations[i];
		}
		return null;
	}

	public String pks(int i) {
		if (i < pks.length) {
			return pks[i];
		}
		return null;
	}

	public String nns(int i) {
		if (i < nns.length) {
			return nns[i];
		}
		return null;
	}

	public String uqs(int i) {
		if (i < uqs.length) {
			return uqs[i];
		}
		return null;
	}

	public String uns(int i) {
		if (i < uns.length) {
			return uns[i];
		}
		return null;
	}

	public String zfs(int i) {
		if (i < zfs.length) {
			return zfs[i];
		}
		return null;
	}

	public String ais(int i) {
		if (i < ais.length) {
			return ais[i];
		}
		return null;
	}

	public String comments(int i) {
		if (i < comments.length) {
			return comments[i];
		}
		return null;
	}

}
