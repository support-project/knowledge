<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="org.support.project.web.util.JspUtil"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/page-token.css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap-datepicker/dist/css/bootstrap-datepicker3.min.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-token.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/clipboard/dist/clipboard.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-datepicker/dist/locales/bootstrap-datepicker.en-GB.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-datepicker/dist/locales/bootstrap-datepicker.ja.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/moment/min/moment.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/token.js"></script>
<!-- endbuild -->

<script>
var _MSG_COPIED = '<%= jspUtil.label("knowledge.token.msg.copy") %>';
</script>
</c:param>


<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.token.title") %></h4>

<form action="<%= request.getContextPath()%>/protect.token/save" method="post" role="form">
<div class="form-group">
    <label for="token">
        <%= jspUtil.label("knowledge.token.label.token") %>
        <small><%= jspUtil.label("knowledge.token.msg.token") %></small>
    </label>
     <div class="input-group">
        <input type="text" class="form-control" name="token" id="token" placeholder="<%= jspUtil.label("knowledge.token.label.token") %>" 
            readonly="readonly" value="<%= jspUtil.attr("token") %>" />
        <span class="input-group-addon" id="copyaddon" data-clipboard-text="<%= jspUtil.attr("token") %>">
            <i class="fa fa-files-o" aria-hidden="true"></i>
        </span>
      </div>
</div>
<div class="form-group">
    <label for="expires">
        <%= jspUtil.label("knowledge.token.label.expires") %>
        <small><%= jspUtil.label("knowledge.token.msg.expires") %></small>
    </label>
     <div class="input-group">
        <input type="text" class="form-control datepicker" name="expires" id="expires" placeholder="<%= jspUtil.label("knowledge.token.label.expires") %>" 
            value="<%= jspUtil.attr("expires") %>" />
        <span class="input-group-addon"><i class="fa fa-calendar" aria-hidden="true"></i></span>
      </div>
</div>

<c:if test="${empty token}">
<button type="submit" class="btn btn-primary"><%= jspUtil.label("knowledge.token.label.create") %></button>
</c:if>
<c:if test="${!empty token}">
<button type="submit" class="btn btn-primary"><%= jspUtil.label("knowledge.token.label.update") %></button>
<button type="submit" class="btn btn-danger" formaction="<%= request.getContextPath()%>/protect.token/delete"><%= jspUtil.label("knowledge.token.label.delete") %></button>
</c:if>

<a href="<%=request.getContextPath()%>/protect.config/index/" class="btn btn-info">
    <i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.back") %>
</a>

</form>

</c:param>

</c:import>

