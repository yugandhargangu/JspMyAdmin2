/**
 * 
 */
package com.jspmyadmin.app.table.common.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.table.common.beans.ForeignKeyBean;
import com.jspmyadmin.app.table.common.logic.ForeignKeyLogic;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandleGetOrPost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.Rest;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.RequestLevel;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 *
 */
@WebController(authentication = true, path = "/table_fetch_columns.text", requestLevel = RequestLevel.TABLE)
@Rest
public class FetchColumnsController {

	@Detect
	private HttpServletResponse response;
	@Model
	private ForeignKeyBean bean;

	@HandleGetOrPost
	private JSONObject fetchColumns() {
		if (bean.getRef_table_name() != null) {
			JSONObject jsonObject = null;
			ForeignKeyLogic foreignKeyLogic = null;
			try {
				foreignKeyLogic = new ForeignKeyLogic(bean.getRef_table_name());
				jsonObject = foreignKeyLogic.fetchColumns(bean.getRequest_db());
			} catch (SQLException e) {
			} catch (JSONException e) {
			}
			return jsonObject;
		}
		return null;
	}
}
