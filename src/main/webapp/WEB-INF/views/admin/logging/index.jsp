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
<!-- build:css(src/main/webapp) css/admin-log-list.css -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.navbar.config.admin.logging") %>
<span class="backlink">
<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
</span>
</h4>

    <h5 class="sub_title"><%= jspUtil.label("knowledge.admin.logging.delete") %></h5>
    
    <div class="alert alert-info alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <%= jspUtil.label("knowledge.admin.logging.delete.info") %>
    </div>
    <form action="<%= request.getContextPath() %>/admin.logging/delete_config" method="post" class="form-inline">
        <div class="form-group">
        <%= jspUtil.label("knowledge.admin.logging.delete.status") %> <b><%= jspUtil.out("status") %></b>
        </div>
        <br/><br/>
        <div class="form-group">
        <label for="exampleInputName2">
            <%= jspUtil.label("knowledge.admin.logging.days") %>
        </label>
        </div>
        <input type="number" name="days" min="1" step="1" class="form-control" value="<%= jspUtil.out("days") %>"/>
        <button type="submit" name="control" value="enable" class="btn btn-primary"><%= jspUtil.label("label.enable") %></button>
        <button type="submit" name="control" value="disable" class="btn btn-warning"><%= jspUtil.label("label.disable") %></button>
    </form>
    
    <br/><br/>
    <h5 class="sub_title"><%= jspUtil.label("knowledge.admin.logging.files") %></h5>
    <div class="list-group">
        <c:forEach var="log" items="${logs}">
            <a class="list-group-item " href="<%= request.getContextPath() %>/admin.logging/download/<%= jspUtil.out("log.filename") %>" >
                <span class="badge"><%= jspUtil.out("log.size") %></span>
                <i class="fa fa-file-archive-o"></i>&nbsp;
                [<%= jspUtil.date("log.lastModified") %>]
                <%= jspUtil.out("log.filename") %>
            </a>
        </c:forEach>
    </div>

</c:param>

</c:import>

