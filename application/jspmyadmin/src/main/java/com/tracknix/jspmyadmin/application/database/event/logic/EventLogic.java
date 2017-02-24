package com.tracknix.jspmyadmin.application.database.event.logic;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tracknix.jspmyadmin.application.database.event.beans.EventBean;
import com.tracknix.jspmyadmin.application.database.event.beans.EventInfo;
import com.tracknix.jspmyadmin.application.database.event.beans.EventListBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.utils.Messages;
import com.tracknix.jspmyadmin.framework.web.utils.RequestAdaptor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class EventLogic {

    @Detect
    private Messages messages;
    @Detect
    private ConnectionHelper connectionHelper;

    /**
     * @param eventListBean {@link EventListBean}
     * @throws SQLException e
     */
    public void fillListBean(EventListBean eventListBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StringBuilder builder = null;
        List<EventInfo> eventInfoList = null;
        EventInfo eventInfo = null;
        int total = 0;
        try {
            apiConnection = connectionHelper.getConnection(eventListBean.getRequest_db());
            builder = new StringBuilder();
            builder.append("SELECT event_name,definer,event_type,status,");
            builder.append("created,last_altered,event_comment FROM ");
            builder.append("information_schema.events WHERE event_schema = ? ");
            builder.append("ORDER BY event_name ASC");
            statement = apiConnection.getStmtSelect(builder.toString());
            statement.setString(1, eventListBean.getRequest_db());
            resultSet = statement.executeQuery();
            eventInfoList = new ArrayList<EventInfo>();
            while (resultSet.next()) {
                eventInfo = new EventInfo();
                eventInfo.setName(resultSet.getString(1));
                eventInfo.setDefiner(resultSet.getString(2));
                eventInfo.setType(resultSet.getString(3));
                eventInfo.setStatus(resultSet.getString(4));
                eventInfo.setCreate_date(resultSet.getString(5));
                eventInfo.setAlter_date(resultSet.getString(6));
                eventInfo.setComments(resultSet.getString(7));
                eventInfoList.add(eventInfo);
                total++;
            }
            eventListBean.setEvent_list(eventInfoList);
            eventListBean.setTotal(Integer.toString(total));
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param eventListBean {@link EventListBean}
     * @throws SQLException e
     */
    public void renameEvent(EventListBean eventListBean) throws SQLException {

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;

        StringBuilder builder = null;
        try {
            if (eventListBean.getEvents() != null && eventListBean.getEvents().length > 0) {
                apiConnection = connectionHelper.getConnection(eventListBean.getRequest_db());
                builder = new StringBuilder();
                builder.append("ALTER EVENT ");
                builder.append(Constants.SYMBOL_TEN);
                builder.append(eventListBean.getEvents()[0]);
                builder.append(Constants.SYMBOL_TEN);
                builder.append(" RENAME TO ");
                builder.append(Constants.SYMBOL_TEN);
                builder.append(eventListBean.getNew_event());
                builder.append(Constants.SYMBOL_TEN);
                statement = apiConnection.getStmt(builder.toString());
                statement.execute();
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param eventListBean {@link EventListBean}
     * @throws SQLException e
     */
    public void enableEvent(EventListBean eventListBean) throws SQLException {

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;

        StringBuilder builder = null;
        try {
            if (eventListBean.getEvents() != null) {
                String alter = "ALTER EVENT ";
                String enable = "ENABLE";
                apiConnection = connectionHelper.getConnection(eventListBean.getRequest_db());
                builder = new StringBuilder();
                for (int i = 0; i < eventListBean.getEvents().length; i++) {
                    builder.append(alter);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(eventListBean.getEvents()[i]);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(Constants.SPACE);
                    builder.append(enable);
                    statement = apiConnection.getStmt(builder.toString());
                    statement.execute();
                    statement = null;
                    builder.delete(0, builder.length());
                }
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param eventListBean {@link EventListBean}
     * @throws SQLException e
     */
    public void dropEvent(EventListBean eventListBean) throws SQLException {

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;

        StringBuilder builder = null;
        try {
            if (eventListBean.getEvents() != null) {
                String alter = "DROP EVENT IF EXISTS";
                apiConnection = connectionHelper.getConnection(eventListBean.getRequest_db());
                builder = new StringBuilder();
                for (int i = 0; i < eventListBean.getEvents().length; i++) {
                    builder.append(alter);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(eventListBean.getEvents()[i]);
                    builder.append(Constants.SYMBOL_TEN);
                    statement = apiConnection.getStmt(builder.toString());
                    statement.execute();
                    statement = null;
                    builder.delete(0, builder.length());
                }
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param eventListBean {@link EventListBean}
     * @throws SQLException e
     */
    public void disableEvent(EventListBean eventListBean) throws SQLException {

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;

        StringBuilder builder = null;
        try {
            if (eventListBean.getEvents() != null) {
                String alter = "ALTER EVENT ";
                String disable = "DISABLE";
                apiConnection = connectionHelper.getConnection(eventListBean.getRequest_db());
                builder = new StringBuilder();
                for (int i = 0; i < eventListBean.getEvents().length; i++) {
                    builder.append(alter);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(eventListBean.getEvents()[i]);
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(Constants.SPACE);
                    builder.append(disable);
                    statement = apiConnection.getStmt(builder.toString());
                    statement.execute();
                    statement = null;
                    builder.delete(0, builder.length());
                }
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param eventListBean {@link EventListBean}
     * @return query
     * @throws SQLException e
     */
    public String getShowCreate(EventListBean eventListBean) throws SQLException {

        String result = null;

        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        StringBuilder builder = null;
        try {
            if (eventListBean.getEvents() != null) {
                apiConnection = connectionHelper.getConnection(eventListBean.getRequest_db());
                String temp = "SHOW CREATE EVENT `";
                builder = new StringBuilder();
                ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
                for (int i = 0; i < eventListBean.getEvents().length; i++) {
                    builder.append(temp);
                    builder.append(eventListBean.getEvents()[i]);
                    builder.append(Constants.SYMBOL_TEN);
                    statement = apiConnection.getStmtSelect(builder.toString());
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        objectNode.put(resultSet.getString(1), resultSet.getString(4) + Constants.SYMBOL_SEMI_COLON);
                    }
                    resultSet.close();
                    resultSet = null;
                    statement.close();
                    statement = null;
                    builder.delete(0, builder.length());
                }
                result = objectNode.toString();
            }
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return result;
    }

    /**
     * @param name     name
     * @param database database
     * @return boolean
     * @throws SQLException e
     */
    public boolean isExisted(String name, String database) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StringBuilder builder = null;
        try {
            apiConnection = connectionHelper.getConnection(database);
            builder = new StringBuilder();
            builder.append("SELECT COUNT(event_name) FROM ");
            builder.append("information_schema.events WHERE ");
            builder.append("event_schema = ? AND event_name = ?");
            statement = apiConnection.getStmtSelect(builder.toString());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return false;
    }

    /**
     * @param eventBean {@link EventBean}
     * @return query
     * @throws SQLException e
     */
    public String saveEvent(EventBean eventBean) throws SQLException {

        String result = null;
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;

        StringBuilder builder = null;
        String[] temp = null;
        String interval = "+ INTERVAL ";
        try {
            apiConnection = connectionHelper.getConnection(eventBean.getRequest_db());
            builder = new StringBuilder();
            builder.append("CREATE ");
            if (!connectionHelper.isEmpty(eventBean.getDefiner())) {
                if (Constants.CURRENT_USER.equalsIgnoreCase(eventBean.getDefiner())) {
                    builder.append("DEFINER = ");
                    builder.append(eventBean.getDefiner());
                    builder.append(Constants.SPACE);
                } else if (!connectionHelper.isEmpty(eventBean.getDefiner_name())) {
                    temp = eventBean.getDefiner_name().split(Constants.SYMBOL_AT);
                    builder.append("DEFINER = ");
                    if (temp.length < 2) {
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(temp[0]);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(Constants.SPACE);
                    } else {
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(temp[0]);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(Constants.SYMBOL_AT);
                        builder.append(Constants.SYMBOL_TEN);
                        builder.append(temp[1]);
                        if (!temp[1].endsWith(Constants.SYMBOL_TEN)) {
                            builder.append(Constants.SYMBOL_TEN);
                        }
                        builder.append(Constants.SPACE);
                    }
                }
            }
            builder.append("EVENT ");
            if (Constants.YES.equalsIgnoreCase(eventBean.getNot_exists())) {
                builder.append("IF NOT EXISTS ");
            }
            builder.append(Constants.SYMBOL_TEN);
            builder.append(eventBean.getEvent_name());
            builder.append(Constants.SYMBOL_TEN);
            builder.append(Constants.SPACE);
            builder.append("ON SCHEDULE ");
            if (Constants.AT.equalsIgnoreCase(eventBean.getSchedule_type())) {
                // one time
                builder.append(eventBean.getSchedule_type());
                builder.append(Constants.SPACE);
                if (Constants.CURRENT_TIMESTAMP.equalsIgnoreCase(eventBean.getStart_date_type())) {
                    builder.append(Constants.CURRENT_TIMESTAMP);
                } else {
                    builder.append(Constants.SYMBOL_QUOTE);
                    builder.append(eventBean.getStart_date());
                    builder.append(Constants.SYMBOL_QUOTE);
                }
                builder.append(Constants.SPACE);
                if (eventBean.getStart_date_interval_quantity() != null) {
                    for (int i = 0; i < eventBean.getStart_date_interval_quantity().length; i++) {
                        if (!connectionHelper.isEmpty(eventBean.getStart_date_interval_quantity()[i])) {
                            if (connectionHelper.isInteger(eventBean.getStart_date_interval_quantity()[i])) {
                                builder.append(interval);
                                builder.append(eventBean.getStart_date_interval_quantity()[i]);
                                builder.append(Constants.SPACE);
                                builder.append(eventBean.getStart_date_interval()[i]);
                            } else {
                                builder.append(interval);
                                builder.append(Constants.SYMBOL_QUOTE);
                                builder.append(eventBean.getStart_date_interval_quantity()[i]);
                                builder.append(Constants.SYMBOL_QUOTE);
                                builder.append(Constants.SPACE);
                                builder.append(eventBean.getStart_date_interval()[i]);
                            }
                        }
                    }
                    builder.append(Constants.SPACE);
                }
            } else if (Constants.EVERY.equalsIgnoreCase(eventBean.getSchedule_type())) {
                // Recursive
                builder.append(eventBean.getSchedule_type());
                builder.append(Constants.SPACE);
                if (connectionHelper.isInteger(eventBean.getInterval_quantity())) {
                    builder.append(eventBean.getInterval_quantity());
                } else {
                    builder.append(Constants.SYMBOL_QUOTE);
                    builder.append(eventBean.getInterval_quantity());
                    builder.append(Constants.SYMBOL_QUOTE);
                }
                builder.append(Constants.SPACE);
                builder.append(eventBean.getInterval());
                builder.append(Constants.SPACE);
                if (!connectionHelper.isEmpty(eventBean.getStart_date_type())) {
                    boolean start = false;
                    if (Constants.CURRENT_TIMESTAMP.equalsIgnoreCase(eventBean.getStart_date_type())) {
                        builder.append("STARTS ");
                        builder.append(Constants.CURRENT_TIMESTAMP);
                        builder.append(Constants.SPACE);
                        start = true;
                    } else if (!connectionHelper.isEmpty(eventBean.getStart_date())) {
                        builder.append("STARTS ");
                        builder.append(Constants.SYMBOL_QUOTE);
                        builder.append(eventBean.getStart_date());
                        builder.append(Constants.SYMBOL_QUOTE);
                        builder.append(Constants.SPACE);
                        start = true;
                    }
                    if (start && eventBean.getStart_date_interval_quantity() != null) {
                        for (int i = 0; i < eventBean.getStart_date_interval_quantity().length; i++) {
                            if (!connectionHelper.isEmpty(eventBean.getStart_date_interval_quantity()[i])) {
                                if (connectionHelper.isInteger(eventBean.getStart_date_interval_quantity()[i])) {
                                    builder.append(interval);
                                    builder.append(eventBean.getStart_date_interval_quantity()[i]);
                                    builder.append(Constants.SPACE);
                                    builder.append(eventBean.getStart_date_interval()[i]);
                                } else {
                                    builder.append(interval);
                                    builder.append(Constants.SYMBOL_QUOTE);
                                    builder.append(eventBean.getStart_date_interval_quantity()[i]);
                                    builder.append(Constants.SYMBOL_QUOTE);
                                    builder.append(Constants.SPACE);
                                    builder.append(eventBean.getStart_date_interval()[i]);
                                }
                            }
                        }
                    }
                }
                if (!connectionHelper.isEmpty(eventBean.getEnd_date_type())) {
                    boolean end = false;
                    if (Constants.CURRENT_TIMESTAMP.equalsIgnoreCase(eventBean.getEnd_date_type())) {
                        builder.append("ENDS ");
                        builder.append(Constants.CURRENT_TIMESTAMP);
                        builder.append(Constants.SPACE);
                        end = true;
                    } else if (!connectionHelper.isEmpty(eventBean.getEnd_date())) {
                        builder.append("ENDS ");
                        builder.append(Constants.SYMBOL_QUOTE);
                        builder.append(eventBean.getEnd_date());
                        builder.append(Constants.SYMBOL_QUOTE);
                        builder.append(Constants.SPACE);
                        end = true;
                    }
                    if (end && eventBean.getEnd_date_interval_quantity() != null) {
                        for (int i = 0; i < eventBean.getEnd_date_interval_quantity().length; i++) {
                            if (!connectionHelper.isEmpty(eventBean.getEnd_date_interval_quantity()[i])) {
                                if (connectionHelper.isInteger(eventBean.getEnd_date_interval_quantity()[i])) {
                                    builder.append(interval);
                                    builder.append(eventBean.getEnd_date_interval_quantity()[i]);
                                    builder.append(Constants.SPACE);
                                    builder.append(eventBean.getEnd_date_interval()[i]);
                                } else {
                                    builder.append(interval);
                                    builder.append(Constants.SYMBOL_QUOTE);
                                    builder.append(eventBean.getEnd_date_interval_quantity()[i]);
                                    builder.append(Constants.SYMBOL_QUOTE);
                                    builder.append(Constants.SPACE);
                                    builder.append(eventBean.getEnd_date_interval()[i]);
                                }
                            }
                        }
                    }
                }
            }
            if (!connectionHelper.isEmpty(eventBean.getPreserve())) {
                builder.append("ON COMPLETION ");
                builder.append(eventBean.getPreserve());
                builder.append(Constants.SPACE);
            }
            if (!connectionHelper.isEmpty(eventBean.getStatus())) {
                builder.append(eventBean.getStatus());
                builder.append(Constants.SPACE);
            }
            if (!connectionHelper.isEmpty(eventBean.getComment())) {
                builder.append(Constants.SYMBOL_QUOTE);
                builder.append(eventBean.getComment());
                builder.append(Constants.SYMBOL_QUOTE);
                builder.append(Constants.SPACE);
            }
            builder.append("DO ");
            builder.append(eventBean.getBody());
            if (Constants.YES.equalsIgnoreCase(eventBean.getAction())) {
                apiConnection = connectionHelper.getConnection(eventBean.getRequest_db());
                statement = apiConnection.getStmt(builder.toString());
                statement.execute();
            } else {
                result = builder.toString();
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return result;
    }

    /**
     * @param list {@link List}
     * @return String
     */
    public String getStartInterval(List<String> list) {
        StringBuilder builder = new StringBuilder();
        builder.append("<div><div class=\"form-input\">");
        builder.append("<label>+ INTERVAL</label></div>");
        builder.append("<div class=\"form-input\"><label>");
        builder.append(messages.getMessage("lbl.quantity"));
        builder.append("</label> <input type=\"text\" ");
        builder.append("name=\"start_date_interval_quantity\" ");
        builder.append("class=\"form-control\"></div>");
        builder.append("<div class=\"form-input\"><label>");
        builder.append(messages.getMessage("lbl.interval"));
        builder.append("</label> <select name=\"start_date_interval\"");
        builder.append(" class=\"form-control\">");
        for (String temp : list) {
            builder.append("<option value=\"");
            builder.append(temp);
            builder.append("\">");
            builder.append(temp);
            builder.append("</option>");
        }
        builder.append("</select></div><div ");
        builder.append("style=\"display: inline-block;\">");
        builder.append("<img alt=\"\" class=\"icon\" ");
        builder.append("onclick=\"removeThisInterval(this);\" ");
        builder.append("src=\"");
        builder.append(RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getContextPath());
        builder.append("/components/icons/minus-r.png\"></div></div>");
        return builder.toString();
    }

    /**
     * @param list {@link List}
     * @return String
     */
    public String getEndInterval(List<String> list) {
        StringBuilder builder = new StringBuilder();
        builder.append("<div><div class=\"form-input\">");
        builder.append("<label>+ INTERVAL</label></div>");
        builder.append("<div class=\"form-input\"><label>");
        builder.append(messages.getMessage("lbl.quantity"));
        builder.append("</label> <input type=\"text\" ");
        builder.append("name=\"end_date_interval_quantity\" ");
        builder.append("class=\"form-control\"></div>");
        builder.append("<div class=\"form-input\"><label>");
        builder.append(messages.getMessage("lbl.interval"));
        builder.append("</label> <select name=\"end_date_interval\"");
        builder.append(" class=\"form-control\">");
        for (String temp : list) {
            builder.append("<option value=\"");
            builder.append(temp);
            builder.append("\">");
            builder.append(temp);
            builder.append("</option>");
        }
        builder.append("</select></div><div ");
        builder.append("style=\"display: inline-block;\">");
        builder.append("<img alt=\"\" class=\"icon\" ");
        builder.append("onclick=\"removeThisInterval(this);\" ");
        builder.append("src=\"");
        builder.append(RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getContextPath());
        builder.append("/components/icons/minus-r.png\"></div></div>");
        return builder.toString();
    }
}
