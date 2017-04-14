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
public class TableTemplateController {

    @Handle(path = "/table/data.html")
    public void databases(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_TABLE_DATA_DATA);
    }
}
