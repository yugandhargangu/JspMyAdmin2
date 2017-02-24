/**
 *
 */
package com.tracknix.jspmyadmin.application.database.structure.controllers;

import com.tracknix.jspmyadmin.application.database.structure.beans.StructureBean;
import com.tracknix.jspmyadmin.application.database.structure.logic.StructureLogic;
import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.annotations.*;
import com.tracknix.jspmyadmin.framework.web.utils.*;

import java.sql.SQLException;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/23
 */
@WebController(requestLevel = RequestLevel.DEFAULT)
public class StructureUtilsController {

    @Handle(path = "/database_structure_utils.text", methodType = MethodType.POST)
    @Rest
    private QueryReturnBean getColumnCreate(RequestAdaptor requestAdaptor, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) throws Exception {
        QueryReturnBean jsonObject = new QueryReturnBean();
        try {
            if (Constants.COLUMN.equalsIgnoreCase(bean.getType())) {
                String result = structureLogic.getNewColumn(bean);
                jsonObject.setQuery(result);
            }
        } catch (SQLException e) {
            jsonObject.setErr(e.getMessage());
        }
        return jsonObject;
    }

    @Handle(path = "/database_structure_prefix.html", methodType = MethodType.POST)
    @ValidateToken
    protected void prefixTables(View view, RedirectParams redirectParams, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        try {
            if (bean.getType() != null) {
                if (Constants.ADD.equalsIgnoreCase(bean.getType())) {
                    structureLogic.addPrefix(bean);
                    redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
                } else if (Constants.REPLACE.equalsIgnoreCase(bean.getType())) {
                    structureLogic.replacePrefix(bean);
                    redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
                } else if (Constants.REMOVE.equalsIgnoreCase(bean.getType())) {
                    structureLogic.removePrefix(bean);
                    redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
                } else if (Constants.COPY.equalsIgnoreCase(bean.getType())) {
                    redirectParams.put(Constants.MSG_KEY, Constants.BLANK);
                }
            }
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(bean.getAction());
    }

    @Handle(path = "/database_structure_suffix.html", methodType = MethodType.POST)
    @ValidateToken
    private void suffixTables(View view, RedirectParams redirectParams, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) {
        try {
            if (bean.getType() != null) {
                if (Constants.ADD.equalsIgnoreCase(bean.getType())) {
                    structureLogic.addSuffix(bean);
                    redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
                } else if (Constants.REPLACE.equalsIgnoreCase(bean.getType())) {
                    structureLogic.replaceSuffix(bean);
                    redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
                } else if (Constants.REMOVE.equalsIgnoreCase(bean.getType())) {
                    structureLogic.removeSuffix(bean);
                    redirectParams.put(Constants.MSG_KEY, AppConstants.MSG_EXECUTED_SUCCESSFULLY);
                } else if (Constants.COPY.equalsIgnoreCase(bean.getType())) {
                    redirectParams.put(Constants.MSG_KEY, Constants.BLANK);
                }

            }
        } catch (SQLException e) {
            redirectParams.put(Constants.ERR, e.getMessage());
        }
        view.setType(ViewType.REDIRECT);
        view.setPath(bean.getAction());
    }

    @Handle(path = "/database_structure_show_create.text", methodType = MethodType.POST)
    @ValidateToken
    @Rest
    private QueryReturnBean tableCreateShow(RequestAdaptor requestAdaptor, @Model StructureBean bean, @LogicParam StructureLogic structureLogic) throws EncodingException {

        QueryReturnBean jsonObject = new QueryReturnBean();
        try {
            String result = structureLogic.showCreate(bean, true);
            jsonObject.setQuery(result);
        } catch (SQLException e) {
            jsonObject.setErr(e.getMessage());
        }
        return jsonObject;
    }
}
