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
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/template.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-template.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/js/template-item-edit.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/admin-template-edit.js"></script>
<!-- endbuild -->
<jsp:include page="include_template_label.jsp"></jsp:include>
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
    <div class="form-group">
        <label for="initialValue"><%= jspUtil.label("knowledge.template.label.initial.value") %></label>
        <textarea class="form-control" name="initialValue" id="initialValue" placeholder="Initia lValue" ><%= jspUtil.out("initialValue") %></textarea>
    </div>
    
    <h5><b><%= jspUtil.label("knowledge.template.label.item") %></b></h5>
    <div class="form-group">
        <a class="btn btn-info hide editbtn" id="addText"><i class="fa fa-pencil"></i>
            &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.text")) %>
        </a>
        <a class="btn btn-info hide editbtn" id="addTextArea"><i class="fa fa-pencil-square-o"></i>
            &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.textarea")) %>
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
    <button type="button" class="btn btn-danger hide" id="deletebutton"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
    <a href="<%= request.getContextPath() %>/admin.template/list/<%= jspUtil.out("offset") %>"
        class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>
    
</form>

</c:param>

</c:import>

