/**
 *
 */
package com.tracknix.jspmyadmin.application.common.services;

import com.tracknix.jspmyadmin.application.common.beans.InstallBean;
import com.tracknix.jspmyadmin.framework.connection.ConnectionConfiguration;
import com.tracknix.jspmyadmin.framework.connection.ConnectionFactory;
import com.tracknix.jspmyadmin.framework.connection.ConnectionHelper;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.annotations.Detect;
import com.tracknix.jspmyadmin.framework.web.annotations.LogicService;
import com.tracknix.jspmyadmin.framework.web.logic.EncodeHelper;
import com.tracknix.jspmyadmin.framework.web.utils.DefaultServlet;

import java.io.*;

/**
 * InstallLogic class is to save or delete JspMyAdmin configuration settings.
 *
 * @author Yugandhar Gangu
 * @created_at 2016/09/06
 * @modified_at 2017/01/12
 */
@LogicService
public class InstallLogic {

    // Configuration path
    private static final String INSTALL_FILE_PATH = "/config.ser";

    @Detect
    private EncodeHelper encodeObj;
    @Detect
    private ConnectionHelper connectionHelper;

    /**
     * To save configuration settings
     *
     * @param installBean InstallBean object
     * @throws IOException
     */
    public boolean installConfig(InstallBean installBean) throws IOException {
        if (installBean == null || connectionHelper.isEmpty(installBean.getAdmin_name())
                || connectionHelper.isEmpty(installBean.getAdmin_password())) {
            return false;
        }
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            boolean isValidSettings = true;
            ConnectionConfiguration configuration = new ConnectionConfiguration();
            configuration.setAdmin_name(encodeObj.encodeInstall(installBean.getAdmin_name()));
            configuration.setAdmin_password(encodeObj.encodeInstall(installBean.getAdmin_password()));
            if (ConnectionFactory.CONFIG_TYPE_CONFIG.equalsIgnoreCase(installBean.getConfig_type())) {
                configuration.setConfig_type(encodeObj.encodeInstall(installBean.getConfig_type()));
                if (!connectionHelper.isEmpty(installBean.getConfig_host())) {
                    configuration.setConfig_host(encodeObj.encodeInstall(installBean.getConfig_host()));
                } else {
                    isValidSettings = false;
                }
                if (!connectionHelper.isEmpty(installBean.getConfig_port())) {
                    configuration.setConfig_port(encodeObj.encodeInstall(installBean.getConfig_port()));
                } else {
                    isValidSettings = false;
                }
                if (!connectionHelper.isEmpty(installBean.getConfig_username())) {
                    configuration.setConfig_username(encodeObj.encodeInstall(installBean.getConfig_username()));
                } else {
                    isValidSettings = false;
                }
                if (!connectionHelper.isEmpty(installBean.getConfig_password())) {
                    configuration.setConfig_password(encodeObj.encodeInstall(installBean.getConfig_password()));
                }
            } else if (ConnectionFactory.CONFIG_TYPE_HALF_CONFIG.equalsIgnoreCase(installBean.getConfig_type())) {
                configuration.setConfig_type(encodeObj.encodeInstall(installBean.getConfig_type()));
                if (!connectionHelper.isEmpty(installBean.getConfig_host())) {
                    configuration.setConfig_host(encodeObj.encodeInstall(installBean.getConfig_host()));
                } else {
                    isValidSettings = false;
                }
                if (!connectionHelper.isEmpty(installBean.getConfig_port())) {
                    configuration.setConfig_port(encodeObj.encodeInstall(installBean.getConfig_port()));
                } else {
                    isValidSettings = false;
                }
            } else {
                configuration.setConfig_type(encodeObj.encodeInstall(ConnectionFactory.CONFIG_TYPE_LOGIN));
            }

            if (isValidSettings) {
                File file = new File(DefaultServlet.getWebInfPath() + INSTALL_FILE_PATH);
                file.setExecutable(true, false);
                file.setReadable(true, false);
                file.setWritable(true, false);
                fileOutputStream = new FileOutputStream(file);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(configuration);
                return true;
            }
        } finally {
            connectionHelper.close(objectOutputStream);
            connectionHelper.close(fileOutputStream);
        }
        return false;
    }

    /**
     * @param installBean InstallBean object
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws EncodingException
     */
    public boolean uninstallConfig(InstallBean installBean)
            throws IOException, ClassNotFoundException, EncodingException {
        if (installBean == null || connectionHelper.isEmpty(installBean.getAdmin_name())
                || connectionHelper.isEmpty(installBean.getAdmin_password())) {
            return false;
        }

        FileInputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            File file = new File(DefaultServlet.getWebInfPath() + INSTALL_FILE_PATH);
            inputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(inputStream);
            ConnectionConfiguration configuration = (ConnectionConfiguration) objectInputStream.readObject();
            objectInputStream.close();
            objectInputStream = null;
            inputStream.close();
            inputStream = null;
            if (configuration.getAdmin_name() != null) {
                configuration.setAdmin_name(encodeObj.decodeInstall(configuration.getAdmin_name()));
            }
            if (configuration.getAdmin_password() != null) {
                configuration.setAdmin_password(encodeObj.decodeInstall(configuration.getAdmin_password()));
            }
            if (installBean.getAdmin_name().equalsIgnoreCase(configuration.getAdmin_name())
                    && installBean.getAdmin_password().equals(configuration.getAdmin_password())) {
                file.setExecutable(true, false);
                file.setReadable(true, false);
                file.setWritable(true, false);
                file.delete();
            } else {
                return false;
            }
        } finally {
            connectionHelper.close(objectInputStream);
            connectionHelper.close(inputStream);
        }
        return true;
    }
}
