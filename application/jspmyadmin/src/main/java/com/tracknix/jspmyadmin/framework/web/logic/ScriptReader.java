package com.tracknix.jspmyadmin.framework.web.logic;

import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class ScriptReader {

    private String separator;
    private List<String> statements = new LinkedList<String>();
    @Getter
    private List<StatementResult> successList;
    @Getter
    private List<StatementResult> errorList;
    @Getter
    private long elapsedTime = -1;
    @Getter
    private ErrorType errorType = ErrorType.NO_ERROR;
    @Getter
    private String error;
    @Getter
    private String errorStatement;

    /**
     * @param separator {@link String}
     */
    public ScriptReader(String separator) {
        this.separator = separator;
    }

    /**
     * @param inputStreamReader {@link InputStreamReader}
     * @throws IOException e
     */
    public void readScript(InputStreamReader inputStreamReader) throws IOException {
        long startTime = System.currentTimeMillis();
        LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);
        try {
            String currentStatement = lineNumberReader.readLine();
            StringBuilder scriptBuilder = new StringBuilder();
            while (currentStatement != null) {
                if (!currentStatement.startsWith(Constants.ONE_LINE_COMMENT)) {
                    if (scriptBuilder.length() > 0) {
                        scriptBuilder.append('\n');
                    }
                    scriptBuilder.append(currentStatement);
                }
                currentStatement = lineNumberReader.readLine();
            }
            this.appendSeparatorToScriptIfNecessary(scriptBuilder);
            String script = scriptBuilder.toString();
            if (separator == null) {
                // default separator
                separator = Constants.SYMBOL_SEMI_COLON;
            }
            if (!containsSqlScriptDelimiters(script, separator)) {
                // fallback separator
                separator = "\n";
            }
            this.splitScript(script);
        } finally {
            lineNumberReader.close();
        }
        elapsedTime = System.currentTimeMillis() - startTime;
    }

    /**
     * @param scriptBuilder {@link StringBuilder}
     */
    private void appendSeparatorToScriptIfNecessary(StringBuilder scriptBuilder) {
        if (separator == null) {
            return;
        }
        String trimmed = separator.trim();
        if (trimmed.length() == separator.length()) {
            return;
        }
        if (scriptBuilder.lastIndexOf(trimmed) == scriptBuilder.length() - trimmed.length()) {
            scriptBuilder.append(separator.substring(trimmed.length()));
        }
    }

    /**
     * @param script String
     */
    private void splitScript(String script) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean inLiteral = false;
        boolean inEscape = false;
        char[] content = script.toCharArray();
        for (int i = 0; i < script.length(); i++) {
            char c = content[i];
            if (inEscape) {
                inEscape = false;
                stringBuilder.append(c);
                continue;
            }
            if (c == '\\') {
                inEscape = true;
                stringBuilder.append(c);
                continue;
            }
            if (c == '\'') {
                inLiteral = !inLiteral;
            }
            if (!inLiteral) {
                if (script.startsWith(separator, i)) {
                    if (stringBuilder.length() > 0) {
                        statements.add(stringBuilder.toString());
                        stringBuilder = new StringBuilder();
                    }
                    i += separator.length() - 1;
                    continue;
                } else if (script.startsWith(Constants.ONE_LINE_COMMENT, i)) {
                    int indexOfNextNewline = script.indexOf("\n", i);
                    if (indexOfNextNewline > i) {
                        i = indexOfNextNewline;
                        continue;
                    } else {
                        break;
                    }
                } else if (script.startsWith(Constants.SYMBOL_SQL_MULTI_START, i)) {
                    int indexOfCommentEnd = script.indexOf(Constants.SYMBOL_SQL_MULTI_END, i);
                    if (indexOfCommentEnd > i) {
                        i = indexOfCommentEnd + Constants.SYMBOL_SQL_MULTI_END.length() - 1;
                        continue;
                    } else {
                        errorType = ErrorType.SCRIPT_ERROR;
                        error = String.format("Missing block comment end delimiter [%s].", Constants.SYMBOL_SQL_MULTI_END);
                        return;
                    }
                } else if (c == ' ' || c == '\n' || c == '\t') {
                    if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) != ' ') {
                        c = ' ';
                    } else {
                        continue;
                    }
                }
            }
            stringBuilder.append(c);
        }
        if (stringBuilder.length() > 0) {
            statements.add(stringBuilder.toString());
        }
    }

    /**
     * @param script    String
     * @param delimiter String
     * @return boolean
     */
    private boolean containsSqlScriptDelimiters(String script, String delimiter) {
        boolean inLiteral = false;
        char[] content = script.toCharArray();
        for (int i = 0; i < script.length(); i++) {
            if (content[i] == '\'') {
                inLiteral = !inLiteral;
            }
            if (!inLiteral && script.startsWith(delimiter, i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param connectionHelper {@link ConnectionHelper}
     * @param continueOnError  boolean
     * @throws SQLException e
     */
    public void execute(ConnectionHelper connectionHelper, boolean continueOnError) throws SQLException {
        long startTime = System.currentTimeMillis();
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            apiConnection = connectionHelper.getConnection();
            for (String query : statements) {
                try {
                    statement = apiConnection.getStmt(query);
                    statement.execute();
                    int rowsAffected = statement.getUpdateCount();
                    if (successList == null) {
                        successList = new LinkedList<StatementResult>();
                    }
                    successList.add(new StatementResult(query, rowsAffected));
                } catch (SQLException e) {
                    errorType = ErrorType.STMT_ERROR;
                    if (continueOnError) {
                        if (errorList == null) {
                            errorList = new LinkedList<StatementResult>();
                        }
                        errorList.add(new StatementResult(query, -1));
                    } else {
                        errorStatement = query;
                        throw e;
                    }
                }
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        elapsedTime += System.currentTimeMillis() - startTime;
    }

    /**
     * To keep statement result list
     */
    public static class StatementResult {
        @Getter
        private final String statement;
        @Getter
        private final int affectedRowCount;

        /**
         * Constructor
         *
         * @param statement        {@link String}
         * @param affectedRowCount int
         */
        private StatementResult(String statement, int affectedRowCount) {
            this.statement = statement;
            this.affectedRowCount = affectedRowCount;
        }
    }

    /**
     *
     */
    public enum ErrorType {
        NO_ERROR, SCRIPT_ERROR, STMT_ERROR
    }
}
