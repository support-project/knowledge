package org.support.project.knowledge.logic;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.support.project.common.config.INT_FLAG;
import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.config.AuthType;
import org.support.project.web.entity.ProxyConfigsEntity;

public abstract class HttpLogic {
    /** HttpClient */
    protected HttpClient httpclient = null;

    /**
     * HttpClientの生成
     * 
     * @return
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    protected HttpClient createHttpClient(ProxyConfigsEntity proxyConfig)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException,
            InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        if (httpclient != null) {
            return httpclient;
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        if (proxyConfig == null) {
            this.httpclient = httpclient;
            return httpclient;
        }

        if (StringUtils.isNotEmpty(proxyConfig.getProxyHostName())) {
            HttpHost proxy = new HttpHost(proxyConfig.getProxyHostName(), proxyConfig.getProxyPortNo());
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            CredentialsProvider credsProvider; // 認証プロバイダー
            if (proxyConfig.getProxyAuthType() != null && proxyConfig.getProxyAuthType().intValue() > AuthType.None.getValue()) {
                // Proxyに対する認証をセットする
                String pass = PasswordUtil.decrypt(proxyConfig.getProxyAuthPassword(), proxyConfig.getProxyAuthSalt());
                credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(new AuthScope(proxyConfig.getProxyHostName(), proxyConfig.getProxyPortNo()),
                        getCredentials(proxyConfig.getProxyAuthType(), proxyConfig.getProxyAuthUserId(), pass, proxyConfig.getProxyAuthPcName(),
                                proxyConfig.getProxyAuthDomain()));
                httpclient.setCredentialsProvider(credsProvider);
            }
        }

        if (proxyConfig.getThirdPartyCertificate() != null && proxyConfig.getThirdPartyCertificate() == INT_FLAG.ON.getValue()) {
            // オレオレ証明証でも全てOKにする
            TrustStrategy trustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            };
            X509HostnameVerifier hostnameVerifier = new AllowAllHostnameVerifier();
            SSLSocketFactory socketFactory = new SSLSocketFactory(trustStrategy, hostnameVerifier);

            Scheme sch = new Scheme("https", 443, socketFactory);
            httpclient.getConnectionManager().getSchemeRegistry().register(sch);
        }

        this.httpclient = httpclient;
        return httpclient;
    }

    /**
     * 認証用のCredentialsを生成
     * 
     * @param authConfigEntity
     * @return
     */
    private Credentials getCredentials(int authType, String user, String pass, String pcName, String domain) {
        if (authType == AuthType.NTLM.getValue()) {
            return new org.apache.http.auth.NTCredentials(user, pass, pcName, domain);
        }
        return new UsernamePasswordCredentials(user, pass);
    }
}