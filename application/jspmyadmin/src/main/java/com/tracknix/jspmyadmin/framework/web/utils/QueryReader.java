package com.tracknix.jspmyadmin.framework.web.utils;

import com.tracknix.jspmyadmin.framework.constants.Constants;
import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Formatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
class QueryReader {
    private static final String PATH = "com/tracknix/jspmyadmin/query/queries.xml";
    private static final String LOGIC = "logic";
    private static final String CLASS = "class";
    private static final String QUERY = "query";
    private static final String INDEX = "index";
    private static final String VALUE = "value";
    private static final String COLUMNS = "columns";
    private static final String PART = "part";
    private static final Logger LOGGER = Logger.getLogger(QueryReader.class.getName());
    private static final Map<Class<?>, QueryInfo[]> _QUERY_MAP = new ConcurrentHashMap<Class<?>, QueryInfo[]>();

    static synchronized void read() throws IOException {
        if (_QUERY_MAP.size() > 0) {
            return;
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = null;
        try {
            inputStream = classLoader.getResourceAsStream(PATH);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            Element root = document.getDocumentElement();
            // Read logic tags
            NodeList logicNodeList = root.getElementsByTagName(LOGIC);
            for (int logicTagIndex = 0; logicTagIndex < logicNodeList.getLength(); logicTagIndex++) {
                Node logicNode = logicNodeList.item(logicTagIndex);
                if (logicNode instanceof Element) {
                    try {
                        Element logicElement = (Element) logicNode;
                        // Read class attribute
                        Class<?> klass = Class.forName(logicElement.getAttributes().getNamedItem(CLASS).getNodeValue());
                        // Read query tags
                        NodeList queryNodeList = logicElement.getElementsByTagName(QUERY);
                        QueryInfo[] queries = new QueryInfo[queryNodeList.getLength()];
                        for (int queryTagIndex = 0; queryTagIndex < queryNodeList.getLength(); queryTagIndex++) {
                            Element queryElement = (Element) queryNodeList.item(queryTagIndex);
                            // read query index attribute
                            int queryIndex = Integer.parseInt(queryElement.getAttributes().getNamedItem(INDEX).getNodeValue());
                            // read value tag
                            Node valueNode = queryElement.getElementsByTagName(VALUE).item(0);
                            // read value tag query attribute
                            boolean isQuery = Boolean.parseBoolean(valueNode.getAttributes().getNamedItem(QUERY).getNodeValue());
                            String query = null;
                            String[] parts = null;
                            if (isQuery) {
                                // read query
                                if (valueNode instanceof CharacterData) {
                                    query = ((CharacterData) valueNode).getData();
                                } else {
                                    query = valueNode.getTextContent();
                                }

                            } else {
                                // Read part tags
                                NodeList partNodeList = ((Element) valueNode).getElementsByTagName(PART);
                                parts = new String[partNodeList.getLength()];
                                for (int k = 0; k < partNodeList.getLength(); k++) {
                                    Node partNode = partNodeList.item(k);
                                    // read index
                                    int partIndex = Integer.parseInt(partNode.getAttributes().getNamedItem(INDEX).getNodeValue());
                                    if (partNode instanceof CharacterData) {
                                        parts[partIndex - 1] = ((CharacterData) partNode).getData();
                                    } else {
                                        parts[partIndex - 1] = partNode.getTextContent();
                                    }
                                }
                            }
                            // read columns
                            NodeList columnNodeList = queryElement.getElementsByTagName(COLUMNS);
                            String[] columns = null;
                            if (columnNodeList.getLength() > 0) {
                                columns = columnNodeList.item(0).getTextContent().split(Constants.SYMBOL_COMMA);
                            }
                            queries[queryIndex - 1] = new QueryInfo(isQuery, query, parts, columns);
                        }
                        _QUERY_MAP.put(klass, queries);
                    } catch (ClassNotFoundException e) {
                        LOGGER.log(Level.WARNING, "Class not found", e);
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            LOGGER.log(Level.WARNING, "Unable to open xml document", e);
        } catch (SAXException e) {
            LOGGER.log(Level.WARNING, "XML parse exception", e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * To hold query information
     */
    private static class QueryInfo {
        private final boolean isQuery;
        private final String query;
        private final String[] parts;
        private final String[] columns;

        private QueryInfo(boolean isQuery, String query, String[] parts, String[] columns) {
            this.isQuery = isQuery;
            this.query = query;
            this.parts = parts;
            this.columns = columns;
        }
    }

    /**
     * {@link QueryHelper} implemented class
     */
    static class QueryHelperIml implements QueryHelper {

        private final QueryInfo[] queryInfos;

        /**
         * Constructor.
         *
         * @param klass {@link Class}
         */
        QueryHelperIml(Class<?> klass) {
            queryInfos = _QUERY_MAP.get(klass);
        }

        @Override
        public boolean isQuery(int queryIndex) {
            return queryInfos[queryIndex].isQuery;
        }

        @Override
        public String getQuery(int queryIndex, Object... args) {
            if (args != null && args.length > 0) {
                return String.format(queryInfos[queryIndex - 1].query, args);
            }
            return queryInfos[queryIndex - 1].query;
        }

        @Override
        public String getPart(int queryIndex, int partIndex, Object... args) {
            if (args != null && args.length > 0) {
                return String.format(queryInfos[queryIndex - 1].parts[partIndex - 1], args);
            }
            return queryInfos[queryIndex - 1].parts[partIndex - 1];
        }

        @Override
        public String[] getColumns(int queryIndex) {
            return queryInfos[queryIndex - 1].columns;
        }

    }
}
