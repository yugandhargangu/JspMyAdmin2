package com.tracknix.jspmyadmin.application.server.controllers;

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

    @Handle(path = "/server/databases.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public ResponseBody<DatabaseListBean> databases(@Model DatabaseListBean bean, @LogicParam DatabaseLogic databaseLogic) {
        try {
            databaseLogic.fillBean(bean);
            return new ResponseBody<DatabaseListBean>().body(bean);
        } catch (SQLException e) {
            return new ResponseBody<DatabaseListBean>().error(ResponseBody.MYSQL_ERROR).message(e.getMessage());
        }
    }

    @Handle(path = "/server/database/create.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public ResponseBody<Void> createDatabase(@Model DatabaseCreateBean bean, @LogicParam DatabaseLogic databaseLogic) {
        try {
            if (databaseLogic.isEmpty(bean.getDatabase())) {
                return new ResponseBody<Void>().error(ResponseBody.PARAMS_ERROR).message(AppConstants.MSG_DATABASE_IS_BLANK);
            } else {
                databaseLogic.createDatabase(bean);
                return new ResponseBody<Void>().message(AppConstants.MSG_CREATE_DB_SUCCESS);
            }
        } catch (SQLException e) {
            return new ResponseBody<Void>().error(ResponseBody.MYSQL_ERROR).message(e.getMessage());
        }
    }

    @Handle(path = "/server/database/drop.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public ResponseBody<Void> dropDatabase(@Model DatabaseDropBean bean, @LogicParam DatabaseLogic databaseLogic) {
        try {
            if (bean.getDatabases() == null || bean.getDatabases().length == 0) {
                return new ResponseBody<Void>().error(ResponseBody.PARAMS_ERROR).message("msg.select_least_one_database");
            } else {
                databaseLogic.dropDatabase(bean);
                return new ResponseBody<Void>().message(AppConstants.MSG_DROP_DB_SUCCESS);
            }
        } catch (SQLException e) {
            return new ResponseBody<Void>().error(ResponseBody.MYSQL_ERROR).message(e.getMessage());
        }
    }
}
