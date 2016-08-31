/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import org.json.JSONObject;

import com.jspmyadmin.framework.constants.FrameworkConstants;
import com.jspmyadmin.framework.web.logic.EncDecLogic;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/08/30
 *
 */
final class RequestTokenAdaptor {

	/**
	 * 
	 * @param bean
	 * @param request
	 * @param encDecLogic
	 */
	public void fillRequestBean(Bean bean, EncDecLogic encDecLogic, RequestLevel requestLevel) {
		if (bean.getToken() == null || FrameworkConstants.BLANK.equals(bean.getToken())) {
			return;
		}
		try {
			JSONObject jsonObject = new JSONObject(encDecLogic.decode(bean.getToken()));
			switch (requestLevel) {
			case TABLE:
				if (jsonObject.has(FrameworkConstants.REQUEST_DB)) {
					bean.setRequest_db(jsonObject.getString(FrameworkConstants.REQUEST_DB));
				}
				if (jsonObject.has(FrameworkConstants.REQUEST_TABLE)) {
					bean.setRequest_table(jsonObject.getString(FrameworkConstants.REQUEST_TABLE));
				}
				break;
			case VIEW:
				if (jsonObject.has(FrameworkConstants.REQUEST_DB)) {
					bean.setRequest_db(jsonObject.getString(FrameworkConstants.REQUEST_DB));
				}
				if (jsonObject.has(FrameworkConstants.REQUEST_VIEW)) {
					bean.setRequest_view(jsonObject.getString(FrameworkConstants.REQUEST_VIEW));
				}
				break;
			case DATABASE:
				if (jsonObject.has(FrameworkConstants.REQUEST_DB)) {
					bean.setRequest_db(jsonObject.getString(FrameworkConstants.REQUEST_DB));
				}
				break;
			case SERVER:
				break;
			default:
				break;
			}

		} catch (Exception e) {

		}
	}

	/**
	 * 
	 * @param bean
	 * @param encDecLogic
	 */
	public void fillRequestToken(Bean bean, EncDecLogic encDecLogic) {
		try {
			JSONObject jsonObject = null;
			if (bean.getRequest_db() != null) {
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.REQUEST_DB, bean.getRequest_db());
			}
			if (bean.getRequest_table() != null) {
				if (jsonObject == null) {
					jsonObject = new JSONObject();
				}
				jsonObject.put(FrameworkConstants.REQUEST_TABLE, bean.getRequest_table());
			}
			if (bean.getRequest_view() != null) {
				if (jsonObject == null) {
					jsonObject = new JSONObject();
				}
				jsonObject.put(FrameworkConstants.REQUEST_VIEW, bean.getRequest_view());
			}
			if (jsonObject != null) {
				bean.setRequest_token(encDecLogic.encode(jsonObject.toString()));
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 
	 * @param bean
	 * @param encDecLogic
	 * @return
	 */
	public String createRequestToken(Bean bean, EncDecLogic encDecLogic) {
		try {
			JSONObject jsonObject = null;
			if (bean.getRequest_db() != null) {
				jsonObject = new JSONObject();
				jsonObject.put(FrameworkConstants.REQUEST_DB, bean.getRequest_db());
			}
			if (bean.getRequest_table() != null) {
				if (jsonObject == null) {
					jsonObject = new JSONObject();
				}
				jsonObject.put(FrameworkConstants.REQUEST_TABLE, bean.getRequest_table());
			}
			if (bean.getRequest_view() != null) {
				if (jsonObject == null) {
					jsonObject = new JSONObject();
				}
				jsonObject.put(FrameworkConstants.REQUEST_VIEW, bean.getRequest_view());
			}
			if (jsonObject != null) {
				return encDecLogic.encode(jsonObject.toString());
			}
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 
	 * @param bean
	 * @param token
	 * @param encDecLogic
	 */
	public String createRequestToken(Bean bean, String token, EncDecLogic encDecLogic) {
		try {
			JSONObject jsonObject = new JSONObject(token);
			if (bean.getRequest_db() != null) {
				jsonObject.put(FrameworkConstants.REQUEST_DB, bean.getRequest_db());
			}
			if (bean.getRequest_table() != null) {
				jsonObject.put(FrameworkConstants.REQUEST_TABLE, bean.getRequest_table());
			}
			if (bean.getRequest_view() != null) {
				jsonObject.put(FrameworkConstants.REQUEST_VIEW, bean.getRequest_view());
			}
			return encDecLogic.encode(jsonObject.toString());
		} catch (Exception e) {

		}
		return null;
	}
}
