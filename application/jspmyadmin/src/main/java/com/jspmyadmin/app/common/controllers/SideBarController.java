/**
 * 
 */
package com.jspmyadmin.app.common.controllers;

import javax.servlet.http.HttpServletResponse;

import com.jspmyadmin.app.common.beans.SideBarBean;
import com.jspmyadmin.app.common.logic.SideBarLogic;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.Rest;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import com.jspmyadmin.framework.web.utils.RequestLevel;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 *
 */
@WebController(authentication = true, path = "/sidebar.text", requestLevel = RequestLevel.DEFAULT)
@Rest
public class SideBarController {

	private static final String _TYPE_MENUBARMAIN = "menubarMain";
	private static final String _TYPE_CALLCOLUMN = "callColumn";
	private static final String _TYPE_CALLEVENT = "callEvent";
	private static final String _TYPE_CALLROUTINE = "callRoutine";
	private static final String _TYPE_CALLVIEW = "callView";
	private static final String _TYPE_CALLTABLE = "callTable";
	private static final String _TYPE_CALLTRIGGER = "callTrigger";
	private static final String _TYPE_CALLFUNCTION = "callFunction";

	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private HttpServletResponse response;
	@Model
	private SideBarBean bean;

	@HandleGetOrPost
	private String getJson() {
		String result = null;
		SideBarLogic sideBarLogic = null;
		try {
			sideBarLogic = new SideBarLogic();
			sideBarLogic.setEncodeObj(encodeObj);
			if (_TYPE_MENUBARMAIN.equals(bean.getType())) {
				result = sideBarLogic.menubarMain();
			} else if (_TYPE_CALLCOLUMN.equals(bean.getType())) {
				result = sideBarLogic.callColumn(bean.getToken());
			} else if (_TYPE_CALLEVENT.equals(bean.getType())) {
				result = sideBarLogic.callEvent(bean.getToken());
			} else if (_TYPE_CALLROUTINE.equals(bean.getType())) {
				result = sideBarLogic.callRoutine(bean.getToken());
			} else if (_TYPE_CALLVIEW.equals(bean.getType())) {
				result = sideBarLogic.callView(bean.getToken());
			} else if (_TYPE_CALLTABLE.equals(bean.getType())) {
				result = sideBarLogic.callTable(bean.getToken());
			} else if (_TYPE_CALLTRIGGER.equals(bean.getType())) {
				result = sideBarLogic.callTrigger(bean.getToken());
			} else if (_TYPE_CALLFUNCTION.equals(bean.getType())) {
				result = sideBarLogic.callFunction(bean.getToken());
			} else {
				result = Constants.BLANK;
			}
		} catch (Exception e) {
			result = Constants.BLANK;
		}
		return result;
	}
}
