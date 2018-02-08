<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<div id="footer">
    <ul class="footer-menu list-inline">
        <li class="first">
            <a class="" href="<%= request.getContextPath() %>/index" style="cursor: pointer;"> <%= jspUtil.label("knowledge.footer.about") %></a>
        </li>
        <li>
            <a class="" href="https://information-knowledge.support-project.org/manual" style="cursor: pointer;"> <%= jspUtil.label("knowledge.footer.manual") %></a>
        </li>
        <li>
            <a class="" href="<%= request.getContextPath() %>/open.license" style="cursor: pointer;"> <%= jspUtil.label("knowledge.footer.license") %></a>
        </li>
        <li>
            <a class="" href="<%= request.getContextPath() %>/open.language" style="cursor: pointer;">
            <%-- 
            <% if (jspUtil.locale().getLanguage().equals("ja")) { %>
                <i class="flag-icon flag-icon-jp"></i>&nbsp;
            <% } else { %>
                <i class="flag-icon flag-icon-us"></i>&nbsp;
            <% } %>
            --%>
            <i class="fa fa-language"></i>&nbsp;
            <%= jspUtil.locale().getDisplayName(jspUtil.locale()) %>
            </a>
        </li>
    </ul>
    <!-- /nav -->
    <div class="clearfix"></div>
    <div class="copy">
        <span>Copyright &#169; 2015 - 2017 <a href="https://support-project.org/knowledge_info/index">support-project.org</a></span>
    </div>
    <!-- /copy -->
</div>
<!-- /footer -->
<p class="pagetop" style="display: none;"><a href="#content_top"><i class="fa fa-arrow-up" aria-hidden="true"></i></a></p>
