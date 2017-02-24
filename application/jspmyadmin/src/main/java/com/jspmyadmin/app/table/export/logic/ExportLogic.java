/**
 * 
 */
package com.jspmyadmin.app.table.export.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jspmyadmin.app.table.export.beans.ExportBean;
import com.jspmyadmin.framework.connection.AbstractLogic;
import com.jspmyadmin.framework.connection.ApiConnection;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.utils.Bean;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/27
 *
 */
public class ExportLogic extends AbstractLogic {

	/**
	 * 
	 * @param bean
	 * @throws SQLException
	 * @throws IOException
	 */
	public void fillBean(Bean bean) throws SQLException {
		ExportBean exportBean = (ExportBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ResultSetMetaData metaData = null;

		StringBuilder builder = null;
		try {
			apiConnection = getConnection(bean.getRequest_db());
			builder = new StringBuilder();
			builder.append("SELECT * FROM `");
			builder.append(bean.getRequest_table());
			builder.append("` WHERE 1 = 2");
			statement = apiConnection.getStmtSelect(builder.toString());
			resultSet = statement.executeQuery();
			metaData = resultSet.getMetaData();
			String[] column_list = new String[metaData.getColumnCount()];
			for (int i = 0; i < metaData.getColumnCount(); i++) {
				column_list[i] = metaData.getColumnName(i + 1);
			}
			exportBean.setColumn_list(column_list);
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
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws JSONException
	 */
	public File exportFile(Bean bean) throws SQLException, IOException, ClassNotFoundException,
			ParserConfigurationException, TransformerException, JSONException {
		ExportBean exportBean = (ExportBean) bean;

		ApiConnection apiConnection = null;
		PreparedStatement statement = null;
		PreparedStatement hexStatement = null;
		ResultSet hexResultSet = null;
		ResultSet resultSet = null;

		StringBuilder builder = null;

		File file = null;
		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			apiConnection = getConnection(bean.getRequest_db());

			builder = new StringBuilder("SELECT ");
			for (int i = 0; i < exportBean.getColumn_list().length; i++) {
				if (i != 0) {
					builder.append(Constants.SYMBOL_COMMA);
				}
				builder.append(Constants.SYMBOL_TEN);
				builder.append(exportBean.getColumn_list()[i]);
				builder.append(Constants.SYMBOL_TEN);
			}
			builder.append(" FROM `");
			builder.append(bean.getRequest_table());
			builder.append(Constants.SYMBOL_TEN);
			statement = apiConnection.getStmtSelect(builder.toString());
			resultSet = statement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

			file = new File(super.getTempFilePath());
			file.setExecutable(true, false);
			file.setReadable(true, false);
			file.setWritable(true, false);
			fileOutputStream = new FileOutputStream(file);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream);
			bufferedWriter = new BufferedWriter(outputStreamWriter);
			if (Constants.CSV.equalsIgnoreCase(exportBean.getExport_type())) {

				// 0 - string, 1 - number, 2 - binary
				int[] typeMap = new int[exportBean.getColumn_list().length];
				for (int i = 0; i < exportBean.getColumn_list().length; i++) {
					if (i != 0) {
						bufferedWriter.write(Constants.SYMBOL_COMMA);
					}
					bufferedWriter.write(Constants.SYMBOL_DOUBLE_QUOTE);
					bufferedWriter.write(exportBean.getColumn_list()[i]);
					bufferedWriter.write(Constants.SYMBOL_DOUBLE_QUOTE);

					String className = resultSetMetaData.getColumnClassName(i + 1);
					if (Constants.BYTE_TYPE.equals(className)) {
						typeMap[i] = 2;
					} else {
						typeMap[i] = 1;
					}
				}

				while (resultSet.next()) {
					bufferedWriter.newLine();
					for (int i = 0; i < exportBean.getColumn_list().length; i++) {
						if (i != 0) {
							bufferedWriter.write(Constants.SYMBOL_COMMA);
						}
						bufferedWriter.write(Constants.SYMBOL_DOUBLE_QUOTE);
						if (typeMap[i] == 2) {
							byte[] byteValue = resultSet.getBytes(i + 1);
							if (byteValue != null) {
								hexStatement = apiConnection.getStmtSelect("SELECT HEX(?) FROM DUAL");
								hexStatement.setBytes(1, byteValue);
								hexResultSet = hexStatement.executeQuery();
								hexResultSet.next();
								bufferedWriter.write(Constants.SYMBOL_HEX);
								bufferedWriter.write(hexResultSet.getString(1));
								close(hexResultSet);
								close(hexStatement);
							} else {
								bufferedWriter.write(Constants.NULL);
							}
						} else {
							String value = resultSet.getString(i + 1);
							if (value != null) {
								bufferedWriter.write(value);
							} else {
								bufferedWriter.write(Constants.NULL);
							}
						}
						bufferedWriter.write(Constants.SYMBOL_DOUBLE_QUOTE);
					}
				}
			} else if (Constants.XML.equalsIgnoreCase(exportBean.getExport_type())) {
				// 0 - string, 1 - number, 2 - binary
				int[] typeMap = new int[exportBean.getColumn_list().length];
				for (int i = 0; i < exportBean.getColumn_list().length; i++) {

					String className = resultSetMetaData.getColumnClassName(i + 1);
					if (Constants.BYTE_TYPE.equals(className)) {
						typeMap[i] = 2;
					} else {
						Class<?> klass = Class.forName(className);
						if (klass == Short.class || klass == Integer.class || klass == Long.class
								|| klass == Boolean.class || klass == Float.class || klass == Double.class
								|| klass == BigDecimal.class || klass == BigInteger.class) {
							typeMap[i] = 1;
						} else {
							typeMap[i] = 0;
						}
					}
				}

				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
				Document document = documentBuilder.newDocument();
				Element rootElement = document.createElement(Constants.JSPMYADMIN_EXPORT);
				rootElement.setAttribute(Constants.DATABASE, exportBean.getRequest_db());
				rootElement.setAttribute(Constants.TABLE, exportBean.getRequest_table());
				document.appendChild(rootElement);

				Element rowsElement = document.createElement(Constants.DATA);
				rootElement.appendChild(rowsElement);

				int count = 1;
				while (resultSet.next()) {
					Element rowElement = document.createElement(Constants.ROW);
					rowElement.setAttribute(Constants.COUNT, String.valueOf(count++));
					for (int i = 0; i < exportBean.getColumn_list().length; i++) {
						Element columnElement = document.createElement(Constants.COLUMN);
						columnElement.setAttribute(Constants.NAME, resultSetMetaData.getColumnName(i + 1));
						switch (typeMap[i]) {
						case 2:
							columnElement.setAttribute(Constants.TYPE, Constants.BINARY);
							byte[] byteValue = resultSet.getBytes(i + 1);
							if (byteValue != null) {
								hexStatement = apiConnection.getStmtSelect("SELECT HEX(?) FROM DUAL");
								hexStatement.setBytes(1, byteValue);
								hexResultSet = hexStatement.executeQuery();
								hexResultSet.next();
								columnElement.appendChild(document.createTextNode(hexResultSet.getString(1)));
								close(hexResultSet);
								close(hexStatement);
							} else {
								columnElement.appendChild(document.createTextNode(Constants.NULL));
							}
							break;
						case 1:
							columnElement.setAttribute(Constants.TYPE, Constants.NUMBER);
							String value = resultSet.getString(i + 1);
							if (value != null) {
								columnElement.appendChild(document.createTextNode(value));
							} else {
								columnElement.appendChild(document.createTextNode(Constants.NULL));
							}
							break;
						default:
							columnElement.setAttribute(Constants.TYPE, Constants.STRING);
							value = resultSet.getString(i + 1);
							if (value != null) {
								columnElement.appendChild(document.createTextNode(value));
							} else {
								columnElement.appendChild(document.createTextNode(Constants.NULL));
							}
							break;
						}
						rowElement.appendChild(columnElement);
					}
					rowsElement.appendChild(rowElement);
				}
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, Constants.YES.toLowerCase());
				DOMSource domSource = new DOMSource(document);
				StreamResult streamResult = new StreamResult(bufferedWriter);
				transformer.transform(domSource, streamResult);
			} else if (Constants.JSON.equalsIgnoreCase(exportBean.getExport_type())) {
				// 0 - string, 1 - number, 2 - binary
				int[] typeMap = new int[exportBean.getColumn_list().length];
				for (int i = 0; i < exportBean.getColumn_list().length; i++) {

					String className = resultSetMetaData.getColumnClassName(i + 1);
					if (Constants.BYTE_TYPE.equals(className)) {
						typeMap[i] = 2;
					} else {
						Class<?> klass = Class.forName(className);
						if (klass == Short.class || klass == Integer.class || klass == Long.class
								|| klass == Boolean.class || klass == Float.class || klass == Double.class
								|| klass == BigDecimal.class || klass == BigInteger.class) {
							typeMap[i] = 1;
						} else {
							typeMap[i] = 0;
						}
					}
				}
				bufferedWriter.write("[");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(Constants.TYPE, Constants.METADATA);
				jsonObject.put(Constants.DATABASE, exportBean.getRequest_db());
				jsonObject.put(Constants.TABLE, exportBean.getRequest_table());
				bufferedWriter.write(jsonObject.toString());
				bufferedWriter.write(Constants.SYMBOL_COMMA);
				bufferedWriter.newLine();
				bufferedWriter.write("{\"type\":\"data\",\"value\":[");
				bufferedWriter.newLine();
				while (resultSet.next()) {
					jsonObject = new JSONObject();
					for (int i = 0; i < exportBean.getColumn_list().length; i++) {
						String value = null;
						switch (typeMap[i]) {
						case 2:
							byte[] byteValue = resultSet.getBytes(i + 1);
							if (byteValue != null) {
								hexStatement = apiConnection.getStmtSelect("SELECT HEX(?) FROM DUAL");
								hexStatement.setBytes(1, byteValue);
								hexResultSet = hexStatement.executeQuery();
								hexResultSet.next();
								value = Constants.SYMBOL_HEX + hexResultSet.getString(1);
								close(hexResultSet);
								close(hexStatement);
							}
							break;
						case 1:
						default:
							value = resultSet.getString(i + 1);
							break;
						}
						if (value != null) {
							jsonObject.put(resultSetMetaData.getColumnName(i + 1), value);
						} else {
							jsonObject.put(resultSetMetaData.getColumnName(i + 1), Constants.NULL);
						}
					}
					bufferedWriter.write(jsonObject.toString());
					bufferedWriter.write(Constants.SYMBOL_COMMA);
					bufferedWriter.newLine();
				}
				bufferedWriter.write("]}]");
			}
		} finally {
			close(bufferedWriter);
			close(outputStreamWriter);
			close(fileOutputStream);
			close(hexResultSet);
			close(hexStatement);
			close(resultSet);
			close(statement);
			close(apiConnection);
		}
		return file;
	}
}
