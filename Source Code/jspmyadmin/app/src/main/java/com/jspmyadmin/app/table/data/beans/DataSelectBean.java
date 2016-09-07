/**
 * 
 */
package com.jspmyadmin.app.table.data.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/06/27
 *
 */
public class DataSelectBean extends Bean {

	private static final long serialVersionUID = 1L;

	private String query = null;
	private String limit = Constants.FETCH_LIMIT;
	private Map<String, String> column_name_map = null;
	private String[] search_columns = null;
	private String[] search_list = null;
	private String show_search = null;
	private List<List<String>> select_list = null;
	private String primary_key = null;
	private List<String> primary_key_list = null;
	private String sort_by = null;
	private String sort_type = null;

	private String current_page = null;
	private String next_page = null;
	private String previous_page = null;
	private String reload_page = null;
	private String exec_time = null;

	private String[] ids = null;

	private List<String> limit_list = new ArrayList<String>(Constants.Utils.LIMIT_LIST);
	private EncodeHelper encodeObj = null;

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the limit
	 */
	public String getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(String limit) {
		this.limit = limit;
	}

	/**
	 * @return the column_name_map
	 */
	public Map<String, String> getColumn_name_map() {
		return column_name_map;
	}

	/**
	 * @param column_name_map
	 *            the column_name_map to set
	 */
	public void setColumn_name_map(Map<String, String> column_name_map) {
		this.column_name_map = column_name_map;
	}

	/**
	 * @return the search_columns
	 */
	public String[] getSearch_columns() {
		return search_columns;
	}

	/**
	 * @param search_columns
	 *            the search_columns to set
	 */
	public void setSearch_columns(String[] search_columns) {
		this.search_columns = search_columns;
	}

	/**
	 * @return the search_list
	 */
	public String[] getSearch_list() {
		return search_list;
	}

	/**
	 * @param search_list
	 *            the search_list to set
	 */
	public void setSearch_list(String[] search_list) {
		this.search_list = search_list;
	}

	/**
	 * @return the show_search
	 */
	public String getShow_search() {
		return show_search;
	}

	/**
	 * @param show_search
	 *            the show_search to set
	 */
	public void setShow_search(String show_search) {
		this.show_search = show_search;
	}

	/**
	 * @return the select_list
	 */
	public List<List<String>> getSelect_list() {
		return select_list;
	}

	/**
	 * @param select_list
	 *            the select_list to set
	 */
	public void setSelect_list(List<List<String>> select_list) {
		this.select_list = select_list;
	}

	/**
	 * @return the limit_list
	 */
	public List<String> getLimit_list() {
		return limit_list;
	}

	/**
	 * @param limit_list
	 *            the limit_list to set
	 */
	public void setLimit_list(List<String> limit_list) {
		this.limit_list = limit_list;
	}

	/**
	 * @return the primary_key
	 */
	public String getPrimary_key() {
		return primary_key;
	}

	/**
	 * @param primary_key
	 *            the primary_key to set
	 */
	public void setPrimary_key(String primary_key) {
		this.primary_key = primary_key;
	}

	/**
	 * @return the sort_by
	 */
	public String getSort_by() {
		return sort_by;
	}

	/**
	 * @param sort_by
	 *            the sort_by to set
	 */
	public void setSort_by(String sort_by) {
		this.sort_by = sort_by;
	}

	/**
	 * @return the sort_type
	 */
	public String getSort_type() {
		return sort_type;
	}

	/**
	 * @param sort_type
	 *            the sort_type to set
	 */
	public void setSort_type(String sort_type) {
		this.sort_type = sort_type;
	}

	/**
	 * @return the current_page
	 */
	public String getCurrent_page() {
		return current_page;
	}

	/**
	 * @param current_page
	 *            the current_page to set
	 */
	public void setCurrent_page(String current_page) {
		this.current_page = current_page;
	}

	/**
	 * @return the next_page
	 */
	public String getNext_page() {
		return next_page;
	}

	/**
	 * @param next_page
	 *            the next_page to set
	 */
	public void setNext_page(String next_page) {
		this.next_page = next_page;
	}

	/**
	 * @return the previous_page
	 */
	public String getPrevious_page() {
		return previous_page;
	}

	/**
	 * @param previous_page
	 *            the previous_page to set
	 */
	public void setPrevious_page(String previous_page) {
		this.previous_page = previous_page;
	}

	/**
	 * @return the reload_page
	 */
	public String getReload_page() {
		return reload_page;
	}

	/**
	 * @param reload_page
	 *            the reload_page to set
	 */
	public void setReload_page(String reload_page) {
		this.reload_page = reload_page;
	}

	/**
	 * @return the exec_time
	 */
	public String getExec_time() {
		return exec_time;
	}

	/**
	 * @param exec_time
	 *            the exec_time to set
	 */
	public void setExec_time(String exec_time) {
		this.exec_time = exec_time;
	}

	/**
	 * @return the primary_key_list
	 */
	public List<String> getPrimary_key_list() {
		return primary_key_list;
	}

	/**
	 * @param primary_key_list
	 *            the primary_key_list to set
	 */
	public void setPrimary_key_list(List<String> primary_key_list) {
		this.primary_key_list = primary_key_list;
	}

	/**
	 * @return the ids
	 */
	public String[] getIds() {
		return ids;
	}

	/**
	 * @param ids
	 *            the ids to set
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// extra getters

	/**
	 * 
	 * @return
	 */
	public int getTotal_data_count() {
		if (select_list != null) {
			return select_list.size();
		}
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getColumn_count() {
		if (column_name_map != null) {
			return column_name_map.size();
		}
		return 0;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public String search_list_item(int index) {
		if (search_list != null && index < search_list.length) {
			return search_list[index];
		}
		return null;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public String primary_key_item(int index) {
		if (primary_key_list != null && index < primary_key_list.size()) {
			return primary_key_list.get(index);
		}
		return null;
	}

	/**
	 * @param encodeObj
	 *            the encodeObj to set
	 */
	public void setEncodeObj(EncodeHelper encodeObj) {
		this.encodeObj = encodeObj;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public String primary_key_token(int index) {
		if (primary_key_list != null && index < primary_key_list.size()) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put(Constants.PK_VAL, primary_key_list.get(index));
				return encodeObj.encode(jsonObject.toString());
			} catch (JSONException e) {
			} catch (EncodingException e) {
			}
		}
		return null;
	}
}
