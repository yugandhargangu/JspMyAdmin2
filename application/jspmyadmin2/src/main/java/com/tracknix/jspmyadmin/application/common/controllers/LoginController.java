package com.tracknix.jspmyadmin.application.common.controllers;

import com.tracknix.jspmyadmin.application.common.beans.LoginBean;
import com.tracknix.jspmyadmin.application.common.services.LoginLogic;
import com.tracknix.jspmyadmin.framework.connection.ConnectionType;
import com.tracknix.jspmyadmin.framework.connection.ConnectionTypeCheck;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 * @modified_at 2017/01/17
 */
@WebController(authentication = false, requestLevel = RequestLevel.DEFAULT)
public class LoginController {

    @Handle(path = "/login.html")
    @GenerateToken
    public void load(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_COMMON_LOGIN);
    }

    @Handle(path = "/login.text", methodType = MethodType.GET)
    @ValidateToken
    @GenerateToken
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public LoginBean getLoginConfig(@Model LoginBean bean) {
        bean.setHalf_config(ConnectionType.HALF_CONFIG.equals(ConnectionTypeCheck.check()));
        return bean;
    }

    @Handle(path = "/login.text", methodType = MethodType.POST)
    @ValidateToken
    @GenerateToken
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean check(Messages messages, @Model LoginBean bean, @LogicParam LoginLogic loginLogic) {
        DefaultBean defaultBean = new DefaultBean();
        if (!loginLogic.isValidConnection(bean)) {
            defaultBean.setErr(messages.getMessage(AppConstants.ERR_INVALID_SETTINGS));
        }
        return defaultBean;
    }

}
