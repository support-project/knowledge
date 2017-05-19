<%@page import="org.support.project.common.util.StringUtils"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="java.util.List"%>
<%@page import="org.support.project.web.logic.SanitizingLogic"%>
<%@page import="org.support.project.web.bean.LabelValue"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.knowledge.config.AppConfig"%>
<%@page import="org.support.project.knowledge.entity.ServiceLocaleConfigsEntity"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutTop.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/page-top.css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/top.css" />
<!-- endbuild -->
</c:param>
<c:param name="PARAM_SCRIPTS">
<script type="text/javascript">
$(document).ready(function() {
    $('#headerwrap').click(function() {
        location.href='<%= request.getContextPath()%>/open.knowledge/list';
    });
});
</script>
</c:param>

<c:param name="PARAM_CONTENT">
    <% 
    ServiceLocaleConfigsEntity localeConfig = SystemConfig.getServiceLocaleConfigsEntity(jspUtil.locale());
    if (localeConfig != null && StringUtils.isNotEmpty(localeConfig.getPageHtml())) { %>
        <%= SanitizingLogic.get().sanitize(localeConfig.getPageHtml()) %>
    <% } else { %>
    <div id="headerimg">
        <div id="headerwrap">
            <h1><span><i class="fa fa-book"></i>&nbsp;Knowledge</span></h1>
            <h2><span>Free Knowledge Base System</span></h2>
            <br/>
            <a id="showlist" class="btn btn-lg get-start" role="button"
                href="<%=request.getContextPath()%>/open.knowledge/list">
                <%=jspUtil.label("knowledge.top.use.button")%>
            </a>
            <br/><br/>
            <br/><br/>
        </div>
    </div>

    <div class="container">
        <div class="contects" id="about">
            <div class="row">
                <div class="col-sm-12 subtitle">
                About Knowledge
                <div class="description"><%=jspUtil.label("knowledge.top.description")%></div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-md-3 col-lg-3">
                    <div class="about-icon">
                        <i class="fa fa-github icon-img"></i>
                    </div>
                    <div class="about-title"><%=jspUtil.label("knowledge.top.about.title.1")%></div>
                    <div class="about-description"><%=jspUtil.label("knowledge.top.about.description.1")%></div>
                </div>
                <div class="col-sm-6 col-md-3 col-lg-3">
                    <div class="about-icon">
                        <i class="fa fa-download icon-img"></i>
                    </div>
                    <div class="about-title"><%=jspUtil.label("knowledge.top.about.title.2")%></div>
                    <div class="about-description"><%=jspUtil.label("knowledge.top.about.description.2")%></div>
                </div>
                <div class="col-sm-6 col-md-3 col-lg-3">
                    <div class="about-icon">
                        <i class="fa fa-mobile-phone icon-img"></i>
                    </div>
                    <div class="about-title"><%=jspUtil.label("knowledge.top.about.title.3")%></div>
                    <div class="about-description"><%=jspUtil.label("knowledge.top.about.description.3")%></div>
                </div>
                <div class="col-sm-6 col-md-3 col-lg-3">
                    <div class="about-icon">
                        <i class="fa fa-pencil icon-img"></i>
                    </div>
                    <div class="about-title"><%=jspUtil.label("knowledge.top.about.title.4")%></div>
                    <div class="about-description"><%=jspUtil.label("knowledge.top.about.description.4")%></div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-md-3 col-lg-3">
                    <div class="about-icon">
                        <i class="fa fa-search icon-img"></i>
                    </div>
                    <div class="about-title"><%=jspUtil.label("knowledge.top.about.title.5")%></div>
                    <div class="about-description"><%=jspUtil.label("knowledge.top.about.description.5")%></div>
                </div>
                <div class="col-sm-6 col-md-3 col-lg-3">
                    <div class="about-icon">
                        <i class="fa fa-bell-o icon-img"></i>
                    </div>
                    <div class="about-title"><%=jspUtil.label("knowledge.top.about.title.6")%></div>
                    <div class="about-description"><%=jspUtil.label("knowledge.top.about.description.6")%></div>
                </div>
                <div class="col-sm-6 col-md-3 col-lg-3">
                    <div class="about-icon">
                        <i class="fa fa-paperclip icon-img"></i>
                    </div>
                    <div class="about-title"><%=jspUtil.label("knowledge.top.about.title.7")%></div>
                    <div class="about-description"><%=jspUtil.label("knowledge.top.about.description.7")%></div>
                </div>
                <div class="col-sm-6 col-md-3 col-lg-3">
                    <div class="about-icon">
                        <i class="fa fa-comments icon-img"></i>
                    </div>
                    <div class="about-title"><%=jspUtil.label("knowledge.top.about.title.8")%></div>
                    <div class="about-description"><%=jspUtil.label("knowledge.top.about.description.8")%></div>
                </div>
            </div>
        
            <div class="row">
                <div class="col-sm-12 subtitle">
                <div class="description">
                <a href="https://support-project.org/knowledge_info/index">-> More Information</a>
                </div>
                </div>
            </div>
        </div>
    </div>
    <% } %>
</c:param>

</c:import>


