package com.tracknix.jspmyadmin.application.database.controllers;

import com.tracknix.jspmyadmin.application.database.beans.event.EventBean;
import com.tracknix.jspmyadmin.application.database.beans.event.EventsBean;
import com.tracknix.jspmyadmin.application.database.services.EventLogic;
import com.tracknix.jspmyadmin.framework.connection.util.Definers;
import com.tracknix.jspmyadmin.framework.connection.util.EventIntervals;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class EventsController {

    @Handle(path = "/database/structure/events.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    public EventsBean events(Messages messages, @Model EventsBean bean, @LogicParam EventLogic eventLogic) {
        try {
            eventLogic.fillListBean(bean);
            String[] msgs = new String[]{
                    messages.getMessage("msg.drop_event_alert")
            };
            bean.setMsgs(msgs);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/database/event/show_create.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    @ValidateToken
    public QueryReturnBean showCreateEvent(@Model EventsBean bean, @LogicParam EventLogic eventLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            queryReturnBean.setQuery(eventLogic.getShowCreate(bean));
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/event/create.text", methodType = MethodType.GET)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    public EventBean createEvent(@Model EventBean bean) {
        bean.setInterval_list(EventIntervals.getInstance().getEventIntervals());
        bean.setDefiner_list(Definers.getInstance().getDefiners());
        return bean;
    }

    @Handle(path = "/database/event/create.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    @ValidateToken
    public QueryReturnBean createEvent(Messages messages, @Model EventBean bean, @LogicParam EventLogic eventLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            if (eventLogic.isEmpty(bean.getEvent_name())) {
                queryReturnBean.setErr(messages.getMessage("msg.event_name_blank"));
            } else if (eventLogic.isExisted(bean.getEvent_name(), bean.getRequest_db())) {
                queryReturnBean.setErr(messages.getMessage("msg.event_already_existed"));
            } else {
                String result = eventLogic.saveEvent(bean);
                if (result != null) {
                    queryReturnBean.setQuery(result.trim());
                } else {
                    queryReturnBean.setMsg(messages.getMessage(AppConstants.MSG_EVENT_CREATE_SUCCESS));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/event/exists.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    @ValidateToken
    public DefaultBean exists(Messages messages, @Model EventBean bean, @LogicParam EventLogic eventLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (eventLogic.isEmpty(bean.getEvent_name())) {
                defaultBean.setErr(messages.getMessage("msg.event_name_blank"));
            } else if (eventLogic.isExisted(bean.getEvent_name(), bean.getRequest_db())) {
                defaultBean.setErr(messages.getMessage("msg.event_already_existed"));
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/event/rename.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    @ValidateToken
    public DefaultBean renameEvent(Messages messages, @Model EventsBean bean, @LogicParam EventLogic eventLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            eventLogic.renameEvent(bean);
            defaultBean.setMsg(messages.getMessage(AppConstants.MSG_EXECUTED_SUCCESSFULLY));
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/event/enable.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    @ValidateToken
    public DefaultBean enableEvent(Messages messages, @Model EventsBean bean, @LogicParam EventLogic eventLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            eventLogic.enableOrDisableEvent(bean, true);
            defaultBean.setMsg(messages.getMessage(AppConstants.MSG_EXECUTED_SUCCESSFULLY));
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/event/disable.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    @ValidateToken
    public DefaultBean disableEvent(Messages messages, @Model EventsBean bean, @LogicParam EventLogic eventLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            eventLogic.enableOrDisableEvent(bean, false);
            defaultBean.setMsg(messages.getMessage(AppConstants.MSG_EXECUTED_SUCCESSFULLY));
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/event/drop.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    @ValidateToken
    public DefaultBean dropEvent(Messages messages, @Model EventsBean bean, @LogicParam EventLogic eventLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            eventLogic.dropEvent(bean);
            defaultBean.setMsg(messages.getMessage(AppConstants.MSG_EVENT_DROP_SUCCESS));
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }
}
