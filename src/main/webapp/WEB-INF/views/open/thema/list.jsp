<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<style>
.thema_box {
	text-align: center;
}

.thema_show {
	border: 1px solid gray;
	height: 250px;
	width: 100%;
	overflow: hidden;
}

</style>

</c:param>

<c:param name="PARAM_SCRIPTS">
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.config.thema") %></h4>


<div class="row">
	<div class="col-xs-4 thema_box">
		<iframe src="<%= request.getContextPath() %>/open.thema/show/flatly" class="thema_show"></iframe>
		<a href="<%= request.getContextPath() %>/open.thema/enable/flatly" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
	</div>
	<div class="col-xs-4 thema_box">
		<iframe src="<%= request.getContextPath() %>/open.thema/show/darkly" class="thema_show"></iframe>
		<a href="<%= request.getContextPath() %>/open.thema/enable/flatly" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
	</div>
	<div class="col-xs-4 thema_box">
		<iframe src="<%= request.getContextPath() %>/open.thema/show/sandstone" class="thema_show"></iframe>
		<a href="<%= request.getContextPath() %>/open.thema/enable/flatly" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
	</div>
</div>

<div class="row">
	<div class="col-xs-4 thema_box">
		<iframe src="<%= request.getContextPath() %>/open.thema/show/cosmo" class="thema_show"></iframe>
		<a href="<%= request.getContextPath() %>/open.thema/enable/cosmo" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
	</div>
	<div class="col-xs-4 thema_box">
		<iframe src="<%= request.getContextPath() %>/open.thema/show/slate" class="thema_show"></iframe>
		<a href="<%= request.getContextPath() %>/open.thema/enable/slate" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
	</div>
	<div class="col-xs-4 thema_box">
		<iframe src="<%= request.getContextPath() %>/open.thema/show/spacelab" class="thema_show"></iframe>
		<a href="<%= request.getContextPath() %>/open.thema/enable/spacelab" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
	</div>
</div>


<div class="row">
	<div class="col-xs-4 thema_box">
		<iframe src="<%= request.getContextPath() %>/open.thema/show/united" class="thema_show"></iframe>
		<a href="<%= request.getContextPath() %>/open.thema/enable/united" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
	</div>
	<div class="col-xs-4">
		<iframe src="<%= request.getContextPath() %>/open.thema/show/superhero" class="thema_show"></iframe>
		<a href="<%= request.getContextPath() %>/open.thema/enable/superhero" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
	</div>
	<div class="col-xs-4">
		<iframe src="<%= request.getContextPath() %>/open.thema/show/cerulean" class="thema_show"></iframe>
		<a href="<%= request.getContextPath() %>/open.thema/enable/cerulean" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
	</div>
</div>


</c:param>

</c:import>

