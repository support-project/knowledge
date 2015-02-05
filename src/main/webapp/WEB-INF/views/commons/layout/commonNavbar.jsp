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
				<i class="fa fa-book"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.title") %>
				<span style="font-size: 8pt;"><%= jspUtil.label("label.version") %></span>
			</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				
				<li class="dropdown" id="tabMenu">
					<a class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-bolt" ></i>&nbsp;<%= jspUtil.label("knowledge.navbar.menu") %><b class="caret"></b>
					</a>
					<ul class="dropdown-menu">
						<li class="dropdown-header">&nbsp;<%= jspUtil.label("knowledge.navbar.menu.knowledge") %></li>
						<li >
							<a href="<%= request.getContextPath() %>/open.knowledge/list" style="cursor: pointer;">
								<i class="fa fa-list-alt"></i>&nbsp;<%= jspUtil.label("knowledge.list.menu.all") %>
							</a>
						</li>
						
						<% if (jspUtil.logined()) { %>
						<li>
							<a href="<%= request.getContextPath() %>/open.knowledge/list?user=<%= jspUtil.id() %>" >
								<i class="fa fa-male"></i>&nbsp;<%= jspUtil.label("knowledge.list.menu.myknowledge") %>
							</a>
						</li>
						<% } %>
						
						<li >
							<a href="<%= request.getContextPath() %>/open.knowledge/search" >
								<i class="glyphicon glyphicon-search"></i>&nbsp;<%= jspUtil.label("knowledge.list.menu.search") %>
							</a>
						</li>
						<li >
							<a href="<%= request.getContextPath() %>/protect.knowledge/view_add" style="cursor: pointer;">
								<i class="fa fa-plus-circle"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.menu.knowledge.add") %>
							</a>
						</li>
							
						<% if (jspUtil.logined()) { %>
						<li class="dropdown-header">&nbsp;<%= jspUtil.label("knowledge.navbar.config.group") %></li>
						<li >
							<a href="<%= request.getContextPath() %>/protect.group/mygroups" style="cursor: pointer;">
								<i class="fa fa-users"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config.group.list") %>
							</a>
						</li>
						<% } %>
						
						<li class="dropdown-header">&nbsp;<%= jspUtil.label("knowledge.navbar.tag") %></li>
						<li >
							<a href="<%= request.getContextPath() %>/open.tag/list">
								<i class="fa fa-tags"></i>&nbsp;<%= jspUtil.label("knowledge.list.tags.list") %>
							</a>
						</li>
						
						<li class="dropdown-header">&nbsp;<%= jspUtil.label("knowledge.navbar.lang") %></li>
						<li >
							<a href="<%= request.getContextPath() %>/open.lang/en" style="cursor: pointer;">
								<i class="fa fa-newspaper-o"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.lang.en") %>
							</a>
						</li>
						<li >
							<a href="<%= request.getContextPath() %>/open.lang/ja" style="cursor: pointer;">
								<i class="fa fa-newspaper-o"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.lang.ja") %>
							</a>
						</li>
					
					</ul>
				</li>
				
				
				<% if (request.isUserInRole("admin")) { %>
				<li class="dropdown" id="tabConfig">
					<a class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-cog" ></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config") %><b class="caret"></b>
					</a>
					<ul class="dropdown-menu">
						<li class="dropdown-header">&nbsp;<%= jspUtil.label("knowledge.navbar.config.admin") %></li>
						<li >
							<a href="<%= request.getContextPath() %>/admin.users/list" style="cursor: pointer;">
								<i class="fa fa-users"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config.admin.users") %>
							</a>
						</li>
						<li >
							<a href="<%= request.getContextPath() %>/admin.users/accept_list" style="cursor: pointer;">
								<i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config.admin.acccept") %> 
							</a>
						</li>
						
						<li class="dropdown-header">&nbsp;<%= jspUtil.label("knowledge.navbar.config.system") %></li>
						<li >
							<a href="<%= request.getContextPath() %>/admin.config/config" style="cursor: pointer;">
								<i class="fa fa-cogs"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config.system.general") %>
							</a>
						</li>
						<li >
							<a href="<%= request.getContextPath() %>/admin.mail/config" style="cursor: pointer;">
								<i class="fa fa-inbox"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config.system.mail") %>
							</a>
						</li>
						
						<li class="dropdown-header">&nbsp;<%= jspUtil.label("knowledge.navbar.data") %></li>
						<li >
							<a href="<%= request.getContextPath() %>/admin.database/index" style="cursor: pointer;">
								<i class="fa fa-recycle"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.data.backup") %>
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
								<i class="fa fa-smile-o"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.account.myaccount") %>
							</a>
						</li>
						<li>
							<a href="<%= request.getContextPath() %>/open.knowledge/list?user=<%= jspUtil.id() %>" >
								<i class="fa fa-male"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.account.myknowledge") %>
							</a>
						</li>
						
						<li class="divider"></li>
						<li id="tabLogout">
							<a href="<%= request.getContextPath() %>/signout" style="cursor: pointer;">
								<i class="fa fa-sign-out"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.signout") %>
							</a>
						</li>
					</ul>
				</li>
				<% } else { %>
				<li>
					<a href="<%= request.getContextPath() %>/signin?page=<%= top %>" style="cursor: pointer;">
						<i class="fa fa-sign-in"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.signin") %>
					</a>
				</li>
				<% } %>
			</ul>
			
			
			<form class="nav navbar-nav navbar-form navbar-right" role="search"
				action="<%= request.getContextPath() %><%= top %>">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="<%= jspUtil.label("knowledge.navbar.search.placeholder") %>"
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

