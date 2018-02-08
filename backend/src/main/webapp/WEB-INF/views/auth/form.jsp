<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.logic.SystemConfigLogic"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("label.login") %></h4>

    <div class="container">
        <form class=""
            action="<%=request.getContextPath()%>/signin"
            name="login" method="post">
            
            <% if (!StringUtils.isEmpty(request.getAttribute("page")) 
                    && !"/open.knowledge/list".equals(request.getAttribute("page"))) { %>
                <div class="form-group">
                    <div class="">
                    <%= jspUtil.label("knowledge.auth.description") %>
                    </div>
                </div>
            <% } %>
            
            <c:if test="${loginError}">
                <div class="form-group">
                    <div class="">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <%= jspUtil.label("message.login.error") %>
                        </div>
                    </div>
                </div>
            </c:if>
            
            <div class="form-group">
                <label for="inputEmail" class="control-label"><%= jspUtil.label("knowledge.auth.label.id") %></label>
                <div class="">
                <input type="text" class="form-control"
                    name="username" value="<%= jspUtil.out("username") %>"
                    placeholder="<%= jspUtil.label("knowledge.auth.label.id") %>" autofocus>
                </div>
            </div>
            <div class="form-group">
                <label for="inputPass" class="control-label"><%= jspUtil.label("knowledge.auth.label.password") %></label>
                <div class="">
                <input type="password" class="form-control"
                    name="password" value="<%= jspUtil.out("password") %>"
                    placeholder="<%= jspUtil.label("knowledge.auth.label.password") %>">
                </div>
            </div>
            <input type="hidden" name="page" value="<%= jspUtil.out("page") %>" id="page">
            
            <div class="form-group">
                <div class="">
                    <button class="btn btn-primary " type="submit">
                        <i class="fa fa-sign-in"></i>&nbsp;<%= jspUtil.label("knowledge.auth.signin") %>
                    </button>
<% if (SystemConfigLogic.get().isUserAddAble()) { %>
                    <a href="<%= request.getContextPath() %>/open.signup/view" class="btn btn-info">
                        <i class="fa fa-plus-square"></i>&nbsp;<%= jspUtil.label("knowledge.auth.signup") %>
                    </a>
<% } %>

            <br/><br/>
            <a href="<%=request.getContextPath()%>/open.PasswordInitialization/view" class="text-primary">
                <i class="fa fa-key"></i>&nbsp;<%= jspUtil.label("knowledge.auth.forgot.password") %>
            </a>


                </div>
            </div>
                
                
        </form>

    </div>
    
</c:param>

</c:import>

