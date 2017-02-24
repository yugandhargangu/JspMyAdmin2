/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.app.database.structure.beans.CreateTableBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.constants.AppConstants;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.annotations.Detect;
import com.jspmyadmin.framework.web.annotations.HandlePost;
import com.jspmyadmin.framework.web.annotations.Model;
import com.jspmyadmin.framework.web.annotations.Rest;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import com.jspmyadmin.framework.web.utils.Messages;
import com.jspmyadmin.framework.web.utils.RequestAdaptor;
import com.jspmyadmin.framework.web.utils.RequestLevel;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/database_create_table_post.text", requestLevel = RequestLevel.DATABASE)
@Rest
public class CreateTablePostController {

	@Detect
	private Messages messages;
	@Detect
	private RequestAdaptor requestAdaptor;
	@Detect
	private HttpServletResponse response;
	@Detect
	private EncodeHelper encodeObj;
	@Model
	private CreateTableBean bean;

	@HandlePost
	@ValidateToken
	private JSONObject createTable() throws JSONException, EncodingException {

		StructureLogic structureLogic = null;
		JSONObject jsonObject = new JSONObject();
		try {
			structureLogic = new StructureLogic();
			structureLogic.setMessages(messages);
			String result = structureLogic.validate(bean);
			if (result != null) {
				jsonObject.append(Constants.ERR, result);
			} else {
				result = structureLogic.createTable(bean);
				jsonObject.append(Constants.ERR, Constants.BLANK);
				if (result != null) {
					jsonObject.append(Constants.DATA, result);
				} else {
					JSONObject msg = new JSONObject();
					msg.put(Constants.MSG_KEY, AppConstants.MSG_TABLE_CREATED);
					jsonObject.append(Constants.MSG, encodeObj.encode(msg.toString()));
				}
			}
		} catch (SQLException e) {
			jsonObject.append(Constants.ERR, e.getMessage());
		}
		jsonObject.put(Constants.TOKEN, requestAdaptor.generateToken());
		return jsonObject;
	}
}
