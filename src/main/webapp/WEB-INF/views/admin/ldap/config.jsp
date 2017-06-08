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
</c:param>

<c:param name="PARAM_SCRIPTS">
<script>
function deleteConfig() {
    bootbox.confirm('<%= jspUtil.label("knowledge.ldap.confirm.delete") %>', function(result) {
        if (result) {
            $('#ldapForm').attr('action', '<%= request.getContextPath()%>/admin.ldap/delete');
            $('#ldapForm').submit();
        }
    }); 
};

$(document).ready(function() {
    <% if (request.getAttribute("configType").equals("config2")) { %>
        $('#myTabs #myTabLdapConfig2').tab('show')
    <% } %>
});

$('#myTabs #myTabLdapConfig1').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
    $('#configType').val('config1');
});
$('#myTabs #myTabLdapConfig2').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
    $('#configType').val('config2');
});

</script>

</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.ldap.title") %></h4>

<div class="alert alert-info alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <strong>Information</strong><br/>
    - <%= jspUtil.label("knowledge.ldap.msg.ldap") %><br/>
    - <%= jspUtil.label("knowledge.ldap.msg.adminid1") %><br/>
    - <%= jspUtil.label("knowledge.ldap.msg.adminid2") %><br/>
    - <%= jspUtil.label("knowledge.ldap.msg.adminid3") %><br/>
    - <%= jspUtil.label("knowledge.ldap.msg.adminid4") %><br/>
</div>

<form action="<%= request.getContextPath()%>/admin.ldap/check" method="post" role="form" id="ldapForm">
    <input type="hidden" name="key" value="<%= jspUtil.out("key") %>" />
    <ul class="nav nav-tabs" role="tablist" id="myTabs">
        <li role="presentation" class="active">
            <a href="#ldapconfig1" aria-controls="ldapconfig1" role="tab" data-toggle="tab" id="myTabLdapConfig1">Ldap Config1</a>
        </li>
        <li role="presentation">
            <a href="#ldapconfig2" aria-controls="ldapconfig2" role="tab" data-toggle="tab" id="myTabLdapConfig2">Ldap Config2</a>
            </li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
<!-- Ldap config1 -->
        <div role="tabpanel" class="tab-pane active" id="ldapconfig1">
            <div class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong>Ldap Config1</strong><br/>
                <%= jspUtil.label("knowledge.ldap.msg.config1") %>
            </div>
            
            <div class="form-group">
                <label for="host"><%= jspUtil.label("knowledge.ldap.label.description") %><%= jspUtil.label("label.required") %></label>
                <input type="text" class="form-control" name="description" id="description"
                    placeholder="<%= jspUtil.label("knowledge.ldap.label.description") %>" value="<%= jspUtil.out("description") %>" />
            </div>
            <div class="form-group">
                <label for="host"><%= jspUtil.label("knowledge.ldap.label.host") %><%= jspUtil.label("label.required") %></label>
                <input type="text" class="form-control" name="host" id="host" placeholder="HOST" value="<%= jspUtil.out("host") %>" />
            </div>
            <div class="form-group">
                <label for="port"><%= jspUtil.label("knowledge.ldap.label.port") %><%= jspUtil.label("label.required") %></label>
                <input type="text" class="form-control" name="port" id="port" placeholder="PORT" value="<%= jspUtil.out("port") %>" />
            </div>
            
            <div class="form-group">
                <label for="plain"><%= jspUtil.label("knowledge.ldap.label.security") %></label><br/>
                <label class="radio-inline">
                    <input type="radio" value="plain" name="security" 
                        id="plain" <%= jspUtil.checked("plain", "security", true) %>/>
                    <i class="fa fa-unlock"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.plain") %>
                </label>
                <label class="radio-inline">
                    <input type="radio" value="usessl" name="security" 
                        id="usessl" <%= jspUtil.checked("usessl", "security", false) %>/>
                    <i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.usessl") %>
                </label>
                <label class="radio-inline">
                    <input type="radio" value="usetls" name="security" 
                        id="usetls" <%= jspUtil.checked("usetls", "security", false) %>/>
                    <i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.usetls") %>
                </label>
            </div>
            
            <div class="form-group">
                <label for="port"><%= jspUtil.label("knowledge.ldap.label.basedn") %><%= jspUtil.label("label.required") %></label>
                <input type="text" class="form-control" name="baseDn" id="baseDn" placeholder="Base DN" value="<%= jspUtil.out("baseDn") %>" />
            </div>
            <div class="form-group">
                <label for="port"><%= jspUtil.label("knowledge.ldap.label.idattr") %><%= jspUtil.label("label.required") %></label>
                <input type="text" class="form-control" name="idAttr" id="idAttr" placeholder="Id Attribute" value="<%= jspUtil.out("idAttr") %>" />
            </div>
            <div class="form-group">
                <label for="port"><%= jspUtil.label("knowledge.ldap.label.nameattr") %></label>
                <input type="text" class="form-control" name="nameAttr" id="nameAttr" placeholder="Name Attribute" value="<%= jspUtil.out("nameAttr") %>" />
            </div>
            <div class="form-group">
                <label for="port"><%= jspUtil.label("knowledge.ldap.label.mailattr") %></label>
                <input type="text" class="form-control" name="mailAttr" id="mailAttr" placeholder="Mail Attribute" value="<%= jspUtil.out("mailAttr") %>" />
            </div>
            
            
            <div class="form-group">
                <label for="port"><%= jspUtil.label("knowledge.ldap.label.testid") %><%= jspUtil.label("label.required") %></label>
                <input type="text" class="form-control" name="bindDn" id="bindDn" placeholder="ID" value="<%= jspUtil.out("bindDn") %>" />
            </div>
            <div class="form-group">
                <label for="port"><%= jspUtil.label("knowledge.ldap.label.testpass") %><%= jspUtil.label("label.required") %></label>
                <input type="password" class="form-control" name="bindPassword" id="bindPassword" placeholder="Password" value="<%= jspUtil.out("bindPassword") %>" />
            </div>
            <div class="form-group">
                <label for="port"><%= jspUtil.label("knowledge.ldap.label.adminid") %></label>
                <input type="text" class="form-control" name="adminCheckFilter" id="adminCheckFilter" 
                    placeholder="<%= jspUtil.label("knowledge.ldap.label.adminid") %>" value="<%= jspUtil.out("adminCheckFilter") %>" />
            </div>
        </div>
<!-- Ldap config2 -->
        <div role="tabpanel" class="tab-pane" id="ldapconfig2">
            <div class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong>Ldap Config2</strong><br/>
                <%= jspUtil.label("knowledge.ldap.msg.config2") %>
            </div>
            
            <div class="form-group">
                <label for="host"><%= jspUtil.label("knowledge.ldap.label.description") %><%= jspUtil.label("label.required") %></label>
                <input type="text" class="form-control" name="description2" id="description2"
                    placeholder="<%= jspUtil.label("knowledge.ldap.label.description") %>" value="<%= jspUtil.out("description") %>" />
            </div>
            <div class="form-group">
                <label for="host"><%= jspUtil.label("knowledge.ldap.label.host") %><%= jspUtil.label("label.required") %></label>
                <input type="text" class="form-control" name="host2" id="host" placeholder="HOST" value="<%= jspUtil.out("host2") %>" />
            </div>
            <div class="form-group">
                <label for="port"><%= jspUtil.label("knowledge.ldap.label.port") %><%= jspUtil.label("label.required") %></label>
                <input type="text" class="form-control" name="port2" id="port" placeholder="PORT" value="<%= jspUtil.out("port2") %>" />
            </div>
            
            <div class="form-group">
                <label for="plain"><%= jspUtil.label("knowledge.ldap.label.security") %></label><br/>
                <label class="radio-inline">
                    <input type="radio" value="plain" name="security2" 
                        id="plain" <%= jspUtil.checked("plain", "security", true) %>/>
                    <i class="fa fa-unlock"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.plain") %>
                </label>
                <label class="radio-inline">
                    <input type="radio" value="usessl" name="security2" 
                        id="usessl" <%= jspUtil.checked("usessl", "security", false) %>/>
                    <i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.usessl") %>
                </label>
                <label class="radio-inline">
                    <input type="radio" value="usetls" name="security2" 
                        id="usetls" <%= jspUtil.checked("usetls", "security", false) %>/>
                    <i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.usetls") %>
                </label>
            </div>
            <div class="form-group">
                <label for="bindDn"><%= jspUtil.label("knowledge.ldap.label.config2.binddn") %><%= jspUtil.label("label.required") %></label>
                <input type="text" class="form-control" name="bindDn2" id="bindDn" placeholder="ID" value="<%= jspUtil.out("bindDn") %>" />
            </div>
            <div class="form-group">
                <label for="bindPassword"><%= jspUtil.label("knowledge.ldap.label.config2.password") %><%= jspUtil.label("label.required") %></label>
                <input type="password" class="form-control" name="bindPassword2" id="bindPassword" placeholder="Password" value="<%= jspUtil.out("bindPassword") %>" />
            </div>
            
            <div class="form-group">
                <label for="baseDn"><%= jspUtil.label("knowledge.ldap.label.config2.basedn") %><%= jspUtil.label("label.required") %></label>
                <input type="text" class="form-control" name="baseDn2" id="baseDn" placeholder="Base DN" value="<%= jspUtil.out("baseDn") %>" />
            </div>
            <div class="form-group">
                <label for="filter"><%= jspUtil.label("knowledge.ldap.label.config2.filter") %><%= jspUtil.label("label.required") %><br/>
                <%= jspUtil.label("knowledge.ldap.info.filter") %>
                </label>
                <input type="text" class="form-control" name="filter2" id="filter"
                placeholder="Filter e.g. (uid=:userid)" value="<%= jspUtil.out("filter") %>" />
            </div>
            
            <div class="form-group">
                <label for="port"><%= jspUtil.label("knowledge.ldap.label.idattr") %><%= jspUtil.label("label.required") %></label>
                <input type="text" class="form-control" name="idAttr2" id="idAttr" placeholder="Id Attribute" value="<%= jspUtil.out("idAttr") %>" />
            </div>
            <div class="form-group">
                <label for="port"><%= jspUtil.label("knowledge.ldap.label.nameattr") %></label>
                <input type="text" class="form-control" name="nameAttr2" id="nameAttr" placeholder="Name Attribute" value="<%= jspUtil.out("nameAttr") %>" />
            </div>
            <div class="form-group">
                <label for="port"><%= jspUtil.label("knowledge.ldap.label.mailattr") %></label>
                <input type="text" class="form-control" name="mailAttr2" id="mailAttr" placeholder="Mail Attribute" value="<%= jspUtil.out("mailAttr") %>" />
            </div>
            
            <div class="form-group">
                <label for="port"><%= jspUtil.label("knowledge.ldap.label.adminid") %></label>
                <input type="text" class="form-control" name="adminCheckFilter2" id="adminCheckFilter" 
                    placeholder="<%= jspUtil.label("knowledge.ldap.label.adminid") %>" value="<%= jspUtil.out("adminCheckFilter") %>" />
            </div>
            
        </div>
    </div>
    
    
    <input type="hidden" name="configType" id="configType" value="<%= jspUtil.out("configType") %>" />
    
    <input type="hidden" name="systemName" value="<%= jspUtil.out("systemName") %>" />
    
    <button type="submit" class="btn btn-success" formaction="<%= request.getContextPath()%>/admin.ldap/check">
        <i class="fa fa-check-square-o"></i>&nbsp;<%= jspUtil.label("knowledge.ldap.label.dotest") %>
    </button>
    <button type="submit" class="btn btn-primary" formaction="<%= request.getContextPath()%>/admin.ldap/save">
        <i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %>
    </button>
    <button type="button" class="btn btn-danger" onclick="deleteConfig();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
    <a href="<%= request.getContextPath() %>/admin.ldap/list"
        class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %>
    </a>
    
</form>


</c:param>

</c:import>

