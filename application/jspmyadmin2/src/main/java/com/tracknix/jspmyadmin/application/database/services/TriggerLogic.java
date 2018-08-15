package com.tracknix.jspmyadmin.application.database.services;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tracknix.jspmyadmin.application.database.beans.trigger.TriggerBean;
import com.tracknix.jspmyadmin.application.database.beans.trigger.TriggerInfo;
import com.tracknix.jspmyadmin.application.database.beans.trigger.TriggersBean;
import com.tracknix.jspmyadmin.framework.connection.ApiConnection;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
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
public class TriggerLogic extends AbstractBaseLogic {

    @Detect
    private ConnectionHelper connectionHelper;
    @Detect
    private QueryHelper queryHelper;

    /**
     * @param triggersBean {@link TriggersBean}
     * @throws SQLException e
     */
    public void fillListBean(TriggersBean triggersBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(triggersBean.getRequest_db());
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(1));
            resultSet = statement.executeQuery();
            List<TriggerInfo> triggerInfoList = new ArrayList<TriggerInfo>();
            String[] columns = queryHelper.getColumns(1);
            while (resultSet.next()) {
                TriggerInfo triggerInfo = new TriggerInfo();
                triggerInfo.setTrigger_name(resultSet.getString(columns[0]));
                triggerInfo.setEvent_type(resultSet.getString(columns[1]));
                triggerInfo.setTable_name(resultSet.getString(columns[2]));
                triggerInfo.setEvent_time(resultSet.getString(columns[3]));
                triggerInfo.setDefiner(resultSet.getString(columns[4]));
                triggerInfoList.add(triggerInfo);
            }
            triggersBean.setTrigger_list(triggerInfoList);
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param name     {@link String}
     * @param database {@link String}
     * @return boolean
     * @throws SQLException e
     */
    public boolean isExisted(String name, String database) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(database);
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(2));
            statement.setString(1, database);
            statement.setString(2, name);
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
     * @param triggersBean {@link TriggersBean}
     * @return String
     * @throws SQLException e
     */
    public String showCreate(TriggersBean triggersBean) throws SQLException {
        String result = null;
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            if (triggersBean.getTriggers() != null) {
                apiConnection = connectionHelper.getConnection(triggersBean.getRequest_db());
                ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
                for (int i = 0; i < triggersBean.getTriggers().length; i++) {
                    statement = apiConnection.getStmtSelect(queryHelper.getQuery(3, triggersBean.getTriggers()[i]));
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        objectNode.put(resultSet.getString(1), resultSet.getString(3));
                    }
                    connectionHelper.close(resultSet);
                    connectionHelper.close(statement);
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
     * @param triggersBean {@link TriggersBean}
     * @throws SQLException e
     */
    public void drop(TriggersBean triggersBean) throws SQLException {
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        try {
            if (triggersBean.getTriggers() != null) {
                apiConnection = connectionHelper.getConnection(triggersBean.getRequest_db());
                for (int i = 0; i < triggersBean.getTriggers().length; i++) {
                    statement = apiConnection.getStmt(queryHelper.getQuery(4, triggersBean.getTriggers()[i]));
                    statement.execute();
                    connectionHelper.close(statement);
                }
            }
        } finally {
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
    }

    /**
     * @param database {@link String}
     * @return List
     * @throws SQLException e
     */
    public List<String> getTriggerList(String database) throws SQLException {
        List<String> triggerList = new ArrayList<String>();
        ApiConnection apiConnection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            apiConnection = connectionHelper.getConnection(database);
            statement = apiConnection.getStmtSelect(queryHelper.getQuery(1));
            resultSet = statement.executeQuery();
            String[] columns = queryHelper.getColumns(1);
            while (resultSet.next()) {
                triggerList.add(resultSet.getString(columns[0]));
            }
        } finally {
            connectionHelper.close(resultSet);
            connectionHelper.close(statement);
            connectionHelper.close(apiConnection);
        }
        return triggerList;
    }

    /**
     * @param triggerBean {@link TriggerBean}
     * @return String
     * @throws SQLException e
     */
    public String save(TriggerBean triggerBean) throws SQLException {
        String definer = Constants.BLANK;
        if (!connectionHelper.isEmpty(triggerBean.getDefiner())) {
            if (Constants.CURRENT_USER.equalsIgnoreCase(triggerBean.getDefiner())) {
                definer = queryHelper.getPart(5, 2);
            } else if (!connectionHelper.isEmpty(triggerBean.getDefiner_name())) {
                String[] temp = triggerBean.getDefiner_name().split(Constants.SYMBOL_AT);
                String user;
                if (temp.length < 2) {
                    user = queryHelper.getPart(5, 4, temp[0]);
                } else {
                    user = queryHelper.getPart(5, 5, temp[0], temp[1]);
                }
                definer = queryHelper.getPart(5, 3, user);
            }
        }
        String order = Constants.BLANK;
        if (!connectionHelper.isEmpty(triggerBean.getTrigger_order()) && !connectionHelper.isEmpty(triggerBean.getOther_trigger_name())) {
            order = queryHelper.getPart(5, 6, triggerBean.getTrigger_order(), triggerBean.getOther_trigger_name());
        }
        String query = queryHelper.getPart(5, 1, definer, triggerBean.getTrigger_name(),
                triggerBean.getTrigger_time(), triggerBean.getTrigger_event(), triggerBean.getDatabase_name(),
                triggerBean.getTable_name(), order, triggerBean.getTrigger_body());
        return super._createExecuteAOrReturn(connectionHelper, triggerBean.getAction(), triggerBean.getRequest_db(), query);
    }
}
