/**
 *
 */
package com.tracknix.jspmyadmin.application.common.controllers;

import com.tracknix.jspmyadmin.application.common.beans.LanguageBean;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.ContentType;
import com.tracknix.jspmyadmin.framework.web.utils.DefaultBean;
import com.tracknix.jspmyadmin.framework.web.utils.MethodType;
import com.tracknix.jspmyadmin.framework.web.utils.RequestLevel;

import javax.servlet.http.HttpSession;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 * @modified_at 2016/01/10
 */
@WebController(authentication = false, requestLevel = RequestLevel.DEFAULT)
public class LanguageController {

    @Handle(path = "/language.text", methodType = MethodType.GET)
    @Rest(contentType = ContentType.APPLICATION_JSON)
    @GenerateToken
    public LanguageBean languages(HttpSession httpSession) {
        LanguageBean languageBean = new LanguageBean();
        languageBean.setLanguage(httpSession.getAttribute(Constants.SESSION_LOCALE).toString());
        languageBean.setLanguage_map(Constants.Utils.LANGUAGE_MAP);
        return languageBean;
    }

    @Handle(path = "/language.text", methodType = MethodType.POST)
    @ValidateToken
    @GenerateToken
    @Rest(contentType = ContentType.APPLICATION_JSON)
    public DefaultBean change(HttpSession httpSession, @Model LanguageBean bean) {
        if (bean.getLanguage() != null && !Constants.BLANK.equals(bean.getLanguage().trim())) {
            httpSession.setAttribute(Constants.SESSION_LOCALE, bean.getLanguage());
        }
        return new DefaultBean();
    }
}
