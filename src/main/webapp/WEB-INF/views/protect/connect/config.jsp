<%@page import="javax.swing.JSplitPane"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="java.util.List"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.web.entity.UserAliasEntity"%>
<%@page import="org.support.project.web.entity.LdapConfigsEntity"%>
<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

    <c:param name="PARAM_HEAD">
    </c:param>

    <c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-connect.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/js/connect.js"></script>
<!-- endbuild -->
    </c:param>

    <c:param name="PARAM_CONTENT">
        <h4 class="title"><%=jspUtil.label("knowledge.connect.title")%></h4>

        <h4 class="sub_title">
            <%=jspUtil.out("config.description")%>
            <c:if test="${!empty alias}">
            [<%=jspUtil.label("knowledge.connect.linked")%>] 
            </c:if>
            <c:if test="${empty alias}">
            [<%=jspUtil.label("knowledge.connect.unlinked")%>] 
            </c:if>
        </h4>

        <form action="<%= request.getContextPath()%>/protect.connect/connect" method="post" role="form" id="form">
            <input type="hidden" name="key" value="<%= jspUtil.out("key") %>" />
            <c:if test="${empty alias}">
                <div class="alert alert-info alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>Information</strong><br/>
                    <%=jspUtil.label("knowledge.connect.info.link", jspUtil.out("config.description"), jspUtil.label("knowledge.connect.link"))%>
                </div>
                <%= jspUtil.label("") %>
                <div class="form-group">
                    <label for="inputEmail" class="control-label"><%= jspUtil.label("knowledge.auth.label.id") %></label>
                    <input type="text" class="form-control"
                        name="username" value="<%= jspUtil.out("username") %>"
                        placeholder="<%= jspUtil.label("knowledge.auth.label.id") %>" autofocus>
                </div>
                <div class="form-group">
                    <label for="inputPass" class="control-label"><%= jspUtil.label("knowledge.auth.label.password") %></label>
                    <input type="password" class="form-control"
                        name="password" value="<%= jspUtil.out("password") %>"
                        placeholder="<%= jspUtil.label("knowledge.auth.label.password") %>">
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="userInfoUpdate" value="1"> <%= jspUtil.label("knowledge.connect.update.me") %>
                    </label>
                </div>
                
                <button type="submit" class="btn btn-primary" id="connect">
                    <i class="fa fa-link"></i>&nbsp;<%= jspUtil.label("knowledge.connect.link") %>
                </button>
            </c:if>

            <c:if test="${!empty alias}">
                <% if (jspUtil.is(Boolean.TRUE, "onlyone")) { %>
                <div class="alert alert-info alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>Information</strong><br/>
                    <%=jspUtil.label("knowledge.connect.disable.unlink")%>
                </div>
                <% } %>
                <div class="row">
                    <div class="col-sm-2">Key</div>
                    <div class="col-sm-10"><%= jspUtil.out("alias.aliasKey") %></div>
                </div>
                <div class="row">
                    <div class="col-sm-2">Name</div>
                    <div class="col-sm-10"><%= jspUtil.out("alias.aliasName") %></div>
                </div>
                <div class="row">
                    <div class="col-sm-2">Mail</div>
                    <div class="col-sm-10"><%= jspUtil.out("alias.aliasMail") %></div>
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="userInfoUpdate" value="1"> <%= jspUtil.label("knowledge.connect.update.me") %>
                    </label>
                </div>
                
                <button type="button" class="btn btn-primary" id="update">
                    <i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.update") %>
                </button>
                
                <button type="button" class="btn btn-danger" id="disconnect" <% if (jspUtil.is(Boolean.TRUE, "onlyone")) { %> disabled="disabled" <% } %>>
                    <i class="fa fa-unlink"></i>&nbsp;<%= jspUtil.label("knowledge.connect.unlink") %>
                </button>
            </c:if>
            
            
            <a href="<%= request.getContextPath() %>/protect.connect"
                class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %>
            </a>
            
            <br/>
            
        
        </form>
        
    </c:param>
        
</c:import>
