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

    @Handle(path = "/database/structure/events.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public EventsBean events(@Model EventsBean bean, @LogicParam EventLogic eventLogic) {
        try {
            eventLogic.fillListBean(bean);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/database/event/show_create.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public QueryReturnBean showCreateEvent(@Model EventsBean bean, @LogicParam EventLogic eventLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            queryReturnBean.setQuery(eventLogic.getShowCreate(bean));
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/event/create.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public QueryReturnBean createEvent(@Model EventBean bean, @LogicParam EventLogic eventLogic) {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            if (eventLogic.isEmpty(bean.getEvent_name())) {
                queryReturnBean.setErr("msg.event_name_blank");
            } else if (eventLogic.isExisted(bean.getEvent_name(), bean.getRequest_db())) {
                queryReturnBean.setErr("msg.event_already_existed");
            } else {
                String result = eventLogic.saveEvent(bean);
                if (result != null) {
                    queryReturnBean.setQuery(result.trim());
                } else {
                    queryReturnBean.setMsg(AppConstants.MSG_EVENT_CREATE_SUCCESS);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database/event/exists.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean exists(@Model EventBean bean, @LogicParam EventLogic eventLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (eventLogic.isEmpty(bean.getEvent_name())) {
                defaultBean.setErr("msg.event_name_blank");
            } else if (eventLogic.isExisted(bean.getEvent_name(), bean.getRequest_db())) {
                defaultBean.setErr("msg.event_already_existed");
            }
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/event/rename.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean renameEvent(@Model EventsBean bean, @LogicParam EventLogic eventLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            eventLogic.renameEvent(bean);
            defaultBean.setMsg(AppConstants.MSG_EXECUTED_SUCCESSFULLY);
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/event/enable.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean enableEvent(@Model EventsBean bean, @LogicParam EventLogic eventLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            eventLogic.enableOrDisableEvent(bean, true);
            defaultBean.setMsg(AppConstants.MSG_EXECUTED_SUCCESSFULLY);
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/event/disable.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean disableEvent(@Model EventsBean bean, @LogicParam EventLogic eventLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            eventLogic.enableOrDisableEvent(bean, false);
            defaultBean.setMsg(AppConstants.MSG_EXECUTED_SUCCESSFULLY);
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }

    @Handle(path = "/database/event/drop.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean dropEvent(@Model EventsBean bean, @LogicParam EventLogic eventLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            eventLogic.dropEvent(bean);
            defaultBean.setMsg(AppConstants.MSG_EVENT_DROP_SUCCESS);
        } catch (SQLException e) {
            defaultBean.setErr(e.getMessage());
        }
        return defaultBean;
    }
}
