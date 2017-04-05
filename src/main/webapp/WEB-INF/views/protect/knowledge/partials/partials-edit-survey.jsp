<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%-- Deprecated --%>

<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.web.logic.HttpRequestCheckLogic"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<div class="modal fade" id="editSurvey" tabindex="-1" role="dialog" aria-labelledby="editSurvey" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form action="<%= request.getContextPath()%>/protect.survey/save" method="post" role="form" id="surveyForm">
                <input type="hidden" name="<%= HttpRequestCheckLogic.REQ_ID_KEY %>"
                    value="<%= jspUtil.out(HttpRequestCheckLogic.REQ_ID_KEY) %>" />
                <input type="hidden" name="knowledgeId" value="<%= jspUtil.out("knowledgeId") %>" id="knowledgeIdSurvey" />

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
                    <span class="sr-only"><%= jspUtil.label("label.close") %></span></button>
                    <h4 class="modal-title" id="myModalLabel">
                        <%= jspUtil.label("knowledge.survey.label.edit") %>
                        <button type="button" class="btn btn-info" id="btnShowAnswer">
                            <i class="fa fa-check-square-o"></i>&nbsp;<%= jspUtil.label("knowledge.survey.label.answer") %>
                        </button>
                    </h4>
                </div>
                <div class="modal-body">
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
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" id="surveySavebutton">
                        <i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %>
                    </button>
                    <button type="button" class="btn btn-danger hide" id="deletebutton">
                        <i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        <i class="fa fa-close"></i>&nbsp;<%= jspUtil.label("label.close") %>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>








<div class="modal fade" id="modalAnswerSurvey" tabindex="-1" role="dialog" aria-labelledby="modalAnswerSurveyLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
                <span class="sr-only"><%= jspUtil.label("label.close") %></span></button>
                <h4 class="modal-title" id="modalAnswerSurveyLabel">
                    <%= jspUtil.label("knowledge.survey.label.answer") %>
                </h4>
            </div>
            <div class="modal-body">
                <h4 id="dataSurveyAnswerTitle"></h4>
                <div>
                    <%= jspUtil.label("knowledge.survey.label.answer.count") %>: <span id="dataSurveyAnswerCount"></span>
                </div>
                <div id="dataSurveyAnswerValues"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <i class="fa fa-close"></i>&nbsp;<%= jspUtil.label("label.close") %>
                </button>
            </div>
        </div>
    </div>
</div>









