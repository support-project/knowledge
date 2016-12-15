<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

            <div class="article_buttons">
                <div>
                    <%
                        if (request.getRemoteUser() != null) {
                            if ((Boolean) request.getAttribute("edit")) {
                    %>
                    <a href="<%=request.getContextPath()%>/protect.knowledge/view_edit/<%=jspUtil.out("knowledgeId")%>"
                        class="btn btn-primary btn_edit" role="button"><i class="fa fa-edit"></i>&nbsp; <%=jspUtil.label("knowledge.view.edit")%>
                    </a>
                    <%
                        } else {
                    %>
                    <button class="btn btn-primary btn_edit disabled" disabled="disabled">
                        <i class="fa fa-info-circle"></i>&nbsp;<%=jspUtil.label("knowledge.view.edit.disable")%>
                    </button>
                    <%
                        }
                    %>
                    <%
                        } else {
                    %>
                    <a href="<%=request.getContextPath()%>/protect.knowledge/view_edit/<%=jspUtil.out("knowledgeId")%>"
                        class="btn btn-primary btn_edit" role="button"><i class="fa fa-edit"></i>&nbsp; <%=jspUtil.label("knowledge.view.edit.with.login")%>
                    </a>
                    <%
                        }
                    %>
                </div>

                <div>
                    <%
                        if (request.getRemoteUser() != null) {
                    %>
                    <button type="button" class="btn btn-info btn_stock" data-toggle="modal" data-target="#stockModal">
                        <i class="fa fa-star-o"></i>&nbsp;
                        <%=jspUtil.label("knowledge.view.fav")%>
                    </button>
                    <%
                        } else {
                    %>
                    <a href="<%=request.getContextPath()%>/protect.knowledge/view/<%=jspUtil.out("knowledgeId")%>"
                        class="btn btn-info btn_stock" role="button"> <i class="fa fa-star-o"></i>&nbsp; <%=jspUtil.label("knowledge.view.fav")%><%--(<%= jspUtil.label("knowledge.navbar.signin") %>) --%>
                    </a>
                    <%
                        }
                    %>
                    <button class="btn btn-warning btn_like" onclick="addlike(<%=jspUtil.out("knowledgeId")%>);">
                        <i class="fa fa-thumbs-o-up"></i>&nbsp;
                        <%=jspUtil.label("knowledge.view.like")%>
                    </button>
                </div>
                
                <div>
                    <button class="btn btn-success btn_agenda" onclick="showAgenda();" id="buttonToc">
                        <i class="fa fa-list"></i>&nbsp;
                        <%=jspUtil.label("knowledge.view.label.show.toc")%>
                    </button>
                    <button class="btn btn-default btn_copy_url" data-clipboard-text="<%= jspUtil.out("url") %>" id="urlBtn">
                        <i class="fa fa-copy"></i>&nbsp;
                        <%=jspUtil.label("knowledge.view.label.copy.url")%>
                    </button>
                </div>
                
            </div>

            <div class="article_info">
                <a href="<%=request.getContextPath()%>/open.knowledge/likes/<%=jspUtil.out("knowledgeId")%><%=jspUtil.out("params")%>">
                    <i class="fa fa-thumbs-o-up"></i>&nbsp;<%=jspUtil.label("knowledge.view.like")%> × <span id="like_count"><%=jspUtil.out("like_count")%></span>
                </a> <a href="#comments" id="commentsLink"> <i class="fa fa-comments-o"></i>&nbsp;<%=jspUtil.label("knowledge.view.comment.label")%>
                    × <%=jspUtil.out("comments.size()")%>
                </a>
            </div>
            
