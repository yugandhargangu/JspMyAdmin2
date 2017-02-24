package com.tracknix.jspmyadmin.application.template;

import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.web.annotations.Handle;
import com.tracknix.jspmyadmin.framework.web.annotations.WebController;
import com.tracknix.jspmyadmin.framework.web.utils.RequestLevel;
import com.tracknix.jspmyadmin.framework.web.utils.View;
import com.tracknix.jspmyadmin.framework.web.utils.ViewType;

/**
 * DatabaseTemplateController class is to have all database template urls.
 *
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DEFAULT)
public class ServerTemplateController {

    @Handle(path = "/server/databases.html")
    public void databases(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_SERVER_DATABASE_DATABASELIST);
    }

    @Handle(path = "/server/status.html")
    public void status(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_SERVER_COMMON_STATUSLIST);
    }

    @Handle(path = "/server/variables.html")
    public void variables(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_SERVER_COMMON_VARIABLELIST);
    }

    @Handle(path = "/server/charsets.html")
    public void charsets(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_SERVER_COMMON_CHARSETLIST);
    }

    @Handle(path = "/server/engines.html")
    public void engines(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_SERVER_COMMON_ENGINELIST);
    }

    @Handle(path = "/server/plugins.html")
    public void plugins(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_SERVER_COMMON_PLUGINLIST);
    }
}
