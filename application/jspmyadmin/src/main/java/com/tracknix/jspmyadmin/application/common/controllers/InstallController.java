/**
 *
 */
package com.tracknix.jspmyadmin.application.common.controllers;

import com.tracknix.jspmyadmin.application.common.beans.InstallBean;
import com.tracknix.jspmyadmin.application.common.logic.InstallLogic;
import com.tracknix.jspmyadmin.framework.connection.ConnectionFactory;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/03
 * @modified_at 2017/01/12
 */
@WebController(authentication = false, requestLevel = RequestLevel.DEFAULT)
public class InstallController {

    @Handle(path = "/install.html", methodType = MethodType.GET)
    @GenerateToken
    public void install_load(View view, @Model InstallBean bean) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_COMMON_INSTALL);
    }

    @Handle(path = "/install.text", methodType = MethodType.POST)
    @ValidateToken
    @GenerateToken
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean install_save(Messages messages, @Model InstallBean bean, @LogicParam InstallLogic installLogic) {
        System.out.println(bean);
        DefaultBean defaultBean = new DefaultBean();
        try {
            boolean response = installLogic.installConfig(bean);
            if (response) {
                ConnectionFactory.init();
            } else {
                defaultBean.setErr(messages.getMessage(AppConstants.ERR_INVALID_SETTINGS));
            }
        } catch (IOException e) {
            defaultBean.setErr(messages.getMessage(AppConstants.ERR_UNABLE_TO_CONNECT_WITH_SERVER));
        }
        return defaultBean;
    }

    @Handle(path = "/uninstall.html", methodType = MethodType.GET)
    @GenerateToken
    public void uninstall_load(View view, @Model InstallBean bean) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_COMMON_UNINSTALL);
    }

    @Handle(path = "/uninstall.text", methodType = MethodType.POST)
    @ValidateToken
    @GenerateToken
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean uninstall_save(HttpSession httpSession, Messages messages, @Model InstallBean bean,
                                      @LogicParam InstallLogic installLogic) {
        DefaultBean defaultBean = new DefaultBean();
        try {
            if (!installLogic.uninstallConfig(bean)) {
                defaultBean.setErr(messages.getMessage(AppConstants.ERR_INVALID_SETTINGS));
                return defaultBean;
            } else {
                httpSession.invalidate();
                ConnectionFactory.init();
                return defaultBean;
            }
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        } catch (EncodingException e) {
        }
        defaultBean.setErr(messages.getMessage(AppConstants.ERR_UNABLE_TO_CONNECT_WITH_SERVER));
        return defaultBean;
    }
}
