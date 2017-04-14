package com.tracknix.jspmyadmin.application.server.controllers;

import com.tracknix.jspmyadmin.application.common.services.HomeLogic;
import com.tracknix.jspmyadmin.application.server.beans.database.DatabaseCreateBean;
import com.tracknix.jspmyadmin.application.server.beans.database.DatabaseDropBean;
import com.tracknix.jspmyadmin.application.server.beans.database.DatabaseListBean;
import com.tracknix.jspmyadmin.application.server.services.DatabaseLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.SERVER)
public class DatabasesController {

    @Handle(path = "/server/databases.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    public DatabaseListBean databases(@Model DatabaseListBean bean, @LogicParam DatabaseLogic databaseLogic, @LogicParam HomeLogic homeLogic) {
        try {
            bean.setCollation_map(homeLogic.getCollationMap());
            databaseLogic.fillBean(bean);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/server/database/create.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean createDatabase(Messages messages, @Model DatabaseCreateBean bean, @LogicParam DatabaseLogic databaseLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            System.out.println(bean.getDatabase());
            if (databaseLogic.isEmpty(bean.getDatabase())) {
                defaultBean.setErr(messages.getMessage(AppConstants.MSG_DATABASE_IS_BLANK));
            } else {
                databaseLogic.createDatabase(bean);
                defaultBean.setMsg(messages.getMessage(AppConstants.MSG_CREATE_DB_SUCCESS));
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/server/database/drop.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean dropDatabase(Messages messages, @Model DatabaseDropBean bean, @LogicParam DatabaseLogic databaseLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (bean.getDatabases() == null || bean.getDatabases().length == 0) {
                defaultBean.setErr(messages.getMessage("msg.select_least_one_database"));
            } else {
                databaseLogic.dropDatabase(bean);
                defaultBean.setMsg(messages.getMessage(AppConstants.MSG_DROP_DB_SUCCESS));
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }
}
