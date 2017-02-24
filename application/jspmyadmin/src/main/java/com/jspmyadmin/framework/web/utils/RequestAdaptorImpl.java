/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.jspmyadmin.framework.constants.Constants;
import com.jspmyadmin.framework.exception.EncodingException;
import com.jspmyadmin.framework.web.logic.EncodeHelper;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/08/30
 *
 */
final class RequestAdaptorImpl extends RequestAdaptorAbstract {

	private final EncodeHelper encodeObj;
	private HttpSession session;
	private RedirectParams redirectParams;
	private JSONObject jsonToken;

	RequestAdaptorImpl(EncodeHelper encodeObj) {
		this.encodeObj = encodeObj;
	}

	@Override
	void setSession(HttpSession session) {
		this.session = session;
	}

	@Override
	void setRedirectParams(RedirectParams redirectParams) {
		this.redirectParams = redirectParams;
	}

	@Override
	void setJsonToken(JSONObject jsonToken) {
		this.jsonToken = jsonToken;
	}

	@Override
	void fillRequestBean(Bean bean, RequestLevel requestLevel) {
		try {
			switch (requestLevel) {
			case TABLE:
				if (jsonToken.has(Constants.REQUEST_DB)) {
					bean.setRequest_db(jsonToken.getString(Constants.REQUEST_DB));
				}
				if (jsonToken.has(Constants.REQUEST_TABLE)) {
					bean.setRequest_table(jsonToken.getString(Constants.REQUEST_TABLE));
				}
				break;
			case VIEW:
				if (jsonToken.has(Constants.REQUEST_DB)) {
					bean.setRequest_db(jsonToken.getString(Constants.REQUEST_DB));
				}
				if (jsonToken.has(Constants.REQUEST_VIEW)) {
					bean.setRequest_view(jsonToken.getString(Constants.REQUEST_VIEW));
				}
				break;
			case DATABASE:
				if (jsonToken.has(Constants.REQUEST_DB)) {
					bean.setRequest_db(jsonToken.getString(Constants.REQUEST_DB));
				}
				break;
			case SERVER:
				break;
			default:
				break;
			}

		} catch (JSONException e) {
		}
	}

	@Override
	void fillRequestToken(Bean bean) {
		try {
			JSONObject jsonObject = null;
			if (bean.getRequest_db() != null) {
				jsonObject = new JSONObject();
				try {
					jsonObject.put(Constants.REQUEST_DB, bean.getRequest_db());
				} catch (JSONException e) {
				}
			}
			if (bean.getRequest_table() != null) {
				if (jsonObject == null) {
					jsonObject = new JSONObject();
				}
				try {
					jsonObject.put(Constants.REQUEST_TABLE, bean.getRequest_table());
				} catch (JSONException e) {
				}
			}
			if (bean.getRequest_view() != null) {
				if (jsonObject == null) {
					jsonObject = new JSONObject();
				}
				try {
					jsonObject.put(Constants.REQUEST_VIEW, bean.getRequest_view());
				} catch (JSONException e) {
				}
			}
			if (jsonObject != null) {
				bean.setRequest_token(encodeObj.encode(jsonObject.toString()));
			}
		} catch (EncodingException e) {
		}
	}

	@Override
	String createRequestToken(Bean bean) {
		try {
			JSONObject jsonObject = null;
			if (bean.getRequest_db() != null) {
				jsonObject = new JSONObject();
				jsonObject.put(Constants.REQUEST_DB, bean.getRequest_db());
			}
			if (bean.getRequest_table() != null) {
				if (jsonObject == null) {
					jsonObject = new JSONObject();
				}
				jsonObject.put(Constants.REQUEST_TABLE, bean.getRequest_table());
			}
			if (bean.getRequest_view() != null) {
				if (jsonObject == null) {
					jsonObject = new JSONObject();
				}
				jsonObject.put(Constants.REQUEST_VIEW, bean.getRequest_view());
			}
			if (jsonObject != null) {
				return encodeObj.encode(jsonObject.toString());
			}
		} catch (JSONException e) {
		} catch (EncodingException e) {
		}
		return null;
	}

	@Override
	String createRequestToken(Bean bean, String token) {
		try {
			JSONObject jsonObject = new JSONObject(token);
			if (bean.getRequest_db() != null) {
				jsonObject.put(Constants.REQUEST_DB, bean.getRequest_db());
			}
			if (bean.getRequest_table() != null) {
				jsonObject.put(Constants.REQUEST_TABLE, bean.getRequest_table());
			}
			if (bean.getRequest_view() != null) {
				jsonObject.put(Constants.REQUEST_VIEW, bean.getRequest_view());
			}
			return encodeObj.encode(jsonObject.toString());
		} catch (JSONException e) {
		} catch (EncodingException e) {
		}
		return null;
	}

	@Override
	boolean isValidToken(String token) {
		try {
			Object temp = session.getAttribute(Constants.SESSION_TOKEN);
			if (temp == null || !(temp instanceof TokenBag)) {
				return false;
			}
			token = encodeObj.decode(token);
			TokenBag tokenBag = (TokenBag) temp;
			if (tokenBag.contains(token)) {
				tokenBag.remove(token);
				return true;
			}
		} catch (EncodingException e) {
		}
		return false;
	}

	@Override
	void fillBasics(Bean bean) {
		if (redirectParams.has(Constants.ERR)) {
			bean.setErr(redirectParams.get(Constants.ERR).toString());
		}
		if (redirectParams.has(Constants.ERR_KEY)) {
			bean.setErr_key(redirectParams.get(Constants.ERR_KEY).toString());
		}
		if (redirectParams.has(Constants.MSG)) {
			bean.setMsg(redirectParams.get(Constants.MSG).toString());
		}
		if (redirectParams.has(Constants.MSG_KEY)) {
			bean.setMsg_key(redirectParams.get(Constants.MSG_KEY).toString());
		}
		if (jsonToken != null) {
			if (jsonToken.has(Constants.MSG_KEY)) {
				try {
					bean.setMsg_key(jsonToken.getString(Constants.MSG_KEY));
				} catch (JSONException e) {
				}
			}
		}
	}

	@Override
	boolean canProceed(Bean bean, RequestLevel requestLevel) {
		switch (requestLevel) {
		case DATABASE:
			if (bean.getRequest_db() == null || Constants.BLANK.equals(bean.getRequest_db())) {
				return false;
			}
			break;
		case TABLE:
			if (bean.getRequest_db() == null || Constants.BLANK.equals(bean.getRequest_db())
					|| bean.getRequest_table() == null || Constants.BLANK.equals(bean.getRequest_table())) {
				return false;
			}
			break;
		case VIEW:
			if (bean.getRequest_db() == null || Constants.BLANK.equals(bean.getRequest_db())
					|| bean.getRequest_view() == null || Constants.BLANK.equals(bean.getRequest_view())) {
				return false;
			}
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public String generateToken() throws EncodingException {
		String token = encodeObj.generateToken();
		Object temp = session.getAttribute(Constants.SESSION_TOKEN);
		TokenBag tokenBag = null;
		if (temp != null && temp instanceof List) {
			tokenBag = (TokenBag) temp;
		} else {
			tokenBag = new TokenBag();
			session.setAttribute(Constants.SESSION_TOKEN, tokenBag);
		}
		tokenBag.add(token);
		token = encodeObj.encode(token);
		return token;
	}

	/**
	 * 
	 * @author Yugandhar Gangu
	 * @created_at 2016/09/07
	 */
	private static class TokenBag {

		private static final int MAX_COUNT = 546;

		private int size = 20;
		private transient String[] elementData = new String[size];

		public void add(String e) {
			if (size >= MAX_COUNT) {
				int numMoved = size - 1;
				if (numMoved > 0)
					System.arraycopy(elementData, 1, elementData, 0, numMoved);
				elementData[--size] = null;
			} else {
				ensureCapacity(size + 1);
				elementData[size++] = e;
			}
		}

		public void remove(Object o) {
			if (o == null) {
				for (int index = 0; index < size; index++)
					if (elementData[index] == null) {
						fastRemove(index);
					}
			} else {
				for (int index = 0; index < size; index++)
					if (o.equals(elementData[index])) {
						fastRemove(index);
					}
			}
		}

		public boolean contains(Object o) {
			return indexOf(o) >= 0;
		}

		private void fastRemove(int index) {
			int numMoved = size - index - 1;
			if (numMoved > 0)
				System.arraycopy(elementData, index + 1, elementData, index, numMoved);
			elementData[--size] = null;
		}

		private void ensureCapacity(int minCapacity) {
			int oldCapacity = elementData.length;
			if (minCapacity > oldCapacity) {
				int newCapacity = (oldCapacity * 3) / 2 + 1;
				if (newCapacity < minCapacity)
					newCapacity = minCapacity;
				elementData = Arrays.copyOf(elementData, newCapacity);
			}
		}

		private int indexOf(Object o) {
			if (o == null) {
				for (int i = 0; i < size; i++)
					if (elementData[i] == null)
						return i;
			} else {
				for (int i = 0; i < size; i++)
					if (o.equals(elementData[i]))
						return i;
			}
			return -1;
		}

	}
}
