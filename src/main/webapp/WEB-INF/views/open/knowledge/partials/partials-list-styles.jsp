<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!-- build:css(src/main/webapp) css/vendor-knowledge-list.css -->
<link rel="stylesheet" href="bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" />
<!-- endbuild -->

<!-- build:css(src/main/webapp) css/app-knowledge-list.css -->
<link rel="stylesheet" href="css/knowledge-list.css" />
<!-- endbuild -->

