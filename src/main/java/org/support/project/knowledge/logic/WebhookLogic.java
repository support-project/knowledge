package org.support.project.knowledge.logic;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.dao.TagsDao;
import org.support.project.knowledge.entity.CommentsEntity;
import org.support.project.knowledge.entity.KnowledgesEntity;
import org.support.project.knowledge.entity.TagsEntity;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.vo.Notify;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.ProxyConfigsEntity;
import org.support.project.web.entity.UsersEntity;

@DI(instance = Instance.Singleton)
public class WebhookLogic extends HttpLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(WebhookLogic.class);

    public static WebhookLogic get() {
        return Container.getComp(WebhookLogic.class);
    }

    /**
     * 記事のjsonデータを取得する
     *
     * @param knowledge
     * @param type
     * @return
     */
    public Map<String, Object> getKnowledgeData(KnowledgesEntity knowledge, Integer type) {
    	Map<String, Object> jsonObject = new HashMap<String, Object>();

    	/**  This code make JSON to send Slack */
        String linktop = "<";
        String linkpipe = "|";
        String linkend = ">";
        
        /**  This code make JSON to send Slack */
        StringBuffer SendBuff = new StringBuffer();
        SendBuff.append(linktop);
        SendBuff.append(NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));
        SendBuff.append(linkpipe);
        SendBuff.append(knowledge.getTitle());
        SendBuff.append(linkend);
        String SendString = SendBuff.toString();
        jsonObject.put("text", SendString);
        
        jsonObject.put("knowledge_id", knowledge.getKnowledgeId());
        jsonObject.put("title", knowledge.getTitle());
        jsonObject.put("content", knowledge.getContent());
        jsonObject.put("public_flag", knowledge.getPublicFlag());
        jsonObject.put("like_count", knowledge.getLikeCount());
        jsonObject.put("comment_count", knowledge.getCommentCount());
        jsonObject.put("type_id", knowledge.getTypeId());
        jsonObject.put("link", NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));

        if (type != null) {
            if (Notify.TYPE_KNOWLEDGE_INSERT == type) {
                jsonObject.put("status", "created");
            } else {
                jsonObject.put("status", "updated");
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        UsersEntity insertUser = UsersDao.get().selectOnKey(knowledge.getInsertUser());
        if (insertUser != null) {
            jsonObject.put("insert_user", insertUser.getUserName());
        } else {
            jsonObject.put("insert_user", "Unknown user");
        }
        jsonObject.put("insert_date", simpleDateFormat.format(knowledge.getInsertDatetime()));

        UsersEntity updateUser = UsersDao.get().selectOnKey(knowledge.getInsertUser());
        if (updateUser != null) {
            jsonObject.put("update_user", updateUser.getUserName());
        } else {
            jsonObject.put("insert_user", "Unknown user");
        }
        jsonObject.put("update_date", simpleDateFormat.format(knowledge.getUpdateDatetime()));

        List<TagsEntity> tagsEntities = TagsDao.get().selectOnKnowledgeId(knowledge.getKnowledgeId());
        List<String> tags = new ArrayList<String>();
        for (TagsEntity tag : tagsEntities) {
            tags.add(tag.getTagName());
        }
        jsonObject.put("tags", tags);

        List<LabelValue> labelGroups = TargetLogic.get().selectTargetsOnKnowledgeId(knowledge.getKnowledgeId());
        List<String> groups = new ArrayList<String>();
        for (LabelValue label : labelGroups) {
            if (label.getValue().startsWith("G")) {
                groups.add(label.getLabel());
            }
        }
        jsonObject.put("groups", groups);

        return jsonObject;
    }

    /**
     * コメントのjsonデータを取得する
     *
     * @param comment
     * @param knowledge
     * @return
     */
    public Map<String, Object> getCommentData(CommentsEntity comment, KnowledgesEntity knowledge) {
        Map<String, Object> jsonObject = new HashMap<String, Object>();

        /**  This code make JSON to send Slack */
        String linktop = "<";
        String linkpipe = "|";
        String linkend = ">";
        
        /**  This code make JSON to send Slack */
        StringBuffer SendBuff = new StringBuffer();
        SendBuff.append(linktop);
        SendBuff.append(NotifyLogic.get().makeURL(knowledge.getKnowledgeId()));
        SendBuff.append(linkpipe);
        SendBuff.append(knowledge.getTitle());
        SendBuff.append(linkend);
        String SendString = SendBuff.toString();
        jsonObject.put("text", SendString);
        
        jsonObject.put("comment_no", comment.getCommentNo());
        jsonObject.put("comment", comment.getComment());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        UsersEntity insertUser = UsersDao.get().selectOnKey(comment.getInsertUser());
        if (insertUser != null) {
            jsonObject.put("insert_user", insertUser.getUserName());
        } else {
            jsonObject.put("insert_user", "Unknown user");
        }
        jsonObject.put("insert_date", simpleDateFormat.format(comment.getInsertDatetime()));

        UsersEntity updateUser = UsersDao.get().selectOnKey(comment.getInsertUser());
        if (updateUser != null) {
            jsonObject.put("update_user", updateUser.getUserName());
        } else {
            jsonObject.put("insert_user", "Unknown user");
        }
        jsonObject.put("update_date", simpleDateFormat.format(knowledge.getUpdateDatetime()));

        jsonObject.put("knowledge", getKnowledgeData(knowledge, null));
        return jsonObject;
    }

    /**
     * 通知を送る
     *
     * @param proxyConfig
     * @param webhookConfig
     * @param json
     * @throws Exception
     */
    public void notify(ProxyConfigsEntity proxyConfig, WebhookConfigsEntity webhookConfig, String json) throws Exception {
        URI uri = new URI(webhookConfig.getUrl());

        // HttpClient生成
        this.httpclient = createHttpClient(proxyConfig);
        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("Content-type", "application/json");
        httpPost.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
        try {
            ResponseHandler<ResponseData> responseHandler = new HttpResponseHandler(uri);
            HttpResponse response = httpclient.execute(httpPost);

            ResponseData responseData = responseHandler.handleResponse(response);
            if (responseData.statusCode != HttpStatus.SC_OK) {
                LOG.error("Request failed: statusCode -> " + responseData.statusCode);
            } else {
                LOG.info("Request success: " + responseData.statusCode);
            }
        } catch (Exception e) {
            // HttpClientをクリア
            httpPost.abort();
            httpPost.releaseConnection();

            this.httpclient = null;
            throw e;
        } finally {
            if (this.httpclient != null) {
                httpPost.abort();
                httpPost.releaseConnection();
            }
        }
    }

    /**
     * ResponseHandlerが返すレスポンスを解析した結果のオブジェクト
     *
     * @author nagodon
     */
    protected class ResponseData {
        public Integer statusCode;
    }

    /**
     * HTTPのレスポンスを処理するResponseHandler
     *
     * @author nagodon
     */
    protected class HttpResponseHandler implements ResponseHandler<ResponseData> {
        URI uri;
        /**
         * @param url
         */
        public HttpResponseHandler(URI uri) {
            super();
            this.uri = uri;
        }

        @Override
        public ResponseData handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            ResponseData responseData = new ResponseData();
            StatusLine statusLine = response.getStatusLine();
            responseData.statusCode = statusLine.getStatusCode();
            return responseData;
        }
    }
}