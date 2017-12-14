<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!DOCTYPE html>
<html>
<head>

<c:import url="/WEB-INF/views/commons/layout/commonHeader.jsp" />

</head>

<body class="container">
<br/>

<div class="jumbotron">
  <h1>
  <i class="fa fa-spin fa-1x fa-cog" aria-hidden="true"></i>
  </h1>
  <p>
  Site is down for maintenance
  </p>
</div>

<hr/>

<% if(jspUtil.logined()) { %>
    <% if (jspUtil.isAdmin()) { %>
        <a href="<%= request.getContextPath() %>/protect.migrate">
            <i class="fa fa-cog" aria-hidden="true"></i><%= jspUtil.label("knowledge.maintenance.do.migrate") %>
        </a><br/>
    <% } else { %>
    <% } %>
    <a href="<%= request.getContextPath() %>/signout">
        <i class="fa fa-sign-out"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.signout") %>
    </a>
<% } else { %>
    <a href="<%= request.getContextPath() %>/protect.migrate">
        <i class="fa fa-cog" aria-hidden="true"></i><%= jspUtil.label("knowledge.maintenance.do.migrate.no.login") %>
    </a>
<% } %>


<!-- build:js(src/main/webapp) js/page-maintenance.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bluebird/js/browser/bluebird.min.js"></script>
<!-- endbuild -->

</body>

</html>