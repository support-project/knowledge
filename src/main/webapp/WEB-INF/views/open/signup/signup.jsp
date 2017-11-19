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
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/account.js") %>"></script>
</c:param>


<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.signup.title") %></h4>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <form action="<%= request.getContextPath()%>/open.signup/save" method="post" role="form">
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
                <input type="password" class="form-control" name="confirm_password" id="confirm_password" placeholder="Confirm Password" value="<%= jspUtil.out("confirm_password") %>" />
            </div>
            
            <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.registration") %></button>
            <a href="<%= request.getContextPath() %>/open.knowledge/list"
                class="btn btn-success " role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("knowledge.view.back.list") %></a>
            
        </form>
    </div>
</div>


</c:param>

</c:import>

