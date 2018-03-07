<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.web.logic.HttpRequestCheckLogic"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<div class="modal fade" id="modalAnswerSurvey" tabindex="-1" role="dialog" aria-labelledby="modalAnswerSurveyLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <form action="<%=request.getContextPath()%>/open.survey/answer" method="post" role="form" id="answerForm">
            <input type="hidden" name="<%= HttpRequestCheckLogic.REQ_ID_KEY %>"
                value="<%= jspUtil.out(HttpRequestCheckLogic.REQ_ID_KEY) %>" />
            <input type="hidden" name="knowledgeId" value="<%= jspUtil.out("knowledgeId") %>" />
            
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="modalAnswerSurveyLabel">
                </h4>
            </div>
            <div class="modal-body">
                <div id="surveyDescription"></div>
                <div class="form-group" id="template_items">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <i class="fa fa-close"></i>&nbsp;<%= jspUtil.label("label.close") %></button>
                <button type="button" class="btn btn-primary" id="saveSurveyButton">
                    <i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
            </div>
            <input type="hidden" name="answerId" id="answerId" value="" />
            </form>
        </div>
    </div>
</div>

