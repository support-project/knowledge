package org.support.project.knowledge.control.open;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.control.KnowledgeControlBase;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.config.WebConfig;
import org.support.project.web.control.service.Get;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.logic.AuthenticationLogic;
import org.support.project.web.logic.UserLogic;
import org.support.project.web.logic.impl.DefaultAuthenticationLogicImpl;
import org.support.project.web.wrapper.HttpServletRequestWrapper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@DI(instance = Instance.Prototype)
public class LoginControl extends KnowledgeControlBase {
	private static Log LOG = LogFactory.getLog(LoginControl.class);

	/**
	 * Redmine連携自動ログイン処理
	 */
	@Get
	public Boundary autologin() {
		LOG.trace("自動ログイン処理");
		try {
			// Redmineユーザ情報を取得
			Document doc = getRedmineUserInfo(getRequest());
			if (doc == null) {
				LOG.trace("Redmine連携取得情報なし");
				// 取得できない場合、連携なしで通常遷移
				return super.redirect(getRequest().getContextPath() + "/open.knowledge/list");
			}
			// 取得したRedmineユーザ情報を元にログイン連携
			String userId = getNodeValue(doc.getDocumentElement(), "login");
			String firstname = getNodeValue(doc.getDocumentElement(), "firstname"); //名前
			String lastname = getNodeValue(doc.getDocumentElement(), "lastname"); //苗字
			String userName = (lastname + firstname).trim();
			String mail = getNodeValue(doc.getDocumentElement(), "mail");
			// Knowledgeログイン処理
			AuthenticationLogic logic = Container.getComp(DefaultAuthenticationLogicImpl.class);
			// ログイン済みかチェック
			if (getRequest().getRemoteUser() != null) {
				// 別ユーザで入る場合を考慮し一旦ログアウト
				logic.clearSession(getRequest());
			}
			// Knowledgeユーザ存在チェック
			UsersDao usersDao = UsersDao.get();
			UsersEntity usersEntity = usersDao.selectOnUserKey(userId);
			if (usersEntity != null) {
				// 登録ユーザーが存在する
				LOG.trace("Knowledgeユーザでログイン");
				// ログイン処理
				logic.setSession(usersEntity.getUserKey(), getRequest());
				return super.redirect(getRequest().getContextPath() + "/open.knowledge/list");
			} else {
				// 登録ユーザーが存在しない
				LOG.trace("Knowledgeユーザ新規登録後ログイン");
				// 新規登録処理
				UsersEntity addUser = super.getParams(UsersEntity.class);
				addUser.setUserKey(userId);
				addUser.setUserName(userName);
				addUser.setPassword("password"); // 初期パスワードは固定値
				addUser.setAdmin(false);
				addUser.setMailAddress(mail);
				String[] roles = {WebConfig.ROLE_USER};
				addUser = UserLogic.get().insert(addUser, roles);
				setAttributeOnProperty(addUser);
				// ログイン処理
				logic.setSession(addUser.getUserKey(), getRequest());
				return super.redirect(getRequest().getContextPath() + "/open.knowledge/list");
			}
		} catch (Exception e) {
			LOG.error("ERROR", e);
			throw new SystemException(e);
		}
	}

	/**
	 * Redmineのログインユーザ情報取得
	 */
	private static Document getRedmineUserInfo(HttpServletRequest request) throws Exception {
		Document doc = null;
		// RedmineのCookie取得
		Cookie cookies[] = request.getCookies();
		String value = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("_redmine_session")) {
					value = cookies[i].getValue();
				}
			}
		}
		// Cookieが存在する場合自動ログイン処理
		if (value != null) {
			HttpURLConnection connection = null;
			try {
				// サーバのホスト名取得
				String hostname = AppConfig.get().getHostName();
				String redmineURL = AppConfig.get().getRedmineURL();
				// RedmineのAPIキー取得アドレス
				URL url = new URL(redmineURL + "my/api_key");
				// Cookieセット
				CookieManager man = new CookieManager();
				CookieHandler.setDefault(man);
				HttpCookie cookie = new HttpCookie("_redmine_session", value);
				cookie.setDomain(hostname);
				cookie.setPath("/");
				cookie.setVersion(0);
				man.getCookieStore().add(new URI("http://" + hostname + "/"), cookie);
				connection = (HttpURLConnection)url.openConnection();
				connection.setRequestMethod("GET");
				String apiKey = null;
				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8); BufferedReader reader = new BufferedReader(isr)) {
						String line;
						while ((line = reader.readLine()) != null) {
							// 取得したHTMLからAPIキーを取り出す
							if (line.indexOf("<pre>") >= 0) {
								int start = line.indexOf("<pre>");
								int end = line.indexOf("</pre>");
								apiKey = line.substring(start + 5, end);
							}
						}
					}
				}
				// REST APIでUserID取得
				if (apiKey != null) {
					url = new URL(redmineURL + "/users/current.xml?key=" + apiKey);
					connection = (HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");
					String xml = null;
					if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
						try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8); BufferedReader reader = new BufferedReader(isr)) {
							String line;
							while ((line = reader.readLine()) != null) {
								xml = line;
								break;
							}
						}
					}
					// XMLパース
					doc = parseXml(xml);
				}
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
		}
		return doc;
	}

	/**
	 * XMLパース
	 */
	private static Document parseXml(String xml) throws Exception {
		ByteArrayInputStream bis = null;
		try {
			bis = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(bis);
		} finally {
			if (bis != null) {
				bis.close();
			}
		}

	}

	/**
	 * XML属性値取得
	 */
	private static String getNodeValue(Node node, String nodeName) {
		for (Node ch = node.getFirstChild(); ch != null; ch = ch.getNextSibling()) {
			if (ch.getNodeName() == nodeName) {
				return ch.getTextContent();
			}
		}
		return "";
	}
}
