/**
 *
 */
package com.tracknix.jspmyadmin.application.common.beans;

import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/02/05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LanguageBean extends Bean {

    private static final long serialVersionUID = 1L;

    private Map<String, String> language_map = null;
    private String language = null;
    private String redirect = null;
}
