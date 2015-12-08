package org.support.project.knowledge.logic;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

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
import org.support.project.knowledge.entity.WebhookConfigsEntity;
import org.support.project.web.entity.ProxyConfigsEntity;

@DI(instance=Instance.Singleton)
public class WebhookLogic extends HttpLogic {
	/** ログ */
	private static Log log = LogFactory.getLog(WebhookLogic.class);

	public static WebhookLogic get() {
		return Container.getComp(WebhookLogic.class);
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
				log.error("Request failed: statusCode -> " + responseData.statusCode);
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
		private URI uri;

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