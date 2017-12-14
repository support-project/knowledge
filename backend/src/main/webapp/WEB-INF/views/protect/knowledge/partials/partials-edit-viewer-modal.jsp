<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


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
                <%-- 決定はしないで、選択したら動的に呼び出し元のフォームに反映する
                <button type="button" class="btn btn-primary" id="groupDecision">
                    <i class="fa fa-legal"></i>&nbsp;<%= jspUtil.label("label.decision") %>
                </button>
                --%>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->



