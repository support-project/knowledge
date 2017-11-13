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
function deleteUser() {
    var msg ='<%= jspUtil.label("knowledge.account.delete.confirm")%>';
    msg += '<label class="radio-inline">';
    msg += '<input type="radio" value="1" name="remove_kind" checked="checked" />';
    msg += '<i class="fa fa-eraser"></i>&nbsp;<%= jspUtil.label("knowledge.withdrawal.label.remove") %>';
    msg += '</label><br/>';
    msg += '<label class="radio-inline">';
    msg += '<input type="radio" value="2" name="remove_kind"/>';
    msg += '<i class="fa fa-gift"></i>&nbsp;<%= jspUtil.label("knowledge.withdrawal.label.leave") %>';
    msg += '</label>';
    bootbox.dialog({
        message: msg,
        buttons: {
            success: {
                label: "Save",
                className: "btn-success",
                callback: function () {
                    var remove_kind = $("input[name='remove_kind']:checked").val();
                    $('#knowledge_remove').val(remove_kind);
                    $('#userForm').attr('action', '<%= request.getContextPath()%>/admin.users/delete');
                    $('#userForm').submit();
                }
            },
            calsel: {
                label: "Cansel",
                className: "btn-default",
                callback: function () {
                    //何もしない
                }
            }
        }
    });
}
</script>
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.user.edit.title") %></h4>
<% if(jspUtil.is(1, "authLdap")) { %>
<div class="alert alert-warning alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <strong>Warning</strong><br/>
    - <%= jspUtil.label("knowledge.account.info.ldap") %>
</div>
<% } %>

<form action="<%= request.getContextPath()%>/admin.users/save" method="post" role="form" id="userForm">
    <div class="form-group">
        <label for="userId"><%= jspUtil.label("knowledge.account.label.no") %></label>
        <input type="text" class="form-control" name="userId" id="userId" placeholder="No"
            value="<%= jspUtil.out("userId") %>" readonly="readonly" />
    </div>
<% if(jspUtil.is(1, "authLdap")) { %>
    <div class="form-group">
        <label for="userKey"><%= jspUtil.label("knowledge.account.id") %></label>
        <input type="text" class="form-control" name="userKey" id="userKey" placeholder="Mail Address" value="<%= jspUtil.out("userKey") %>" readonly="readonly" />
    </div>
    <div class="form-group">
        <label for="userKey"><%= jspUtil.label("knowledge.account.mail") %></label>
        <input type="text" class="form-control" name="userKey" id="userKey" placeholder="Mail Address" value="<%= jspUtil.out("mailAddress") %>" readonly="readonly" />
    </div>
    <div class="form-group">
        <label for="userName"><%= jspUtil.label("knowledge.signup.label.name") %></label>
        <input type="text" class="form-control" name="userName" id="userName" placeholder="User Name" value="<%= jspUtil.out("userName") %>" readonly="readonly" />
    </div>

<% } else { %>
    <div class="form-group">
        <label for="userKey"><%= jspUtil.label("knowledge.account.id") %></label>
        <input type="text" class="form-control" name="userKey" id="userKey" placeholder="Mail Address" value="<%= jspUtil.out("userKey") %>" />
    </div>
    <div class="form-group">
        <label for="userKey"><%= jspUtil.label("knowledge.account.mail") %></label>
        <input type="text" class="form-control" name="userKey" id="userKey" placeholder="Mail Address" value="<%= jspUtil.out("mailAddress") %>" readonly="readonly" />
    </div>
    <div class="form-group">
        <label for="userName"><%= jspUtil.label("knowledge.signup.label.name") %></label>
        <input type="text" class="form-control" name="userName" id="userName" placeholder="User Name" value="<%= jspUtil.out("userName") %>" />
    </div>

    <div class="form-group">
        <label for="password"><%= jspUtil.label("knowledge.signup.label.password") %><%= jspUtil.label("knowledge.account.label.password.msg") %></label>
        <input type="password" class="form-control" name="password" id="password" placeholder="Password" value="<%= jspUtil.out("password") %>" />
    </div>
    <div class="form-group">
        <label for="confirm_password"><%= jspUtil.label("knowledge.signup.label.confirm.password") %></label>
        <input type="password" class="form-control" name="confirm_password" id="confirm_password" placeholder="Confirm Password" value="<%= jspUtil.out("confirm_password") %>" />
    </div>
<% } %>

    <div class="form-group">
        <label for="role_${role.roleId}"><%= jspUtil.label("label.role") %></label><br/>
        <c:forEach var="role" items="${systemRoles}" varStatus="status">
        <label class="radio-inline">
            <input type="checkbox" value="<%= jspUtil.out("role.roleKey") %>" name="roles" 
                id="role_${role.roleId}" <% 
                if (jspUtil.getValue("role", Roles.class).isChecked()) {
                    out.write("checked=\"checked\"");
                } 
            %>/>
            <%= jspUtil.out("role.roleName") %>
        </label>
        </c:forEach>
    </div>

    <div class="form-group">
        <label><%= jspUtil.label("knowledge.user.stealth.access") %></label><br/>
        <label class="radio-inline">
            <input type="checkbox" value="1" name="STEALTH_ACCESS" <%= jspUtil.checked("1", "STEALTH_ACCESS", false) %> />
            ON
        </label>
    </div>

    <div class="form-group">
        <label for="input_no"><%= jspUtil.label("label.regist.datetime") %> / <%= jspUtil.label("label.update.datetime") %></label>
        <p class="form-control-static">
            <i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("insertDatetime")%> / 
            <i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("updateDatetime")%>
        </p>
    </div>

    <input type="hidden" name="offset" value="<%= jspUtil.out("offset") %>" />
    <input type="hidden" name="userId" value="<%= jspUtil.out("userId") %>" />
    <input type="hidden" value="" name="knowledge_remove" id="knowledge_remove" />

    <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.update") %></button>
    <button type="button" class="btn btn-danger" onclick="deleteUser();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
    <a href="<%= request.getContextPath() %>/admin.users/list/<%= jspUtil.out("offset") %>"
    class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>

</form>


</c:param>

</c:import>

