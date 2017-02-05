<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<script type="text/x-mathjax-config">
MathJax.Hub.Config({
    tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]},
    skipStartupTypeset: true
  });
</script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML,Safe"></script>
<script type="text/javascript" src="bower/emoji-parser/main.min.js"></script>

<!-- build:js(src/main/webapp) js/page-knowledge-edit.js -->
<script type="text/javascript" src="bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="bower/bootstrap3-typeahead/bootstrap3-typeahead.min.js"></script>
<script type="text/javascript" src="bower/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="bower/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="bower/jquery-file-upload/js/jquery.iframe-transport.js"></script>

<script type="text/javascript" src="js/tagselect.js"></script>
<script type="text/javascript" src="js/slide.js"></script>
<script type="text/javascript" src="js/knowledge-common.js"></script>
<script type="text/javascript" src="js/knowledge-edit.js"></script>
<script type="text/javascript" src="js/knowledge-attachfile.js"></script>
<script type="text/javascript" src="js/knowledge-emoji-select.js"></script>
<script type="text/javascript" src="js/knowledge-target-select.js"></script>
<script type="text/javascript" src="js/knowledge-preview.js"></script>
<script type="text/javascript" src="js/knowledge-tag-select.js"></script>
<script type="text/javascript" src="js/knowledge-template.js"></script>
<script type="text/javascript" src="js/paste_image.js"></script>
<!-- endbuild -->

<script>
var _UPLOADED = '<%= jspUtil.label("knowledge.edit.label.uploaded") %>';
var _DELETE_LABEL= '<%= jspUtil.label("label.delete") %>';
var _FAIL_UPLOAD = '<%= jspUtil.label("knowledge.edit.label.fail.upload") %>';
var _REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.delete.upload") %>';
var _FAIL_REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.fail.delete.upload") %>';
var _CONFIRM = '<%= jspUtil.label("knowledge.edit.label.confirm.delete") %>';
var _SET_IMAGE_LABEL= '<%= jspUtil.label("knowledge.edit.set.image.path") %>';
var _SET_SLIDE_LABEL= '<%= jspUtil.label("knowledge.edit.set.slide.path") %>';
var _LABEL_UPDATE = '<%= jspUtil.label("label.update") %>';
var _UPDATE_TITLE = '<%= jspUtil.label("knowledge.edit.title") %>';

var _TAGS = [];
<c:forEach var="tagitem" items="${tagitems}" varStatus="status">
_TAGS.push('<%= jspUtil.out("tagitem.tagName") %>');
</c:forEach>

</script>
