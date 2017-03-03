<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/page-template.css -->
<link rel="stylesheet" href="css/template.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-template.js -->
<script type="text/javascript" src="js/template.js"></script>
<!-- endbuild -->
<script>
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
var LABEL_UPDATE = '<%= jspUtil.label("label.update") %>';
</script>
<script>
function deleteTemplate() {
    bootbox.confirm("Are you sure delete this data?", function(result) {
        if (result) {
            $('#templateForm').attr('action', '<%= request.getContextPath()%>/admin.template/delete');
            $('#templateForm').submit();
        }
    }); 
};
</script>
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.template.add.title") %></h4>

<form action="<%= request.getContextPath()%>/admin.template/create" method="post" role="form" id="templateForm">
    <div class="alert alert-info alert-dismissible hide" role="alert" id="editableMsg">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong>Information</strong><br/>
        - <%= jspUtil.label("knowledge.template.label.not.editable") %>
    </div>


    <div class="form-group">
        <label for="typeName"><%= jspUtil.label("knowledge.template.label.name") %></label>
        <input type="text" class="form-control" name="typeName" id="typeName" placeholder="Name" value="<%= jspUtil.out("typeName") %>" />
    </div>
    <div class="form-group">
        <label for="typeIcon"><%= jspUtil.label("knowledge.template.label.icon") %><%= jspUtil.label("knowledge.template.label.icon.msg") %></label>
        <input type="text" class="form-control" name="typeIcon" id="typeIcon" placeholder="Icon" value="<%= jspUtil.out("typeIcon") %>" />
    </div>
    <div class="form-group">
        <label for="description"><%= jspUtil.label("knowledge.template.label.description") %></label>
        <textarea class="form-control" name="description" id="description" placeholder="Description" ><%= jspUtil.out("description") %></textarea>
    </div>
    
    <h5><b><%= jspUtil.label("knowledge.template.label.item") %></b></h5>
    <div class="form-group">
        <a class="btn btn-info hide editbtn" id="addText"><i class="fa fa-pencil"></i>
            &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.text")) %>
        </a>
        <a class="btn btn-info hide editbtn" id="addRadio"><i class="fa fa-dot-circle-o"></i>
            &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.radio")) %>
        </a>
        <a class="btn btn-info hide editbtn" id="addCheckbox"><i class="fa fa-check-square-o"></i>
            &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.checkbox")) %>
        </a>
        <a class="btn btn-info hide editbtn" id="addInteger"><i class="fa fa-calculator"></i>
            &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.integer")) %>
        </a>
        <a class="btn btn-info hide editbtn" id="addDate"><i class="fa fa-calendar"></i>
            &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.date")) %>
        </a>
        <a class="btn btn-info hide editbtn" id="addTime"><i class="fa fa-clock-o"></i>
            &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.time")) %>
        </a>
        <a class="btn btn-info hide editbtn" id="addTimezone"><i class="fa fa-globe"></i>
            &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.timezone")) %>
        </a>
    </div>
    <div id="items"></div>
    
    <input type="hidden" name="typeId" value="<%= jspUtil.out("id") %>" id="typeId"/>
    
    <button type="submit" class="btn btn-primary" id="savebutton"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
    <% String hide = "hide"; %>
    <c:if test="${id != -1}">
        <% hide = ""; %>
    </c:if>
    <button type="button" class="btn btn-danger <%= hide %>" id="deletebutton"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
    <a href="<%= request.getContextPath() %>/admin.template/list/<%= jspUtil.out("offset") %>"
        class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>
    
</form>

</c:param>

</c:import>

