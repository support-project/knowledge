<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.logic.SystemConfigLogic"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.auth.title.forgot.password") %></h4>

	<div class="container">
		<form class=""
			action="<%=request.getContextPath()%>/open.PasswordInitialization/change"
			name="login" method="post">
			
			<%= jspUtil.label("knowledge.auth.msg.change.password") %>
			
			<div class="form-group">
				<label for="inputEmail" class="control-label"><%= jspUtil.label("knowledge.auth.label.mail") %></label>
				<div class="">
				<input type="text" class="form-control"
					name="username" value="<%= jspUtil.out("reset.userKey") %>"
					placeholder="Email address" readonly="readonly">
				</div>
			</div>
			
			<div class="form-group">
				<label for="password"><%= jspUtil.label("knowledge.signup.label.password") %></label>
				<input type="password" class="form-control" name="password" id="password" placeholder="Password" value="<%= jspUtil.out("password") %>" />
			</div>
			<div class="form-group">
				<label for="confirm_password"><%= jspUtil.label("knowledge.signup.label.confirm.password") %></label>
				<input type="password" class="form-control" name="confirm_password" id="confirm_password" placeholder="Confirm Password" value="<%= jspUtil.out("confirm_password") %>" />
			</div>
			
			<input type="hidden" name="key" value="<%= jspUtil.out("key") %>" />
			
			<div class="form-group">
				<div class="">
					<button class="btn btn-primary " type="submit">
						<i class="fa fa-sign-in"></i>&nbsp;<%= jspUtil.label("knowledge.auth.label.change.password") %>
					</button>
				</div>
			</div>
		</form>

	</div>
	
</c:param>

</c:import>

