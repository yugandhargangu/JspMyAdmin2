/**
 *
 */
package com.tracknix.jspmyadmin.application.common.beans;

import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SideBarBean extends Bean {

    private static final long serialVersionUID = 1L;

    private String type = null;

}
