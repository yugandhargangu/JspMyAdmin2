/**
 * 
 */
package com.jspmyadmin.framework.connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jspmyadmin.framework.constants.FrameworkConstants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/14
 *
 */
public class QuerySeparator {

	private final String _script;
	private final List<String> queryList = new ArrayList<String>(1);

	/**
	 * 
	 * @param script
	 */
	public QuerySeparator(String script) {
		_script = script;
		_generateQueries();
	}

	/**
	 * 
	 */
	private void _generateQueries() {
		String query = _script;
		int start = -1;
		while ((start = query.indexOf("/*")) > -1) {
			int end = query.indexOf("*/");
			if (end == -1) {
				if (start == 0) {
					query = FrameworkConstants.BLANK;
				} else {
					query = query.substring(0, start - 1);
				}
			} else {
				if (start == 0) {
					query = query.substring(end + 2);
				} else {
					query = query.substring(0, start - 1) + query.substring(end + 2, query.length() - 1);
				}
			}
		}
		List<String> delimeterList = new ArrayList<String>();
		while (query != null && query.contains("DELIMITER $$")) {
			int indexCheck = query.indexOf("DELIMITER $$");
			if (indexCheck != 0) {
				String temp = query.substring(0, indexCheck - 1);
				String[] qArray = temp.split(";");
				if (qArray != null && qArray.length > 0) {
					delimeterList.addAll(Arrays.asList(qArray));
				}
				query = query.substring(temp.length());
			}
			indexCheck = query.indexOf("DELIMITER $$");
			if (query.contains("DELIMITER ;")) {
				int index = query.indexOf("DELIMITER ;");
				String temp = query.substring(indexCheck + 12, index - 1);
				String[] qArray = temp.split("\\$\\$");
				if (qArray != null && qArray.length > 0) {
					delimeterList.addAll(Arrays.asList(qArray));
				}
				query = query.substring(index + 11);
			} else {
				String temp = query.substring(indexCheck + 12);
				String[] qArray = temp.split("\\$\\$");
				if (qArray != null && qArray.length > 0) {
					delimeterList.addAll(Arrays.asList(qArray));
				}
				if (query.length() > temp.length() + 13) {
					query = query.substring(temp.length() + 13);
				} else {
					query = null;
				}
			}
		}
		if (query != null && !FrameworkConstants.BLANK.equals(query)) {
			String[] qArray = query.split(";");
			if (qArray != null && qArray.length > 0) {
				delimeterList.addAll(Arrays.asList(qArray));
			}
		}

		String[] queries = new String[delimeterList.size()];
		queries = delimeterList.toArray(queries);
		for (int i = 0; i < queries.length; i++) {

			StringBuilder builder = new StringBuilder();
			String queryPart = queries[i];
			String[] queryParts = queryPart.split("\\r\\n");
			for (int j = 0; j < queryParts.length; j++) {
				int mIndex = -1;
				if ((mIndex = queryParts[j].indexOf("#")) > -1 || (mIndex = queryParts[j].indexOf("--")) > -1) {
					if (mIndex == 0) {
						queryParts[j] = null;
					} else {
						queryParts[j] = queryParts[j].substring(0, mIndex - 1);
					}
				}
				if (queryParts[j] != null) {
					builder.append(FrameworkConstants.SPACE);
					builder.append(queryParts[j]);
				}
			}
			String finalQuery = builder.toString();
			if (!FrameworkConstants.BLANK.equals(finalQuery.trim())) {
				queryList.add(finalQuery);
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getQueries() {
		return queryList;
	}

}
