package com.tracknix.jspmyadmin.application.database.trigger.controllers;

import com.tracknix.jspmyadmin.application.common.logic.DataLogic;
import com.tracknix.jspmyadmin.application.database.trigger.beans.TriggerBean;
import com.tracknix.jspmyadmin.application.database.trigger.beans.TriggerListBean;
import com.tracknix.jspmyadmin.application.database.trigger.logic.TriggerLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.logic.EncodeHelper;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class TriggersController {

    @Handle(path = "/database_triggers.html")
    @GenerateToken
    private void triggers(View view, @Model TriggerListBean bean, @LogicParam TriggerLogic triggerLogic) throws EncodingException, SQLException {
        triggerLogic.fillListBean(bean);
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_TRIGGER_TRIGGERS);
    }


    @Handle(path = "/database_trigger_table_list.text", methodType = MethodType.POST)
    @ValidateToken
    private Object handlePost(RequestAdaptor requestAdaptor, @Model TriggerBean bean, @LogicParam DataLogic dataLogic) throws EncodingException {

        try {
            return dataLogic.getTableList(bean.getDatabase_name(), false);
        } catch (SQLException e) {
            DefaultBean defaultBean = new DefaultBean();
            defaultBean.setErr(e.getMessage());
            return defaultBean;
        }
    }

    @Handle(path = "/database_trigger_create.html", methodType = MethodType.POST)
    @ValidateToken
    private void createTrigger(View view, Messages messages, RedirectParams redirectParams, @Model TriggerBean bean, @LogicParam TriggerLogic triggerLogic) {
        try {
            if (triggerLogic.isExisted(bean.getTrigger_name(), bean.getRequest_db())) {
                redirectParams.put(Constants.ERR,
                        messages.getMessage(AppConstants.MSG_TRIGGER_ALREADY_EXISTED));
                view.setType(ViewType.REDIRECT);
                view.setPath(AppConstants.PATH_DATABASE_TRIGGERS);
                return;
            }
            bean.setOther_trigger_name_list(triggerLogic.getTriggerList(bean.getRequest_db()));
            bean.setDatabase_name(bean.getRequest_db());
            DataLogic dataLogic = new DataLogic();
            bean.setDatabase_name_list(dataLogic.getDatabaseList());
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
            view.setType(ViewType.REDIRECT);
            view.setPath(AppConstants.PATH_DATABASE_TRIGGERS);
            return;
        }
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_TRIGGER_CREATETRIGGER);
    }

    @Handle(path = "/database_trigger_create_post.text", methodType = MethodType.POST)
    @ValidateToken
    @Rest
    private QueryReturnBean createTrigger(Messages messages, EncodeHelper encodeObj, RequestAdaptor requestAdaptor, @Model TriggerBean bean, @LogicParam TriggerLogic triggerLogic) throws EncodingException {
        QueryReturnBean jsonObject = new QueryReturnBean();
        try {
            if (triggerLogic.isExisted(bean.getTrigger_name(), bean.getRequest_db())) {
                jsonObject.setErr(messages.getMessage(AppConstants.MSG_TRIGGER_ALREADY_EXISTED));
            } else {
                String result = triggerLogic.save(bean);
                if (result != null) {
                    jsonObject.setQuery(result);
                } else {
                    jsonObject.setMsg(AppConstants.MSG_TRIGGER_CREATE_SUCCESS);
                }
            }
        } catch (SQLException e) {
            jsonObject.setErr(e.getMessage());
        }
        return jsonObject;
    }

    @Handle(path = "/database_trigger_show_create.text", methodType = MethodType.POST)
    @ValidateToken
    private QueryReturnBean showCreateTriggier(RequestAdaptor requestAdaptor, @Model TriggerListBean bean, @LogicParam TriggerLogic triggerLogic) throws EncodingException {
        QueryReturnBean jsonObject = new QueryReturnBean();
        try {
            String result = triggerLogic.showCreate(bean);
            jsonObject.setQuery(result);
        } catch (SQLException e) {
            jsonObject.setErr(e.getMessage());
        }
        return jsonObject;
    }

    @Handle(path = "/database_trigger_drop.html", methodType = MethodType.POST)
    @ValidateToken
    private void dropTrigger(View view, RedirectParams redirectParams, @Model TriggerListBean bean) {
        try {
            TriggerLogic triggerLogic = new TriggerLogic();
            triggerLogic.drop(bean);
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_TRIGGER_DROP_SUCCESS);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_TRIGGERS);
    }
}