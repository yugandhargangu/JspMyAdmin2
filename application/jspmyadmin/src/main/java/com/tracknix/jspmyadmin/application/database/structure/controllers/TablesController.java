package com.tracknix.jspmyadmin.application.database.structure.controllers;

import com.tracknix.jspmyadmin.application.database.structure.beans.CreateTableBean;
import com.tracknix.jspmyadmin.application.database.structure.beans.StructureBean;
import com.tracknix.jspmyadmin.application.database.structure.beans.TableListBean;
import com.tracknix.jspmyadmin.application.database.structure.logic.StructureLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class TablesController {

    @Handle(path = "/database/structure.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    public TableListBean tables(@Model TableListBean bean, @LogicParam StructureLogic structureLogic) throws SQLException {
        structureLogic.fillBean(bean, true);
        return bean;
    }

    @Handle(path = "/database/table/create.text", methodType = MethodType.GET)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean createTable(Messages messages, @Model CreateTableBean bean, @LogicParam StructureLogic structureLogic) throws SQLException {
        DefaultBean defaultBean = new DefaultBean();
        if (structureLogic.isTableExisted(bean.getTable_name(), bean.getRequest_db())) {
            defaultBean.setErr(messages.getMessage(AppConstants.MSG_TABLE_ALREADY_EXISTED));
        }
        return defaultBean;
    }

    @Handle(path = "/database/create/table.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    private QueryReturnBean createTable(Messages messages, RequestAdaptor requestAdaptor, @Model CreateTableBean bean, @LogicParam StructureLogic structureLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            String query = structureLogic.createTable(bean);
            if (query != null) {
                queryReturnBean.setQuery(query);
            } else {
                queryReturnBean.setMsg(messages.getMessage(AppConstants.MSG_TABLE_CREATED));
            }
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/structure/copy.text", methodType = MethodType.POST)
    @ValidateToken
    private void copyTables(View view, RedirectParams redirectParams, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        try {
            structureLogic.copyTables(bean);
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_COPY_DONE);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_STRUCTURE);
    }

    @Handle(path = "/database_structure_duplicate.html", methodType = MethodType.POST)
    @ValidateToken
    private void duplidateTables(View view, RedirectParams redirectParams, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        try {
            structureLogic.duplicateTable(bean);
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_DUPLICATE_TABLE_SUCCESSFULLY);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_STRUCTURE);
    }

    @Handle(path = "/database_structure_truncate.html", methodType = MethodType.POST)
    @ValidateToken
    private void truncate(View view, RedirectParams redirectParams, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        try {
            structureLogic.truncateTables(bean);
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_TABLES_TRUNCATE_SUCCESSFULLY);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_STRUCTURE);
    }

    @Handle(path = "/database_structure_drop.html", methodType = MethodType.POST)
    @ValidateToken
    private void dropTables(View view, RedirectParams redirectParams, @Model StructureBean bean) {

        StructureLogic structureLogic = null;
        try {
            structureLogic = new StructureLogic();
            structureLogic.dropTables(bean, true);
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_TABLES_DROPPED_SUCCESSFULLY);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(bean.getAction());
    }
}
