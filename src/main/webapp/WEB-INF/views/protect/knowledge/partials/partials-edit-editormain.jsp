<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>
    <input type="hidden" name="offset" value="<%= jspUtil.out("offset") %>" />
    <input type="hidden" name="keyword" value="<%= jspUtil.out("keyword") %>" />
    <input type="hidden" name="tag" value="<%= jspUtil.out("tag") %>" />
    <input type="hidden" name="user" value="<%= jspUtil.out("user") %>" />
    
    <!-- title -->
    <div class="form-group">
        <label for="input_title"><%= jspUtil.label("knowledge.add.label.title") %></label>
        <input type="text" class="form-control" name="title" id="input_title" placeholder="<%= jspUtil.label("knowledge.add.label.title") %>" value="<%= jspUtil.out("title") %>" />
    </div>
    
    <!-- items -->
    <div class="form-group" id="template_items">
    </div>

    <!-- contents -->
    <div class="form-group">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#writeable" data-toggle="tab"><%= jspUtil.label("knowledge.add.label.content") %></a></li>
            <li><a href="#previewTab" data-toggle="tab" onclick="preview();"><%= jspUtil.label("label.preview") %></a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="writeable">
                <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/people" data-target="#emojiPeopleModal">people</a>
                <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/nature" data-target="#emojiNatureModal">nature</a>
                <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/objects" data-target="#emojiObjectsModal">objects</a>
                <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/places" data-target="#emojiPlacesModal">places</a>
                <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/symbols" data-target="#emojiSymbolsModal">symbols</a>
                <span class="helpMarkdownLabel pull-right">
                    <a data-toggle="modal" data-target="#helpMarkdownModal">Markdown supported</a>
                </span>
                <textarea class="form-control" name="content" rows="20" placeholder="<%= jspUtil.label("knowledge.add.label.content") %>" id="content"><%= jspUtil.out("content") %></textarea>
            </div>

            <div class="tab-pane preview markdown" id="previewTab">
                <div class="row">
                <span class="col-sm-8" id="preview"></span>
                </div>
            </div>
        </div>
    </div>


