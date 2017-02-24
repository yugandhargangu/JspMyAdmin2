package com.tracknix.jspmyadmin.application.database.structure.controllers;

import com.tracknix.jspmyadmin.application.database.structure.beans.CreateViewBean;
import com.tracknix.jspmyadmin.application.database.structure.beans.StructureBean;
import com.tracknix.jspmyadmin.application.database.structure.beans.TableListBean;
import com.tracknix.jspmyadmin.application.database.structure.logic.StructureLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.logic.EncodeHelper;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DATABASE)
public class ViewsController {

    @Handle(path = "/database_view_list.html")
    @GenerateToken
    private void views(View view, @Model TableListBean bean, @LogicParam StructureLogic structureLogic) throws SQLException, EncodingException {

        structureLogic.fillBean(bean, false);
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_STRUCTURE_VIEWS);
    }

    @Handle(path = "/database_create_view.html", methodType = MethodType.POST)
    @ValidateToken
    @GenerateToken
    private void createView(View view, RedirectParams redirectParams, @Model CreateViewBean bean, @LogicParam StructureLogic structureLogic) throws SQLException {
        if (structureLogic.isTableExisted(bean.getView_name(), bean.getRequest_db())) {
            redirectParams.put(Constants.ERR_KEY, AppConstants.MSG_VIEW_ALREADY_EXISTED);
            view.setType(ViewType.REDIRECT);
            view.setPath(AppConstants.PATH_DATABASE_VIEW_LIST);
            return;
        }

        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_STRUCTURE_CREATE_VIEW);
    }

    @Handle(path = "/database_create_view_post.text", methodType = MethodType.POST)
    @ValidateToken
    @Rest
    private QueryReturnBean createView(Messages messages, EncodeHelper encodeObj, RequestAdaptor requestAdaptor, @Model CreateViewBean bean, @LogicParam StructureLogic structureLogic) throws EncodingException {
        QueryReturnBean jsonObject = new QueryReturnBean();
        try {
            if (structureLogic.isTableExisted(bean.getView_name(), bean.getRequest_db())) {
                jsonObject.setErr(messages.getMessage(AppConstants.MSG_VIEW_ALREADY_EXISTED));
            } else {
                String result = structureLogic.createView(bean);
                if (result != null) {
                    jsonObject.setQuery(result);
                } else {
                    jsonObject.setMsg(AppConstants.MSG_VIEW_CREATED);
                }
            }
        } catch (SQLException e) {
            jsonObject.setErr(e.getMessage());
        }
        return jsonObject;
    }


    @Handle(path = "/database_structure_drop_view.html", methodType = MethodType.POST)
    @ValidateToken
    private void dropViews(View view, RedirectParams redirectParams, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        try {
            structureLogic.dropTables(bean, false);
            redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_VIEW_DROPPED_SUCCESSFULLY);
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(bean.getAction());
    }
}
