<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/echojs/dist/echo.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/emoji-parser/main.min.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.iframe-transport.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/clipboard/dist/clipboard.min.js"></script>

<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/slide.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-common.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-view.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-view-markdown.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-view-template.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-view-comment.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-view-emoji-select.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-view-preview.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-view-attachfile.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-view-stock.js") %>"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-view-toc.js") %>"></script>


<script>
var LABEL_LIKE = '<%= jspUtil.label("knowledge.view.like") %>';

var _UPLOADED = '<%= jspUtil.label("knowledge.edit.label.uploaded") %>';
var _DELETE_LABEL= '<%= jspUtil.label("label.delete") %>';
var _FAIL_UPLOAD = '<%= jspUtil.label("knowledge.edit.label.fail.upload") %>';
var _REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.delete.upload") %>';
var _FAIL_REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.fail.delete.upload") %>';
var _CONFIRM = '<%= jspUtil.label("knowledge.edit.label.confirm.delete") %>';
var _SET_IMAGE_LABEL= '<%= jspUtil.label("knowledge.edit.set.image.path") %>';
var _SET_SLIDE_LABEL= '<%= jspUtil.label("knowledge.edit.set.slide.path") %>';
var _MSG_TOC_EMPTY = '<%= jspUtil.label("knowledge.view.msg.toc.empty") %>';
var _MSG_COPIED = '<%= jspUtil.label("knowledge.view.msg.url.copy") %>';
</script>
