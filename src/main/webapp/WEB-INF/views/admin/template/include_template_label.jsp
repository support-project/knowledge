<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="org.support.project.web.util.JspUtil"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<script>
var LABEL_DELETE = '<%= jspUtil.label("knowledge.template.label.item.delete") %>';
var LABEL_UPDATE = '<%= jspUtil.label("label.update") %>';
var LABEL_TEXT_ITEM = '<i class="fa fa-pencil"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.text") %>';
var LABEL_TEXTAREA_ITEM = '<i class="fa fa-pencil-square-o"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.textarea") %>';
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

var LABEL_MOVE_UP = '<%= jspUtil.label("label.move.up") %>';
var LABEL_MOVE_DOWN = '<%= jspUtil.label("label.move.down") %>';

</script>

