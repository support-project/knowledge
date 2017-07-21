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
</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-protect-targets.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/js/targetselect.js"></script>
<!-- endbuild -->

<script>
<c:forEach var="viewer" items="${viewers}" varStatus="status">
selectedGroups.push({label: '<%= jspUtil.out("viewer.label") %>', value: '<%= jspUtil.out("viewer.value") %>'});
</c:forEach>
</script>
</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.account.targets") %></h4>

<form action="<%= request.getContextPath()%>/protect.account/savetargets" method="post" role="form" id="mailForm">
    
    <!-- view targets -->
    <div class="form-group">
        <label for="input_content"><%= jspUtil.label("knowledge.account.targets.select") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="<%= KnowledgeLogic.PUBLIC_FLAG_PRIVATE %>" name="publicFlag" 
                id="publicFlag_private" <%= jspUtil.checked(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "publicFlag", true) %>/>
            <i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.public.class.private") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="<%= KnowledgeLogic.PUBLIC_FLAG_PUBLIC %>" name="publicFlag" 
                id="publicFlag_piblic" <%= jspUtil.checked(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "publicFlag") %>/>
            <i class="fa fa-globe"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.public.class.public") %>
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
    
    <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
    <a href="<%=request.getContextPath()%>/protect.config/index/" class="btn btn-info">
        <i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.back") %>
    </a>
</form>


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

