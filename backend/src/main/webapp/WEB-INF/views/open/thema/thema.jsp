<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />

<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootswatch/<%= jspUtil.out("thema") %>/bootstrap.min.css" />

<style>
body {
    padding-top: 70px;
    overflow: hidden;
}
</style>

</head>

<body>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
                data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <a class="navbar-brand" style="cursor: pointer;">
                <i class="fa fa-book"></i>&nbsp;<%=jspUtil.label("knowledge.navbar.title") %>
            </a>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>

<div class="container text-center">

<h3>
<%= jspUtil.out("title") %>
</h3>

<button class="btn btn-default">
default
</button>
<button class="btn btn-primary">
primary
</button>
<button class="btn btn-success">
success
</button>
<button class="btn btn-info">
info
</button>
<button class="btn btn-warning">
warning
</button>
<button class="btn btn-danger">
danger
</button>


</div>


</body>
</html>

