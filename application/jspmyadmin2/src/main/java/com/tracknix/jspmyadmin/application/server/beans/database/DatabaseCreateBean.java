package com.tracknix.jspmyadmin.application.server.beans.database;

import com.tracknix.jspmyadmin.framework.web.utils.Bean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Yugandhar Gangu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DatabaseCreateBean extends Bean {
    private String database;
    private String collation;
}
