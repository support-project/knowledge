<%@page import="org.support.project.web.bean.LabelValue"%>
<%@page import="java.util.List"%>
<%@page import="org.support.project.knowledge.config.AppConfig"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% String top = "/open.knowledge/list"; %>
<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand"
				href="<%= request.getContextPath() %><%= top %>"
				style="cursor: pointer;"> <i class="fa fa-book"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.title") %>
			</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

			<form class="nav navbar-nav navbar-form " role="search"
				action="<%= request.getContextPath() %><%= top %>">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="<%= jspUtil.label("knowledge.navbar.search.placeholder") %>"
						name="keyword" id="navSearch" value="<%= jspUtil.out("searchKeyword") %>" />
					<div class="input-group-btn">
						<button class="btn btn-default" type="submit">
							<i class="glyphicon glyphicon-search"></i>
						</button>
					</div>
				</div>
			</form>


			<ul class="nav navbar-nav navbar-right">
				<li class="navButton navAddButton">
					<a href="<%= request.getContextPath() %>/protect.knowledge/view_add<%= jspUtil.out("params") %>" style="cursor: pointer;"
						id="navAddButtonLink">
						<i class="fa fa-plus-circle"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.add.knowledge") %>
					</a>
				</li>
				<li class="navButton navListButton">
					<a href="<%= request.getContextPath() %>/open.knowledge/list" style="cursor: pointer;"
					id="navListButtonLink">
						<i class="fa fa-list-alt"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.list.knowledge") %>
					</a>
				</li>
				
				<% if (!jspUtil.logined()) { %>
				<li class="dropdown navButton navMenuButton">
					<a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-expanded="false" id="navMenuButtonLink">
						<img src="<%= request.getContextPath()%>/open.account/icon/"
							alt="icon" width="24" height="24"/>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu" role="menu">
						<li >
							<a href="<%= request.getContextPath() %>/open.knowledge/search" >
								<i class="glyphicon glyphicon-search"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.search") %>
							</a>
						</li>
						<li class="divider"></li>
						<li>
							<a id="menuSignin" href="<%= request.getContextPath() %>/signin?page=<%= top %>" style="cursor: pointer;">
								<i class="fa fa-sign-in"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.signin") %>
							</a>
						</li>
					</ul>
				</li>
				<% } else { %>
				<li class="dropdown navButton navLoginedMenuButton">
					<a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-expanded="false" id="navMenuButtonLink">
						<img src="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.id() %>"
							alt="icon" width="24" height="24"/>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu" role="menu">
						<li >
							<a href="<%= request.getContextPath() %>/open.knowledge/search" >
								<i class="glyphicon glyphicon-search"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.search") %>
							</a>
						</li>
						<% if (request.isUserInRole("admin")) { %>
						<li class="divider"></li>
						<li >
							<a href="<%= request.getContextPath() %>/admin.systemconfig/index" >
								<i class="fa fa-cogs" ></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config.system") %>
							</a>
						</li>
						<% } %>
						<% if (jspUtil.logined()) { %>
						<li class="divider"></li>
						<li >
							<a href="<%= request.getContextPath() %>/protect.config/index" >
								<i class="fa fa-cog" ></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config") %>
							</a>
						</li>
						<% } %>
						<li class="divider"></li>
						<li >
							<a id="menuSignout" href="<%= request.getContextPath() %>/signout" style="cursor: pointer;">
								<i class="fa fa-sign-out"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.signout") %>
							</a>
						</li>
					</ul>
				</li>
				<% } %>
			</ul>
			
			
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>

