<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.web.logic.HttpRequestCheckLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_SCRIPTS">
</c:param>


<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.account.changekey.title") %></h4>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <form action="<%= request.getContextPath()%>/protect.account/changerequest" method="post" role="form">
        <input type="hidden" name="<%= HttpRequestCheckLogic.REQ_ID_KEY %>"
            value="<%= jspUtil.out(HttpRequestCheckLogic.REQ_ID_KEY) %>" />
        
            <div class="form-group">
                <label for="userKey"><%= jspUtil.label("knowledge.account.label.email") %></label>
                <input type="text" class="form-control" name="userKey" id="userKey" placeholder="Mail Address" value="<%= jspUtil.out("userKey") %>" 
                />
            </div>
            
            <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.update") %></button>
            <a href="<%= request.getContextPath()%>/protect.account/index" class="btn btn-default">
            <i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.back")%></a>
            
        </form>
    </div>
</div>


</c:param>

</c:import>

