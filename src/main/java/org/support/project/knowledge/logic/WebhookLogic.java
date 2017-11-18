package org.support.project.knowledge.logic;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
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
import org.support.project.common.config.Flag;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.dao.WebhookConfigsDao;
import org.support.project.knowledge.dao.WebhooksDao;
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.knowledge.entity.WebhooksEntity;
import org.support.project.knowledge.logic.notification.webhook.AbstractWebHookNotification;
import org.support.project.knowledge.logic.notification.webhook.CommentInsertWebhookNotification;
import org.support.project.knowledge.logic.notification.webhook.CommentLikedWebhookNotification;
import org.support.project.knowledge.logic.notification.webhook.EmptyWebHookNotification;
import org.support.project.knowledge.logic.notification.webhook.KnowledgeLikedWebHookNotification;
import org.support.project.knowledge.logic.notification.webhook.KnowledgeUpdateWebHookNotification;
import org.support.project.web.dao.ProxyConfigsDao;
import org.support.project.web.entity.ProxyConfigsEntity;

@DI(instance = Instance.Singleton)
public class WebhookLogic extends HttpLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(WebhookLogic.class);

    public static WebhookLogic get() {
        return Container.getComp(WebhookLogic.class);
    }
    
    /** webhookの状態：未送信（送信待ち） */
    public static final int WEBHOOK_STATUS_UNSENT = 0;
    /** webhookの状態：なんらかの通信エラーが発生した */
    public static final int WEBHOOK_STATUS_ERROR = -1;
    

    /**
     * Webhookの実行
     */
    public void startNotifyWebhook() {
        Map<String, List<WebhookConfigsEntity>> configs = getMappedConfigs();
        if (0 == configs.size()) {
            // webhookの設定が登録されていなければ、送信処理は終了
            return;
        }

        ProxyConfigsDao proxyConfigDao = ProxyConfigsDao.get();
        ProxyConfigsEntity proxyConfig = proxyConfigDao.selectOnKey(AppConfig.get().getSystemName());

        WebhooksDao dao = WebhooksDao.get();
        List<WebhooksEntity> entities = dao.selectOnStatus(WEBHOOK_STATUS_UNSENT);
        int count = 0;
        for (WebhooksEntity entity : entities) {
            try {
                List<WebhookConfigsEntity> configEntities = configs.get(entity.getHook());
                for (WebhookConfigsEntity configEntity : configEntities) {
                    String json = "";
                    try {
                        json = createJson(entity, configEntity);
                    } catch (Exception e) {
                        LOG.warn("Failed to generate JSON to send webhook. [webhook config]" + configEntity.getHookId() + "[gen data]" + entity.getContent());
                    }
                    if (StringUtils.isEmpty(json)) {
                        LOG.warn("Failed to generate JSON to send webhook. [webhook config]" + configEntity.getHookId() + "[gen data]" + entity.getContent());
                    } else {
                        notify(proxyConfig, configEntity, json);
                    }
                }
                dao.physicalDelete(entity);
                count++;
            } catch (Exception e) {
                LOG.warn("Failed to send webhook. [webhook id]" + entity.getWebhookId(), e);
                entity.setStatus(WEBHOOK_STATUS_ERROR);
                dao.save(entity);
            }
        }
        LOG.info("Webhook sended. count: " + count);
    }
    
    /**
     * WebHook通知クラスを取得
     * @param configEntity
     * @return
     */
    public AbstractWebHookNotification getWebHookNotification(WebhookConfigsEntity configEntity) {
        if (configEntity.getHook().equals(WebhookConfigsEntity.HOOK_KNOWLEDGES)) {
            return KnowledgeUpdateWebHookNotification.get();
        } else if (configEntity.getHook().equals(WebhookConfigsEntity.HOOK_COMMENTS)) {
            return CommentInsertWebhookNotification.get();
        } else if (configEntity.getHook().equals(WebhookConfigsEntity.HOOK_LIKED_KNOWLEDGE)) {
            return KnowledgeLikedWebHookNotification.get();
        } else if (configEntity.getHook().equals(WebhookConfigsEntity.HOOK_LIKED_COMMENT)) {
            return CommentLikedWebhookNotification.get();
        }
        return new EmptyWebHookNotification();
    }
    /**
     * JSON生成のテンプレートを読み出す
     * @param configEntity
     * @return
     * @throws Exception 
     */
    public String loadTemplate(WebhookConfigsEntity configEntity) throws Exception {
        return getWebHookNotification(configEntity).loadTemplate(configEntity);
    }
    
    /**
     * 送信するJSONを生成する
     * @param entity
     * @param configEntity
     * @return
     */
    public String createJson(WebhooksEntity entity, WebhookConfigsEntity configEntity) throws Exception {
        return getWebHookNotification(configEntity).createSendJson(entity, configEntity);
    }

    /**
     * 設定を返す
     * @return
     */
    private Map<String, List<WebhookConfigsEntity>> getMappedConfigs() {
        Map<String, List<WebhookConfigsEntity>> hooks = new HashMap<String, List<WebhookConfigsEntity>>();

        WebhookConfigsDao webhookConfigsDao = WebhookConfigsDao.get();
        List<WebhookConfigsEntity> entities = webhookConfigsDao.selectAll();

        if (0 == entities.size()) {
            return hooks;
        }
        
        // TODO 後でリファクタリング（WebHookの種類を追加する毎に、ロジックを修正しなくてはいけなくて面倒）
        List<WebhookConfigsEntity> knowledgeHooks = new ArrayList<WebhookConfigsEntity>();
        List<WebhookConfigsEntity> commentHooks = new ArrayList<WebhookConfigsEntity>();
        List<WebhookConfigsEntity> likedHooks = new ArrayList<WebhookConfigsEntity>();
        List<WebhookConfigsEntity> likedCommentHooks = new ArrayList<WebhookConfigsEntity>();

        for (WebhookConfigsEntity entity : entities) {
            if (WebhookConfigsEntity.HOOK_KNOWLEDGES.equals(entity.getHook())) {
                knowledgeHooks.add(entity);
            } else if (WebhookConfigsEntity.HOOK_COMMENTS.equals(entity.getHook())) {
                commentHooks.add(entity);
            } else if (WebhookConfigsEntity.HOOK_LIKED_KNOWLEDGE.equals(entity.getHook())) {
                likedHooks.add(entity);
            } else if (WebhookConfigsEntity.HOOK_LIKED_COMMENT.equals(entity.getHook())) {
                likedCommentHooks.add(entity);
            }
        }

        hooks.put(WebhookConfigsEntity.HOOK_KNOWLEDGES, knowledgeHooks);
        hooks.put(WebhookConfigsEntity.HOOK_COMMENTS, commentHooks);
        hooks.put(WebhookConfigsEntity.HOOK_LIKED_KNOWLEDGE, likedHooks);
        hooks.put(WebhookConfigsEntity.HOOK_LIKED_COMMENT, likedCommentHooks);

        return hooks;
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
        if (Flag.is(webhookConfig.getIgnoreProxy())) {
            this.httpclient = createHttpClient(null); // IgnoreProxy が ON の場合、Proxy設定を使わない
        } else {
            this.httpclient = createHttpClient(proxyConfig);
        }
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