<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</style>

</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-admin-pins.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/js/admin-pins.js"></script>
<!-- endbuild -->
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.admin.pin.menu") %></h4>

<div class="form-inline">
    <div class="form-group">
        <label for="host"><%= jspUtil.label("knowledge.admin.pin.label.add") %></label>
        <input type="number" class="form-control" name="knowledgeId" id="knowledgeId"
            placeholder="<%= jspUtil.label("knowledge.admin.pin.label.add") %>" min="1" step="1"/>
        <button type="button" class="btn btn-primary" id="addPin">
            <i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("knowledge.admin.pin.label.submit") %>
        </button>
    </div>
</div>

<hr/>

<span><%= jspUtil.label("knowledge.admin.pin.list") %></span>
<div class="list-group" id="list">
</div>



</c:param>

</c:import>

