<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>
<div class="panel panel-primary">
    <div class="panel-heading">
        <h4 class="panel-title">
        <%= jspUtil.label("knowledge.add.label.meta") %>
        </h4>
    </div>
    <div class="panel-body">
        <div class="article_meta">
        
            <!-- template -->
            <div class="form-group" style="margin-top: 3px;">
                <label for="input_title"><%= jspUtil.label("knowledge.add.label.type") %></label><br/>
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
                    <br/>
                </c:forEach>
                
                <div class="tips_info hide" id="template_info">
                    <span id="template_msg"></span>
                </div>
                <div style="height:5px"></div>
            </div>


            <!-- view targets -->
            <div class="form-group">
                <label for="input_content">
                    <%= jspUtil.label("knowledge.add.label.public.class") %><br/>
                </label><br/>
                
                <div class="tips_info"><%= jspUtil.label("knowledge.add.label.public.class.info") %></div>
                
                <label class="radio-inline">
                    <input type="radio" value="<%= KnowledgeLogic.PUBLIC_FLAG_PRIVATE %>" name="publicFlag" 
                        id="publicFlag_private" <%= jspUtil.checked(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PRIVATE), "publicFlag", true) %>/>
                    <i class="fa fa-lock"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.public.class.private") %>
                </label><br/>
                <label class="radio-inline">
                    <input type="radio" value="<%= KnowledgeLogic.PUBLIC_FLAG_PUBLIC %>" name="publicFlag" 
                        id="publicFlag_piblic" <%= jspUtil.checked(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PUBLIC), "publicFlag") %>/>
                    <i class="fa fa-globe"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.public.class.public") %>
                </label><br/>
                <label class="radio-inline">
                    <input type="radio" value="<%= KnowledgeLogic.PUBLIC_FLAG_PROTECT %>" name="publicFlag" 
                        id="publicFlag_protect" <%= jspUtil.checked(String.valueOf(KnowledgeLogic.PUBLIC_FLAG_PROTECT), "publicFlag") %>/>
                    <i class="fa fa-gavel"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.public.class.protect") %>
                </label><br/>
                <div style="height:5px"></div>
                <div class="" id="grops_area" <%= jspUtil.isnot(KnowledgeLogic.PUBLIC_FLAG_PROTECT, "publicFlag", "style=\"display: none;\"") %>>
                    <label for="input_groups"><%= jspUtil.label("knowledge.add.label.destination") %></label><br/>
                    <a id="groupselect" class="btn btn-primary btn-xs" data-toggle="modal" href="#groupSelectModal">
                        <i class="fa fa-th-list"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.destination.select") %>
                    </a>
                    <p>
                        <input type="hidden" name="groups" id="groups" value="">
                        <span id="groupsLabel"></span>
                    </p>
                </div>
                
            </div>


            <!-- tags -->
            <div class="form-group">
                <label for="input_tag">
                <%= jspUtil.label("knowledge.add.label.tags") %>
                <a class="btn btn-primary btn-xs" data-toggle="modal" data-target="#tagSelectModal"><i class="fa fa-tags"></i>&nbsp;<%= jspUtil.label("label.search.tags") %></a>
                </label>
                <br/>
                <div class="tags">
                <input type="text" class="form-control" name="tagNames" id="input_tags" data-role="tags input"
                    placeholder="<%= jspUtil.label("knowledge.add.label.tags") %>" value="<%= jspUtil.out("tagNames") %>" />
                </div>
            </div>


            <!-- editors -->
            <div class="form-group" id="editor_area">
                <label for="input_groups"><%= jspUtil.label("knowledge.add.label.editors") %></label><br/>
                <a id="groupselect" class="btn btn-primary btn-xs" data-toggle="modal" href="#editorSelectModal">
                    <i class="fa fa-th-list"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.editors.select") %>
                </a>
                <p>
                    <input type="hidden" name="editors" id="editors" value="">
                    <span id="editorsLabel"></span>
                </p>
            </div>
        
            <!-- upload files -->
            <div>
                <jsp:include page="partials-edit-attach.jsp"></jsp:include>
            </div>
                
        </div>
        

    
    </div>
</div>
