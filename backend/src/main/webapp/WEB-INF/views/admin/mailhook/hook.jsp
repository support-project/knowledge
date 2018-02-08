<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/page-mailhook.css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-mailhook.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/mailhook.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/targetselect.js"></script>
<!-- endbuild -->

<script>
function deleteMail() {
    bootbox.confirm('<%= jspUtil.label("message.confirm.delete") %>', function(result) {
        if (result) {
            $('#mailForm').attr('action', '<%= request.getContextPath()%>/admin.mailhook/deleteHook');
            $('#mailForm').submit();
        }
    }); 
};

<c:forEach var="viewer" items="${viewers}" varStatus="status">
selectedGroups.push({label: '<%= jspUtil.out("viewer.label") %>', value: '<%= jspUtil.out("viewer.value") %>'});
</c:forEach>
<c:forEach var="editor" items="${editors}" varStatus="status">
selectedEditors.push({label: '<%= jspUtil.out("editor.label") %>', value: '<%= jspUtil.out("editor.value") %>'});
</c:forEach>

</script>
</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.admin.mailhook.condition") %></h4>

<form action="<%= request.getContextPath()%>/admin.mailhook/hook" method="post" role="form" id="mailForm">
    
    <input type="hidden" name="hookId" value="1" />
    <input type="hidden" name="conditionNo" value="<%= jspUtil.out("conditionNo") %>" id="conditionNo" />
    
    <div class="form-group">
        <label for="host"><%= jspUtil.label("knowledge.admin.mailhook.condition.kind") %></label>
        <select class="form-control" name="conditionKind">
            <option value="1" <% if (jspUtil.is("1", "conditionKind")) { %>selected="selected" <% } %>>
                <%= jspUtil.label("knowledge.admin.mailhook.condition.kind.1") %>
            </option>
            <option value="2" <% if (jspUtil.is("2", "conditionKind")) { %>selected="selected" <% } %>>
                <%= jspUtil.label("knowledge.admin.mailhook.condition.kind.2") %>
            </option>
        </select>
    </div>
    
    <div class="form-group">
        <label for="host"><%= jspUtil.label("knowledge.admin.mailhook.condition.character") %></label>
        <input type="text" class="form-control" name="condition" id="condition"
            placeholder="<%= jspUtil.label("knowledge.admin.mailhook.condition.character") %>" value="<%= jspUtil.out("condition") %>" />
    </div>
    
    <!-- tags -->
    <div class="form-group">
        <label for="input_tag">
            <%= jspUtil.label("knowledge.add.label.tags") %>
        </label>
        <p class="tags">
        <input type="text" class="form-control" name="tags" id="input_tags" data-role="tags input"
            placeholder="<%= jspUtil.label("knowledge.add.label.tags") %>" value="<%= jspUtil.out("tags") %>" />
        </p>
    </div>
    
    <!-- view targets -->
    <div class="form-group">
        <label for="input_content"><%= jspUtil.label("knowledge.add.label.public.class") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="<%= KnowledgeLogic.PUBLIC_FLAG_PUBLIC %>" name="publicFlag" 
                id="publicFlag_piblic" <%= jspUtil.checked(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "publicFlag", true) %>/>
            <i class="fa fa-globe"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.public.class.public") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="<%= KnowledgeLogic.PUBLIC_FLAG_PRIVATE %>" name="publicFlag" 
                id="publicFlag_private" <%= jspUtil.checked(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "publicFlag") %>/>
            <i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.public.class.private") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="<%= KnowledgeLogic.PUBLIC_FLAG_PROTECT %>" name="publicFlag" 
                id="publicFlag_protect" <%= jspUtil.checked(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT), "publicFlag") %>/>
            <i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.public.class.protect") %>
        </label>
    </div>
    
    <div class="form-group" id="grops_area" <%= jspUtil.isnot(KnowledgeLogic.PUBLIC_FLAG_PROTECT, "publicFlag", "style=\"display: none;\"") %>>
        <label for="input_groups"><%= jspUtil.label("knowledge.add.label.destination") %></label>
        <a id="groupselect" class="btn btn-primary btn-xs" data-toggle="modal" href="#groupSelectModal">
            <i class="fa fa-th-list"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.destination.select") %>
        </a>
        <p>
            <input type="hidden" name="viewers" id="groups" value="">
            <span id="groupsLabel"></span>
        </p>
    </div>
    
    <div class="form-group">
        <label for="input_content"><%= jspUtil.label("knowledge.admin.mailhook.condition.process.user.kind") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="1" name="processUserKind" 
                id="processUserKind1" <%= jspUtil.checked("1", "processUserKind", true) %>/>
            <i class="fa fa-mail"></i>&nbsp;<%= jspUtil.label("knowledge.admin.mailhook.condition.process.user.kind.1") %>
        </label>
        <br/>
        <label class="radio-inline">
            <input type="radio" value="2" name="processUserKind" 
                id="processUserKind2" <%= jspUtil.checked("2", "processUserKind") %>/>
            <i class="fa fa-user"></i>&nbsp;<%= jspUtil.label("knowledge.admin.mailhook.condition.process.user.kind.2") %>
        </label>
    </div>
    
    <div class="form-group">
        <label for="host"><%= jspUtil.label("knowledge.admin.mailhook.condition.process.user") %></label>
        <input type="text" class="form-control" name="processUser" id="processUser"
            placeholder="<%= jspUtil.label("knowledge.admin.mailhook.condition.process.user") %>" value="<%= jspUtil.out("processUser") %>" />
        <span id="helpBlock" class="help-block">
        <%= jspUtil.label("knowledge.admin.mailhook.condition.info.user.1") %><br/>
        <%= jspUtil.label("knowledge.admin.mailhook.condition.info.user.2") %>
        </span>
    </div>
    
    <!-- editors -->
    <div class="form-group" id="editor_area">
        <label for="input_groups"><%= jspUtil.label("knowledge.add.label.editors") %></label>
        <a id="groupselect" class="btn btn-primary btn-xs" data-toggle="modal" href="#editorSelectModal">
            <i class="fa fa-th-list"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.editors.select") %>
        </a>
        <p>
            <input type="hidden" name="editors" id="editors" value="">
            <span id="editorsLabel"></span>
        </p>
    </div>
    
    
    <div class="form-group">
        <label for="input_content"><%= jspUtil.label("knowledge.admin.mailhook.condition.limit.post") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="0" name="postLimit" 
                id="postLimit0" <%= jspUtil.checked("0", "postLimit", true) %>/>
            <i class="fa fa-xxx"></i>&nbsp;<%= jspUtil.label("knowledge.admin.mailhook.condition.limit.post.0") %>
        </label>
        <br/>
        <label class="radio-inline">
            <input type="radio" value="1" name="postLimit" 
                id="postLimit1" <%= jspUtil.checked("1", "postLimit") %>/>
            <i class="fa fa-xxx"></i>&nbsp;<%= jspUtil.label("knowledge.admin.mailhook.condition.limit.post.1") %>
        </label>
        <br/>
        <label class="radio-inline">
            <input type="radio" value="2" name="postLimit" 
                id="postLimit2" <%= jspUtil.checked("2", "postLimit") %>/>
            <i class="fa fa-xxx"></i>&nbsp;<%= jspUtil.label("knowledge.admin.mailhook.condition.limit.post.2") %>
        </label>
    </div>
    
    <div class="form-group">
        <label for="limitParam"><%= jspUtil.label("knowledge.admin.mailhook.condition.limit.param") %></label>
        <input type="text" class="form-control" name="limitParam" id="limitParam"
            placeholder="<%= jspUtil.label("knowledge.admin.mailhook.condition.limit.param") %>" value="<%= jspUtil.out("limitParam") %>" />
    </div>
    
    
    
    <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
    <% if (!jspUtil.is(-1, "conditionNo")) { %>
    <button type="button" class="btn btn-danger" onclick="deleteMail();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
    <% } %>
    <a href="<%=request.getContextPath()%>/admin.mailhook/" class="btn btn-info">
        <i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.back") %>
    </a>
</form>



<%-- Editors --%>
<div class="modal fade" id="editorSelectModal" tabindex="-1" role="dialog" aria-labelledby="editorModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
                <span class="sr-only"><%= jspUtil.label("label.close") %></span></button>
                <h4 class="modal-title" id="editorModalLabel">
                    <%= jspUtil.label("knowledge.add.label.editors.select") %>
                    <span style="font-size: 14px;" id="editorPage"></span>
                </h4>
            </div>
            <div class="modal-body">
                <div role="form" class="form-inline">
                    <input type="text" name="keyword" class="form-control" value="<%= jspUtil.out("keyword") %>" placeholder="Keyword" id="editorKeyword">
                    <button type="button" class="btn btn-success" id="editorSearchButton">
                        <i class="fa fa-search"></i>&nbsp;<%= jspUtil.label("label.filter") %>
                    </button>
                    <button type="button" class="btn btn-default" id="editorSearchPrevious">
                        <i class="fa fa-arrow-circle-left"></i>&nbsp;<%= jspUtil.label("label.previous") %>
                    </button>
                    <button type="button" class="btn btn-default" id="editorSearchNext">
                        <%= jspUtil.label("label.next") %>&nbsp;<i class="fa fa-arrow-circle-right "></i>
                    </button>
                </div>
                <hr/>
                <p>
                    <%-- 選択済みの一覧 --%>
                    <span id="selectedEditorList"></span>
                    <button type="button" class="btn btn-default" id="clearSelectedEditor">
                        <i class="fa fa-eraser"></i>&nbsp;<%= jspUtil.label("label.clear") %>&nbsp;
                    </button>
                </p>
                <hr/>
                <p id="editorList">
                    <%-- 選択するための一覧 --%>
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <i class="fa fa-close"></i>&nbsp;<%= jspUtil.label("label.close") %>
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->




<%-- Targets --%>
<div class="modal fade" id="groupSelectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span>
                <span class="sr-only"><%= jspUtil.label("label.close") %></span></button>
                <h4 class="modal-title" id="myModalLabel">
                    <%= jspUtil.label("knowledge.add.label.destination.select") %>
                    <span style="font-size: 14px;" id="groupPage"></span>
                </h4>
            </div>
            <div class="modal-body">
                <div role="form" class="form-inline">
                    <input type="text" name="keyword" class="form-control" value="<%= jspUtil.out("keyword") %>" placeholder="Keyword" id="groupKeyword">
                    <button type="button" class="btn btn-success" id="groupSearchButton">
                        <i class="fa fa-search"></i>&nbsp;<%= jspUtil.label("label.filter") %>
                    </button>
                    <button type="button" class="btn btn-default" id="groupSearchPrevious">
                        <i class="fa fa-arrow-circle-left"></i>&nbsp;<%= jspUtil.label("label.previous") %>
                    </button>
                    <button type="button" class="btn btn-default" id="groupSearchNext">
                        <%= jspUtil.label("label.next") %>&nbsp;<i class="fa fa-arrow-circle-right "></i>
                    </button>
                </div>
                <hr/>
                <p>
                    <%-- 選択済みの一覧 --%>
                    <span id="selectedList"></span>
                    <button type="button" class="btn btn-default" id="clearSelectedGroup">
                        <i class="fa fa-eraser"></i>&nbsp;<%= jspUtil.label("label.clear") %>&nbsp;
                    </button>
                </p>
                <hr/>
                <p id="groupList">
                    <%-- 選択するための一覧 --%>
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <i class="fa fa-close"></i>&nbsp;<%= jspUtil.label("label.close") %>
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</c:param>

</c:import>

