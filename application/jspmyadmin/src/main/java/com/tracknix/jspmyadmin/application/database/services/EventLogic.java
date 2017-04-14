package com.tracknix.jspmyadmin.application.database.services;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tracknix.jspmyadmin.application.database.beans.event.EventBean;
import com.tracknix.jspmyadmin.application.database.beans.event.EventInfo;
import com.tracknix.jspmyadmin.application.database.beans.event.EventsBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.utils.Messages;
import com.tracknix.jspmyadmin.framework.web.utils.QueryHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
@LogicService
public class EventLogic extends AbstractBaseLogic {

    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private QueryHelper queryHelper;

    /**
     * @param eventsBean {@link EventsBean}
     * @throws SQLException e
     */
    public void fillListBean(EventsBean eventsBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(eventsBean.getRequest_db());
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(1));
            statement.setString(1, eventsBean.getRequest_db());
            resultSet = statement.executeQuery();
            List<EventInfo> eventInfoList = new ArrayList<EventInfo>();
            while (resultSet.next()) {
                EventInfo eventInfo = new EventInfo();
                eventInfo.setName(resultSet.getString(1));
                eventInfo.setDefiner(resultSet.getString(2));
                eventInfo.setType(resultSet.getString(3));
                eventInfo.setStatus(resultSet.getString(4));
                eventInfo.setCreate_date(resultSet.getString(5));
                eventInfo.setAlter_date(resultSet.getString(6));
                eventInfo.setComments(resultSet.getString(7));
                eventInfoList.add(eventInfo);
            }
            eventsBean.setEvent_list(eventInfoList);
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param eventsBean {@link EventsBean}
     * @throws SQLException e
     */
    public void renameEvent(EventsBean eventsBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            if (eventsBean.getEvents() != null && eventsBean.getEvents().length > 0) {
                apiConnection = connectionHelper.getConnection(eventsBean.getRequest_db());
                String query = queryHelper.getQuery(2, eventsBean.getEvents()[0], eventsBean.getNew_event());
                statement = apiConnection.getStmt(query);
                statement.execute();
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param eventsBean {@link EventsBean}
     * @throws SQLException e
     */
    public void enableOrDisableEvent(EventsBean eventsBean, boolean enable) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            if (eventsBean.getEvents() != null) {
                apiConnection = connectionHelper.getConnection(eventsBean.getRequest_db());
                for (int i = 0; i < eventsBean.getEvents().length; i++) {
                    String query;
                    if (enable) {
                        query = queryHelper.getQuery(3, eventsBean.getEvents()[i]);
                    } else {
                        query = queryHelper.getQuery(4, eventsBean.getEvents()[i]);
                    }
                    statement = apiConnection.getStmt(query);
                    statement.execute();
                    connectionHelper.close(statement);
                }
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }


    /**
     * @param eventsBean {@link EventsBean}
     * @throws SQLException e
     */
    public void dropEvent(EventsBean eventsBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            if (eventsBean.getEvents() != null) {
                apiConnection = connectionHelper.getConnection(eventsBean.getRequest_db());
                for (int i = 0; i < eventsBean.getEvents().length; i++) {
                    statement = apiConnection.getStmt(queryHelper.getQuery(5, eventsBean.getEvents()[i]));
                    statement.execute();
                    connectionHelper.close(statement);
                }
                apiConnection.commit();
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }


    /**
     * @param eventsBean {@link EventsBean}
     * @return query
     * @throws SQLException e
     */
    public String getShowCreate(EventsBean eventsBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            if (eventsBean.getEvents() != null) {
                apiConnection = connectionHelper.getConnection(eventsBean.getRequest_db());
                ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
                for (int i = 0; i < eventsBean.getEvents().length; i++) {
                    statement = apiConnection.getStmtSelect(queryHelper.getQuery(6, eventsBean.getEvents()[i]));
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        objectNode.put(resultSet.getString(1), resultSet.getString(4) + Constants.SYMBOL_SEMI_COLON);
                    }
                    connectionHelper.close(resultSet);
                    connectionHelper.close(statement);
                }
                return objectNode.toString();
            }
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return null;
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
        try {
            apiConnection = connectionHelper.getConnection(database);
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(7));
            statement.setString(1, database);
            statement.setString(2, name);
            resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
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
        String definer = Constants.BLANK;
        if (!connectionHelper.isEmpty(eventBean.getDefiner())) {
            if (Constants.CURRENT_USER.equalsIgnoreCase(eventBean.getDefiner())) {
                definer = queryHelper.getPart(8, 2);
            } else if (!connectionHelper.isEmpty(eventBean.getDefiner_name())) {
                String[] temp = eventBean.getDefiner_name().split(Constants.SYMBOL_AT);
                String user;
                if (temp.length < 2) {
                    user = queryHelper.getPart(8, 4, temp[0]);
                } else {
                    user = queryHelper.getPart(8, 5, temp[0], temp[1]);
                }
                definer = queryHelper.getPart(8, 3, user);
            }
        }

        String exists = Constants.BLANK;
        if (Constants.YES.equalsIgnoreCase(eventBean.getNot_exists())) {
            exists = queryHelper.getPart(8, 6);
        }

        String schedule = Constants.BLANK;
        if (Constants.AT.equalsIgnoreCase(eventBean.getSchedule_type())) {
            // one time
            String start_type;
            if (Constants.CURRENT_TIMESTAMP.equalsIgnoreCase(eventBean.getStart_date_type())) {
                start_type = queryHelper.getPart(8, 8);
            } else {
                start_type = queryHelper.getPart(8, 9, eventBean.getStart_date());
            }

            String interval = Constants.BLANK;
            if (eventBean.getStart_date_interval_quantity() != null) {
                interval = this.getInteral(eventBean.getStart_date_interval_quantity(), eventBean.getStart_date_interval());
            }
            schedule = queryHelper.getPart(8, 7, eventBean.getSchedule_type(), start_type,
                    interval, Constants.BLANK, Constants.BLANK);
        } else if (Constants.EVERY.equalsIgnoreCase(eventBean.getSchedule_type())) {
            // Recursive
            String quantity;
            if (connectionHelper.isInteger(eventBean.getInterval_quantity())) {
                quantity = eventBean.getInterval_quantity();
            } else {
                quantity = queryHelper.getPart(8, 9, eventBean.getInterval_quantity());
            }
            String start_date = Constants.BLANK;
            if (!connectionHelper.isEmpty(eventBean.getStart_date_type())) {
                boolean start = false;
                StringBuilder builder = new StringBuilder();
                if (Constants.CURRENT_TIMESTAMP.equalsIgnoreCase(eventBean.getStart_date_type())) {
                    builder.append(queryHelper.getPart(8, 12));
                    start = true;
                } else if (!connectionHelper.isEmpty(eventBean.getStart_date())) {
                    builder.append(queryHelper.getPart(8, 13, eventBean.getStart_date()));
                    start = true;
                }
                if (start && eventBean.getStart_date_interval_quantity() != null) {
                    builder.append(this.getInteral(eventBean.getStart_date_interval_quantity(), eventBean.getStart_date_interval()));
                }
                start_date = builder.toString();
            }
            String end_date = Constants.BLANK;
            if (!connectionHelper.isEmpty(eventBean.getEnd_date_type())) {
                boolean end = false;
                StringBuilder builder = new StringBuilder();
                if (Constants.CURRENT_TIMESTAMP.equalsIgnoreCase(eventBean.getEnd_date_type())) {
                    builder.append(queryHelper.getPart(8, 14));
                    end = true;
                } else if (!connectionHelper.isEmpty(eventBean.getEnd_date())) {
                    builder.append(queryHelper.getPart(8, 15, eventBean.getEnd_date()));
                    end = true;
                }
                if (end && eventBean.getEnd_date_interval_quantity() != null) {
                    builder.append(this.getInteral(eventBean.getEnd_date_interval_quantity(), eventBean.getEnd_date_interval()));
                }
                end_date = builder.toString();
            }
            schedule = queryHelper.getPart(8, 7, eventBean.getSchedule_type(), quantity,
                    eventBean.getInterval(), start_date, end_date);
        }
        String preserve = Constants.BLANK;
        if (!connectionHelper.isEmpty(eventBean.getPreserve())) {
            preserve = queryHelper.getPart(8, 16, eventBean.getPreserve());
        }
        String status = Constants.BLANK;
        if (!connectionHelper.isEmpty(eventBean.getStatus())) {
            status = eventBean.getStatus();
        }
        String comment = Constants.BLANK;
        if (!connectionHelper.isEmpty(eventBean.getComment())) {
            comment = queryHelper.getPart(8, 9, eventBean.getComment());
        }
        String query = queryHelper.getPart(8, 1, definer, exists, eventBean.getEvent_name(),
                schedule, preserve, status, comment, eventBean.getBody());
        return super._createExecuteAOrReturn(connectionHelper, eventBean.getAction(), eventBean.getRequest_db(), query);
    }

    /**
     * @param quantity {@link String}[]
     * @param interval {@link String}[
     * @return String
     */
    private String getInteral(String[] quantity, String[] interval) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < quantity.length; i++) {
            if (!connectionHelper.isEmpty(quantity[i])) {
                if (connectionHelper.isInteger(quantity[i])) {
                    builder.append(queryHelper.getPart(8, 10, quantity[i], interval[i]));
                } else {
                    builder.append(queryHelper.getPart(8, 11, quantity[i], interval[i]));
                }
            }
        }
        return builder.toString();
    }

    /**
     * @param event_name {@link String}
     * @return true or false
     */
    public boolean isEmpty(String event_name) {
        return connectionHelper.isEmpty(event_name);
    }
}
