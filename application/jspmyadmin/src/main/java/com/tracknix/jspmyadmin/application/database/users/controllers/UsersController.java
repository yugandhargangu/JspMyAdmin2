package com.tracknix.jspmyadmin.application.database.users.controllers;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tracknix.jspmyadmin.application.database.users.beans.ColumnPrivilegeBean;
import com.tracknix.jspmyadmin.application.database.users.beans.RoutinePrivilegeBean;
import com.tracknix.jspmyadmin.application.database.users.beans.TablePrivilegeBean;
import com.tracknix.jspmyadmin.application.database.users.beans.UserListBean;
import com.tracknix.jspmyadmin.application.database.users.logic.UserLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/15
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class UsersController {

    @Handle(path = "/database_privileges.html", methodType = MethodType.GET)
    @GenerateToken
    private void loadDatabasePrivileges(View view, @Model UserListBean bean, @LogicParam UserLogic userLogic) throws EncodingException {
        try {
            userLogic.fillBean(bean);
        } catch (SQLException e) {
            bean.setError(Constants.ONE);
        }
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_USERS_USERS);
    }

    @Handle(path = "/database_privileges.html", methodType = MethodType.POST)
    @ValidateToken
    private void saveDatabasePrivileges(View view, RedirectParams redirectParams, @Model UserListBean bean, @LogicParam UserLogic userLogic) {
        try {
            userLogic.saveSchemaPrivileges(bean);
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_SAVE_SUCCESS);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_PRIVILEGES);
    }

    @Handle(path = "/database_table_privileges.html", methodType = MethodType.GET)
    @GenerateToken
    private void loadTablePrivileges(View view, @Model TablePrivilegeBean bean, @LogicParam UserLogic userLogic) throws EncodingException {
        try {
            userLogic.fillTablePrivileges(bean);
            view.setType(ViewType.FORWARD);
            view.setPath(AppConstants.JSP_DATABASE_USERS_TABLEPRIVILEGES);
        } catch (SQLException e) {
            view.setType(ViewType.REDIRECT);
            view.setPath(AppConstants.PATH_DATABASE_PRIVILEGES);
        }
    }

    @Handle(path = "/database_table_privileges.html", methodType = MethodType.POST)
    @ValidateToken
    @GenerateToken
    private void saveTablePrivileges(View view, RedirectParams redirectParams, @Model TablePrivilegeBean bean, @LogicParam UserLogic userLogic) throws EncodingException {
        if (Constants.ONE.equals(bean.getFetch()) || Constants.TWO.equals(bean.getFetch())) {
            this.loadTablePrivileges(view, bean, userLogic);
            return;
        }
        try {
            userLogic.saveTablePrivileges(bean);
            ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
            jsonObject.put(Constants.USER, bean.getUser());
            if (bean.getTable() != null) {
                jsonObject.put(Constants.TABLE, bean.getTable());
            }
            view.setToken(jsonObject.toString());
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_SAVE_SUCCESS);
        } catch (SQLException e) {
            ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
            jsonObject.put(Constants.USER, bean.getUser());
            view.setToken(jsonObject.toString());
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_TABLE_PRIVILEGES);
    }


    @Handle(path = "/database_column_privileges.html", methodType = MethodType.GET)
    @GenerateToken
    private void loadColumnPrivileges(View view, @Model ColumnPrivilegeBean bean, @LogicParam UserLogic userLogic) throws EncodingException {
        try {
            userLogic.fillColumnPrivileges(bean);
            view.setType(ViewType.FORWARD);
            view.setPath(AppConstants.JSP_DATABASE_USERS_COLUMNPRIVILEGES);
        } catch (SQLException e) {
            view.setType(ViewType.REDIRECT);
            view.setPath(AppConstants.PATH_DATABASE_PRIVILEGES);
        }
    }

    @Handle(path = "/database_column_privileges.html", methodType = MethodType.POST)
    @ValidateToken
    private void saveColumnPrivileges(View view, RedirectParams redirectParams, @Model ColumnPrivilegeBean bean, @LogicParam UserLogic userLogic) {
        try {
            userLogic.saveColumnPrivileges(bean);
            ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
            jsonObject.put(Constants.USER, bean.getUser());
            if (bean.getTable() != null) {
                jsonObject.put(Constants.TABLE, bean.getTable());
            }
            view.setToken(jsonObject.toString());
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_SAVE_SUCCESS);
        } catch (SQLException e) {
            ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
            jsonObject.put(Constants.USER, bean.getUser());
            view.setToken(jsonObject.toString());
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_COLUMN_PRIVILEGES);
    }

    @Handle(path = "/database_routine_privileges.html", methodType = MethodType.GET)
    private void loadRoutinePrivileges(View view, RedirectParams redirectParams, @Model RoutinePrivilegeBean bean, @LogicParam UserLogic userLogic) throws EncodingException {
        try {
            userLogic.fillRoutinePrivileges(bean);
            view.setType(ViewType.FORWARD);
            view.setPath(AppConstants.JSP_DATABASE_USERS_ROUTINEPRIVILEGES);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
            view.setType(ViewType.REDIRECT);
            view.setPath(AppConstants.PATH_DATABASE_PRIVILEGES);
        }
    }

    @Handle(path = "/database_routine_privileges.html", methodType = MethodType.POST)
    @ValidateToken
    @GenerateToken
    private void saveRoutinePrivileges(View view, RedirectParams redirectParams, @Model RoutinePrivilegeBean bean, @LogicParam UserLogic userLogic) throws EncodingException {
        if (!Constants.ONE.equals(bean.getFetch()) && !Constants.TWO.equals(bean.getFetch())) {
            this.loadRoutinePrivileges(view, redirectParams, bean, userLogic);
            return;
        }
        try {
            userLogic.saveRoutinePrivileges(bean);
            ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
            jsonObject.put(Constants.USER, bean.getUser());
            view.setToken(jsonObject.toString());
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_SAVE_SUCCESS);
        } catch (SQLException e) {
            ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
            jsonObject.put(Constants.USER, bean.getUser());
            view.setToken(jsonObject.toString());
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_ROUTINE_PRIVILEGES);
    }
}
