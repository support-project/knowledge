<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.config.AuthType"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
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
.viewarea {
    border: 1px solid #DDD;
    border-radius: 8px;
    -moz-border-radius: 8px;
    -webkit-border-radius: 8px;
    color: #111;
    padding: 10px;
}
</style>
</c:param>

<c:param name="PARAM_SCRIPTS">
<script>
function deleteConfig() {
    bootbox.confirm('<%= jspUtil.label("knowledge.mail.confirm.delete") %>', function(result) {
        if (result) {
            $('#proxyForm').attr('action', '<%= request.getContextPath()%>/admin.proxy/delete');
            $('#proxyForm').submit();
        }
    }); 
};
</script>
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.proxy.title") %>
<span class="backlink">
<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
</span>
</h4>

<div class="alert alert-info alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <%= jspUtil.label("knowledge.proxy.msg") %><br/>
</div>


<form action="<%= request.getContextPath()%>/admin.proxy/save" method="post" role="form" id="proxyForm">
    <div class="form-group">
        <label for="host"><%= jspUtil.label("label.host") %></label>
        <input type="text" class="form-control" name="proxyHostName" id="host" placeholder="HOST NAME" value="<%= jspUtil.out("proxyHostName") %>" />
    </div>
    <div class="form-group">
        <label for="port"><%= jspUtil.label("label.port") %></label>
        <input type="text" class="form-control" name="proxyPortNo" id="port" placeholder="PORT" value="<%= jspUtil.out("proxyPortNo") %>" />
    </div>
    <div class="form-group">
        <label for="authType_lock"><%= jspUtil.label("label.auth") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="<%= AuthType.None.getValue() %>" name="proxyAuthType" 
                id="authType_lock" <%= jspUtil.checked(String.valueOf(AuthType.None.getValue()), "proxyAuthType", true) %>/>
            <i class="fa fa-unlock"></i>&nbsp;<%= jspUtil.label("knowledge.proxy.auth.none") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="<%= AuthType.Basic.getValue() %>" name="proxyAuthType" 
                id="authType_basic" <%= jspUtil.checked(String.valueOf(AuthType.Basic.getValue()), "proxyAuthType", false) %>/>
            <i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.proxy.auth.basic") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="<%= AuthType.NTLM.getValue() %>" name="proxyAuthType" 
                id="authType_basic" <%= jspUtil.checked(String.valueOf(AuthType.NTLM.getValue()), "proxyAuthType", false) %>/>
            <i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.proxy.auth.ntlm") %>
        </label>
    </div>
    <div class="form-group ">
        <label for="proxyAuthUserId"><%= jspUtil.label("label.auth.id") %></label>
        <input type="text" class="form-control" name="proxyAuthUserId" id="proxyAuthUserId" placeholder="ID" value="<%= jspUtil.out("proxyAuthUserId") %>" />
    </div>
    <div class="form-group">
        <label for="proxyAuthPassword"><%= jspUtil.label("label.auth.password") %></label>
        <input type="password" class="form-control" name="proxyAuthPassword" id="proxyAuthPassword" placeholder="PASSWORD" value="<%= jspUtil.out("proxyAuthPassword") %>" />
    </div>
    <div class="form-group">
        <label for="proxyAuthDomain"><%= jspUtil.label("knowledge.proxy.auth.ntlm.domain") %></label>
        <input type="text" class="form-control" name="proxyAuthDomain" id="proxyAuthDomain" placeholder="Domain" value="<%= jspUtil.out("proxyAuthDomain") %>" />
    </div>
    <div class="form-group">
        <label for="proxyAuthPcName"><%= jspUtil.label("knowledge.proxy.auth.ntlm.domain") %></label>
        <input type="text" class="form-control" name="proxyAuthPcName" id="proxyAuthPcName" placeholder="PC NAME" value="<%= jspUtil.out("proxyAuthPcName") %>" />
    </div>
    
    
    <div class="form-group">
        <label for="testType_Proxy"><%= jspUtil.label("knowledge.proxy.test.type") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="<%= INT_FLAG.ON.getValue() %>" name="testType" 
                id="testType_Proxy" <%= jspUtil.checked(String.valueOf(INT_FLAG.ON.getValue()), "testType", true) %>/>
            <%= jspUtil.label("knowledge.proxy.test.type.proxy") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="<%= INT_FLAG.OFF.getValue() %>" name="testType" 
                id="testType_Direct" <%= jspUtil.checked(String.valueOf(INT_FLAG.OFF.getValue()), "testType", false) %>/>
            <%= jspUtil.label("knowledge.proxy.test.type.direct") %>
        </label>
    </div>
    
    <div class="form-group">
        <label for="testUrl"><%= jspUtil.label("knowledge.proxy.testurl") %></label>
        <input type="text" class="form-control" name="testUrl" id="testUrl" placeholder="Test URL" value="<%= jspUtil.out("testUrl") %>" />
    </div>
    
    <input type="hidden" name="systemName" value="<%= jspUtil.out("systemName") %>" />
    
    <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
    
    <button type="submit" class="btn btn-success" formaction="<%= request.getContextPath()%>/admin.proxy/test">
        <i class="fa fa-check-square-o"></i>&nbsp;<%= jspUtil.label("knowledge.proxy.test") %>
    </button>
    
    <button type="button" class="btn btn-danger" onclick="deleteConfig();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
</form>


<% if (StringUtils.isNotEmpty(jspUtil.getValue("content", String.class))) { %>
<br/><br/>
<%= jspUtil.label("knowledge.proxy.test.result") %>
<div class="viewarea">
<%= jspUtil.out("content", JspUtil.ESCAPE_HTML) %>
</div>
<% } %>

</c:param>

</c:import>

