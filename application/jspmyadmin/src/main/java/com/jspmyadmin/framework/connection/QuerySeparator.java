/**
 * 
 */
package com.jspmyadmin.framework.connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jspmyadmin.framework.constants.Constants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/14
 *
 */
public class QuerySeparator {

	private final String _script;
	private final File _file;
	private final List<String> queryList = new ArrayList<String>(1);

	/**
	 * 
	 * @param script
	 */
	public QuerySeparator(String script) {
		_file = null;
		_script = script;
		_generateQueriesScript();
	}

	/**
	 * 
	 * @param script
	 */
	public QuerySeparator(File file) {
		_script = null;
		_file = file;
		_generateQueriesFile();
	}

	/**
	 * 
	 */
	private void _generateQueriesScript() {
		String query = _script;
		int start = -1;
		StringBuilder builder = new StringBuilder();
		while ((start = query.indexOf(Constants.SYMBOL_SQL_MULTI_START)) > -1) {
			int end = query.indexOf(Constants.SYMBOL_SQL_MULTI_END);
			if (end == -1) {
				if (start == 0) {
					query = Constants.BLANK;
				} else {
					query = new String(query.substring(0, start - 1));
				}
			} else {
				if (start == 0) {
					query = new String(query.substring(end + 2));
				} else {
					builder.delete(0, builder.length());
					builder.append(new String(query.substring(0, start - 1)));
					builder.append(new String(query.substring(end + 2, query.length() - 1)));
					query = null;
					query = builder.toString();
					builder.delete(0, builder.length());
				}
			}
		}
		List<String> delimeterList = new ArrayList<String>();
		while (query != null && query.contains(Constants.DELIMITER_$$)) {
			int indexCheck = query.indexOf(Constants.DELIMITER_$$);
			if (indexCheck != 0) {
				String temp = new String(query.substring(0, indexCheck - 1));
				String[] qArray = temp.split(Constants.SYMBOL_SEMI_COLON);
				if (qArray != null && qArray.length > 0) {
					delimeterList.addAll(Arrays.asList(qArray));
				}
				query = new String(query.substring(temp.length()));
			}
			indexCheck = query.indexOf(Constants.DELIMITER_$$);
			if (query.contains(Constants.DELIMITER_COMMA)) {
				int index = query.indexOf(Constants.DELIMITER_COMMA);
				String temp = new String(query.substring(indexCheck + 12, index - 1));
				String[] qArray = temp.split(Constants.REGEX_$$);
				if (qArray != null && qArray.length > 0) {
					delimeterList.addAll(Arrays.asList(qArray));
				}
				query = new String(query.substring(index + 11));
			} else {
				String temp = new String(query.substring(indexCheck + 12));
				String[] qArray = temp.split(Constants.REGEX_$$);
				if (qArray != null && qArray.length > 0) {
					delimeterList.addAll(Arrays.asList(qArray));
				}
				if (query.length() > temp.length() + 13) {
					query = new String(query.substring(temp.length() + 13));
				} else {
					query = null;
				}
			}
		}
		if (query != null && !Constants.BLANK.equals(query)) {
			String[] qArray = query.split(Constants.SYMBOL_SEMI_COLON);
			if (qArray != null && qArray.length > 0) {
				delimeterList.addAll(Arrays.asList(qArray));
			}
		}

		String[] queries = new String[delimeterList.size()];
		queries = delimeterList.toArray(queries);
		for (int i = 0; i < queries.length; i++) {

			builder.delete(0, builder.length());
			String queryPart = queries[i];
			String[] queryParts = queryPart.split(Constants.REGEX_NEW_LINE);
			for (int j = 0; j < queryParts.length; j++) {
				int mIndex = -1;
				if ((mIndex = queryParts[j].indexOf(Constants.SYMBOL_HASH)) > -1
						|| (mIndex = queryParts[j].indexOf(Constants.SYMBOL_HIFEN_HIFEN)) > -1) {
					if (mIndex == 0) {
						queryParts[j] = null;
					} else {
						queryParts[j] = new String(queryParts[j].substring(0, mIndex - 1));
					}
				}
				if (queryParts[j] != null) {
					builder.append(Constants.SPACE);
					builder.append(queryParts[j]);
				}
			}
			String finalQuery = builder.toString();
			if (!Constants.BLANK.equals(finalQuery.trim())) {
				queryList.add(finalQuery);
			}
		}
	}

	/**
	 * 
	 */
	private void _generateQueriesFile() {
		StringBuilder builder = new StringBuilder();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(_file);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;

			int pastStart = -1;
			while ((line = bufferedReader.readLine()) != null) {
				int start = line.indexOf(Constants.SYMBOL_SQL_MULTI_START);
				int end = line.indexOf(Constants.SYMBOL_SQL_MULTI_END);
				if (start > -1 && end > -1) {
					pastStart = -1;
					end = end + 2;
					if (start == 0) {
						builder.append(new String(line.substring(end)));
					} else {
						builder.append(new String(line.substring(0, start - 1)));
						if (end < line.length()) {
							builder.append(new String(line.substring(end, line.length() - 1)));
						}
					}
				} else if (start > 0) {
					pastStart = start;
					builder.append(new String(line.substring(0, start - 1)));
				} else if (end > -1) {
					pastStart = -1;
					end = end + 2;
					if (end < line.length()) {
						builder.append(new String(line.substring(end, line.length() - 1)));
					}
				} else if (pastStart == -1) {
					builder.append(line);
					builder.append(Constants.NEW_LINE);
				}
			}
		} catch (IOException e) {

		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
				}
			}
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
				}
			}
		}
		String query = builder.toString();
		List<String> delimeterList = new ArrayList<String>();
		while (query != null && query.contains(Constants.DELIMITER_$$)) {
			int indexCheck = query.indexOf(Constants.DELIMITER_$$);
			if (indexCheck != 0) {
				String temp = new String(query.substring(0, indexCheck - 1));
				String[] qArray = temp.split(Constants.SYMBOL_SEMI_COLON);
				if (qArray != null && qArray.length > 0) {
					delimeterList.addAll(Arrays.asList(qArray));
				}
				query = new String(query.substring(temp.length()));
			}
			indexCheck = query.indexOf(Constants.DELIMITER_$$);
			if (query.contains(Constants.DELIMITER_COMMA)) {
				int index = query.indexOf(Constants.DELIMITER_COMMA);
				String temp = new String(query.substring(indexCheck + 12, index - 1));
				String[] qArray = temp.split(Constants.REGEX_$$);
				if (qArray != null && qArray.length > 0) {
					delimeterList.addAll(Arrays.asList(qArray));
				}
				query = new String(query.substring(index + 11));
			} else {
				String temp = new String(query.substring(indexCheck + 12));
				String[] qArray = temp.split(Constants.REGEX_$$);
				if (qArray != null && qArray.length > 0) {
					delimeterList.addAll(Arrays.asList(qArray));
				}
				if (query.length() > temp.length() + 13) {
					query = new String(query.substring(temp.length() + 13));
				} else {
					query = null;
				}
			}
		}
		if (query != null && !Constants.BLANK.equals(query)) {
			String[] qArray = query.split(Constants.SYMBOL_SEMI_COLON);
			if (qArray != null && qArray.length > 0) {
				delimeterList.addAll(Arrays.asList(qArray));
			}
		}

		String[] queries = new String[delimeterList.size()];
		queries = delimeterList.toArray(queries);
		for (int i = 0; i < queries.length; i++) {

			builder.delete(0, builder.length());
			String queryPart = queries[i];
			String[] queryParts = queryPart.split(Constants.REGEX_NEW_LINE);
			for (int j = 0; j < queryParts.length; j++) {
				int mIndex = -1;
				if ((mIndex = queryParts[j].indexOf(Constants.SYMBOL_HASH)) > -1
						|| (mIndex = queryParts[j].indexOf(Constants.SYMBOL_HIFEN_HIFEN)) > -1) {
					if (mIndex == 0) {
						queryParts[j] = null;
					} else {
						queryParts[j] = new String(queryParts[j].substring(0, mIndex - 1));
					}
				}
				if (queryParts[j] != null) {
					builder.append(Constants.SPACE);
					builder.append(queryParts[j]);
				}
			}
			String finalQuery = builder.toString();
			if (!Constants.BLANK.equals(finalQuery.trim())) {
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
