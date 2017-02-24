package com.tracknix.jspmyadmin.framework.web.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tracknix.jspmyadmin.framework.constants.Constants;
import com.tracknix.jspmyadmin.framework.exception.EncodingException;
import com.tracknix.jspmyadmin.framework.web.logic.EncodeHelper;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Yugandhar Gangu
 */
final class RequestAdaptorImpl extends RequestAdaptorAbstract {

    private final EncodeHelper encodeObj;
    private HttpSession session;
    private RedirectParams redirectParams;
    private JsonNode jsonToken;

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
    void setJsonToken(JsonNode jsonToken) {
        this.jsonToken = jsonToken;
    }

    @Override
    void fillRequestBean(Bean bean, RequestLevel requestLevel) {
        switch (requestLevel) {
            case TABLE:
                if (jsonToken.has(Constants.REQUEST_DB)) {
                    bean.setRequest_db(jsonToken.get(Constants.REQUEST_DB).asText());
                }
                if (jsonToken.has(Constants.REQUEST_TABLE)) {
                    bean.setRequest_table(jsonToken.get(Constants.REQUEST_TABLE).asText());
                }
                break;
            case VIEW:
                if (jsonToken.has(Constants.REQUEST_DB)) {
                    bean.setRequest_db(jsonToken.get(Constants.REQUEST_DB).asText());
                }
                if (jsonToken.has(Constants.REQUEST_VIEW)) {
                    bean.setRequest_view(jsonToken.get(Constants.REQUEST_VIEW).asText());
                }
                break;
            case DATABASE:
                if (jsonToken.has(Constants.REQUEST_DB)) {
                    bean.setRequest_db(jsonToken.get(Constants.REQUEST_DB).asText());
                }
                break;
            case SERVER:
                break;
            default:
                break;
        }
    }

    @Override
    void fillRequestToken(Bean bean) {
        try {
            ObjectNode objectNode = null;
            if (bean.getRequest_db() != null) {
                objectNode = JsonNodeFactory.instance.objectNode();
                objectNode.put(Constants.REQUEST_DB, bean.getRequest_db());
            }
            if (bean.getRequest_table() != null) {
                if (objectNode == null) {
                    objectNode = JsonNodeFactory.instance.objectNode();
                }
                objectNode.put(Constants.REQUEST_TABLE, bean.getRequest_table());
            }
            if (bean.getRequest_view() != null) {
                if (objectNode == null) {
                    objectNode = JsonNodeFactory.instance.objectNode();
                }
                objectNode.put(Constants.REQUEST_VIEW, bean.getRequest_view());
            }
            if (objectNode != null) {
                bean.setRequest_token(encodeObj.encode(objectNode.toString()));
            }
        } catch (EncodingException ignored) {
        }
    }

    @Override
    String createRequestToken(Bean bean) {
        try {
            ObjectNode objectNode = null;
            if (bean.getRequest_db() != null) {
                objectNode = JsonNodeFactory.instance.objectNode();
                objectNode.put(Constants.REQUEST_DB, bean.getRequest_db());
            }
            if (bean.getRequest_table() != null) {
                if (objectNode == null) {
                    objectNode = JsonNodeFactory.instance.objectNode();
                }
                objectNode.put(Constants.REQUEST_TABLE, bean.getRequest_table());
            }
            if (bean.getRequest_view() != null) {
                if (objectNode == null) {
                    objectNode = JsonNodeFactory.instance.objectNode();
                }
                objectNode.put(Constants.REQUEST_VIEW, bean.getRequest_view());
            }
            if (objectNode != null) {
                return encodeObj.encode(objectNode.toString());
            }
        } catch (EncodingException ignored) {
        }
        return null;
    }

    @Override
    String createRequestToken(Bean bean, String token) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode objectNode = (ObjectNode) objectMapper.readTree(token);
            if (bean.getRequest_db() != null) {
                objectNode.put(Constants.REQUEST_DB, bean.getRequest_db());
            }
            if (bean.getRequest_table() != null) {
                objectNode.put(Constants.REQUEST_TABLE, bean.getRequest_table());
            }
            if (bean.getRequest_view() != null) {
                objectNode.put(Constants.REQUEST_VIEW, bean.getRequest_view());
            }
            return encodeObj.encode(objectNode.toString());
        } catch (EncodingException ignored) {
        } catch (JsonProcessingException ignored) {
        } catch (IOException ignored) {
        }
        return null;
    }

    @Override
    boolean isValidToken(String token) {
        try {
            Object temp = session.getAttribute(Constants.SESSION_TOKEN);
            if (token == null || temp == null || !(temp instanceof TokenBag)) {
                return false;
            }
            token = encodeObj.decode(token);
            TokenBag tokenBag = (TokenBag) temp;
            if (tokenBag.contains(token)) {
                tokenBag.remove(token);
                return true;
            }
        } catch (EncodingException ignored) {
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
                bean.setMsg_key(jsonToken.get(Constants.MSG_KEY).asText());
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
        TokenBag tokenBag;
        if (temp != null && temp instanceof TokenBag) {
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
     * @author Yugandhar Gangu
     */
    private static class TokenBag {

        private static final int MAX_COUNT = 546;
        private static final int REMOVE_INDEX = 0;

        private transient List<String> elementData = new CopyOnWriteArrayList<String>();

        /**
         * @param e {@link String}
         */
        public void add(String e) {
            if (elementData.size() >= MAX_COUNT) {
                elementData.remove(REMOVE_INDEX);
            }
            elementData.add(e);
        }

        /**
         * @param o {@link Object}
         */
        public void remove(Object o) {
            elementData.remove(o);
        }

        /**
         * @param o {@link Object}
         * @return boolean
         */
        public boolean contains(Object o) {
            return elementData.contains(o);
        }

    }
}
