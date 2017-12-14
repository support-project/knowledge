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
    </c:param>

    <c:param name="PARAM_CONTENT">
    
        <h4 class="title"><%=jspUtil.label("knowledge.connect.title")%></h4>
        
        <div class="alert alert-info alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <strong>Information</strong><br/>
            <%=jspUtil.label("knowledge.connect.msg")%>
        </div>
        
        <div class="list-group">
        <% List<LdapConfigsEntity> ldapConfigs = (List<LdapConfigsEntity>) request.getAttribute("ldapConfigs"); %>
        <% List<UserAliasEntity> alias = (List<UserAliasEntity>) request.getAttribute("alias"); %>
        <% for (LdapConfigsEntity ldapConfig : ldapConfigs) { %>
            <a href="<%= request.getContextPath() %>/protect.connect/config?key=<%= ldapConfig.getSystemName() %>" class="list-group-item">
                <%= ldapConfig.getDescription() %>
                <% 
                UserAliasEntity info = null;
                for (UserAliasEntity a : alias) { 
                    if (a.getAuthKey().equals(ldapConfig.getSystemName())) {
                        info = a;
                        break;
                    }
                }
                if (info != null) {
                %>
                [<i class="fa fa-link"></i>&nbsp; <%=jspUtil.label("knowledge.connect.linked")%>] (<%= info.getAliasKey() %>)
                <% } else { %>
                [<i class="fa fa-unlink"></i>&nbsp; <%=jspUtil.label("knowledge.connect.unlinked")%>] 
                <% } %>
            </a>
        <% } %>
        </div>
        
    </c:param>
    
</c:import>
