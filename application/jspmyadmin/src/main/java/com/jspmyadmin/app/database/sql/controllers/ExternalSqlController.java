/**
 * 
 */
package com.jspmyadmin.app.database.sql.controllers;

import java.sql.SQLException;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.database.event.beans.EventListBean;
import com.jspmyadmin.app.database.event.logic.EventLogic;
import com.jspmyadmin.app.database.routine.beans.RoutineListBean;
import com.jspmyadmin.app.database.routine.logic.RoutineLogic;
import com.jspmyadmin.app.database.sql.beans.ExternalSqlBean;
import com.jspmyadmin.app.database.structure.beans.StructureBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestLevel;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/database_ext_sql.html", requestLevel = RequestLevel.DATABASE)
public class ExternalSqlController {

	@Detect
	private HttpSession session;
	@Detect
	private View view;
	@Model
	private ExternalSqlBean bean;

	@HandlePost
	@ValidateToken
	private void alterSql() throws JSONException, SQLException {
		if (bean.getEdit_type() != null && bean.getEdit_name() != null
				&& !Constants.BLANK.equals(bean.getEdit_name().trim())) {
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
				jsonObject.put(Constants.QUERY, builder.toString());
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
				builder.append(Constants.SYMBOL_TEN);
				builder.append(Constants.SYMBOL_SEMI_COLON);
				builder.append("\n\n");
				builder.append("DELIMITER $$\n\n");
				builder.append(result);
				builder.append("$$");
				jsonObject = new JSONObject();
				jsonObject.put(Constants.QUERY, builder.toString());
				break;
			case 3:
				// execute procedure
				builder = new StringBuilder();
				builder.append("CALL PROCEDURE `");
				builder.append(bean.getEdit_name());
				builder.append(Constants.SYMBOL_TEN);
				builder.append("(<params>)");
				builder.append(Constants.SYMBOL_SEMI_COLON);
				jsonObject = new JSONObject();
				jsonObject.put(Constants.QUERY, builder.toString());
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
				builder.append(Constants.SYMBOL_TEN);
				builder.append(Constants.SYMBOL_SEMI_COLON);
				builder.append("\n\n");
				builder.append("DELIMITER $$\n\n");
				builder.append(result);
				builder.append("$$");
				jsonObject = new JSONObject();
				jsonObject.put(Constants.QUERY, builder.toString());
				break;
			case 5:
				// execute function
				builder = new StringBuilder();
				builder.append("SELECT `");
				builder.append(bean.getEdit_name());
				builder.append(Constants.SYMBOL_TEN);
				builder.append("(<params>)");
				builder.append(Constants.SYMBOL_SEMI_COLON);
				jsonObject = new JSONObject();
				jsonObject.put(Constants.QUERY, builder.toString());
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
				jsonObject.put(Constants.QUERY, builder.toString());
				break;
			default:
				break;
			}
			if (jsonObject != null) {
				session.setAttribute(Constants.QUERY, jsonObject.toString());
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
