<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="java.util.List"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/knowledge-list.css") %>" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/echojs/dist/echo.min.js"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-list.js") %>"></script>
</c:param>


<c:param name="PARAM_CONTENT">
<div class="row">
	<div class="col-sm-6 col-md-6">
		<h4 class="title">
		<img id="icon" src="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.out("userId") %>"
		width="64" height="64" />&nbsp;
		<%= jspUtil.out("userName") %>
		</h4>
	</div>
	<div class="col-sm-6 col-md-6">
		<div class="row">
			<div class="col-xs-6">
			<i class="fa fa-book"></i>&nbsp;<%= jspUtil.label("knowledge.account.label.knowledge.count") %>
			</div>
			<div class="col-xs-6">
			<i class="fa fa-times"></i>&nbsp;<%= jspUtil.out("knowledgeCount") %>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
			<i class="fa fa-thumbs-o-up"></i>&nbsp;<%= jspUtil.label("knowledge.account.label.like.count") %>
			</div>
			<div class="col-xs-6">
			<i class="fa fa-times"></i>&nbsp;<%= jspUtil.out("likeCount") %>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
			<i class="fa fa-star-o"></i>&nbsp;<%= jspUtil.label("knowledge.account.label.stock.count") %>
			</div>
			<div class="col-xs-6">
			<i class="fa fa-times"></i>&nbsp;<%= jspUtil.out("stockCount") %>
			</div>
		</div>
	</div>
</div>

<br/>
<div class="sub_title">
<%= jspUtil.label("knowledge.account.label.knowledges") %>
</div>

<!-- リスト -->
<div class="row" id="knowledgeList">
	<% request.setAttribute("list_data", jspUtil.getValue("knowledges", List.class)); %>
	<c:import url="/WEB-INF/views/open/knowledge/partials/common_list.jsp" />
</div>


	<!-- Pager -->
	<nav>
		<ul class="pager">
			<li class="previous">
				<a href="<%= request.getContextPath() %>/open.account/info/<%= jspUtil.out("userId") %>?offset=<%= jspUtil.out("previous") %>">
					<span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
				</a>
			</li>
			<li class="next">
				<a href="<%= request.getContextPath() %>/open.account/info/<%= jspUtil.out("userId") %>?offset=<%= jspUtil.out("next") %>">
					<%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
				</a>
			</li>
		</ul>
	</nav>



</c:param>

</c:import>

