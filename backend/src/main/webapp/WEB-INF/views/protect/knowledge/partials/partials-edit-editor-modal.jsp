<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>



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
