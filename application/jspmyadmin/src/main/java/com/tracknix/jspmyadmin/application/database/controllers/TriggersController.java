package com.tracknix.jspmyadmin.application.database.controllers;

import com.tracknix.jspmyadmin.application.common.services.DataLogic;
import com.tracknix.jspmyadmin.application.database.beans.trigger.TablesBean;
import com.tracknix.jspmyadmin.application.database.beans.trigger.TriggerBean;
import com.tracknix.jspmyadmin.application.database.beans.trigger.TriggersBean;
import com.tracknix.jspmyadmin.application.database.services.TriggerLogic;
import com.tracknix.jspmyadmin.framework.connection.util.Definers;
import com.tracknix.jspmyadmin.framework.connection.util.TriggerEvents;
import com.tracknix.jspmyadmin.framework.connection.util.TriggerOrder;
import com.tracknix.jspmyadmin.framework.connection.util.TriggerTime;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class TriggersController {

    @Handle(path = "/database/structure/triggers.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    public TriggersBean triggers(Messages messages, @Model TriggersBean bean, @LogicParam TriggerLogic triggerLogic) {
        try {
            triggerLogic.fillListBean(bean);
            bean.setMsgs(new String[]{messages.getMessage("msg.drop_trigger_alert")});
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }


    @Handle(path = "/database/trigger/table_list.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    @ValidateToken
    public TablesBean tableList(@Model TriggerBean bean, @LogicParam DataLogic dataLogic) {
        TablesBean tablesBean = new TablesBean();
        try {
            tablesBean.setTables(dataLogic.getTableList(bean.getDatabase_name()));
        } catch (SQLException e) {
            tablesBean.setErr(e.getMessage());
        }
        return tablesBean;
    }

    @Handle(path = "/database/trigger/exists.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    @ValidateToken
    public DefaultBean triggerExists(Messages messages, @Model TriggerBean bean, @LogicParam TriggerLogic triggerLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (triggerLogic.isExisted(bean.getTrigger_name(), bean.getRequest_db())) {
                defaultBean.setErr(messages.getMessage(AppConstants.MSG_TRIGGER_ALREADY_EXISTED));
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/trigger/create.text", methodType = MethodType.GET)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    public TriggerBean createTrigger(@Model TriggerBean bean, @LogicParam TriggerLogic triggerLogic, @LogicParam DataLogic dataLogic) {
        try {
            bean.setOther_trigger_name_list(triggerLogic.getTriggerList(bean.getRequest_db()));
            bean.setDatabase_name(bean.getRequest_db());
            bean.setDatabase_name_list(dataLogic.getDatabaseList());
            bean.setDefiner_list(Definers.getInstance().getDefiners());
            bean.setTrigger_time_list(TriggerTime.getInstance().getTriggerTimes());
            bean.setTrigger_event_list(TriggerEvents.getInstance().getTriggerEvents());
            bean.setTrigger_order_list(TriggerOrder.getInstance().getTriggerOrders());
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/database/trigger/create.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    @ValidateToken
    public QueryReturnBean createTrigger(Messages messages, @Model TriggerBean bean, @LogicParam TriggerLogic triggerLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            if (triggerLogic.isExisted(bean.getTrigger_name(), bean.getRequest_db())) {
                queryReturnBean.setErr(messages.getMessage(AppConstants.MSG_TRIGGER_ALREADY_EXISTED));
            } else {
                String result = triggerLogic.save(bean);
                if (result != null) {
                    queryReturnBean.setQuery(result);
                } else {
                    queryReturnBean.setMsg(messages.getMessage(AppConstants.MSG_TRIGGER_CREATE_SUCCESS));
                }
            }
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/trigger/show_create.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    @ValidateToken
    public QueryReturnBean showCreateTrigger(@Model TriggersBean bean, @LogicParam TriggerLogic triggerLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            queryReturnBean.setQuery(triggerLogic.showCreate(bean));
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/trigger/drop.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    @ValidateToken
    public DefaultBean dropTrigger(Messages messages, @Model TriggersBean bean, @LogicParam TriggerLogic triggerLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            triggerLogic.drop(bean);
            defaultBean.setMsg(messages.getMessage(AppConstants.MSG_TRIGGER_DROP_SUCCESS));
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }
}