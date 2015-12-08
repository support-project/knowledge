<%@page import="org.support.project.common.util.StringUtils"%>
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
<h4 class="title">
	<%= jspUtil.out("stockName") %>
	<a class="btn btn-info" href="<%= request.getContextPath() %>/protect.stock/edit/<%= jspUtil.out("stockId") %>" >
		<i class="fa fa-tag"></i>&nbsp;<%= jspUtil.label("label.edit") %>
	</a>
</h4>
	<%= jspUtil.out("description") %>
	
	<nav>
		<ul class="pager">
			<li class="previous">
				<a href="<%= request.getContextPath() %>/protect.stock/knowledge?stockId=<%= jspUtil.out("stockId") %>&offset=<%= jspUtil.out("previous") %>">
					<span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
				</a>
			</li>
			<li class="next">
				<a href="<%= request.getContextPath() %>/protect.stock/knowledge?stockId=<%= jspUtil.out("stockId") %>&offset=<%= jspUtil.out("next") %>">
					<%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
				</a>
			</li>
		</ul>
	</nav>

	<c:if test="${empty knowledges}">
		<div class="col-sm-12">
		empty
		</div>
	</c:if>
	
	<div class="list-group">
		<c:forEach var="knowledge" items="${knowledges}">
			<a class="list-group-item" 
			href="<%= request.getContextPath() %>/open.knowledge/view/<%= jspUtil.out("knowledge.knowledgeId") %>" >
				<h4 class="list-group-item-heading">
					<i class="fa fa-book"></i>&nbsp;[<%= jspUtil.out("knowledge.knowledgeId") %>]&nbsp;<%= jspUtil.out("knowledge.title") %>
				</h4>
				<% if (StringUtils.isNotEmpty(jspUtil.out("knowledge.comment"))) { %>
				<p class="list-group-item-text">
					<i class="fa fa-comment-o"></i>&nbsp;<%= jspUtil.out("knowledge.comment") %>
				</p>
				<% } %>
			</a>
			
		</c:forEach>
	</div>

	<nav>
		<ul class="pager">
			<li class="previous">
				<a href="<%= request.getContextPath() %>/protect.stock/knowledge?stockId=<%= jspUtil.out("stockId") %>&offset=<%= jspUtil.out("previous") %>">
					<span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
				</a>
			</li>
			<li class="next">
				<a href="<%= request.getContextPath() %>/protect.stock/knowledge?stockId=<%= jspUtil.out("stockId") %>&offset=<%= jspUtil.out("next") %>">
					<%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
				</a>
			</li>
		</ul>
	</nav>

</c:param>

</c:import>

