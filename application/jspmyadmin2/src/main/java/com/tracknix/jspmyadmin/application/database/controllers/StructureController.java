package com.tracknix.jspmyadmin.application.database.controllers;

import com.tracknix.jspmyadmin.application.database.beans.structure.CreateTableBean;
import com.tracknix.jspmyadmin.application.database.beans.structure.CreateViewBean;
import com.tracknix.jspmyadmin.application.database.beans.structure.StructureBean;
import com.tracknix.jspmyadmin.application.database.beans.structure.StructureListBean;
import com.tracknix.jspmyadmin.application.database.services.StructureLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class StructureController {

    @Handle(path = "/database/structure/tables.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public StructureListBean tables(@Model StructureListBean bean, @LogicParam StructureLogic structureLogic) {
        try {
            structureLogic.fillBean(bean, true);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/database/structure/views.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public StructureListBean views(@Model StructureListBean bean, @LogicParam StructureLogic structureLogic) {
        try {
            structureLogic.fillBean(bean, false);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/database/table/exists.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean tableExists(@Model CreateTableBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (structureLogic.isEmpty(bean.getTable_name())) {
                defaultBean.setErr("msg.table_name_blank");
            } else if (structureLogic.isTableExisted(bean.getTable_name(), bean.getRequest_db())) {
                defaultBean.setErr(AppConstants.MSG_TABLE_ALREADY_EXISTED);
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/table/partition.jma", methodType = MethodType.GET)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public boolean isSupportsPartition(@LogicParam StructureLogic structureLogic) throws SQLException{
        return structureLogic.isSupportsPartition();
    }

    @Handle(path = "/database/table/create.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public QueryReturnBean createTable(@Model CreateTableBean bean, @LogicParam StructureLogic structureLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            if (structureLogic.isEmpty(bean.getTable_name())) {
                queryReturnBean.setErr("msg.table_name_blank");
            } else if (structureLogic.isTableExisted(bean.getTable_name(), bean.getRequest_db())) {
                queryReturnBean.setErr(AppConstants.MSG_TABLE_ALREADY_EXISTED);
            } else {
                String query = structureLogic.createTable(bean);
                if (query != null) {
                    queryReturnBean.setQuery(query);
                } else {
                    queryReturnBean.setMsg(AppConstants.MSG_TABLE_CREATED);
                }
            }
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/view/exists.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean viewExists(@Model CreateViewBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (structureLogic.isEmpty(bean.getView_name())) {
                defaultBean.setErr("msg.view_name_blank");
            } else if (structureLogic.isTableExisted(bean.getView_name(), bean.getRequest_db())) {
                defaultBean.setErr(AppConstants.MSG_VIEW_ALREADY_EXISTED);
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/view/create.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    private QueryReturnBean createView(@Model CreateViewBean bean, @LogicParam StructureLogic structureLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            if (structureLogic.isEmpty(bean.getView_name())) {
                queryReturnBean.setErr("msg.view_name_blank");
            } else if (structureLogic.isEmpty(bean.getDefinition())) {
                queryReturnBean.setErr("msg.view_definition_blank");
            } else if (structureLogic.isTableExisted(bean.getView_name(), bean.getRequest_db())) {
                queryReturnBean.setErr(AppConstants.MSG_VIEW_ALREADY_EXISTED);
            } else {
                String result = structureLogic.createView(bean);
                if (result != null) {
                    queryReturnBean.setQuery(result);
                } else {
                    queryReturnBean.setMsg(AppConstants.MSG_VIEW_CREATED);
                }
            }
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/structure/tables/drop.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean dropTables(@Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            structureLogic.dropTables(bean, true);
            defaultBean.setMsg(AppConstants.MSG_TABLES_DROPPED_SUCCESSFULLY);
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }


    @Handle(path = "/database/structure/views/drop.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean dropViews(@Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            structureLogic.dropTables(bean, false);
            defaultBean.setMsg(AppConstants.MSG_VIEW_DROPPED_SUCCESSFULLY);
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/structure/truncate.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean truncate(@Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            structureLogic.truncateTables(bean);
            defaultBean.setMsg(AppConstants.MSG_TABLES_TRUNCATE_SUCCESSFULLY);
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/structure/copy.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean copyTables(@Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            structureLogic.copyTables(bean);
            defaultBean.setMsg(AppConstants.MSG_COPY_DONE);
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/structure/duplicate.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean duplidateTables(@Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            structureLogic.duplicateTable(bean);
            defaultBean.setMsg(AppConstants.MSG_DUPLICATE_TABLE_SUCCESSFULLY);
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/structure/prefix.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean prefixTables(@Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (bean.getType() != null) {
                if (Constants.ADD.equalsIgnoreCase(bean.getType())) {
                    structureLogic.addPrefix(bean);
                    defaultBean.setMsg(AppConstants.MSG_EXECUTED_SUCCESSFULLY);
                } else if (Constants.REPLACE.equalsIgnoreCase(bean.getType())) {
                    structureLogic.replacePrefix(bean);
                    defaultBean.setMsg(AppConstants.MSG_EXECUTED_SUCCESSFULLY);
                } else if (Constants.REMOVE.equalsIgnoreCase(bean.getType())) {
                    structureLogic.removePrefix(bean);
                    defaultBean.setMsg(AppConstants.MSG_EXECUTED_SUCCESSFULLY);
                }
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/structure/suffix.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean suffixTables(@Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (bean.getType() != null) {
                if (Constants.ADD.equalsIgnoreCase(bean.getType())) {
                    structureLogic.addSuffix(bean);
                    defaultBean.setMsg(AppConstants.MSG_EXECUTED_SUCCESSFULLY);
                } else if (Constants.REPLACE.equalsIgnoreCase(bean.getType())) {
                    structureLogic.replaceSuffix(bean);
                    defaultBean.setMsg(AppConstants.MSG_EXECUTED_SUCCESSFULLY);
                } else if (Constants.REMOVE.equalsIgnoreCase(bean.getType())) {
                    structureLogic.removeSuffix(bean);
                    defaultBean.setMsg(AppConstants.MSG_EXECUTED_SUCCESSFULLY);
                }
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/structure/show_create.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public QueryReturnBean tableCreateShow(@Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        QueryReturnBean jsonObject = new QueryReturnBean();
        try {
            String result = structureLogic.showCreate(bean, true);
            jsonObject.setQuery(result);
        } catch (SQLException e) {
            jsonObject.setErr(e.getMessage());
        }
        return jsonObject;
    }
}
