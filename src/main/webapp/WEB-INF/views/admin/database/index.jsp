<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<style>
.radio_block {
    margin-bottom: 10px;
}
</style>
<!-- build:css(src/main/webapp) css/page-database.css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap-fileinput/css/fileinput.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminpage.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-database.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-fileinput/js/fileinput.min.js"></script>
<!-- endbuild -->

<script>
$("#input-id").fileinput();
</script>
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.navbar.data.backup") %>
<span class="backlink">
<a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
</span>
</h4>

<div class="alert alert-warning alert-dismissible" role="alert">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <strong>Warning!</strong> <%= jspUtil.label("knowledge.data.label.restore.msg.danger") %>
</div>

<div class="alert alert-info alert-dismissible" role="alert">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  <strong>Information</strong> <%= jspUtil.label("knowledge.data.label.restore.msg.info") %>
</div>



<%= jspUtil.label("knowledge.data.msg") %>




<br/>
<br/>
<br/>
<h5 class="sub_title"><i class="fa fa-caret-right "></i>&nbsp; <%= jspUtil.label("knowledge.data.label.active") %></h5>
<%= jspUtil.label("knowledge.data.label.active.msg") %><br/>
<%= jspUtil.label("knowledge.data.label.active.msg2") %><br/>
<%= jspUtil.label("knowledge.data.label.active.msg3") %><br/>
<%= jspUtil.label("knowledge.data.label.active.msg4") %><br/>

<br/>
<%= jspUtil.label("knowledge.data.label.active.status") %>: 
<% if (jspUtil.is(Boolean.TRUE, "active")) { %>
<%= jspUtil.label("knowledge.data.label.active.status.active") %>
<a href="<%= request.getContextPath()%>/admin.database/stop" class="btn btn-danger">
    <i class="fa fa-stop"></i>&nbsp;<%= jspUtil.label("knowledge.data.label.active.status.to.stop") %>
</a>
<% } else { %>
<%= jspUtil.label("knowledge.data.label.active.status.stop") %>
<a href="<%= request.getContextPath()%>/admin.database/start" class="btn btn-success">
    <i class="fa fa-play"></i>&nbsp;<%= jspUtil.label("knowledge.data.label.active.status.to.active") %>
</a>
<% } %>




<br/>
<br/>
<br/>
<h5 class="sub_title"><i class="fa fa-caret-right "></i>&nbsp; <%= jspUtil.label("knowledge.data.label.backup") %></h5>
<%= jspUtil.label("knowledge.data.label.backup.msg") %>
<br/>
<a href="<%= request.getContextPath()%>/admin.database/backup" class="btn btn-info">
    <i class="fa fa-cloud-download"></i>&nbsp;<%= jspUtil.label("label.backup") %>
</a>


<br/>
<br/>
<br/>
<h5 class="sub_title"><i class="fa fa-caret-right "></i>&nbsp; <%= jspUtil.label("knowledge.data.label.restore") %></h5>
<%= jspUtil.label("knowledge.data.msg") %>





<form action="<%= request.getContextPath()%>/admin.database/restore" method="post" role="form" enctype="multipart/form-data">
    <div class="form-group">
        <input id="input-id" type="file" class="file" data-preview-file-type="text" name="upload">
    </div>
    <button type="submit" class="btn btn-danger"><i class="fa fa-cloud-upload"></i>&nbsp;<%= jspUtil.label("label.restore") %></button>
</form>


</c:param>

</c:import>

