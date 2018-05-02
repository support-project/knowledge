package org.support.project.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.web.logic.AuthenticationLogic;

public class InitializationServlet extends HttpServlet {
	/** ログ */
	private static final Log LOG = LogFactory.getLog(InitializationServlet.class);

	/** 初期ユーザ */
	private String initialUserId = "web-admin";
	/** 初期パスワード */
	private String initialPassword = "qazxsw23edc";
	/** 初期ユーザ名 */
	private String initialUserName = "初期ユーザ";
	/** 初期ユーザの権限 */
	private String initialRoleId = "admin";
	
	
	/** 認証／認可ロジックのクラス名 */
	private String authLogicClassName = "org.support.project.web.logic.impl.DBAuthenticationLogic";
	/** 認証／認可ロジックのインスタンス */
	private AuthenticationLogic authenticationLogic = null;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String initialUserId = config.getInitParameter("initial-user-id");
		if (StringUtils.isNotEmpty(initialUserId)) {
			this.initialUserId = initialUserId;
		}

		String initialPassword = config.getInitParameter("initial-password");
		if (StringUtils.isNotEmpty(initialPassword)) {
			this.initialPassword = initialPassword;
		}
		
		String initialUserName = config.getInitParameter("initial-user-name");
		if (StringUtils.isNotEmpty(initialUserName)) {
			this.initialUserName = initialUserName;
		}
		
		String initialRoleId = config.getInitParameter("initial-role-id");
		if (StringUtils.isNotEmpty(initialRoleId)) {
			this.initialRoleId = initialRoleId;
		}
		
		String authLogicClassName = config.getInitParameter("auth-class-name");
		if (StringUtils.isNotEmpty(authLogicClassName)) {
			this.authLogicClassName = authLogicClassName;
		}
		try {
			Class<AuthenticationLogic> class1;
			class1 = (Class<AuthenticationLogic>) Class.forName(this.authLogicClassName);
			this.authenticationLogic = org.support.project.di.Container.getComp(class1);
		} catch (ClassNotFoundException e) {
			throw new ServletException(e);
		}
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		try {
//			authenticationLogic.insertUser(initialUserId, initialPassword, initialUserName, "init", null, initialRoleId);
			
			response.getWriter().write("OK");
			response.getWriter().close();
			
		} catch (Throwable throwable) {
			LOG.error("error", throwable);
		}
	}

}
