/**
 * 
 */
package com.jspmyadmin.app.common.controllers;

import java.io.PrintWriter;

import com.jspmyadmin.app.common.beans.SideBarBean;
import com.jspmyadmin.app.common.logic.SideBarLogic;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ResponseBody;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 *
 */
@WebController(authentication = true, path = "/sidebar.text")
public class SideBarController extends Controller<SideBarBean> {

	private static final String _TYPE_MENUBARMAIN = "menubarMain";
	private static final String _TYPE_CALLCOLUMN = "callColumn";
	private static final String _TYPE_CALLEVENT = "callEvent";
	private static final String _TYPE_CALLROUTINE = "callRoutine";
	private static final String _TYPE_CALLVIEW = "callView";
	private static final String _TYPE_CALLTABLE = "callTable";
	private static final String _TYPE_CALLTRIGGER = "callTrigger";
	private static final String _TYPE_CALLFUNCTION = "callFunction";

	private static final long serialVersionUID = 1L;

	@Override
	@ResponseBody
	protected void handleGet(SideBarBean bean, View view) throws Exception {
		this.handlePost(bean, view);
	}

	@Override
	@ResponseBody
	protected void handlePost(SideBarBean bean, View view) throws Exception {
		String result = null;
		SideBarLogic sideBarLogic = null;
		try {
			sideBarLogic = new SideBarLogic();
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
				result = FrameworkConstants.BLANK;
			}
		} catch (Exception e) {
			result = FrameworkConstants.BLANK;
		} finally {
			sideBarLogic = null;
		}
		PrintWriter writer = response.getWriter();
		try {
			writer.print(super.encrypt(result));
		} finally {
			writer.close();
		}
	}
}
