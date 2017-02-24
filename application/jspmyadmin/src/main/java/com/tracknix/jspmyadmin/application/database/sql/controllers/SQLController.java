package com.tracknix.jspmyadmin.application.database.sql.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tracknix.jspmyadmin.application.database.event.beans.EventListBean;
import com.tracknix.jspmyadmin.application.database.event.logic.EventLogic;
import com.tracknix.jspmyadmin.application.database.routine.beans.RoutineListBean;
import com.tracknix.jspmyadmin.application.database.routine.logic.RoutineLogic;
import com.tracknix.jspmyadmin.application.database.sql.beans.ExternalSqlBean;
import com.tracknix.jspmyadmin.application.database.sql.beans.SqlBean;
import com.tracknix.jspmyadmin.application.database.sql.logic.SqlLogic;
import com.tracknix.jspmyadmin.application.database.structure.beans.StructureBean;
import com.tracknix.jspmyadmin.application.database.structure.logic.StructureLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.RequestLevel;
import com.tracknix.jspmyadmin.framework.web.utils.View;
import com.tracknix.jspmyadmin.framework.web.utils.ViewType;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class SQLController {

    @Handle(path = "/database_sql.html")
    private void sqlEditor(View view, HttpSession session, @Model SqlBean bean, @LogicParam SqlLogic sqlLogic) throws Exception {

        try {
            boolean fetch = true;
            Object temp = session.getAttribute(Constants.QUERY);
            if (temp != null) {
                session.removeAttribute(Constants.QUERY);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonObject = objectMapper.readTree(temp.toString());
                if (jsonObject.has(Constants.QUERY)) {
                    bean.setQuery(jsonObject.get(Constants.QUERY).asText());
                    fetch = false;
                }
            }
            sqlLogic.fillBean(bean, fetch);
        } catch (SQLException e) {
            bean.setError(e.getMessage());
        }
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_SQL_SQL);
    }

    @Handle(path = "/database_ext_sql.html")
    @ValidateToken
    private void alterSql(View view, HttpSession session, @Model ExternalSqlBean bean, @LogicParam StructureLogic structureLogic, @LogicParam RoutineLogic routineLogic, @LogicParam EventLogic eventLogic) throws SQLException, IOException {
        if (bean.getEdit_type() != null && bean.getEdit_name() != null
                && !Constants.BLANK.equals(bean.getEdit_name().trim())) {
            int type = _getInteger(bean.getEdit_type());
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode objectNode = null;
            switch (type) {
                case 1:
                    // alter view
                    StructureBean structureBean = new StructureBean();
                    structureBean.setTables(new String[]{bean.getEdit_name()});
                    String result = structureLogic.showCreate(structureBean, false);
                    JsonNode jsonObject = objectMapper.readTree(result);
                    Iterator<String> iterator = jsonObject.fieldNames();
                    if (iterator.hasNext()) {
                        result = jsonObject.get(iterator.next()).asText();
                    }
                    result = result.substring(6);
                    StringBuilder builder = new StringBuilder();
                    builder.append("DELIMITER $$\n\n");
                    builder.append("ALTER");
                    builder.append(result);
                    builder.append("$$");
                    objectNode = JsonNodeFactory.instance.objectNode();
                    objectNode.put(Constants.QUERY, builder.toString());
                    break;
                case 2:
                    // alter procedure
                    RoutineListBean routineListBean = new RoutineListBean();
                    routineListBean.setRoutines(new String[]{bean.getEdit_name()});
                    result = routineLogic.showCreate(routineListBean, true);
                    jsonObject = objectMapper.readTree(result);
                    iterator = jsonObject.fieldNames();
                    if (iterator.hasNext()) {
                        result = jsonObject.get(iterator.next()).asText();
                    }
                    builder = new StringBuilder();
                    builder.append("DROP PROCEDURE IF EXISTS `");
                    builder.append(bean.getEdit_name());
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(Constants.SYMBOL_SEMI_COLON);
                    builder.append("\n\n");
                    builder.append("DELIMITER $$\n\n");
                    builder.append(result);
                    builder.append("$$");
                    objectNode = JsonNodeFactory.instance.objectNode();
                    objectNode.put(Constants.QUERY, builder.toString());
                    break;
                case 3:
                    // execute procedure
                    builder = new StringBuilder();
                    builder.append("CALL PROCEDURE `");
                    builder.append(bean.getEdit_name());
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append("(<params>)");
                    builder.append(Constants.SYMBOL_SEMI_COLON);
                    objectNode = JsonNodeFactory.instance.objectNode();
                    objectNode.put(Constants.QUERY, builder.toString());
                    break;
                case 4:
                    // alter function
                    routineListBean = new RoutineListBean();
                    routineListBean.setRoutines(new String[]{bean.getEdit_name()});
                    result = routineLogic.showCreate(routineListBean, false);
                    jsonObject = objectMapper.readTree(result);
                    iterator = jsonObject.fieldNames();
                    if (iterator.hasNext()) {
                        result = jsonObject.get(iterator.next()).asText();
                    }
                    builder = new StringBuilder();
                    builder.append("DROP FUNCTION IF EXISTS `");
                    builder.append(bean.getEdit_name());
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append(Constants.SYMBOL_SEMI_COLON);
                    builder.append("\n\n");
                    builder.append("DELIMITER $$\n\n");
                    builder.append(result);
                    builder.append("$$");
                    objectNode = JsonNodeFactory.instance.objectNode();
                    objectNode.put(Constants.QUERY, builder.toString());
                    break;
                case 5:
                    // execute function
                    builder = new StringBuilder();
                    builder.append("SELECT `");
                    builder.append(bean.getEdit_name());
                    builder.append(Constants.SYMBOL_TEN);
                    builder.append("(<params>)");
                    builder.append(Constants.SYMBOL_SEMI_COLON);
                    objectNode = JsonNodeFactory.instance.objectNode();
                    objectNode.put(Constants.QUERY, builder.toString());
                    break;
                case 6:
                    // alter event
                    EventListBean eventListBean = new EventListBean();
                    eventListBean.setEvents(new String[]{bean.getEdit_name()});
                    result = eventLogic.getShowCreate(eventListBean);
                    jsonObject = objectMapper.readTree(result);
                    iterator = jsonObject.fieldNames();
                    if (iterator.hasNext()) {
                        result = jsonObject.get(iterator.next()).asText();
                    }
                    result = result.substring(6);
                    builder = new StringBuilder();
                    builder.append("DELIMITER $$\n\n");
                    builder.append("ALTER");
                    builder.append(result);
                    builder.append("$$");
                    objectNode = JsonNodeFactory.instance.objectNode();
                    objectNode.put(Constants.QUERY, builder.toString());
                    break;
                default:
                    break;
            }
            if (objectNode != null) {
                session.setAttribute(Constants.QUERY, objectNode.toString());
            }
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_SQL);
    }

    /**
     * @param val
     * @return
     */
    private int _getInteger(String val) {
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
        }
        return 0;
    }
}
