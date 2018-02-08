<%@page import="org.support.project.web.logic.HttpRequestCheckLogic"%>
<%@page import="java.util.Locale"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/admin-system-config.css -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-mailtemplate.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/js/mailtemplate.js"></script>
<!-- endbuild -->
</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.mailtemplate.title") %>
<span class="backlink">
<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
</span>
</h4>

<form action="<%= request.getContextPath()%>/admin.mailtemplate/save" method="post" role="form" id="mailTemplateForm">
    <input type="hidden" name="<%= HttpRequestCheckLogic.REQ_ID_KEY %>"
        value="<%= jspUtil.out(HttpRequestCheckLogic.REQ_ID_KEY) %>" />
        
    <div class="form-group">
        <label for="templateId"><%= jspUtil.label("knowledge.mailtemplate.label.template") %></label>
        <select class="form-control" name="templateId" id="templateId">
        </select>
    </div>
    <div class="form-group">
        <pre id="template_description">
        </pre>
    </div>
    
    <div class="row">
        <div class="col-sm-6">
            <h4>
                <%--<i class="flag-icon flag-icon-us"></i>&nbsp; --%>
                <%= Locale.ENGLISH.getDisplayName(jspUtil.locale()) %>
            </h4>
            <label for="en_title"><%= jspUtil.label("knowledge.mailtemplate.label.title") %></label>
            <input type="text" class="form-control" name="en_title" id="en_title">
            <label for="en_content"><%= jspUtil.label("knowledge.mailtemplate.label.content") %></label>
            <textarea class="form-control" rows="10" id="en_content" name="en_content"></textarea>
        </div>
        <div class="col-sm-6">
            <h4>
                <%--<i class="flag-icon flag-icon-jp"></i>&nbsp; --%>
                <%= Locale.JAPANESE.getDisplayName(jspUtil.locale()) %>
            </h4>
            <label for="ja_title"><%= jspUtil.label("knowledge.mailtemplate.label.title") %></label>
            <input type="text" class="form-control" name="ja_title" id="ja_title">
            <label for="ja_content"><%= jspUtil.label("knowledge.mailtemplate.label.content") %></label>
            <textarea class="form-control" rows="10" id="ja_content" name="ja_content"></textarea>
       </div>
    </div>
    
    <div style="height: 5px;">
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
        <button type="button" class="btn btn-warning" id="initializeBtn"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.initialize") %></button>
    </div>
</form>



</c:param>

</c:import>

