package com.tracknix.jspmyadmin.application.server.common.controllers;

import com.tracknix.jspmyadmin.application.server.common.beans.CharsetBean;
import com.tracknix.jspmyadmin.application.server.common.beans.CommonListBean;
import com.tracknix.jspmyadmin.application.server.common.beans.StatusBean;
import com.tracknix.jspmyadmin.application.server.common.logic.CharsetLogic;
import com.tracknix.jspmyadmin.application.server.common.logic.EngineLogic;
import com.tracknix.jspmyadmin.application.server.common.logic.PluginLogic;
import com.tracknix.jspmyadmin.application.server.common.logic.StatusLogic;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.ContentType;
import com.tracknix.jspmyadmin.framework.web.utils.RequestLevel;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.SERVER)
public class CommonController {

    @Handle(path = "/server/status.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public StatusBean status(@Model StatusBean bean, @LogicParam StatusLogic statusLogic) {
        try {
            statusLogic.fillBean(bean);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/server/charsets.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public CharsetBean charsets(@Model CharsetBean bean, @LogicParam CharsetLogic charsetLogic) {
        try {
            charsetLogic.fillBean(bean);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/server/engines.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public CommonListBean engines(@Model CommonListBean bean, @LogicParam EngineLogic engineLogic) {
        try {
            engineLogic.fillBean(bean);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }

    @Handle(path = "/server/plugins.text")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public CommonListBean plugins(@Model CommonListBean bean, @LogicParam PluginLogic pluginLogic) {
        try {
            pluginLogic.fillBean(bean);
        } catch (SQLException e) {
            bean.setErr(e.getMessage());
        }
        return bean;
    }
}
