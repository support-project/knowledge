<%@page import="org.support.project.common.util.HtmlUtils"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.entity.TemplateMastersEntity"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.knowledge.logic.TemplateLogic"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>
<div class="panel panel-success">
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
                        <% 
                        int type = jspUtil.getValue("typeId", Integer.class);
                        if (pageContext.getAttribute("typeId") != null) {
                            type = (Integer) pageContext.getAttribute("typeId");
                        }
                        TemplateMastersEntity template = (TemplateMastersEntity) pageContext.getAttribute("template");
                        String id = "typeId_" + template.getTypeId();
                        
                        StringBuilder builder = new StringBuilder();
                        builder.append("<input type=\"radio\" value=\"").append(template.getTypeId()).append("\" name=\"typeId\" ");
                        builder.append("id=\"").append(id).append("\"");
                        if (template.getTypeId() == type) {
                            builder.append(" checked");
                        }
                        builder.append(" />");
                        if (!StringUtils.isEmpty(template.getTypeIcon())) {
                            builder.append("<i class=\"fa ").append(HtmlUtils.escapeHTML(template.getTypeIcon())).append("\" ></i>&nbsp;");
                        } else {
                            builder.append("<i class=\"fa fa-edit\"></i>&nbsp;");
                        }
                        builder.append(HtmlUtils.escapeHTML(template.getTypeName()));
                        %>
                        <%= builder.toString() %>
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
                <a class="btn btn-info btn-xs" data-toggle="modal" data-target="#tagSelectModal"><i class="fa fa-tags"></i>&nbsp;<%= jspUtil.label("label.search.tags") %></a>
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
                <a id="groupselect" class="btn btn-info btn-xs" data-toggle="modal" href="#editorSelectModal">
                    <i class="fa fa-th-list"></i>&nbsp;<%= jspUtil.label("knowledge.add.label.editors.select") %>
                </a>
                <p>
                    <input type="hidden" name="editors" id="editors" value="">
                    <span id="editorsLabel"></span>
                </p>
            </div>
        
            <!-- upload files -->
            <div class="form-group">
                <jsp:include page="partials-edit-attach.jsp"></jsp:include>
            </div>
            
        </div>
        

    
    </div>
</div>
