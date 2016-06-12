<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_SCRIPTS">
<script>
function deleteMail() {
    bootbox.confirm('<%= jspUtil.label("message.confirm.delete") %>', function(result) {
        if (result) {
            $('#mailForm').attr('action', '<%= request.getContextPath()%>/admin.mailhook/delete');
            $('#mailForm').submit();
        }
    }); 
};
</script>
</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.admin.post.from.mail") %></h4>

<form action="<%= request.getContextPath()%>/admin.mailhook/save" method="post" role="form" id="mailForm">
    
    <h5 class="sub_title"><%= jspUtil.label("knowledge.mail.subtitle.param") %></h5>
    
    <div class="form-group">
        <label for="host"><%= jspUtil.label("knowledge.admin.mailhook.host") %></label>
        <input type="text" class="form-control" name="mailHost" id="mailHost"
            placeholder="<%= jspUtil.label("knowledge.admin.mailhook.host") %>" value="<%= jspUtil.out("mailHost") %>" />
    </div>
    <div class="form-group">
        <label for="port"><%= jspUtil.label("knowledge.admin.mailhook.port") %></label>
        <input type="text" class="form-control" name="mailPort" id="mailPort"
            placeholder="<%= jspUtil.label("knowledge.admin.mailhook.port") %>" value="<%= jspUtil.out("mailPort") %>" />
    </div>
    <div class="form-group">
        <label for="authType_lock"><%= jspUtil.label("knowledge.admin.mailhook.protocol") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="imap" name="mailProtocol" 
                id="mailProtocol_Imap" <%= jspUtil.checked("imap", "mailProtocol", true) %>/>
            <i class="fa fa-lock"></i>&nbsp;
            <%= jspUtil.label("knowledge.admin.mailhook.protocol.imap") %>
        </label>
    </div>
    <div class="form-group">
        <label for="smtpId"><%= jspUtil.label("knowledge.admin.mailhook.user") %></label>
        <input type="text" class="form-control" name="mailUser" id="mailUser"
            placeholder="<%= jspUtil.label("knowledge.admin.mailhook.user") %>" value="<%= jspUtil.out("mailUser") %>" />
    </div>
    <div class="form-group">
        <label for="smtpPassword"><%= jspUtil.label("knowledge.admin.mailhook.pass") %></label>
        <input type="password" class="form-control" name="mailPass" id="mailPass"
            placeholder="<%= jspUtil.label("knowledge.admin.mailhook.pass") %>" value="<%= jspUtil.out("mailPass") %>" />
    </div>
    
    <input type="hidden" name="hookId" value="1" />
    
    <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
    <% if (jspUtil.is(1, "hookId")) { %>
    <button type="submit" class="btn btn-info" formaction="<%= request.getContextPath()%>/admin.mailhook/check" formmethod="get">
        <i class="fa fa-bolt"></i>&nbsp;<%= jspUtil.label("knowledge.admin.mailhook.check") %>
    </button>
    <button type="button" class="btn btn-danger" onclick="deleteMail();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
    <% } %>
</form>


<% if (jspUtil.is(1, "hookId")) { %>
<hr/>
<h5 class="sub_title"><%= jspUtil.label("knowledge.admin.mailhook.condition") %></h5>
<form action="<%= request.getContextPath()%>/admin.mailhook/hook" method="get" role="form">
<button type="submit" class="btn btn-success">
    <i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("knowledge.admin.mailhook.add.condition") %>
</button>
<br/>

<c:forEach var="mailhook" items="${mailHooks}">

<a href="<%= request.getContextPath()%>/admin.mailhook/hook/<%= jspUtil.out("mailhook.conditionNo") %>">
<% String key = "knowledge.admin.mailhook.condition.kind." + jspUtil.out("mailhook.conditionKind"); %>
<%= jspUtil.label(key) %>
[<%= jspUtil.out("mailhook.condition") %>]
</a>
<br/>


</c:forEach>
</form>
<% } %>



</c:param>

</c:import>

