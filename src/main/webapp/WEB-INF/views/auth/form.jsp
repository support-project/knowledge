<%@page import="org.support.project.knowledge.logic.SystemConfigLogic"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<style>
body {
	padding-top: 70px;
	padding-bottom: 40px;
	background-color: #eee;
}

.form-signin {
	max-width: 330px;
	padding: 15px;
	margin: 0 auto;
}

.form-signin .form-signin-heading,.form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin .checkbox {
	font-weight: normal;
}

.form-signin .form-control {
	position: relative;
	font-size: 16px;
	height: auto;
	padding: 10px;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

.form-signin .form-control:focus {
	z-index: 2;
}

.form-signin input[type="text"] {
	margin-bottom: -1px;
	border-bottom-left-radius: 0;
	border-bottom-right-radius: 0;
}

.form-signin input[type="password"] {
	margin-bottom: 10px;
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}
</style>

</c:param>



	<c:param name="PARAM_CONTENT">

		<div class="container">
			<form class="form-signin"
				action="<%=request.getContextPath()%>/signin"
				name="login" method="post">
				<h2 class="form-signin-heading"></h2>
				<% if (!StringUtils.isEmpty(request.getAttribute("page")) 
						&& !"/open.knowledge/list".equals(request.getAttribute("page"))) { %>
				<p>サインインが必要な機能です。<br/>(ナレッジの編集などはサインインが必要です)</p>
				<% } %>
				
				<c:if test="${loginError}">
					<p>
						<span class="err">ID/パスワードが間違ってます</span>
					</p>
				</c:if>

				<input type="text" class="form-control"
					name="username" value="${username}"
					placeholder="Email address" autofocus>
				<input type="password" class="form-control"
					name="password" value="${password}"
					placeholder="Password">

				<input type="hidden" name="page" value="${page}" id="page">
				
				<button class="btn btn-lg btn-primary btn-block" type="submit">
					<i class="fa fa-sign-in"></i>&nbsp;Sign in
				</button>
				
<% if (SystemConfigLogic.get().isUserAddAble()) { %>
				<br/>
				<a href="<%= request.getContextPath() %>/open.signup/view" class="btn btn-info btn-block">
					<i class="fa fa-plus-square"></i>&nbsp;ユーザ新規登録
				</a>
<% } %>
				
				
			</form>

		</div>
		
	</c:param>

</c:import>

