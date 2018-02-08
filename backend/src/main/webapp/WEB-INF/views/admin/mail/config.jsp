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
<!-- build:css(src/main/webapp) css/admin-mail-config.css -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<script>
function deleteMail() {
    bootbox.confirm('<%= jspUtil.label("knowledge.mail.confirm.delete") %>', function(result) {
        if (result) {
            $('#mailForm').attr('action', '<%= request.getContextPath()%>/admin.mail/delete');
            $('#mailForm').submit();
        }
    }); 
};
</script>
</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.mail.title") %>
<span class="backlink">
<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
</span>
</h4>

<form action="<%= request.getContextPath()%>/admin.mail/save" method="post" role="form" id="mailForm">
    
    <h5 class="sub_title"><%= jspUtil.label("knowledge.mail.subtitle.param") %></h5>
    
    <div class="form-group">
        <label for="host"><%= jspUtil.label("knowledge.mail.label.host") %></label>
        <input type="text" class="form-control" name="host" id="host"
            placeholder="<%= jspUtil.label("knowledge.mail.label.host") %>" value="<%= jspUtil.out("host") %>" />
    </div>
    <div class="form-group">
        <label for="port"><%= jspUtil.label("knowledge.mail.label.port") %></label>
        <input type="text" class="form-control" name="port" id="port"
            placeholder="<%= jspUtil.label("knowledge.mail.label.port") %>" value="<%= jspUtil.out("port") %>" />
    </div>
    <div class="form-group">
        <label for="authType_lock"><%= jspUtil.label("label.auth") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="<%= INT_FLAG.ON.getValue() %>" name="authType" 
                id="authType_lock" <%= jspUtil.checked(String.valueOf(INT_FLAG.ON.getValue()), "authType", true) %>/>
            <i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.mail.label.auth.yes") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="<%= INT_FLAG.OFF.getValue() %>" name="authType" 
                id="authType_unlock" <%= jspUtil.checked(String.valueOf(INT_FLAG.OFF.getValue()), "authType", true) %>/>
            <i class="fa fa-unlock"></i>&nbsp;<%= jspUtil.label("knowledge.mail.label.auth.no") %>
        </label>
    </div>
    <div class="form-group">
        <label for="smtpId"><%= jspUtil.label("knowledge.mail.label.auth.id") %></label>
        <input type="text" class="form-control" name="smtpId" id="smtpId"
            placeholder="<%= jspUtil.label("knowledge.mail.label.auth.id") %>" value="<%= jspUtil.out("smtpId") %>" />
    </div>
    <div class="form-group">
        <label for="smtpPassword"><%= jspUtil.label("knowledge.mail.label.auth.password") %></label>
        <input type="password" class="form-control" name="smtpPassword" id="smtpPassword"
            placeholder="<%= jspUtil.label("knowledge.mail.label.auth.password") %>" value="<%= jspUtil.out("smtpPassword") %>" />
    </div>
    <div class="form-group">
        <label for="fromAddress"><%= jspUtil.label("knowledge.mail.label.auth.send.address") %></label>
        <input type="text" class="form-control" name="fromAddress" id="fromAddress"
            placeholder="<%= jspUtil.label("knowledge.mail.label.auth.send.address") %>" value="<%= jspUtil.out("fromAddress") %>" />
    </div>
    <div class="form-group">
        <label for="fromName"><%= jspUtil.label("knowledge.mail.label.auth.send.name") %></label>
        <input type="text" class="form-control" name="fromName" id="fromName"
            placeholder="<%= jspUtil.label("knowledge.mail.label.auth.send.name") %>" value="<%= jspUtil.out("fromName") %>" />
    </div>
    
    <input type="hidden" name="systemName" value="<%= jspUtil.out("systemName") %>" />
    
    <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
    <button type="button" class="btn btn-danger" onclick="deleteMail();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
</form>

<br/><br/>
<form action="<%= request.getContextPath()%>/admin.mail/test_send" method="post" role="form" id="mailTestForm">
    <h5 class="sub_title"><%= jspUtil.label("knowledge.mail.subtitle.test") %></h5>
    <c:if test="${mail_send_error != null}">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <strong>Mail Send Error [<%= jspUtil.out("mail_send_error_class") %>]</strong><br/>
            <%= jspUtil.out("mail_send_error") %>
        </div>
    </c:if>
    
    <div class="form-group">
        <label for="to_address"><%= jspUtil.label("knowledge.mail.label.to") %></label>
        <input type="text" class="form-control" name="to_address" id="to_address"
            placeholder="<%= jspUtil.label("knowledge.mail.label.to") %>" value="<%= jspUtil.out("to_address") %>" />
    </div>
    <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.submit") %></button>
</form>


</c:param>

</c:import>

