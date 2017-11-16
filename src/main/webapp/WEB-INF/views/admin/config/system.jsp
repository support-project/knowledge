<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.web.logic.HttpRequestCheckLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/admin-system-config.css -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->

<style>
.radio_block {
    margin-bottom: 10px;
}
</style>
</c:param>

<c:param name="PARAM_SCRIPTS">
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.config.system.title") %>
<span class="backlink">
<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
</span>
</h4>

<form action="<%= request.getContextPath()%>/admin.config/save_params" method="post" role="form">
    <input type="hidden" name="<%= HttpRequestCheckLogic.REQ_ID_KEY %>"
        value="<%= jspUtil.out(HttpRequestCheckLogic.REQ_ID_KEY) %>" />
    <div class="form-group">
        <label for="host"><%= jspUtil.label("knowledge.config.system.label.url") %></label>
        <input type="text" class="form-control" name="systemurl" id="host" placeholder="URL" value="<%= jspUtil.out("systemurl") %>" />
    </div>
    
    
    <div class="form-group">
        <label for="authType_lock"><%= jspUtil.label("knowledge.config.system.open.title") %></label><br/>
        <label class="radio-inline radio_block">
            <input type="radio" value="<%=SystemConfig.SYSTEM_EXPOSE_TYPE_OPEN%>" name="system_open_type" 
                id="system_open_type_open" <%=jspUtil.checked(SystemConfig.SYSTEM_EXPOSE_TYPE_OPEN, "system_open_type", true)%>/>
            <i class="fa fa-unlock fa-lg"></i>&nbsp;<%= jspUtil.label("knowledge.config.system.open") %>
        </label>
        <br/>
        <label class="radio-inline radio_block">
            <input type="radio" value="<%=SystemConfig.SYSTEM_EXPOSE_TYPE_CLOSE%>" name="system_open_type" 
                id="system_open_type_close" <%=jspUtil.checked(SystemConfig.SYSTEM_EXPOSE_TYPE_CLOSE, "system_open_type", false)%>/>
            <i class="fa fa-lock fa-lg"></i>&nbsp;<%= jspUtil.label("knowledge.config.system.close") %>
        </label>
    </div>
    
    <div class="form-group">
        <label for="limitAttach"><%= jspUtil.label("knowledge.config.system.label.limit.attach") %> [1 - 300]</label>
        <input type="number" min="1" max="300" step="1"
            class="form-control" name="uploadMaxMBSize" id="limitAttach" 
            placeholder="<%= jspUtil.label("knowledge.config.system.label.limit.attach") %>"
            value="<%= jspUtil.out("uploadMaxMBSize") %>" />
    </div>
    
    <div class="form-group">
        <label for="authType_lock"><%= jspUtil.label("knowledge.config.system.like") %></label><br/>
        <label class="radio-inline radio_block">
            <input type="radio" value="<%=SystemConfig.LIKE_CONFIG_MANY%>" name="like_config" 
                id="like_config_many" <%=jspUtil.checked(SystemConfig.LIKE_CONFIG_MANY, "like_config", true)%>/>
            <i class="fa fa-unlock fa-lg"></i>&nbsp;<%= jspUtil.label("knowledge.config.system.like.many") %>
        </label>
        <br/>
        <label class="radio-inline radio_block">
            <input type="radio" value="<%=SystemConfig.LIKE_CONFIG_ONLY_ONE%>" name="like_config" 
                id="like_config_onlyone" <%=jspUtil.checked(SystemConfig.LIKE_CONFIG_ONLY_ONE, "like_config", false)%>/>
            <i class="fa fa-lock fa-lg"></i>&nbsp;<%= jspUtil.label("knowledge.config.system.like.onlyone") %>
        </label>
    </div>
    
    <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
</form>

<hr/>
Application version: <%= jspUtil.label("label.version") %><br/>
Database schema version: <%= jspUtil.out("db_version") %><br/>

</c:param>

</c:import>

