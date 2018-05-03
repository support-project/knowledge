package org.support.project.web.boundary;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.HtmlUtils;
import org.support.project.common.util.PropertyUtil;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.bean.Msg;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.config.MessageStatus;

import net.arnx.jsonic.JSON;

public class JsonBoundary extends AbstractBoundary {
    /** ログ */
    private static Log log = LogFactory.getLog(JsonBoundary.class);
    /** 送信するオブジェクト */
    private Object obj;

    private boolean sendEscapeHtml = true;

    /**
     * コンストラクタ
     * 
     * @param obj obj
     */
    public JsonBoundary(Object obj) {
        super();
        this.obj = obj;
    }

    /**
     * JSONで送る文字をHTMLエスケープ処理
     * 
     * @param o obj
     */
    private void escape(Object o) {
        if (o == null) {
            return;
        }
        if (o instanceof Collection) {
            Collection collection = (Collection) o;
            for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
                Object object = (Object) iterator.next();
                escape(object);
            }
        } else {
            List<String> props = PropertyUtil.getPropertyNames(o.getClass());
            if (props != null) {
                for (String prop : props) {
                    Class<?> c = PropertyUtil.getPropertyType(o.getClass(), prop);
                    if (String.class.isAssignableFrom(c)) {
                        String str = (String) PropertyUtil.getPropertyValue(o, prop);
                        str = HtmlUtils.escapeHTML(str);
                        PropertyUtil.setPropertyValue(o, prop, str);
                    } else if (!PropertyUtil.isValueClass(c)) {
                        escape(PropertyUtil.getPropertyValue(o, prop));
                    }
                }
            }
        }
    }

    @Override
    public void navigate() throws Exception {
        HttpServletResponse res = getResponse();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        if (sendEscapeHtml && obj != null) {
            escape(obj);
        }
        if (obj instanceof MessageResult) {
            MessageResult messageResult = (MessageResult) obj;
            if (messageResult.getStatus() > MessageStatus.Success.getValue()) {
                if (messageResult.getCode() > 0) {
                    res.setStatus(messageResult.getCode());
                } else {
                    res.setStatus(HttpStatus.SC_500_INTERNAL_SERVER_ERROR);
                }
            }
        }
        JSON json = new JSON();
        // デフォルトの日時書式を指定
        json.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        
        String jsonString = "";
        if (obj instanceof String) {
            Msg msg = new Msg((String) obj);
            jsonString = json.format(msg);
        } else {
            jsonString = json.format(obj);
        }
        if (log.isDebugEnabled()) {
            log.debug("send : " + jsonString);
        }
        res.getWriter().write(jsonString);
        res.getWriter().close();
    }

    /**
     * Get obj
     * 
     * @return obj
     */
    public Object getObj() {
        return obj;
    }

    /**
     * @return the sendEscapeHtml
     */
    public boolean isSendEscapeHtml() {
        return sendEscapeHtml;
    }

    /**
     * @param sendEscapeHtml the sendEscapeHtml to set
     */
    public void setSendEscapeHtml(boolean sendEscapeHtml) {
        this.sendEscapeHtml = sendEscapeHtml;
    }

}
