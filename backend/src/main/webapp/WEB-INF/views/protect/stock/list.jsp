<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_SCRIPTS">
</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.navbar.account.mystock") %></h4>
	
<div class="alert alert-info alert-dismissible" role="alert">
	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	<strong><%= jspUtil.label("knowledge.stock.info.title") %></strong><br/>
	- <%= jspUtil.label("knowledge.stock.info.1") %><br/>
	- <%= jspUtil.label("knowledge.stock.info.2") %>
</div>
	
	
	
	<nav>
		<ul class="pager">
			<li class="previous">
				<a href="<%= request.getContextPath() %>/protect.stock/mylist/<%= jspUtil.out("previous") %>">
					<span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
				</a>
			</li>
			<li>
				<a href="<%= request.getContextPath() %>/protect.stock/add" style="cursor: pointer;">
					<i class="fa fa-plus-circle"></i>&nbsp;<%= jspUtil.label("label.add") %>
				</a>
			</li>
			<li class="next">
				<a href="<%= request.getContextPath() %>/protect.stock/mylist/<%= jspUtil.out("next") %>">
					<%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
				</a>
			</li>
		</ul>
	</nav>

	<c:if test="${empty stocks}">
		<div class="col-sm-12">
		empty
		</div>
	</c:if>
	
	<div class="list-group">
		<c:forEach var="stock" items="${stocks}">
			<a class="list-group-item " 
			href="<%= request.getContextPath() %>/protect.stock/knowledge?stockId=<%= jspUtil.out("stock.stockId") %>&offset=0" >
				<span class="badge"><%= jspUtil.out("stock.knowledgeCount") %></span>
				<i class="fa fa-tag"></i>&nbsp;<%= jspUtil.out("stock.stockName") %>
			</a>
			
		</c:forEach>
	</div>

	<nav>
		<ul class="pager">
			<li class="previous">
				<a href="<%= request.getContextPath() %>/protect.stock/mylist/<%= jspUtil.out("previous") %>">
					<span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
				</a>
			</li>
			<li>
				<a href="<%= request.getContextPath() %>/protect.stock/add" style="cursor: pointer;">
					<i class="fa fa-plus-circle"></i>&nbsp;<%= jspUtil.label("label.add") %>
				</a>
			</li>
			<li class="next">
				<a href="<%= request.getContextPath() %>/protect.stock/mylist/<%= jspUtil.out("next") %>">
					<%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
				</a>
			</li>
		</ul>
	</nav>

</c:param>

</c:import>

