/**
 * 
 */
package com.jspmyadmin.app.database.trigger.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.common.logic.DataLogic;
import com.jspmyadmin.app.database.trigger.beans.TriggerBean;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.Rest;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/04/11
 *
 */
@WebController(authentication = true, path = "/database_trigger_table_list.text", requestLevel = RequestLevel.DATABASE)
@Rest
public class TriggerTableListController {

	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private EncodeHelper encodeObj;
	@Detect
	private HttpServletResponse response;
	@Model
	private TriggerBean bean;

	@HandlePost
	@ValidateToken
	private JSONObject handlePost() throws EncodingException, JSONException {

		JSONObject jsonObject = new JSONObject();
		DataLogic dataLogic = null;
		try {
			dataLogic = new DataLogic();
			dataLogic.setEncodeObj(encodeObj);
			List<String> tableList = dataLogic.getTableList(bean.getDatabase_name(), false);
			jsonObject.put(Constants.ERR, Constants.BLANK);
			jsonObject.put(Constants.DATA, tableList);
		} catch (SQLException e) {
			jsonObject.put(Constants.ERR, e.getMessage());
		}
		jsonObject.put(Constants.TOKEN, requestAdaptor.generateToken());
		return jsonObject;
	}

}
