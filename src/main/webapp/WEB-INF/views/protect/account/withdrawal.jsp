<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.logic.HttpRequestCheckLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>
<c:param name="PARAM_SCRIPTS">
<script>
var withdrawalFromKnowledge = function() {
    bootbox.confirm('<%= jspUtil.label("knowledge.withdrawal.label.confirm")%>', function(result) {
        if (result) {
            $('#withdrawalForm').attr('action', '<%= request.getContextPath()%>/protect.account/delete');
            $('#withdrawalForm').submit();
        }
    }); 
};

</script>

</c:param>


<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.withdrawal.title") %></h4>

<form action="<%= request.getContextPath()%>/protect.account" method="post" role="form" id="withdrawalForm">
    <input type="hidden" name="<%= HttpRequestCheckLogic.REQ_ID_KEY %>"
        value="<%= jspUtil.out(HttpRequestCheckLogic.REQ_ID_KEY) %>" />
    
    <div class="form-group">
    <%= jspUtil.label("knowledge.withdrawal.msg") %>
    </div>
    
    <div class="form-group">
        <label class="radio-inline">
            <input type="radio" value="1" name="knowledge_remove" checked="checked" />
            <i class="fa fa-eraser"></i>&nbsp;<%= jspUtil.label("knowledge.withdrawal.label.remove") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="2" name="knowledge_remove"/>
            <i class="fa fa-gift"></i>&nbsp;<%= jspUtil.label("knowledge.withdrawal.label.leave") %>
        </label>
    </div>
    
    <div class="form-group">
    <button type="button" class="btn btn-danger" onclick="withdrawalFromKnowledge();">
    <i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("knowledge.withdrawal.label.bottun") %></button>
    <a href="<%= request.getContextPath() %>/protect.account"
    class="btn btn-warning" role="button"><i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.cancel") %></a>
    </div>
    
</form>


</c:param>

</c:import>

