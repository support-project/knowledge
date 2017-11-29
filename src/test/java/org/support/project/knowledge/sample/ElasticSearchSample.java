package org.support.project.knowledge.sample;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.json.JSONObject;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author koda
 */
public class ElasticSearchSample {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(ElasticSearchSample.class);
    // thread safeなのでstatic fieldに持って使いまわすことが推奨されています
    private static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")); // セットしないとDateは数値で送られる
        ElasticSearchSample app = new ElasticSearchSample();
        app.start();
    }
    
    private String objectToJSON(Object obj) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(obj);
//        String json = JSON.encode(obj);
        LOG.info(json);
        return json;
    }

    private void start() throws IOException {
//        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200, "http")).build();
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200))
                .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                    @Override
                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                        return requestConfigBuilder.setConnectTimeout(5000)
                                .setSocketTimeout(60000);
                    }
                })
                .setMaxRetryTimeoutMillis(60000);
        RestClient restClient = builder.build();
        try {
            deleteAll(restClient);
            
            put(restClient, "1", "hoge", "title", "日本語でOK");
            
            get(restClient, "1");
            
            for (int i = 0; i < 10; i++) {
                post(restClient, "fuga", "タイトル-" + i, "登録サンプル");
            }
            
            Response response = restClient.performRequest("GET", "/_cat/indices?v"); 
            debugresponse("GETの結果", response);
            
            response = restClient.performRequest("GET", "/posts/_search?q=*"); 
            debugresponse("GETの結果", response);
            
            /*
            JSONObject query = new JSONObject();
            JSONObject bool = new JSONObject();
            query.append("query", bool);
            JSONObject must = new JSONObject();
            bool.append("must", must);
            */
            
            
            /*
            params = Collections.emptyMap();
            HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory consumerFactory =
                    new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024);
            response = restClient.performRequest("GET", "/posts/_search", params, null, consumerFactory); 
            debugresponse(response);
            */
            
        } finally {
            restClient.close();
        }
    }
    
    private void deleteAll(RestClient restClient) throws IOException {
        Map<String, String> params = Collections.emptyMap();
        // 全削除
        JSONObject param = new JSONObject();
        param.append("acknowledged", true);
        HttpEntity entity = new NStringEntity(param.toString(), ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("DELETE", "/*", params, entity); 
        debugresponse("DELETEの結果", response);

    }
    
    private void put(RestClient restClient, String id, String user, String title, String contents) throws IOException {
        Map<String, String> params = Collections.emptyMap();
        // 1件登録
        Document d1 = new Document(user, title, contents);
        HttpEntity entity = new NStringEntity(objectToJSON(d1), ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("PUT", "/posts/doc/" + id, params, entity); 
        debugresponse("PUTの結果", response);
    }

    private void post(RestClient restClient, String user, String title, String contents) throws IOException {
        Map<String, String> params = Collections.emptyMap();
        // 1件登録
        Document d1 = new Document(user, title, contents);
        HttpEntity entity = new NStringEntity(objectToJSON(d1), ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("POST", "/posts/doc", params, entity); 
        debugresponse("POSTの結果", response);
    }
    
    private void get(RestClient restClient, String id) throws IOException {
        Response response = restClient.performRequest("GET", "/posts/doc/" + id); 
        debugresponse("GETの結果", response);
    }
    
    
    private void debugresponse(String title, Response response) throws ParseException, IOException {
        LOG.info(title);
        LOG.info("statusCode: " + response.getStatusLine().getStatusCode());
        LOG.info("responseBody: " + EntityUtils.toString(response.getEntity()));
    }

    
    private class Document {
        public String user;
        public Date date;
        public String title;
        public String contents;
        public Document(String user, String title, String contents) {
            super();
            this.user = user;
            this.date = new Date();
            this.title = title;
            this.contents = contents;
        }
    }
    
    
}
