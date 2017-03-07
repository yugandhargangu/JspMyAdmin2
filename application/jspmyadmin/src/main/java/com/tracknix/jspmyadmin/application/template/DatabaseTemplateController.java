package com.tracknix.jspmyadmin.application.template;

import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.web.annotations.Handle;
import com.tracknix.jspmyadmin.framework.web.annotations.WebController;
import com.tracknix.jspmyadmin.framework.web.utils.RequestLevel;
import com.tracknix.jspmyadmin.framework.web.utils.View;
import com.tracknix.jspmyadmin.framework.web.utils.ViewType;

import java.sql.SQLException;

/**
 * CommonTemplateController has all common template paths.
 *
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DEFAULT)
public class DatabaseTemplateController {

    @Handle(path = "/database/structure/tables.html")
    public void tables(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_STRUCTURE_TABLES);
    }

    @Handle(path = "/database/structure/views.html")
    public void views(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_STRUCTURE_VIEWS);
    }

    @Handle(path = "/database/table/create.html")
    public void createTable(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_STRUCTURE_CREATE_TABLE);
    }

    @Handle(path = "/database/view/create.html")
    public void createView(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_STRUCTURE_CREATE_VIEW);
    }

    @Handle(path = "/database/structure/procedures.html")
    public void procedures(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_ROUTINE_PROCEDURES);
    }

    @Handle(path = "/database/structure/functions.html")
    public void functions(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_ROUTINE_FUNCTIONS);
    }

    @Handle(path = "/database/procedure/create.html")
    public void createProcedure(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_ROUTINE_CREATEPROCEDURE);
    }

    @Handle(path = "/database/function/create.html")
    public void functionCreate(View view) throws SQLException {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_ROUTINE_CREATEFUNCTION);
    }

}
