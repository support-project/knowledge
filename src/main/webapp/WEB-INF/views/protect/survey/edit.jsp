<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.web.logic.HttpRequestCheckLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/page-survey-edit.css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/template.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-survey-edit.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/js/template-item-edit.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/survey-edit.js"></script>
<!-- endbuild -->

<%-- テンプレート用 --%>
<jsp:include page="/WEB-INF/views/admin/template/include_template_label.jsp"></jsp:include>
<%-- テンプレート用(END) --%>

<script>
var _MSG_SUVEY_NOT_FOUND = '<%= jspUtil.label("knowledge.survey.msg.survey.notfound") %>';
var _MSG_CONFIRM_COPY = '<%= jspUtil.label("knowledge.survey.msg.copy.confirm") %>';
</script>

</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.survey.label.edit") %></h4>

<div class="alert alert-warning alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <strong>Information</strong><br/>
    - <%= jspUtil.label("knowledge.survey.msg.warning.move") %>
</div>

<form action="<%= request.getContextPath()%>/protect.survey/save" method="post" role="form" id="surveyForm">
    <input type="hidden" name="<%= HttpRequestCheckLogic.REQ_ID_KEY %>"
        value="<%= jspUtil.out(HttpRequestCheckLogic.REQ_ID_KEY) %>" />
    <input type="hidden" name="knowledgeId" value="<%= jspUtil.out("knowledgeId") %>" id="knowledgeId" />
    
    <div class="form-group">
        <a id="btnCopySurvey" class="btn btn-success btn_col2" data-toggle="modal" href="#modalCopySurvey">
            <i class="fa fa-copy"></i>&nbsp;
            <%=jspUtil.label("knowledge.survey.label.copy")%>
        </a>
    </div>


    <div id="survey_info" class="">
        <%= jspUtil.label("knowledge.survey.msg.survey") %>
    </div>
    <div id="survey_edit" class="hide">
        <div class="form-group">
            <label for="typeName"><%= jspUtil.label("knowledge.template.label.name") %></label>
            <input type="text" class="form-control" name="typeName" id="typeName" placeholder="Name" value="<%= jspUtil.out("typeName") %>" />
        </div>
        <div class="form-group">
            <label for="description"><%= jspUtil.label("knowledge.template.label.description") %></label>
            <textarea class="form-control" name="description" id="description" placeholder="Description" ><%= jspUtil.out("description") %></textarea>
        </div>
        
        <h5><b><%= jspUtil.label("knowledge.template.label.item") %></b></h5>
        <div class="form-group">
            <a class="btn btn-info editbtn" id="addText"><i class="fa fa-pencil"></i>
                &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.text")) %>
            </a>
            <a class="btn btn-info editbtn" id="addTextArea"><i class="fa fa-pencil-square-o"></i>
                &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.textarea")) %>
            </a>
            <a class="btn btn-info editbtn" id="addRadio"><i class="fa fa-dot-circle-o"></i>
                &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.radio")) %>
            </a>
            <a class="btn btn-info editbtn" id="addCheckbox"><i class="fa fa-check-square-o"></i>
                &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.checkbox")) %>
            </a>
            <a class="btn btn-info editbtn" id="addInteger"><i class="fa fa-calculator"></i>
                &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.integer")) %>
            </a>
            <a class="btn btn-info editbtn" id="addDate"><i class="fa fa-calendar"></i>
                &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.date")) %>
            </a>
            <a class="btn btn-info editbtn" id="addTime"><i class="fa fa-clock-o"></i>
                &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.time")) %>
            </a>
            <a class="btn btn-info editbtn" id="addTimezone"><i class="fa fa-globe"></i>
                &nbsp;<%= jspUtil.label("knowledge.template.label.item.add", jspUtil.label("knowledge.template.label.item.timezone")) %>
            </a>
        </div>
        <div id="items" class="form-horizontal"></div>
    </div>
        
    <div class="modal-footer">
        <button type="submit" class="btn btn-primary" id="surveySavebutton">
            <i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %>
        </button>
        <button type="button" class="btn btn-danger hide" id="deletebutton">
            <i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
        </button>
        <a href="<%= request.getContextPath() %>/open.knowledge/view/<%= jspUtil.out("knowledgeId") %>"
            class="btn btn-warning" role="button"><i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.back") %>
        </a>
    </div>
</form>


<!-- Copy dialog -->
<div class="modal fade" id="modalCopySurvey" tabindex="-1" role="dialog" aria-labelledby="modalCopySurvey">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title"><%=jspUtil.label("knowledge.survey.label.copy")%></h4>
            </div>
            <div class="modal-body">
                <div>
                    <%=jspUtil.label("knowledge.survey.msg.copy.select")%>
                </div>
                
                <div class="form-inline form-group text-right">
                    <%=jspUtil.label("knowledge.survey.label.search")%>:
                    <input type="number" class="form-control" id="searchId" placeholder="Id" value="" />
                </div>
                <hr/>
                <div id="surveyList">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <i class="fa fa-close"></i>&nbsp;<%= jspUtil.label("label.close") %></button>
            </div>
        </div>
    </div>
</div>


</c:param>

</c:import>



