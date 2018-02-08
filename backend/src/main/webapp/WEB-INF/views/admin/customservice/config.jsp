<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>

<%@page import="java.util.Locale"%>
<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.web.logic.HttpRequestCheckLogic"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.knowledge.entity.ServiceConfigsEntity"%>
<%@page import="org.support.project.knowledge.entity.ServiceLocaleConfigsEntity"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    JspUtil jspUtil = new JspUtil(request, pageContext);
    ServiceConfigsEntity serviceConfig = SystemConfig.getServiceConfigsEntity();
    ServiceLocaleConfigsEntity en = SystemConfig.getServiceLocaleConfigsEntity(new Locale("en"));
    ServiceLocaleConfigsEntity ja = SystemConfig.getServiceLocaleConfigsEntity(new Locale("ja"));
%>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

    <c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/admin-system-config.css -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->
    </c:param>

    <c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-customservice.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/js/customservice.js"></script>
<!-- endbuild -->

    </c:param>

    <c:param name="PARAM_CONTENT">
        <h4 class="title"><%=jspUtil.label("knowledge.custom.service.title")%>
        <span class="backlink">
        <a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
        </span>
        </h4>

        <form action="<%= request.getContextPath() %>/admin.customservice/save" method="post" id="fm">
            <input type="hidden" name="<%= HttpRequestCheckLogic.REQ_ID_KEY %>"
                value="<%= jspUtil.out(HttpRequestCheckLogic.REQ_ID_KEY) %>" />
            
            <div class="form-group">
                <label for="serviceLabel"><%= jspUtil.label("knowledge.custom.service.label.title") %></label>
                <input type="text" class="form-control" name="serviceLabel" id="serviceLabel"
                    placeholder="Label of service" value="<%= jspUtil.sanitize(serviceConfig.getServiceLabel()) %>" />
            </div>
            
            <div class="form-group">
                <label for="serviceIcon">
                <%= jspUtil.label("knowledge.custom.service.label.icon") %>
                <%= jspUtil.label("knowledge.template.label.icon.msg") %>
                </label>
                <input type="text" class="form-control" name="serviceIcon" id="serviceIcon"
                    placeholder="Icon" value="<%= jspUtil.sanitize(serviceConfig.getServiceIcon()) %>" />
            </div>
            
            <div class="panel panel-default">
                <div class="panel-heading">
                    <%=jspUtil.label("knowledge.custom.service.label.custom.html") %>
                    <a href="#customHtml" data-toggle="collapse"><i class="fa fa-chevron-circle-down"></i></a>
                </div>
                <div class="panel-body">
                    <div id="customHtml" class="collapse">
                        <div class="form-group">
                            <label for="enPage"><%= jspUtil.label("knowledge.custom.service.label.custom.html.en") %></label>
                            <textarea rows="12" class="form-control" name="enPage" id="enPage" 
                                placeholder="<%= jspUtil.label("knowledge.custom.service.label.custom.html.en") %>"><%= jspUtil.sanitize(en.getPageHtml()) %></textarea>
                        </div>
                        <div class="form-group">
                            <label for="jaPage"><%= jspUtil.label("knowledge.custom.service.label.custom.html.ja") %></label>
                            <textarea rows="12" class="form-control" name="jaPage" id="jaPage" 
                                placeholder="<%= jspUtil.label("knowledge.custom.service.label.custom.html.ja") %>"><%= jspUtil.sanitize(ja.getPageHtml()) %></textarea>
                        </div>
                    </div>
                </div>
            </div>
            
            <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
        </form>

    </c:param>

</c:import>

