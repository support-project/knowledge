<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<style>
.pull-right {
    margin-left: 5px;
}
</style>
</c:param>

<c:param name="PARAM_SCRIPTS">
<script>
function deleteConfig(hookId) {
    $('input:hidden[name=hook_id]').val(hookId);
    bootbox.confirm('<%= jspUtil.label("knowledge.webhook.confirm.delete") %>', function(result) {
        if (result) {
            var $form = $("#webhookForm");
            $form.attr('action', '<%= request.getContextPath()%>/admin.webhook/delete');
            $form.submit();
        }
    });
};

function testConfig(hookId) {
    $('input:hidden[name=hook_id]').val(hookId);
    bootbox.confirm('<%= jspUtil.label("knowledge.webhook.confirm.test") %>', function(result) {
        if (result) {
            var $form = $("#webhookForm");
            $form.attr('action', '<%= request.getContextPath()%>/admin.webhook/test');
            $form.submit();
        }
    });
};
</script>
</c:param>
<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.webhook.title") %></h4>
<div class="alert alert-info alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <%= jspUtil.label("knowledge.webhook.msg") %><br/>
</div>
<form action="<%= request.getContextPath()%>/admin.webhook/save" method="post" role="form"class="form-horizontal">
    <div class="form-group">
        <label for="url" class="col-sm-2 control-label"><%= jspUtil.label("label.url") %></label>
        <div class="col-sm-8">
            <input type="text" class="form-control" name="url" id="url" placeholder="http://example.jp/post.json" value="<%= jspUtil.out("webHookUrl") %>" />
        </div>
    </div>
    <div class="form-group">
        <label for="knowledges" class="col-sm-2 control-label"><%= jspUtil.label("label.hook") %></label>
        <div class="col-sm-8">
            <div>
                <input type="checkbox" class="pull-left" name="hooks[]" id="knowledges" value="knowledges" checked/>
                <div class="prepend-left-20">
                    <label for="knowledges" class="list-label"><strong><%= jspUtil.label("knowledge.webhook.event.knowledge.title") %></strong></label>
                    <p class="light"><%= jspUtil.label("knowledge.webhook.event.knowledge.description") %></p>
                </div>
            </div>
            <div>
                <input type="checkbox" class="pull-left" name="hooks[]" id="comments" value="comments" />
                <div class="prepend-left-20">
                    <label for="comments" class="list-label"><strong><%= jspUtil.label("knowledge.webhook.event.comment.title") %></strong></label>
                    <p class="light"><%= jspUtil.label("knowledge.webhook.event.comment.description") %></p>
                </div>
            </div>
            <div>
                <input type="checkbox" class="pull-left" name="hooks[]" id="liked_knowledge" value="liked_knowledge" />
                <div class="prepend-left-20">
                    <label for="comments" class="list-label"><strong><%= jspUtil.label("knowledge.webhook.event.liked.knowledge.title") %></strong></label>
                    <p class="light"><%= jspUtil.label("knowledge.webhook.event.liked.knowledge.description") %></p>
                </div>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary"><i class="fa fa-add"></i>&nbsp;<%= jspUtil.label("label.add") %></button>
        </div>
    </div>
</form>
<div class="panel panel-default">
    <div class="panel-heading"><%= jspUtil.label("knowledge.webhook.list.title") %>(<%= jspUtil.out("webhookConfigsEntitiesCount") %>)</div>
    <ul class="list-group">
        <c:forEach var="webhookConfigsEntity" items="${webhookConfigsEntities}">
        <li class="list-group-item">
            <div class="pull-right">
                <a class="btn btn-danger pull-right" href="javascript:void(0);" onclick="deleteConfig(<%= jspUtil.out("webhookConfigsEntity.hookId") %>)"><%= jspUtil.label("label.delete") %></a>
                <a class="btn btn-default pull-right" href="javascript:void(0);" onclick="testConfig(<%= jspUtil.out("webhookConfigsEntity.hookId") %>)"><%= jspUtil.label("knowledge.webhook.test") %></a>
            </div>
            <div class="clearfix">
                <span class="h4" style="font-family: monospace;"><%= jspUtil.out("webhookConfigsEntity.url") %></span>
            </div>
            <p><span class="label label-default h5"><%= jspUtil.out("webhookConfigsEntity.eventName()") %></span></p>
        </li>
        </c:forEach>
    </ul>
</div>
<form id="webhookForm" method="post">
    <input type="hidden" name="hook_id" value="">
</form>
</c:param>
</c:import>