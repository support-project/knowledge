<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="java.util.List"%>
<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.knowledge.entity.MailPropertiesEntity"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/admin-mailhook-config.css -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-mailhook-config.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/js/mailhookconfig.js"></script>
<!-- endbuild -->

<script>
function deleteMail() {
    bootbox.confirm('<%= jspUtil.label("message.confirm.delete") %>', function(result) {
        if (result) {
            $('#mailForm').attr('action', '<%= request.getContextPath()%>/admin.mailhook/delete');
            $('#mailForm').submit();
        }
    }); 
};

<% 
List<MailPropertiesEntity> properties = (List<MailPropertiesEntity>) request.getAttribute("properties");
for (MailPropertiesEntity prop: properties) {
%>
addProperty('<%= prop.getPropertyKey() %>', '<%= prop.getPropertyValue() %>');
<%
}
%>

</script>
</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.admin.post.from.mail") %>
<span class="backlink">
<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
</span>
</h4>

<div class="alert alert-info alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
    <strong>Information</strong><br/>
    <%=jspUtil.label("knowledge.admin.mailhook.info.1")%>
    <%=jspUtil.label("knowledge.admin.mailhook.info.2")%><br/>
    <strong><%=jspUtil.label("knowledge.admin.mailhook.info.3")%><br/></strong>
</div>


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
    <input type="hidden" name="mailProtocol" value="-" />
    
    <div class="panel panel-default">
        <div class="panel-heading">
            <%=jspUtil.label("knowledge.admin.mailhook.title.properties") %>
            <a href="#advancedOption" data-toggle="collapse"><i class="fa fa-chevron-circle-down"></i></a>
        </div>
        <div class="panel-body">
            <div id="advancedOption" class="collapse">
                <div class="text-right" style="width:100%;margin-bottom: 5px">
                    <button class="btn btn-success" type="button" id="addProperty"><i class="fa fa-plus-square"></i>Add</button>
                    <button class="btn btn-warning" type="button" id="removeProperty"><i class="fa fa-minus-square"></i>Remove</button>
                </div>
                <div id="properties">
                </div>
            </div>
        </div>
    </div>
    
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

