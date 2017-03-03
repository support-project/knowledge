<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<!-- build:css(src/main/webapp) css/page-knowledge-edit.css -->
<link rel="stylesheet" href="bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" />
<link rel="stylesheet" href="bower/jquery-file-upload/css/jquery.fileupload.css" />
<link rel="stylesheet" href="bower/jquery-file-upload/css/jquery.fileupload-ui.css" />
<link rel="stylesheet" href="bower/At.js/dist/css/jquery.atwho.min.css" />
<link rel="stylesheet" href="bower/picEdit/dist/css/picedit.min.css" />
<link rel="stylesheet" href="bower/bootstrap-datepicker/dist/css/bootstrap-datepicker3.min.css" />
<link rel="stylesheet" href="bower/clockpicker/dist/bootstrap-clockpicker.min.css" />

<link rel="stylesheet" href="css/knowledge-edit.css" />
<link rel="stylesheet" href="css/markdown.css" />
<link rel="stylesheet" href="css/slide.css" />
<!-- endbuild -->

<style>
<%-- width の上書き --%>
.picedit_box {
    display: inline-block;
    position: relative;
    border: 1px solid #ddd;
    font-family: "Myriad Pro", Arial, Helvetica, sans-serif;
    font-size: 1rem;
    width: 250px; 
    height: 200px;
    color: #aaa;
    overflow: hidden;
}
</style>
