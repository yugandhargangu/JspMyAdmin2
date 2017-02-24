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
public class CommonTemplateController {

    @Handle(path = "/index.html")
    private void index(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_INDEX);
    }

    @Handle(path = "/home.html")
    private void home(View view) {
        view.setType(ViewType.FORWARD);
        view.setPath(AppConstants.JSP_COMMON_HOME);
    }
}
