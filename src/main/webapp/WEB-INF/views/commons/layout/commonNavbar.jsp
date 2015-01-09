<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% String top = "/open.knowledge/list"; %>
<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!-- navbar -->
<div class="navbar navbar-default navbar-fixed-top">
	<div class="container" id="myNavbar">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="<%= request.getContextPath() %><%= top %>" style="cursor: pointer;">
				<i class="fa fa-book"></i>&nbsp;Knowledge
			</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<% if (request.isUserInRole("admin")) { %>
				<li class="dropdown" id="tabConfig">
					<a class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-cog" ></i>&nbsp;Config<b class="caret"></b>
					</a>
					<ul class="dropdown-menu">
						<li class="dropdown-header">&nbsp;Admin</li>
						<li >
							<a href="<%= request.getContextPath() %>/admin.users/list" style="cursor: pointer;">
								<i class="fa fa-users"></i>&nbsp;Users
							</a>
						</li>
						<li >
							<a href="<%= request.getContextPath() %>/admin.users/accept_list" style="cursor: pointer;">
								<i class="fa fa-gavel"></i>&nbsp;Accept request for add user 
							</a>
						</li>
						<li class="dropdown-header">&nbsp;System Config</li>
						<li >
							<a href="<%= request.getContextPath() %>/admin.config/config" style="cursor: pointer;">
								<i class="fa fa-cogs"></i>&nbsp;General Config
							</a>
						</li>
						<li >
							<a href="<%= request.getContextPath() %>/admin.mail/config" style="cursor: pointer;">
								<i class="fa fa-inbox"></i>&nbsp;Mail Config
							</a>
						</li>
					</ul>
				</li>
				<% } %>
				
				<% if (request.getRemoteUser() != null) { %>
				<li class="dropdown" id="tabProfile">
					<a class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-user" ></i>&nbsp;<%= jspUtil.name() %><b class="caret"></b>
					</a>
					<ul class="dropdown-menu">
						<li>
							<a href="<%= request.getContextPath() %>/protect.account" style="cursor: pointer;">
								<i class="fa fa-smile-o"></i>&nbsp;My Account
							</a>
						</li>
						<li class="divider"></li>
						<li id="tabLogout">
							<a href="<%= request.getContextPath() %>/signout" style="cursor: pointer;">
								<i class="fa fa-sign-out"></i>&nbsp;Sign out
							</a>
						</li>
					</ul>
				</li>
				<% } else { %>
				<li>
					<a href="<%= request.getContextPath() %>/signin?page=<%= top %>" style="cursor: pointer;">
						<i class="fa fa-sign-in"></i>&nbsp;Sign In
					</a>
				</li>
				<% } %>
			</ul>
			
			
			<form class="nav navbar-nav navbar-form navbar-right" role="search"
				action="<%= request.getContextPath() %><%= top %>">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Search"
						name="keyword" id="keyword" value="<%= jspUtil.out("keyword") %>" />
					<div class="input-group-btn">
						<button class="btn btn-default" type="submit">
							<i class="glyphicon glyphicon-search"></i>
						</button>
					</div>
				</div>
			</form>
			
		</div>
	</div>
</div>
<!-- /navbar -->

