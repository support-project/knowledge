package org.support.project.knowledge.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.FileUtil;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.parser.Parser;
import org.support.project.knowledge.parser.ParserFactory;
import org.support.project.knowledge.vo.ParseResult;
import org.support.project.web.dao.ProxyConfigsDao;
import org.support.project.web.entity.ProxyConfigsEntity;

@DI(instance = Instance.Singleton)
public class CrawlerLogic extends HttpLogic {
    /** ログ */
    private static Log log = LogFactory.getLog(MethodHandles.lookup());

    public static CrawlerLogic get() {
        return Container.getComp(CrawlerLogic.class);
    }

    /** Webで除外するパターン */
    private static final Pattern FILTERS_PATTERN = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
            + "|png|tiff?|mid|mp2|mp3|mp4"
            // + "|wav|avi|mov|mpeg|ram|m4v|pdf"
            + "|wav|avi|mov|mpeg|ram|m4v"
            // + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
            + "|rm|smil|wmv|swf|wma))$");

    public static void main(String[] args) throws Exception {
        CrawlerLogic logic = new CrawlerLogic();
        ProxyConfigsEntity proxyConfig = ProxyConfigsDao.get().selectOnKey(AppConfig.get().getSystemName());
        proxyConfig.setProxyAuthPassword(PasswordUtil.decrypt(proxyConfig.getProxyAuthPassword(), proxyConfig.getProxyAuthSalt()));
        logic.crawle(proxyConfig, "http://www.yahoo.co.jp");
    }

    /**
     * URLにある情報を取得
     * 
     * @param proxyConfig
     * @param url
     * @return
     * @throws Exception
     */
    public String crawle(ProxyConfigsEntity proxyConfig, String url) throws Exception {
        URI uri = new URI(url);

        // HttpClient生成
        this.httpclient = createHttpClient(proxyConfig);

        // Getリクエスト実行
        HttpGet httpGet = new HttpGet(uri);
        try {
            ResponseHandler<ResponseData> responseHandler = new HttpResponseHandler(uri);
            HttpResponse response;
            try {
                response = httpclient.execute(httpGet);
            } catch (IllegalStateException e) {
                // HttpGetが何らかのエラーを返したので、このアイテムの取得終了
                log.error("http get error.   -->" + url + "  : " + e.getMessage());
                // HttpClientをクリア
                httpGet.abort();
                httpGet.releaseConnection();

                this.httpclient = null;
                return "";
            }
            ResponseData responseData = responseHandler.handleResponse(response);
            if (responseData.cashed) {
                if (log.isDebugEnabled()) {
                    log.debug("結果" + responseData.cashed);
                    log.debug("url の情報を取得");
                    log.debug(responseData.cashFile.getAbsolutePath());
                    log.debug("url のリンク");
                    for (URI child : responseData.childLinks) {
                        log.debug(child);
                    }
                }

                // Tikaでテキスト抽出
                Parser parser = ParserFactory.getParser(responseData.cashFile.getAbsolutePath());
                ParseResult result = parser.parse(responseData.cashFile);
                log.debug(result.getText());

                FileUtil.delete(responseData.cashFile);

                return result.getText();
            } else {
                return null;
            }
        } finally {
            if (this.httpclient != null) {
                httpGet.abort();
                httpGet.releaseConnection();
            }
        }
    }

    /**
     * ResponseHandlerが返すレスポンスを解析した結果のオブジェクト
     * 
     * @author koda
     * 
     */
    private class ResponseData {
        public boolean cashed;
        public List<URI> childLinks;
        public File cashFile;
        public String mime;
    }

    /**
     * HTTPのレスポンスを処理するResponseHandler
     * 
     * @author koda
     * 
     */
    private class HttpResponseHandler implements ResponseHandler<ResponseData> {
        private URI uri;

        /**
         * @param url
         */
        HttpResponseHandler(URI uri) {
            super();
            this.uri = uri;
        }

        @Override
        public ResponseData handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            ResponseData responseData = new ResponseData();
            responseData.cashed = false;
            final List<URI> list = new ArrayList<>();
            responseData.childLinks = list;

            debugResponse(response);

            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES) {
                log.error(statusLine.getStatusCode() + ":" + statusLine.getReasonPhrase());
                return responseData;
            }
            if (entity == null) {
                log.error("Response contains no content");
                return responseData;
            }

            try {
                Header contentsTypeheader = entity.getContentType();
                if (contentsTypeheader == null) {
                    throw new ClientProtocolException("Unexpected content type: null");
                }
                log.debug(contentsTypeheader.toString());

                ContentType contentType = ContentType.getOrDefault(entity);
                responseData.mime = contentType.getMimeType();

                // 取得した情報をテンポラリ領域にキャッシュする
                File tempDir = new File(AppConfig.get().getTmpPath());
                if (!tempDir.exists()) {
                    tempDir.mkdirs();
                }
                // tempDir.createTempFile("tmp", ".tmp", tempDir);

                // BasicHeader header = new BasicHeader("Content-Disposition", "attachment; filename=blah.txt");
                String tempfilename = null;
                Header[] headers = response.getHeaders("Content-Disposition");
                for (Header header : headers) {
                    HeaderElement[] helelms = header.getElements();
                    if (helelms.length > 0) {
                        HeaderElement helem = helelms[0];
                        if (helem.getName().equalsIgnoreCase("attachment")) {
                            NameValuePair nmv = helem.getParameterByName("filename");
                            if (nmv != null) {
                                tempfilename = nmv.getValue();
                            }
                        }
                    }
                }
                if (tempfilename == null) {
                    String path = getPath(uri);
                    if (path.endsWith("/")) {
                        // 最後の「/」を消す
                        path = path.substring(0, path.length() - 1);
                    }
                    tempfilename = StringUtils.getFileName(path);

                    MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();

                    MimeType mimeType = allTypes.forName(responseData.mime);
                    String mimeExtention = mimeType.getExtension(); // Mimeから拡張子判定

                    String extention = StringUtils.getExtension(getPath(uri)); // パスから拡張子判定
                    if (StringUtils.isEmpty(extention)) {
                        tempfilename = tempfilename + mimeExtention;
                    } else {
                        if (!extention.equals(mimeExtention)) {
                            tempfilename = tempfilename + mimeExtention;
                        }
                    }
                }
                
                // 日本語が入っていたらエンコード
                try {
                    tempfilename = new URI(tempfilename).toASCIIString();
                } catch (URISyntaxException e) {
                    log.warn("URISyntaxException", e);
                }
                File tempFile = new File(tempDir, tempfilename);

                log.debug("cash to " + tempFile.getPath());
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(response.getEntity());
                    log.debug("content-length : " + bufHttpEntity.getContentLength());

                    outputStream = new FileOutputStream(tempFile);
                    // inputStream = new ByteArrayInputStream(EntityUtils.toByteArray(bufHttpEntity));
                    FileUtil.copy(bufHttpEntity.getContent(), outputStream);
                    EntityUtils.consume(entity);

                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                }

                responseData.cashFile = tempFile;
                responseData.cashed = true;

                if (!contentType.getMimeType().toLowerCase().equals("text/html")) {
                    // HTMLでない為パースはしない(子のリンクは無し)
                    return responseData;
                }

                // 再帰的に、子のリンクまで処理したい場合、HTMLを解析する
                /*
                 * Charset enc = contentType.getCharset(); if (enc == null) { enc = Charset.forName("UTF-8"); } String charset = enc.name();
                 * 
                 * // HTMLのパースして、子のリンクを格納 ParserDelegator parserDelegator = new ParserDelegator(); ParserCallback callback = new ParserCallback(list,
                 * uri, enc);
                 * 
                 * Reader reader = null; try { reader = new BufferedReader(new InputStreamReader(new FileInputStream(tempFile), charset));
                 * parserDelegator.parse(reader, callback, true); } finally { if (reader != null) { reader.close(); } }
                 */
            } catch (IllegalStateException | MimeTypeException e) {
                throw new ClientProtocolException("ParseError.", e);
            }
            return responseData;
        }
    }

    /**
     * Responceのデバッグ
     * 
     * @param res
     */
    private void debugResponse(HttpResponse res) {
        if (log.isDebugEnabled()) {
            Header[] headers = res.getAllHeaders();
            log.debug("======================================= response header =================================================");
            for (Header header : headers) {
                log.debug(header.toString());
            }
            log.debug("=========================================================================================================");
        }
    }

    /**
     * HTMLをパースして、子のリンク情報をchildLinksのリストに格納する
     * 
     * @author koda
     */
    private class ParserCallback extends HTMLEditorKit.ParserCallback {
        private List<URI> childLinks;
        private URI uri;
        private Charset charset;

        /**
         * @param childLinks
         * @param charset
         */
        ParserCallback(List<URI> childLinks, URI uri, Charset charset) {
            super();
            this.childLinks = childLinks;
            this.uri = uri;
            this.charset = charset;
        }

        @Override
        public void handleSimpleTag(Tag tag, MutableAttributeSet attr, int pos) {
            // 閉じタグを持たない１つだけのタグを処理する
            if (tag.equals(HTML.Tag.IMG)) {
                // String src = (String) attr.getAttribute(HTML.Attribute.SRC);
            }
            super.handleSimpleTag(tag, attr, pos);
        }

        @Override
        public void handleStartTag(Tag tag, MutableAttributeSet attr, int pos) {
            // 閉じタグを持ったタグの解した具部分を処理するもので、<a>タグはこれにより処理されます。
            if (tag.equals(HTML.Tag.A)) {
                String src = (String) attr.getAttribute(HTML.Attribute.HREF);
                if (src == null) {
                    super.handleStartTag(tag, attr, pos);
                    return;
                }
                if (src.startsWith("#")) {
                    // ページ内リンクなのでリンク一覧に入れない
                    super.handleStartTag(tag, attr, pos);
                    return;
                }
                if (src.equals("/")) {
                    // "/"だけの場合はリンク一覧に入れない
                    // ページ内リンクなのでリンク一覧に入れない
                    super.handleStartTag(tag, attr, pos);
                    return;
                }
                try {
                    URL url = new URL(uri.toURL(), src);
                    if (shouldVisit(url.toString())) {
                        URI next = new URI(url.toString());
                        childLinks.add(next);
                    }
                } catch (MalformedURLException | URISyntaxException e) {
                    log.error("URL syntax error." + e.getMessage());
                }
            }
            super.handleStartTag(tag, attr, pos);
        }
    }

    /**
     * 子のリンクを訪れるかどうかのチェック
     * 
     * @param url
     * @return
     */
    private boolean shouldVisit(String url) {
        String href = url.toLowerCase();

        if (href.startsWith("mailto")) {
            return false;
        }
        if (href.startsWith("javascript")) {
            return false;
        }
        boolean visit = !FILTERS_PATTERN.matcher(href).matches();
        return visit;
    }

    /**
     * パスの取得 パスが無い場合、ホスト名とする
     * 
     * @param uri
     * @return
     */
    private String getPath(URI uri) {
        StringBuilder builder = new StringBuilder();
        builder.append(uri.getScheme());
        builder.append("://");
        if (!StringUtils.isEmpty(uri.getHost())) {
            builder.append(uri.getHost());
            if (uri.getPort() > 0) {
                builder.append(":");
                builder.append(uri.getPort());
            }
        }
        if (StringUtils.isNotEmpty(uri.getPath())) {
            builder.append(uri.getPath());
        }
        return builder.toString();
    }
}