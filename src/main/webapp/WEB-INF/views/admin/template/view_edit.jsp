<%@page import="org.support.project.knowledge.logic.TemplateLogic"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
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
var LABEL_ITEM_TITLE = '<%= jspUtil.label("knowledge.template.label.item.title") %>';
var LABEL_ITEM_DESCRIPTION = '<%= jspUtil.label("knowledge.template.label.item.description") %>';
var LABEL_ADD_CHOICE = '<%= jspUtil.label("knowledge.template.label.choice.add") %>';
var LABEL_DELETE_CHOICE = '<%= jspUtil.label("knowledge.template.label.choice.remove") %>';
var LABEL_CHOICE_LABEL = '<%= jspUtil.label("knowledge.template.label.choice.label") %>';
var LABEL_CHOICE_VALUE = '<%= jspUtil.label("knowledge.template.label.choice.value") %>';
var LABEL_UPDATE = '<%= jspUtil.label("label.update") %>';
</script>
<script>
function deleteUser() {
    bootbox.confirm("本当に削除しますか?", function(result) {
        if (result) {
            $('#templateForm').attr('action', '<%= request.getContextPath()%>/admin.template/delete');
            $('#templateForm').submit();
        }
    }); 
};
</script>
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.template.edit.title") %></h4>

<form action="<%= request.getContextPath()%>/admin.template/update" method="post" role="form" id="templateForm">
    <c:if test="${!editable}">
    <div class="alert alert-info alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong>Information</strong><br/>
        - <%= jspUtil.label("knowledge.template.label.not.editable") %>
    </div>
    
    </c:if>

    <div class="form-group">
        <label for="typeName"><%= jspUtil.label("knowledge.template.label.name") %></label>
        <input type="text" class="form-control" name="typeName" id="typeName" placeholder="Name" value="<%= jspUtil.out("typeName") %>" />
    </div>
    <div class="form-group">
        <label for="typeIcon"><%= jspUtil.label("knowledge.template.label.icon") %></label>
        <input type="text" class="form-control" name="typeIcon" id="typeIcon" placeholder="Icon" value="<%= jspUtil.out("typeIcon") %>" />
    </div>
    <div class="form-group">
        <label for="description"><%= jspUtil.label("knowledge.template.label.description") %></label>
        <textarea class="form-control" name="description" id="description" placeholder="Description" ><%= jspUtil.out("description") %></textarea>
    </div>
    
    <h5><b><%= jspUtil.label("knowledge.template.label.item") %></b></h5>
    <% if(jspUtil.is(Boolean.TRUE, "editable")) { %>
    <div class="form-group">
        <a class="btn btn-info" id="addText"><i class="fa fa-pencil"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.text.add") %></a>
        <a class="btn btn-info" id="addRadio"><i class="fa fa-dot-circle-o"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.radio.add") %></a>
        <a class="btn btn-info" id="addCheckbox"><i class="fa fa-check-square-o"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.checkbox.add") %></a>
    </div>
    <% } %>
    <div id="items">
    <c:forEach var="item" items="${items}" varStatus="status">
        <div id="item<%= jspUtil.out("item.itemNo") %>" class="add_item">
            <h5 class="item_title">
            <% if (jspUtil.is(String.valueOf(TemplateLogic.ITEM_TYPE_TEXT), "item.itemType")) { %>
                <i class="fa fa-pencil"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.text") %>
                <button type="button" class="btn btn-warning" onclick="deleteItem('item<%= jspUtil.out("item.itemNo") %>');" >
                <i class="fa fa-minus-square"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.delete") %>
                </button>
            <% } else if (jspUtil.is(String.valueOf(TemplateLogic.ITEM_TYPE_RADIO), "item.itemType")) { %>
                <i class="fa fa-dot-circle-o"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.radio") %>
                <button type="button" class="btn btn-warning" onclick="deleteItem('item<%= jspUtil.out("item.itemNo") %>');" >
                <i class="fa fa-minus-square"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.delete") %>
                </button>&nbsp;
                
                <button type="button" class="btn btn-success" onclick="addChoice('item<%= jspUtil.out("item.itemNo") %>');" >
                <i class="fa fa-plus-circle"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.choice.add") %>
                </button>&nbsp;
                <button type="button" class="btn btn-success" onclick="deleteChoice('item<%= jspUtil.out("item.itemNo") %>');" >
                <i class="fa fa-minus-circle"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.choice.remove") %>
                </button>
            <% } else if (jspUtil.is(String.valueOf(TemplateLogic.ITEM_TYPE_CHECKBOX), "item.itemType")) { %>
                <i class="fa fa-check-square-o"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.checkbox") %>
                <button type="button" class="btn btn-warning" onclick="deleteItem('item<%= jspUtil.out("item.itemNo") %>');" >
                <i class="fa fa-minus-square"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.item.delete") %>
                </button>&nbsp;
                
                <button type="button" class="btn btn-success" onclick="addChoice('item<%= jspUtil.out("item.itemNo") %>');" >
                <i class="fa fa-plus-circle"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.choice.add") %>
                </button>&nbsp;
                <button type="button" class="btn btn-success" onclick="deleteChoice('item<%= jspUtil.out("item.itemNo") %>');" >
                <i class="fa fa-minus-circle"></i>&nbsp;<%= jspUtil.label("knowledge.template.label.choice.remove") %>
                </button>
            <% } %>
            
            <input type="hidden" name="itemType" value="<%= jspUtil.out("item.itemTypeText") %>_item<%= jspUtil.out("item.itemNo") %>"/>
            </h5>
            <div class="form-group">
            <label for="">
            <%= jspUtil.label("knowledge.template.label.item.title") %></label>
            <input type="text" class="form-control" name="title_item<%= jspUtil.out("item.itemNo") %>" id="title_item<%= jspUtil.out("item.itemNo") %>"
            value="<%= jspUtil.out("item.itemName") %>" />
            </div>
            <div class="form-group">
            <label for=""><%= jspUtil.label("knowledge.template.label.item.description") %></label>
            <input type="text" class="form-control" name="description_item<%= jspUtil.out("item.itemNo") %>" id="description_item<%= jspUtil.out("item.itemNo") %>"
            value="<%= jspUtil.out("item.description") %>" />
            </div>
            
            <% if (
                    jspUtil.is(String.valueOf(TemplateLogic.ITEM_TYPE_RADIO), "item.itemType")
                    || jspUtil.is(String.valueOf(TemplateLogic.ITEM_TYPE_CHECKBOX), "item.itemType")
            ) { %>
            <div id="ch_item<%= jspUtil.out("item.itemNo") %>" class="choice_item_list">
<c:forEach var="choice" items="${item.choices}" varStatus="status">
                <div class="form-group choice_item choice_item_top">
                <label for=""><%= jspUtil.label("knowledge.template.label.choice.label") %></label>
                <input type="text" class="form-control" name="label_item<%= jspUtil.out("item.itemNo") %>"
                    id="label_item<%= jspUtil.out("item.itemNo") %>_<%= jspUtil.out("choice.choiceNo") %>"
                    value="<%= jspUtil.out("choice.choiceLabel") %>"/>
                </div>
                
                <div class="form-group choice_item_bottom">
                <label for=""><%= jspUtil.label("knowledge.template.label.choice.value") %></label>
                <input type="text" class="form-control" name="value_item<%= jspUtil.out("item.itemNo") %>"
                    id="value_item<%= jspUtil.out("item.itemNo") %>_<%= jspUtil.out("choice.choiceNo") %>"
                    value="<%= jspUtil.out("choice.choiceLabel") %>"/>
                </div>
</c:forEach>
            </div>
            <% } %>
        </div>
    </c:forEach>
    </div>
    
    <input type="hidden" name="typeId" value="<%= jspUtil.out("typeId") %>" id="typeId"/>
    
    <button type="submit" class="btn btn-primary" id="savebutton"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.update") %></button>
    <c:if test="${editable}">
    <button type="button" class="btn btn-danger" onclick="deleteUser();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
    </c:if>
    <a href="<%= request.getContextPath() %>/admin.template/list/<%= jspUtil.out("offset") %>"
        class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>
    
</form>

</c:param>

</c:import>

