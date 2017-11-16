<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    JspUtil jspUtil = new JspUtil(request, pageContext);
%>

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
        <%
            if (jspUtil.is(Boolean.TRUE, "transfer")) {
        %>
        <script>
        var webSocket;
        window.onload = function() {
            var forRtoA = document.createElement('a');
            forRtoA.href = '<%= request.getContextPath() %>/data_transfer';
            console.log(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
            webSocket = new WebSocket(forRtoA.href.replace("http://", "ws://").replace("https://", "wss://"));
            webSocket.onopen = function() {
            }
            webSocket.onclose = function() {
                console.log('onclose');
                $("#transfer_msg").alert('close');
            };
            webSocket.onmessage = function(message) {
                //console.log('[RECEIVE] ');
                var result = JSON.parse(message.data);
                console.log(result);
                if (result.message.lastIndexOf('Processing has been completed', 0) === 0) {
                    $.notify(result.message, 'info');
                    $("#transfer_msg").alert('close');
                }
            };
            webSocket.onerror = function(message) {
            };
        }
        </script>
        <%
            }
        %>
    </c:param>



    <c:param name="PARAM_CONTENT">
        <h4 class="title"><%=jspUtil.label("knowledge.navbar.data.connect")%>
        <span class="backlink">
        <a href="<%= request.getContextPath() %>/admin.systemconfig/index"><%= jspUtil.label("knowledge.config.system.back.to.list") %></a>
        </span>
        </h4>

        <%
            if (!jspUtil.is(Boolean.TRUE, "postgres")) {
        %>
        <div class="alert alert-info alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>Information</strong>
            <%=jspUtil.label("knowledge.connection.msg.recommend.postgres")%>
        </div>
        <% } %>
        
        <%
            if (jspUtil.is(Boolean.TRUE, "custom")) {
        %>
        <div class="alert alert-success alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>Information</strong>
            <%=jspUtil.label("knowledge.connection.msg.custom.enable")%>
        </div>

        <%
            if (jspUtil.is(Boolean.TRUE, "transfer")) {
        %>
        <div class="alert alert-warning alert-dismissible" role="alert" id="transfer_msg">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>Warning!</strong>
            <%=jspUtil.label("knowledge.connection.msg.custom.transfer")%>
        </div>
        <%
            }
        %>

        <%
            } else {
        %>
        <div class="alert alert-info alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>Information</strong>
            <%=jspUtil.label("knowledge.connection.msg.custom.disable")%>
        </div>
        <%
            }
        %>

        <form action="<%=request.getContextPath()%>/admin.database/custom_save" method="post" role="form">
            <div class="form-group">
                <!-- 
                <label for="driverClass"><%=jspUtil.label("knowledge.connection.label.driverClass")%></label> <br /> <label
                    class="radio-inline radio_block"> <input type="radio" value="org.h2.Driver" name="driverClass" id="driverClass_h2"
                    <%=jspUtil.checked("org.h2.Driver", "driverClass", true)%> /> <i class="fa fa-chain fa-lg"></i>&nbsp;org.h2.Driver(1.4.183)
                </label>
                <br />
                -->
                <label class="radio-inline radio_block"> <input type="radio" value="org.postgresql.Driver" name="driverClass"
                    id="driverClass_postgresql" <%=jspUtil.checked("org.postgresql.Driver", "driverClass", false)%> checked="checked"/>
                    <i class="fa fa-chain fa-lg"></i>&nbsp;org.postgresql.Driver(9.3-1103-jdbc41)
                </label>
            </div>
            <div class="form-group">
                <label for="uRL">
                    <%=jspUtil.label("knowledge.connection.label.URL")%>
                    <span class="small">(<%=jspUtil.label("knowledge.connection.msg.info.url")%>)</span>
                </label>
                <input type="text" class="form-control" name="uRL"
                    id="uRL" placeholder="<%=jspUtil.label("knowledge.connection.msg.info.url")%>" value="<%=jspUtil.out("uRL")%>" />
            </div>
            <div class="form-group">
                <label for="user"><%=jspUtil.label("knowledge.connection.label.user")%></label> <input type="text" class="form-control" name="user"
                    id="user" placeholder="user" value="<%=jspUtil.out("user")%>" />
            </div>
            <div class="form-group">
                <label for="password"><%=jspUtil.label("knowledge.connection.label.password")%></label> <input type="password" class="form-control"
                    name="password" id="password" placeholder="password" value="<%=jspUtil.out("password")%>" />
            </div>
            <div class="form-group">
                <label for="schema"><%=jspUtil.label("knowledge.connection.label.schema")%></label> <input type="text" class="form-control"
                    name="schema" id="schema" placeholder="schema" value="<%=jspUtil.out("schema")%>" />
            </div>
            <div class="form-group">
                <label for="maxConn"><%=jspUtil.label("knowledge.connection.label.maxConn")%></label> <input type="text" class="form-control"
                    name="maxConn" id="maxConn" placeholder="maxConn" value="<%=jspUtil.out("maxConn")%>" />
            </div>
            <div class="form-group">
                <label for="autocommit"><%=jspUtil.label("knowledge.connection.label.autocommit")%></label> <input type="text" class="form-control"
                    name="autocommit" id="autocommit" placeholder="autocommit" value="<%=jspUtil.out("autocommit")%>" />
            </div>


            <button type="submit" class="btn btn-primary">
                <i class="fa fa-save"></i>&nbsp;<%=jspUtil.label("label.save")%></button>

            <a href="<%=request.getContextPath()%>/admin.database/custom_delete" class="btn btn-danger"> <i class="fa fa-remove"></i>&nbsp;<%=jspUtil.label("label.delete")%>
            </a>

            <%
                if (jspUtil.is(Boolean.TRUE, "custom") && !(jspUtil.is(Boolean.TRUE, "transfer"))) {
            %>
            <a href="<%=request.getContextPath()%>/admin.database/data_transfer" class="btn btn-success"> <i class="fa fa-copy"></i>&nbsp;<%=jspUtil.label("knowledge.connection.label.transfer")%>
            </a> <a href="<%=request.getContextPath()%>/admin.database/data_transfer_back" class="btn btn-success"> <i class="fa fa-copy"></i>&nbsp;<%=jspUtil.label("knowledge.connection.label.transfer.back")%>
            </a>
            <%
                }
            %>

        </form>



    </c:param>

</c:import>

