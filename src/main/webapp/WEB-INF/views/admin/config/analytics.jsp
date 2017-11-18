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
<!-- build:css(src/main/webapp) css/admin-system-config.css -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->

<style>
.radio_block {
    margin-bottom: 10px;
}
</style>

</c:param>

<c:param name="PARAM_SCRIPTS">
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.analytics.title") %>
<span class="backlink">
<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
</span>
</h4>

<div class="alert alert-info alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <strong>Information</strong><br/>
    - <%= jspUtil.label("knowledge.analytics.msg.info") %>
</div>


<form action="<%= request.getContextPath()%>/admin.config/analytics_save" method="post" role="form">

    <div class="form-group">
        <label for="analytics_script"><%= jspUtil.label("knowledge.analytics.label.script") %></label>
        <textarea rows="12" class="form-control" name="analytics_script" id="analytics_script" 
            placeholder="<%= jspUtil.label("knowledge.analytics.label.script") %>"><%= jspUtil.out("analytics_script") %></textarea>
    </div>
    <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
</form>


</c:param>

</c:import>

