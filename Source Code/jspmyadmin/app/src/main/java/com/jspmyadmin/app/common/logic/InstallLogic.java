/**
 * 
 */
package com.jspmyadmin.app.common.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.jspmyadmin.app.common.beans.InstallBean;
import com.jspmyadmin.framework.connection.ConnectionConfiguration;
import com.jspmyadmin.framework.connection.ConnectionFactory;
import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.logic.EncodeHelper;
import com.jspmyadmin.framework.web.utils.Bean;
import com.jspmyadmin.framework.web.utils.DefaultServlet;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/09/06
 *
 */
public class InstallLogic {

	private static final String INSTALL_FILE_PATH = "/config.ser";

	private final EncodeHelper encodeObj;

	public InstallLogic(EncodeHelper encodeObj) {
		this.encodeObj = encodeObj;
	}

	/**
	 * 
	 * @param bean
	 * @throws IOException
	 */
	public void installConfig(Bean bean) throws IOException {
		InstallBean installBean = (InstallBean) bean;

		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			ConnectionConfiguration configuration = new ConnectionConfiguration();
			configuration.setAdmin_name(encodeObj.encodeInstall(installBean.getAdmin_name()));
			configuration.setAdmin_password(encodeObj.encodeInstall(installBean.getAdmin_password()));
			configuration.setConfig_type(encodeObj.encodeInstall(installBean.getConfig_type()));
			if (ConnectionFactory.CONFIG_TYPE_CONFIG.equalsIgnoreCase(installBean.getConfig_type())) {
				if (!isEmpty(installBean.getConfig_host())) {
					configuration.setConfig_host(encodeObj.encodeInstall(installBean.getConfig_host()));
				}
				if (!isEmpty(installBean.getConfig_port())) {
					configuration.setConfig_port(encodeObj.encodeInstall(installBean.getConfig_port()));
				}
				if (!isEmpty(installBean.getConfig_username())) {
					configuration.setConfig_username(encodeObj.encodeInstall(installBean.getConfig_username()));
				}
				if (!isEmpty(installBean.getConfig_password())) {
					configuration.setConfig_password(encodeObj.encodeInstall(installBean.getConfig_password()));
				}
			} else if (ConnectionFactory.CONFIG_TYPE_HALF_CONFIG.equalsIgnoreCase(installBean.getConfig_type())) {
				if (!isEmpty(installBean.getConfig_host())) {
					configuration.setConfig_host(encodeObj.encodeInstall(installBean.getConfig_host()));
				}
				if (!isEmpty(installBean.getConfig_port())) {
					configuration.setConfig_port(encodeObj.encodeInstall(installBean.getConfig_port()));
				}
			}

			File file = new File(DefaultServlet.getWebInfPath() + INSTALL_FILE_PATH);
			file.setExecutable(true, false);
			file.setReadable(true, false);
			file.setWritable(true, false);
			fileOutputStream = new FileOutputStream(file);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(configuration);
		} finally {
			if (objectOutputStream != null) {
				objectOutputStream.close();
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 
	 * @param bean
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws EncodingException
	 */
	public boolean uninstallConfig(Bean bean) throws IOException, ClassNotFoundException, EncodingException {

		InstallBean installBean = (InstallBean) bean;

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
			if (objectInputStream != null) {
				objectInputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return true;
	}

	/**
	 * 
	 * @param val
	 * @return
	 */
	private boolean isEmpty(String val) {
		return val == null || Constants.BLANK.equals(val.trim());
	}
}
