/**
 * 
 */
package com.jspmyadmin.app.database.structure.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.jspmyadmin.app.database.structure.beans.CreateViewBean;
import com.jspmyadmin.app.database.structure.logic.StructureLogic;
import com.jspmyadmin.framework.util.AppConstants;
import com.jspmyadmin.framework.util.FrameworkConstants;
import com.jspmyadmin.framework.web.annotations.ValidateToken;
import com.jspmyadmin.framework.web.annotations.WebController;
import com.jspmyadmin.framework.web.utils.Controller;
import com.jspmyadmin.framework.web.utils.View;
import com.jspmyadmin.framework.web.utils.ViewType;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/22
 *
 */
@WebController(authentication = true, path = "/database_create_view")
public class CreateViewController extends Controller<CreateViewBean> {

	private static final long serialVersionUID = 1L;

	@Override
	@ValidateToken
	protected void handleGet(CreateViewBean bean, View view) throws Exception {
		view.setType(ViewType.REDIRECT);
		view.setPath(AppConstants.JSP_COMMON_HOME);
	}

	@Override
	@ValidateToken
	protected void handlePost(CreateViewBean bean, View view) throws Exception {
		StructureLogic structureLogic = new StructureLogic();
		if (structureLogic.isTableExisted(bean.getView_name())) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(FrameworkConstants.ERR_KEY, "msg.view_already_existed");
			view.setToken(super.encode(jsonObject));
			view.setType(ViewType.REDIRECT);
			view.setPath(AppConstants.PATH_DATABASE_VIEW_LIST);
			return;
		}

		List<String> algorithm_list = new ArrayList<String>(3);
		algorithm_list.add("UNDEFINED");
		algorithm_list.add("MERGE");
		algorithm_list.add("TEMPTABLE");
		bean.setAlgorithm_list(algorithm_list);

		List<String> definer_list = new ArrayList<String>(2);
		definer_list.add(FrameworkConstants.CURRENT_USER);
		definer_list.add("OTHER");
		bean.setDefiner_list(definer_list);

		List<String> security_list = new ArrayList<String>(2);
		security_list.add("DEFINER");
		security_list.add("INVOKER");
		bean.setSecurity_list(security_list);

		List<String> check_list = new ArrayList<String>(2);
		check_list.add("CASCADED");
		check_list.add("LOCAL");
		bean.setCheck_list(check_list);

		super.generateToken(bean);
		view.setType(ViewType.FORWARD);
		view.setPath(AppConstants.JSP_DATABASE_STRUCTURE_CREATE_VIEW);
	}
}
