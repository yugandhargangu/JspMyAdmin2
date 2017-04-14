package com.tracknix.jspmyadmin.application.database.controllers;

import com.tracknix.jspmyadmin.application.common.services.HomeLogic;
import com.tracknix.jspmyadmin.application.common.services.SideBarLogic;
import com.tracknix.jspmyadmin.application.database.beans.structure.CreateTableBean;
import com.tracknix.jspmyadmin.application.database.beans.structure.CreateViewBean;
import com.tracknix.jspmyadmin.application.database.beans.structure.StructureBean;
import com.tracknix.jspmyadmin.application.database.beans.structure.StructureListBean;
import com.tracknix.jspmyadmin.application.database.services.StructureLogic;
import com.tracknix.jspmyadmin.application.server.services.EngineLogic;
import com.tracknix.jspmyadmin.framework.connection.util.*;
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

    @Handle(path = "/database/structure/tables.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    public StructureListBean tables(Messages messages, @Model StructureListBean bean, @LogicParam StructureLogic structureLogic, @LogicParam SideBarLogic sideBarLogic) {
        try {
            structureLogic.fillBean(bean, true);
            bean.setDatabase_list(sideBarLogic.getDatabaseList());
            String[] msgs = new String[6];
            msgs[0] = messages.getMessage("msg.drop_table_alert");
            msgs[1] = messages.getMessage("msg.truncate_table_alert");
            msgs[2] = messages.getMessage("msg.prefix_blank");
            msgs[3] = messages.getMessage("msg.new_prefix_blank");
            msgs[4] = messages.getMessage("msg.suffix_blank");
            msgs[5] = messages.getMessage("msg.new_suffix_blank");
            bean.setMsgs(msgs);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/database/structure/views.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    public StructureListBean views(Messages messages, @Model StructureListBean bean, @LogicParam StructureLogic structureLogic) {
        try {
            structureLogic.fillBean(bean, false);
            String[] msgs = new String[5];
            msgs[0] = messages.getMessage("msg.drop_view_alert");
            msgs[1] = messages.getMessage("msg.prefix_blank");
            msgs[2] = messages.getMessage("msg.new_prefix_blank");
            msgs[3] = messages.getMessage("msg.suffix_blank");
            msgs[4] = messages.getMessage("msg.new_suffix_blank");
            bean.setMsgs(msgs);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/database/table/exists.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean tableExists(Messages messages, @Model CreateTableBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (structureLogic.isEmpty(bean.getTable_name())) {
                defaultBean.setErr(messages.getMessage("msg.table_name_blank"));
            } else if (structureLogic.isTableExisted(bean.getTable_name(), bean.getRequest_db())) {
                defaultBean.setErr(messages.getMessage(AppConstants.MSG_TABLE_ALREADY_EXISTED));
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/table/create.text", methodType = MethodType.GET)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    public CreateTableBean createTable(Messages messages, @Model CreateTableBean bean, @LogicParam HomeLogic homeLogic, @LogicParam EngineLogic engineLogic, @LogicParam StructureLogic structureLogic) {
        try {
            bean.setCollation_map(homeLogic.getCollationMap());
            bean.setEngine_list(engineLogic.getEngineList());
            bean.setPartition_support(structureLogic.isSupportsPartition());
            bean.setPartition_list(Partitions.getInstance().getPartitions());
            bean.setDatatype_list(DataTypes.getInstance().getDatatypes());
            String[] msgs = new String[7];
            msgs[0] = messages.getMessage("msg.primary_key_one_column_alert");
            msgs[1] = messages.getMessage("msg.auto_increment_one_column_alert");
            msgs[2] = messages.getMessage("msg.duplicate_columna_name");
            msgs[3] = messages.getMessage("msg.length_value_blank_column");
            msgs[4] = messages.getMessage("msg.default_value_invalid_column");
            msgs[5] = messages.getMessage("msg.comment_invalid_column");
            msgs[6] = messages.getMessage("msg.all_columns_blank");
            bean.setMsgs(msgs);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/database/table/create.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public QueryReturnBean createTable(Messages messages, RequestAdaptor requestAdaptor, @Model CreateTableBean bean, @LogicParam StructureLogic structureLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            if (structureLogic.isEmpty(bean.getTable_name())) {
                queryReturnBean.setErr(messages.getMessage("msg.table_name_blank"));
            } else if (structureLogic.isTableExisted(bean.getTable_name(), bean.getRequest_db())) {
                queryReturnBean.setErr(messages.getMessage(AppConstants.MSG_TABLE_ALREADY_EXISTED));
            } else {
                String query = structureLogic.createTable(bean);
                if (query != null) {
                    queryReturnBean.setQuery(query);
                } else {
                    queryReturnBean.setMsg(messages.getMessage(AppConstants.MSG_TABLE_CREATED));
                }
            }
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/view/exists.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean viewExists(Messages messages, @Model CreateViewBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (structureLogic.isEmpty(bean.getView_name())) {
                defaultBean.setErr(messages.getMessage("msg.view_name_blank"));
            } else if (structureLogic.isTableExisted(bean.getView_name(), bean.getRequest_db())) {
                defaultBean.setErr(messages.getMessage(AppConstants.MSG_VIEW_ALREADY_EXISTED));
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/view/create.text", methodType = MethodType.GET)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    public CreateViewBean createView(Messages messages, @Model CreateViewBean bean) {
        bean.setAlgorithm_list(Algorithms.getInstance().getAlgorithms());
        bean.setDefiner_list(Definers.getInstance().getDefiners());
        bean.setSecurity_list(SecurityTypes.getInstance().getSecurityTypes());
        bean.setCheck_list(CheckOptions.getInstance().getCheckOptions());
        return bean;
    }

    @Handle(path = "/database/view/create.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    private QueryReturnBean createView(Messages messages, @Model CreateViewBean bean, @LogicParam StructureLogic structureLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            if (structureLogic.isEmpty(bean.getView_name())) {
                queryReturnBean.setErr(messages.getMessage("msg.view_name_blank"));
            } else if (structureLogic.isEmpty(bean.getDefinition())) {
                queryReturnBean.setErr(messages.getMessage("msg.view_definition_blank"));
            } else if (structureLogic.isTableExisted(bean.getView_name(), bean.getRequest_db())) {
                queryReturnBean.setErr(messages.getMessage(AppConstants.MSG_VIEW_ALREADY_EXISTED));
            } else {
                String result = structureLogic.createView(bean);
                System.out.println(result);
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

    @Handle(path = "/database/structure/tables/drop.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean dropTables(Messages messages, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            structureLogic.dropTables(bean, true);
            defaultBean.setMsg(messages.getMessage(AppConstants.MSG_TABLES_DROPPED_SUCCESSFULLY));
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }


    @Handle(path = "/database/structure/views/drop.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean dropViews(Messages messages, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            structureLogic.dropTables(bean, false);
            defaultBean.setMsg(messages.getMessage(AppConstants.MSG_VIEW_DROPPED_SUCCESSFULLY));
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/structure/truncate.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean truncate(Messages messages, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            structureLogic.truncateTables(bean);
            defaultBean.setMsg(messages.getMessage(AppConstants.MSG_TABLES_TRUNCATE_SUCCESSFULLY));
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/structure/copy.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean copyTables(Messages messages, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            structureLogic.copyTables(bean);
            defaultBean.setMsg(messages.getMessage(AppConstants.MSG_COPY_DONE));
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/structure/duplicate.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean duplidateTables(Messages messages, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            structureLogic.duplicateTable(bean);
            defaultBean.setMsg(messages.getMessage(AppConstants.MSG_DUPLICATE_TABLE_SUCCESSFULLY));
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/structure/prefix.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean prefixTables(Messages messages, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (bean.getType() != null) {
                if (Constants.ADD.equalsIgnoreCase(bean.getType())) {
                    structureLogic.addPrefix(bean);
                    defaultBean.setMsg(messages.getMessage(AppConstants.MSG_EXECUTED_SUCCESSFULLY));
                } else if (Constants.REPLACE.equalsIgnoreCase(bean.getType())) {
                    structureLogic.replacePrefix(bean);
                    defaultBean.setMsg(messages.getMessage(AppConstants.MSG_EXECUTED_SUCCESSFULLY));
                } else if (Constants.REMOVE.equalsIgnoreCase(bean.getType())) {
                    structureLogic.removePrefix(bean);
                    defaultBean.setMsg(messages.getMessage(AppConstants.MSG_EXECUTED_SUCCESSFULLY));
                }
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/structure/suffix.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean suffixTables(Messages messages, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (bean.getType() != null) {
                if (Constants.ADD.equalsIgnoreCase(bean.getType())) {
                    structureLogic.addSuffix(bean);
                    defaultBean.setMsg(messages.getMessage(AppConstants.MSG_EXECUTED_SUCCESSFULLY));
                } else if (Constants.REPLACE.equalsIgnoreCase(bean.getType())) {
                    structureLogic.replaceSuffix(bean);
                    defaultBean.setMsg(messages.getMessage(AppConstants.MSG_EXECUTED_SUCCESSFULLY));
                } else if (Constants.REMOVE.equalsIgnoreCase(bean.getType())) {
                    structureLogic.removeSuffix(bean);
                    defaultBean.setMsg(messages.getMessage(AppConstants.MSG_EXECUTED_SUCCESSFULLY));
                }
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/structure/show_create.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
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
