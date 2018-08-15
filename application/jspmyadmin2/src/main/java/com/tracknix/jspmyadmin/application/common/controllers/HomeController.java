package com.tracknix.jspmyadmin.application.common.controllers;

import com.tracknix.jspmyadmin.application.common.beans.HomeBean;
import com.tracknix.jspmyadmin.application.common.services.HomeLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.SERVER)
public class HomeController {

    @Handle(path = "/home.jma", methodType = MethodType.GET)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public ResponseBody<HomeBean> home(@Model HomeBean bean, @LogicParam HomeLogic homeLogic) {
        try {
            homeLogic.fillBean(bean);
            return new ResponseBody<HomeBean>().body(bean);
        } catch (SQLException e) {
            return new ResponseBody<HomeBean>().error(ResponseBody.MYSQL_ERROR).message(e.getMessage());
        }
    }

    @Handle(path = "/collations.jma", methodType = MethodType.GET)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public ResponseBody<Map<String, List<String>>> collations(@LogicParam HomeLogic homeLogic) {
        try {
            return new ResponseBody<Map<String, List<String>>>().body(homeLogic.getCollationMap());
        } catch (SQLException e) {
            return new ResponseBody<Map<String, List<String>>>().error(ResponseBody.MYSQL_ERROR).message(e.getMessage());
        }
    }

    @Handle(path = "/home/save_collation.jma", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public ResponseBody<Void> saveCollation(@Model HomeBean bean, @LogicParam HomeLogic homeLogic) {
        if (bean.getCollation() != null && !Constants.BLANK.equals(bean.getCollation().trim())) {
            try {
                homeLogic.saveServerCollation(bean.getCollation());
            } catch (SQLException e) {
                return new ResponseBody<Void>().error(ResponseBody.MYSQL_ERROR).message(e.getMessage());
            }
            return new ResponseBody<Void>().message(AppConstants.MSG_SAVE_SUCCESS);
        } else {
            return new ResponseBody<Void>().error(ResponseBody.PARAMS_ERROR).message("err.collection_empty");
        }
    }
}