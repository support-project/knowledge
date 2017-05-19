<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.web.logic.HttpRequestCheckLogic"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/page-protect-account.css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload-ui.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/cropper/dist/cropper.min.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<!-- build:js(src/main/webapp) js/page-protect-account.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/cropper/dist/cropper.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/account.js"></script>
<!-- endbuild -->
</c:param>


<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.account.title") %></h4>

<% if(jspUtil.is(1, "authLdap")) { %>
<div class="alert alert-warning alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <strong>Warning</strong><br/>
    - <%= jspUtil.label("knowledge.account.info.ldap2") %>
</div>
<% } %>

<div class="row">
    <div class="col-sm-3 col-md-2">
        <div id="icondiv">
            <img id="icon" src="<%= request.getContextPath()%>/open.account/icon/<%= jspUtil.id() %>" width="64" height="64" />
        </div>
        <form action="<%= request.getContextPath()%>/protect.account/iconupload" method="post" role="form" enctype="multipart/form-data">
        <div class="form-group" style="display: none;" id="progress">
            <div class="progress">
                <div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">0%</div>
            </div>
        </div>
        <div class="form-group" id="drop_target">
            <%= jspUtil.label("knowledge.account.label.icon.drop") %><br/>
        </div>
        <div id="fileupload">
            <span class="btn btn-info fileinput-button">
                <i class="fa fa-cloud-upload"></i>&nbsp;<span><%= jspUtil.label("knowledge.account.label.icon.select") %></span>
                <input type="file" name="files[]" id="file">
            </span>
        </div>
        </form>
    </div>

    <div class="col-sm-9 col-md-10">
        <form action="<%= request.getContextPath()%>/protect.account/update" method="post" role="form">
        <input type="hidden" name="<%= HttpRequestCheckLogic.REQ_ID_KEY %>"
            value="<%= jspUtil.out(HttpRequestCheckLogic.REQ_ID_KEY) %>" />
        
<% if(jspUtil.is(1, "authLdap")) { %>
            <div class="form-group">
                <label for="userKey"><%= jspUtil.label("knowledge.auth.label.id") %></label>
                <input type="text" class="form-control" name="userKey" id="userKey" placeholder="<%= jspUtil.label("knowledge.auth.label.id") %>"
                    value="<%= jspUtil.out("userKey") %>" readonly="readonly" />
            </div>
            <div class="form-group">
                <label for="userKey"><%= jspUtil.label("knowledge.auth.label.mail") %><%= jspUtil.label("knowledge.account.info.mail") %></label>
                <input type="text" class="form-control" name="mailAddress" id="mailAddress" placeholder="<%= jspUtil.label("knowledge.auth.label.mail") %>"
                    value="<%= jspUtil.out("mailAddress") %>" readonly="readonly" />
            </div>
            <div class="form-group">
                <label for="userName"><%= jspUtil.label("knowledge.signup.label.name") %></label>
                <input type="text" class="form-control" name="userName" id="userName" placeholder="User Name" 
                    value="<%= jspUtil.out("userName") %>" readonly="readonly" />
            </div>
<% } else { %>
            <div class="form-group">
                <label for="userKey"><%= jspUtil.label("knowledge.signup.label.mail") %></label>
                <input type="text" class="form-control" name="userKey" id="userKey" placeholder="Mail Address" value="<%= jspUtil.out("userKey") %>" 
                    <%= jspUtil.isnot(SystemConfig.USER_ADD_TYPE_VALUE_USER, "userAddType", "readonly=\"readonly\"") %>
                />
                <% if (jspUtil.is(SystemConfig.USER_ADD_TYPE_VALUE_MAIL, "userAddType")) { %>
                <br/><a class="btn btn-success" href="<%= request.getContextPath()%>/protect.account/changekey"><%= jspUtil.label("knowledge.account.change.email") %></a>
                <% } %>
            </div>
            <div class="form-group">
                <label for="userName"><%= jspUtil.label("knowledge.signup.label.name") %></label>
                <input type="text" class="form-control" name="userName" id="userName" placeholder="User Name" value="<%= jspUtil.out("userName") %>" />
            </div>

            <div class="form-group">
                <label for="password"><%= jspUtil.label("knowledge.signup.label.password") %><%= jspUtil.label("knowledge.account.label.password.msg") %></label>
                <input type="password" class="form-control" name="password" id="password" placeholder="Password" value="<%= jspUtil.out("password") %>" />
            </div>
            <div class="form-group">
                <label for="confirm_password"><%= jspUtil.label("knowledge.signup.label.confirm.password") %></label>
                <input type="password" class="form-control" name="confirm_password" id="confirm_password" placeholder="Confirm Password" value="<%= jspUtil.out("confirm_password") %>" />
            </div>
<% } %>


            <div class="form-group">
                <label for="input_no"><%= jspUtil.label("label.regist.datetime") %> / <%= jspUtil.label("label.update.datetime") %></label>
                <p class="form-control-static">
                    <i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("insertDatetime")%> / 
                    <i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("updateDatetime")%>
                </p>
            </div>

<% if (!jspUtil.is(1, "authLdap")) { %>
            <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.update") %></button>
<% } %>
            <a href="<%= request.getContextPath()%>/protect.account/withdrawal" class="btn btn-default">
            <i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.withdrawal")%></a>
            
            <a href="<%=request.getContextPath()%>/protect.config/index/" class="btn btn-info">
                <i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.back") %>
            </a>
            
        </form>
    </div>
</div>


</c:param>

</c:import>

