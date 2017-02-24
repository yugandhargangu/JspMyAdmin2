package com.tracknix.jspmyadmin.application.template;

import com.tracknix.jspmyadmin.framework.constants.AppConstants;
import com.tracknix.jspmyadmin.framework.web.annotations.Handle;
import com.tracknix.jspmyadmin.framework.web.annotations.WebController;
import com.tracknix.jspmyadmin.framework.web.utils.RequestLevel;
import com.tracknix.jspmyadmin.framework.web.utils.View;
import com.tracknix.jspmyadmin.framework.web.utils.ViewType;

/**
 * CommonTemplateController has all common template paths.
 *
 * @author Yugandhar Gangu
 */
@WebController(requestLevel = RequestLevel.DEFAULT)
public class DatabaseTemplateController {

    @Handle(path = "/database/structure.html")
    public void tables(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_DATABASE_STRUCTURE_TABLES);
    }

    @Handle(path = "/database/table/create.html")
    private void createTable(View view) {
        view.setType(ViewType.REDIRECT);
        view.setPath(AppConstants.PATH_DATABASE_STRUCTURE);
    }
}
