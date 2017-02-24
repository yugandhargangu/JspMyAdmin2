package com.tracknix.jspmyadmin.application.database.event.controllers;

import com.tracknix.jspmyadmin.application.database.event.beans.EventBean;
import com.tracknix.jspmyadmin.application.database.event.beans.EventListBean;
import com.tracknix.jspmyadmin.application.database.event.logic.EventLogic;
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
public class EventsController {

    @Handle(path = "/database_events.html")
    @GenerateToken
    private void events(View view, @Model EventListBean bean, @LogicParam EventLogic eventLogic) throws SQLException, EncodingException {
        eventLogic.fillListBean(bean);
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_EVENT_EVENTS);
    }

    @Handle(path = "/database_event_show_create.text")
    @ValidateToken
    @Rest
    private QueryReturnBean showCreateEvent(RequestAdaptor requestAdaptor, @Model EventListBean bean, @LogicParam EventLogic eventLogic) throws EncodingException {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            String result = eventLogic.getShowCreate(bean);
            queryReturnBean.setQuery(result);
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database_event_create.html", methodType = MethodType.POST)
    @ValidateToken
    @GenerateToken
    private void createEvent(View view, @Model EventBean bean, @LogicParam EventLogic eventLogic) throws EncodingException {
        bean.setStart_interval(eventLogic.getStartInterval(bean.getInterval_list()));
        bean.setEnd_interval(eventLogic.getEndInterval(bean.getInterval_list()));
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_EVENT_CREATEEVENT);
    }

    @Handle(path = "/database_event_create_post.text", methodType = MethodType.POST)
    @ValidateToken
    @Rest
    private QueryReturnBean createEvent(EncodeHelper encodeObj, RequestAdaptor requestAdaptor, @Model EventBean bean, @LogicParam EventLogic eventLogic) throws EncodingException {
        QueryReturnBean queryReturnBean = new QueryReturnBean();
        try {
            String result = eventLogic.saveEvent(bean);
            if (result != null) {
                queryReturnBean.setQuery(result.trim());
            } else {
                queryReturnBean.setMsg(AppConstants.MSG_EVENT_CREATE_SUCCESS);
            }
        } catch (SQLException e) {
            queryReturnBean.setErr(e.getMessage());
        }
        return queryReturnBean;
    }

    @Handle(path = "/database_event_rename.html")
    @ValidateToken
    private void renameEvent(View view, RedirectParams redirectParams, @Model EventListBean bean, @LogicParam EventLogic eventLogic) {
        try {
            eventLogic.renameEvent(bean);
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_EVENTS);
    }

    @Handle(path = "/database_event_enable.html")
    @ValidateToken
    private void enableEvent(View view, RedirectParams redirectParams, @Model EventListBean bean, @LogicParam EventLogic eventLogic) {
        try {
            eventLogic.enableEvent(bean);
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_EVENTS);
    }

    @Handle(path = "/database_event_disable.html")
    @ValidateToken
    private void disableEvent(View view, RedirectParams redirectParams, @Model EventListBean bean, @LogicParam EventLogic eventLogic) {
        try {
            eventLogic.disableEvent(bean);
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_EVENTS);
    }

    @Handle(path = "/database_event_drop.html")
    @ValidateToken
    private void dropEvent(View view, RedirectParams redirectParams, @Model EventListBean bean, @LogicParam EventLogic eventLogic) {
        try {
            eventLogic.dropEvent(bean);
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EVENT_DROP_SUCCESS);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_EVENTS);
    }
}
