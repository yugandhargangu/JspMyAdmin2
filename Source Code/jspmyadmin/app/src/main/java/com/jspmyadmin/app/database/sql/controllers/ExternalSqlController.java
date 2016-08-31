/**
 * 
 */
package com.jspmyadmin.app.database.sql.controllers;

import java.util.Iterator;

import org.json.JSONObject;

import com.jspmyadmin.app.database.event.beans.EventListBean;
import com.jspmyadmin.app.database.event.logic.EventLogic;
import com.jspmyadmin.app.database.routine.beans.RoutineListBean;
import com.jspmyadmin.app.database.routine.logic.RoutineLogic;
import com.jspmyadmin.app.database.sql.beans.ExternalSqlBean;
import com.jspmyadmin.app.database.structure.beans.StructureBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/database_ext_sql.html", requestLevel = RequestLevel.DATABASE)
public class ExternalSqlController extends Controller<ExternalSqlBean> {

	private static final long serialVersionUID = 1L;

	@Override
	protected void handleGet(ExternalSqlBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_SQL);
	}

	@Override
	@ValidateToken
	protected void handlePost(ExternalSqlBean bean, View view) throws Exception {
		if (bean.getEdit_type() != null && bean.getEdit_name() != null
				&& !FrameworkConstants.BLANK.equals(bean.getEdit_name().trim())) {
			JSONObject jsonObject = null;
			int type = _getInteger(bean.getEdit_type());
			switch (type) {
			case 1:
				// alter view
				StructureBean structureBean = new StructureBean();
				structureBean.setTables(new String[] { bean.getEdit_name() });
				StructureLogic structureLogic = new StructureLogic();
				String result = structureLogic.showCreate(structureBean, false);
				jsonObject = new JSONObject(result);
				Iterator<?> iterator = jsonObject.keys();
				if (iterator.hasNext()) {
					result = jsonObject.getString(iterator.next().toString());
				}
				result = result.substring(6);
				StringBuilder builder = new StringBuilder();
				builder.append("DELIMITER $$\n\n");
				builder.append("ALTER");
				builder.append(result);
				builder.append("$$");
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.QUERY, builder.toString());
				break;
			case 2:
				// alter procedure
				RoutineListBean routineListBean = new RoutineListBean();
				routineListBean.setRoutines(new String[] { bean.getEdit_name() });
				RoutineLogic routineLogic = new RoutineLogic();
				result = routineLogic.showCreate(routineListBean, true);
				jsonObject = new JSONObject(result);
				iterator = jsonObject.keys();
				if (iterator.hasNext()) {
					result = jsonObject.getString(iterator.next().toString());
				}
				builder = new StringBuilder();
				builder.append("DROP PROCEDURE IF EXISTS `");
				builder.append(bean.getEdit_name());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(FrameworkConstants.SYMBOL_SEMI_COLON);
				builder.append("\n\n");
				builder.append("DELIMITER $$\n\n");
				builder.append(result);
				builder.append("$$");
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.QUERY, builder.toString());
				break;
			case 3:
				// execute procedure
				builder = new StringBuilder();
				builder.append("CALL PROCEDURE `");
				builder.append(bean.getEdit_name());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append("(<params>)");
				builder.append(FrameworkConstants.SYMBOL_SEMI_COLON);
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.QUERY, builder.toString());
				break;
			case 4:
				// alter function
				routineListBean = new RoutineListBean();
				routineListBean.setRoutines(new String[] { bean.getEdit_name() });
				routineLogic = new RoutineLogic();
				result = routineLogic.showCreate(routineListBean, false);
				jsonObject = new JSONObject(result);
				iterator = jsonObject.keys();
				if (iterator.hasNext()) {
					result = jsonObject.getString(iterator.next().toString());
				}
				builder = new StringBuilder();
				builder.append("DROP FUNCTION IF EXISTS `");
				builder.append(bean.getEdit_name());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append(FrameworkConstants.SYMBOL_SEMI_COLON);
				builder.append("\n\n");
				builder.append("DELIMITER $$\n\n");
				builder.append(result);
				builder.append("$$");
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.QUERY, builder.toString());
				break;
			case 5:
				// execute function
				builder = new StringBuilder();
				builder.append("SELECT `");
				builder.append(bean.getEdit_name());
				builder.append(FrameworkConstants.SYMBOL_TEN);
				builder.append("(<params>)");
				builder.append(FrameworkConstants.SYMBOL_SEMI_COLON);
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.QUERY, builder.toString());
				break;
			case 6:
				// alter event
				EventListBean eventListBean = new EventListBean();
				eventListBean.setEvents(new String[] { bean.getEdit_name() });
				EventLogic eventLogic = new EventLogic();
				result = eventLogic.getShowCreate(eventListBean);
				jsonObject = new JSONObject(result);
				iterator = jsonObject.keys();
				if (iterator.hasNext()) {
					result = jsonObject.getString(iterator.next().toString());
				}
				result = result.substring(6);
				builder = new StringBuilder();
				builder.append("DELIMITER $$\n\n");
				builder.append("ALTER");
				builder.append(result);
				builder.append("$$");
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.QUERY, builder.toString());
			default:
				break;
			}
			if (jsonObject != null) {
				session.setAttribute(FrameworkConstants.QUERY, jsonObject.toString());
			}
		}
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.PATH_DATABASE_SQL);
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	private int _getInteger(String val) {
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
		}
		return 0;
	}
}
