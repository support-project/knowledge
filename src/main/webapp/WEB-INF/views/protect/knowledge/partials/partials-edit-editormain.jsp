<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>
    <input type="hidden" name="offset" value="<%= jspUtil.out("offset") %>" />
    <input type="hidden" name="keyword" value="<%= jspUtil.out("keyword") %>" />
    <input type="hidden" name="tag" value="<%= jspUtil.out("tag") %>" />
    <input type="hidden" name="user" value="<%= jspUtil.out("user") %>" />
    
    <!-- template -->
    <div class="form-group" style="margin-top: 3px;">
        <label for="input_title"><%= jspUtil.label("knowledge.add.label.type") %></label>
        <c:forEach var="template" items="${templates}" >
            <label class="radio-inline">
                <input type="radio" value="<%= jspUtil.out("template.typeId") %>" name="typeId" 
                    id="typeId_<%= jspUtil.out("template.typeId") %>" <%= jspUtil.checked(jspUtil.out("template.typeId"), "typeId", false) %>/>
                <% if (!StringUtils.isEmpty(jspUtil.out("template.typeIcon"))) { %>
                    <i class="fa <%= jspUtil.out("template.typeIcon") %>"></i>&nbsp;
                <% } else { %>
                    <i class="fa fa-edit"></i>&nbsp;
                <% } %>
                <%= jspUtil.out("template.typeName") %>
            </label>
        </c:forEach>
    </div>

    <div class="alert alert-info hide" role="alert" id="template_info">
        <span id="template_msg"></span>
    </div>


    <!-- view targets -->
    <div class="form-group">
        <label for="input_content">
            <%= jspUtil.label("knowledge.add.label.public.class") %>
            <font size="-2"><%= jspUtil.label("knowledge.add.label.public.class.info") %></font>
        </label><br/>
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
            <input type="hidden" name="groups" id="groups" value="">
            <span id="groupsLabel"></span>
        </p>
    </div>


    <!-- title -->
    <div class="form-group">
        <label for="input_title"><%= jspUtil.label("knowledge.add.label.title") %></label>
        <input type="text" class="form-control" name="title" id="input_title" placeholder="<%= jspUtil.label("knowledge.add.label.title") %>" value="<%= jspUtil.out("title") %>" />
    </div>
    
    
    <!-- items -->
    <div class="form-group" id="template_items">
    </div>

    <!-- tags -->
    <div class="form-group">
        <label for="input_tag">
        <%= jspUtil.label("knowledge.add.label.tags") %>
        <a class="btn btn-primary btn-xs" data-toggle="modal" data-target="#tagSelectModal"><i class="fa fa-tags"></i>&nbsp;<%= jspUtil.label("label.search.tags") %></a>
        </label>
        <p class="tags">
        <input type="text" class="form-control" name="tagNames" id="input_tags" data-role="tags input"
            placeholder="<%= jspUtil.label("knowledge.add.label.tags") %>" value="<%= jspUtil.out("tagNames") %>" />
        </p>
    </div>

    <!-- contents -->
    <div class="form-group">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#writeable" data-toggle="tab"><%= jspUtil.label("knowledge.add.label.content") %></a></li>
            <li><a href="#preview" data-toggle="tab" onclick="preview();"><%= jspUtil.label("label.preview") %></a></li>
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

            <div class="tab-pane preview markdown" id="preview">
                <span style="display: none;" id="content_text">
                </span>
            </div>
        </div>
    </div>




    