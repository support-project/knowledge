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

</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.user.add.title") %></h4>

<form action="<%= request.getContextPath()%>/admin.users/create" method="post" role="form">
<div class="alert alert-info alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <strong>Information</strong><br/>
    - <%= jspUtil.label("knowledge.account.id.info1") %><br/>
    - <%= jspUtil.label("knowledge.account.id.info2") %>
</div>


    <div class="form-group">
        <label for="userKey"><%= jspUtil.label("knowledge.signup.label.mail") %></label>
        <input type="text" class="form-control" name="userKey" id="userKey" placeholder="Mail Address" value="<%= jspUtil.out("userKey") %>" />
    </div>
    <div class="form-group">
        <label for="userName"><%= jspUtil.label("knowledge.signup.label.name") %></label>
        <input type="text" class="form-control" name="userName" id="userName" placeholder="User Name" value="<%= jspUtil.out("userName") %>" />
    </div>
    
    <div class="form-group">
        <label for="password"><%= jspUtil.label("knowledge.signup.label.password") %></label>
        <input type="password" class="form-control" name="password" id="password" placeholder="Password" value="<%= jspUtil.out("password") %>" />
    </div>
    <div class="form-group">
        <label for="confirm_password"><%= jspUtil.label("knowledge.signup.label.confirm.password") %></label>
        <input type="password" class="form-control" name="confirm_password" id="confirm_password"
            placeholder="Confirm Password" value="<%= jspUtil.out("confirm_password") %>" />
    </div>
    
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

    
    <input type="hidden" name="offset" value="<%= jspUtil.out("offset") %>" />
    
    <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.registration") %></button>
    <a href="<%= request.getContextPath() %>/admin.users/list/<%= jspUtil.out("offset") %>"
    class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>
    
</form>


</c:param>

</c:import>

