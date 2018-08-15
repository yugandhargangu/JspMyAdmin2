package com.tracknix.jspmyadmin.application.server.controllers;

import com.tracknix.jspmyadmin.application.server.beans.common.CharsetBean;
import com.tracknix.jspmyadmin.application.server.beans.common.CommonListBean;
import com.tracknix.jspmyadmin.application.server.beans.common.StatusBean;
import com.tracknix.jspmyadmin.application.server.services.CharsetLogic;
import com.tracknix.jspmyadmin.application.server.services.EngineLogic;
import com.tracknix.jspmyadmin.application.server.services.PluginLogic;
import com.tracknix.jspmyadmin.application.server.services.StatusLogic;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.ContentType;
import com.tracknix.jspmyadmin.framework.web.utils.RequestLevel;
import com.tracknix.jspmyadmin.framework.web.utils.ResponseBody;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.SERVER)
public class CommonController {

    @Handle(path = "/server/status.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public ResponseBody<StatusBean> status(@Model StatusBean bean, @LogicParam StatusLogic statusLogic) {
        try {
            statusLogic.fillBean(bean);
            return new ResponseBody<StatusBean>().body(bean);
        } catch (SQLException e) {
            return new ResponseBody<StatusBean>().error(ResponseBody.MYSQL_ERROR).message(e.getMessage());
        }
    }

    @Handle(path = "/server/charsets.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public ResponseBody<CharsetBean> charsets(@Model CharsetBean bean, @LogicParam CharsetLogic charsetLogic) {
        try {
            charsetLogic.fillBean(bean);
            return new ResponseBody<CharsetBean>().body(bean);
        } catch (SQLException e) {
            return new ResponseBody<CharsetBean>().error(ResponseBody.MYSQL_ERROR).message(e.getMessage());
        }
    }

    @Handle(path = "/server/engines.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public ResponseBody<CommonListBean> engines(@Model CommonListBean bean, @LogicParam EngineLogic engineLogic) {
        try {
            engineLogic.fillBean(bean);
            return new ResponseBody<CommonListBean>().body(bean);
        } catch (SQLException e) {
            return new ResponseBody<CommonListBean>().error(ResponseBody.MYSQL_ERROR).message(e.getMessage());
        }
    }

    @Handle(path = "/server/plugins.jma")
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public ResponseBody<CommonListBean> plugins(@Model CommonListBean bean, @LogicParam PluginLogic pluginLogic) {
        try {
            pluginLogic.fillBean(bean);
            return new ResponseBody<CommonListBean>().body(bean);
        } catch (SQLException e) {
            return new ResponseBody<CommonListBean>().error(ResponseBody.MYSQL_ERROR).message(e.getMessage());
        }
    }
}
