package com.tracknix.jspmyadmin.application.common.controllers;

import com.tracknix.jspmyadmin.application.common.beans.HomeBean;
import com.tracknix.jspmyadmin.application.common.services.HomeLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.SERVER)
public class HomeController {

    @Handle(path = "/home.text", methodType = MethodType.GET)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    public HomeBean home(HttpSession httpSession, @Model HomeBean bean, @LogicParam HomeLogic homeLogic) throws SQLException {
        bean.setLanguage_map(new LinkedHashMap<String, String>(Constants.Utils.LANGUAGE_MAP));
        bean.setCollation_map(homeLogic.getCollationMap());
        int[] fontsizes = new int[141];
        for (int i = 0, j = 60; i < fontsizes.length; i++) {
            fontsizes[i] = j++;
        }
        bean.setFontsizes(fontsizes);
        Object temp = httpSession.getAttribute(Constants.SESSION_FONTSIZE);
        if (temp != null) {
            bean.setFontsize(temp.toString());
        } else {
            bean.setFontsize("80");
        }
        homeLogic.fillBean(bean);
        return bean;
    }

    @Handle(path = "/home/save_collation.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean saveCollation(Messages messages, @Model HomeBean bean, @LogicParam HomeLogic homeLogic) {
        DefaultBean defaultBean = new DefaultBean();
        if (bean.getCollation() != null && !Constants.BLANK.equals(bean.getCollation().trim())) {
            try {
                homeLogic.saveServerCollation(bean.getCollation());
            } catch (SQLException e) {
                defaultBean.setErr(e.getMessage());
            }
            defaultBean.setMsg(messages.getMessage(AppConstants.MSG_SAVE_SUCCESS));
        } else {
            defaultBean.setErr(messages.getMessage(AppConstants.ERR_INVALID_ACCESS));
        }
        return defaultBean;
    }

    @Handle(path = "/home/save_locale.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean saveLocale(Messages messages, HttpSession httpSession, @Model HomeBean bean) {
        DefaultBean defaultBean = new DefaultBean();
        if (bean.getLanguage() != null && !Constants.BLANK.equals(bean.getLanguage().trim())) {
            httpSession.setAttribute(Constants.SESSION_LOCALE, bean.getLanguage());
            defaultBean.setMsg(messages.getMessage(AppConstants.MSG_SAVE_SUCCESS));
        } else {
            defaultBean.setErr(messages.getMessage(AppConstants.ERR_INVALID_ACCESS));
        }
        return defaultBean;
    }

    @Handle(path = "/home/save_fontsize.text", methodType = MethodType.POST)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @ValidateToken
    @GenerateToken
    public DefaultBean saveFontSize(Messages messages, HttpSession httpSession, @Model HomeBean bean) {
        DefaultBean defaultBean = new DefaultBean();
        httpSession.setAttribute(Constants.SESSION_FONTSIZE, bean.getFontsize());
        defaultBean.setMsg(messages.getMessage(AppConstants.MSG_SAVE_SUCCESS));
        return defaultBean;
    }
}