<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<style>
.thema_box {
    text-align: center;
    margin-bottom: 20px;
}

.thema_show {
    border: 1px solid gray;
    height: 300px;
    width: 100%;
    overflow: hidden;
}

.selected_thema {
    margin-bottom: 20px;
    font-size:12pt;
}
.highlight_show {
    border: none;
    height: 250px;
    width: 100%;
    overflow: hidden;
}
.hr {
    height: 100px;
    border-bottom: 1px solid gray;
}
form {
    margin-top: 30px;
}
</style>

</c:param>

<c:param name="PARAM_SCRIPTS">
<script>
function setThema() {
    location.href = '<%= request.getContextPath() %>/open.thema/enable/' + $('#thema').val();
}

function setStyle() {
    location.href = '<%= request.getContextPath() %>/open.thema/style/' + $('#style').val();
}
</script>

</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title">
<%= jspUtil.label("knowledge.config.thema") %>
</h4>

<div class="row">
    <div class="col-xs-12 selected_thema">
        [<%= jspUtil.label("knowledge.config.thema.now") %>]: 
        <% if (StringUtils.isNotEmpty(jspUtil.out("thema"))) { %>
        <%= jspUtil.out("thema") %>
        <% } else { %>
        <%= jspUtil.cookie(SystemConfig.COOKIE_KEY_THEMA, "flatly") %>
        <% } %>
    </div>
</div>


<div class="row">
    <div class="col-sm-6 col-md-6 col-lg-4 thema_box">
        <iframe src="<%= request.getContextPath() %>/open.thema/show/flatly" class="thema_show"></iframe>
        <a href="<%= request.getContextPath() %>/open.thema/enable/flatly" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
    </div>
    <div class="col-sm-6 col-md-6 col-lg-4 thema_box">
        <iframe src="<%= request.getContextPath() %>/open.thema/show/darkly" class="thema_show"></iframe>
        <a href="<%= request.getContextPath() %>/open.thema/enable/darkly" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
    </div>
    <div class="col-sm-6 col-md-6 col-lg-4 thema_box">
        <iframe src="<%= request.getContextPath() %>/open.thema/show/sandstone" class="thema_show"></iframe>
        <a href="<%= request.getContextPath() %>/open.thema/enable/sandstone" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
    </div>
    <div class="col-sm-6 col-md-6 col-lg-4 thema_box">
        <iframe src="<%= request.getContextPath() %>/open.thema/show/cosmo" class="thema_show"></iframe>
        <a href="<%= request.getContextPath() %>/open.thema/enable/cosmo" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
    </div>
    <div class="col-sm-6 col-md-6 col-lg-4 thema_box">
        <iframe src="<%= request.getContextPath() %>/open.thema/show/slate" class="thema_show"></iframe>
        <a href="<%= request.getContextPath() %>/open.thema/enable/slate" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
    </div>
    <div class="col-sm-6 col-md-6 col-lg-4 thema_box">
        <iframe src="<%= request.getContextPath() %>/open.thema/show/spacelab" class="thema_show"></iframe>
        <a href="<%= request.getContextPath() %>/open.thema/enable/spacelab" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
    </div>
    <div class="col-sm-6 col-md-6 col-lg-4 thema_box">
        <iframe src="<%= request.getContextPath() %>/open.thema/show/united" class="thema_show"></iframe>
        <a href="<%= request.getContextPath() %>/open.thema/enable/united" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
    </div>
    <div class="col-sm-6 col-md-6 col-lg-4 thema_box">
        <iframe src="<%= request.getContextPath() %>/open.thema/show/superhero" class="thema_show"></iframe>
        <a href="<%= request.getContextPath() %>/open.thema/enable/superhero" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
    </div>
    <div class="col-sm-6 col-md-6 col-lg-4 thema_box">
        <iframe src="<%= request.getContextPath() %>/open.thema/show/cerulean" class="thema_show"></iframe>
        <a href="<%= request.getContextPath() %>/open.thema/enable/cerulean" class="btn btn-primary"><%= jspUtil.label("knowledge.config.thema.enable") %></a>
    </div>
</div>

<div class="row">
    <div class="col-xs-12">
        <form class="form-inline" method="get">
            (Advanced option) Other: 
            <div class="form-group">
                <!-- <label class="sr-only" for="thema">Other thema</label> -->
                <input type="text" class="form-control" id="thema" placeholder="Other thema">
            </div>
            <button type="button" class="btn btn-default" onclick="setThema()">
                <%= jspUtil.label("knowledge.config.thema.enable") %>
            </button>
            <a href="https://bootswatch.com/" target="_blank">thema list</a>
        </form>
    </div>
</div>


<div class="hr"></div>


<h4 class="title">
<%= jspUtil.label("knowledge.config.highlight") %>
</h4>

<div class="row">
    <div class="col-xs-12 selected_thema">
        [<%= jspUtil.label("knowledge.config.thema.now") %>]: 
        <% if (StringUtils.isNotEmpty(jspUtil.out("highlight"))) { %>
        <%= jspUtil.out("highlight") %>
        <% } else { %>
        <%= jspUtil.cookie(SystemConfig.COOKIE_KEY_HIGHLIGHT, "darkula") %>
        <% } %>
    </div>
</div>


<div class="row">
    <div class="col-sm-6 thema_box">
        <iframe src="<%= request.getContextPath() %>/open.thema/highlight/darkula" class="highlight_show"></iframe>
        <a href="<%= request.getContextPath() %>/open.thema/style/darkula" class="btn btn-primary">
        <%= jspUtil.label("knowledge.config.thema.enable") %>
        </a>
    </div>
    <div class="col-sm-6 thema_box">
        <iframe src="<%= request.getContextPath() %>/open.thema/highlight/default" class="highlight_show"></iframe>
        <a href="<%= request.getContextPath() %>/open.thema/style/default" class="btn btn-primary">
        <%= jspUtil.label("knowledge.config.thema.enable") %>
        </a>
    </div>
    <div class="col-sm-6 thema_box">
        <iframe src="<%= request.getContextPath() %>/open.thema/highlight/far" class="highlight_show"></iframe>
        <a href="<%= request.getContextPath() %>/open.thema/style/far" class="btn btn-primary">
        <%= jspUtil.label("knowledge.config.thema.enable") %>
        </a>
    </div>
    <div class="col-sm-6 thema_box">
        <iframe src="<%= request.getContextPath() %>/open.thema/highlight/kimbie.light" class="highlight_show"></iframe>
        <a href="<%= request.getContextPath() %>/open.thema/style/kimbie.light" class="btn btn-primary">
        <%= jspUtil.label("knowledge.config.thema.enable") %>
        </a>
    </div>
</div>


<div class="row">
    <div class="col-xs-12">
        <form class="form-inline" method="get">
            (Advanced option) Other: 
            <div class="form-group">
                <input type="text" class="form-control" id="style" placeholder="Other code highlight style">
            </div>
            <button type="button" class="btn btn-default" onclick="setStyle()">
                <%= jspUtil.label("knowledge.config.thema.enable") %>
            </button>
            <a href="https://highlightjs.org/static/demo/" target="_blank">style list</a>
        </form>
    </div>
</div>

<br/>
<hr/>
<br/>
<a href="<%=request.getContextPath()%>/protect.config/index/" class="btn btn-info">
    <i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.back") %>
</a>

</c:param>

</c:import>

