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
<script type="text/javascript" src="bower/Caret.js/dist/jquery.caret.min.js"></script>
<script type="text/javascript" src="bower/At.js/dist/js/jquery.atwho.min.js"></script>
<script type="text/javascript" src="bower/picEdit/dist/js/picedit.min.js"></script>
<script type="text/javascript" src="bower/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="bower/bootstrap-datepicker/dist/locales/bootstrap-datepicker.en-GB.min.js"></script>
<script type="text/javascript" src="bower/bootstrap-datepicker/dist/locales/bootstrap-datepicker.ja.min.js"></script>
<script type="text/javascript" src="bower/clockpicker/dist/bootstrap-clockpicker.min.js"></script>
<script type="text/javascript" src="bower/moment/min/moment.min.js"></script>

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
<script type="text/javascript" src="js/knowledge-clipimage.js"></script>
<script type="text/javascript" src="js/emojilist.js"></script>
<script type="text/javascript" src="js/autocomplete.js"></script>

<script type="text/javascript" src="js/template.js"></script>
<script type="text/javascript" src="js/knowledge-edit-survey.js"></script>
<!-- endbuild -->

<script>
var _LANG = '<%= jspUtil.locale().getLanguage()%>';
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
var _IMAGE_UPLOAD = '<%= jspUtil.label("knowledge.edit.image.upload") %>';

<%-- テンプレート用 --%>
var LABEL_DELETE = '<%= jspUtil.label("knowledge.template.label.item.delete") %>';
var LABEL_TEXT_ITEM = '<i class="fa fa-pencil"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.text") %>';
var LABEL_RADIO_ITEM = '<i class="fa fa-dot-circle-o"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.radio") %>';
var LABEL_CHECKBOX_ITEM = '<i class="fa fa-check-square-o"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.checkbox") %>';
var LABEL_INTEGER_ITEM = '<i class="fa fa-calculator"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.integer") %>';
var LABEL_DATE_ITEM = '<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.date") %>';
var LABEL_TIME_ITEM = '<i class="fa fa-clock-o"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.time") %>';
var LABEL_TIMEZONE_ITEM = '<i class="fa fa-globe"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.timezone") %>';
var LABEL_ITEM_TITLE = '<%= jspUtil.label("knowledge.template.label.item.title") %>';
var LABEL_ITEM_DESCRIPTION = '<%= jspUtil.label("knowledge.template.label.item.description") %>';
var LABEL_ADD_CHOICE = '<%= jspUtil.label("knowledge.template.label.choice.add") %>';
var LABEL_DELETE_CHOICE = '<%= jspUtil.label("knowledge.template.label.choice.remove") %>';
var LABEL_CHOICE_LABEL = '<%= jspUtil.label("knowledge.template.label.choice.label") %>';
var LABEL_CHOICE_VALUE = '<%= jspUtil.label("knowledge.template.label.choice.value") %>';
<%-- テンプレート用(END) --%>


var _TAGS = [];
<c:forEach var="tagitem" items="${tagitems}" varStatus="status">
_TAGS.push('<%= jspUtil.out("tagitem.tagName") %>');
</c:forEach>

</script>
