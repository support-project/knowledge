<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
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
<h4 class="title"><%= jspUtil.label("knowledge.notify.title") %></h4>


<form action="<%= request.getContextPath()%>/protect.notify/save" method="post" role="form" id="notifyForm">

    <div class="form-group">
    <%= jspUtil.label("knowledge.notify.msg") %>
    </div>
    
    <h5 class="sub_title"><%= jspUtil.label("knowledge.notify.target") %></h5>
    
    <div class="form-group">
        <label for="mail_notify"><%= jspUtil.label("knowledge.notify.target.mail") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="1" name="notifyMail" <%= jspUtil.checked(1, "notifyMail") %> />
            <i class="fa fa-bell-o"></i>&nbsp;<%= jspUtil.label("knowledge.notify.on") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="0" name="notifyMail" <%= jspUtil.checked(0, "notifyMail") %>/>
            <i class="fa fa-bell-slash-o"></i>&nbsp;<%= jspUtil.label("knowledge.notify.off") %>
        </label>
    </div>

    <div class="form-group">
        <label for="mail_notify"><%= jspUtil.label("knowledge.notify.target.desktop") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="1" name="notifyDesktop" <%= jspUtil.checked(1, "notifyDesktop") %> />
            <i class="fa fa-bell-o"></i>&nbsp;<%= jspUtil.label("knowledge.notify.on") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="0" name="notifyDesktop" <%= jspUtil.checked(0, "notifyDesktop") %>/>
            <i class="fa fa-bell-slash-o"></i>&nbsp;<%= jspUtil.label("knowledge.notify.off") %>
        </label>
    </div>
    
    
    <h5 class="sub_title"><%= jspUtil.label("knowledge.notify.conditions") %></h5>
    
    <div class="form-group">
        <label for="mail_notify"><%= jspUtil.label("knowledge.notify.conditions.myitem.comment") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="1" name="myItemComment" <%= jspUtil.checked(1, "myItemComment") %> />
            <i class="fa fa-bell-o"></i>&nbsp;<%= jspUtil.label("knowledge.notify.on") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="0" name="myItemComment" <%= jspUtil.checked(0, "myItemComment") %>/>
            <i class="fa fa-bell-slash-o"></i>&nbsp;<%= jspUtil.label("knowledge.notify.off") %>
        </label>
    </div>
    
    <div class="form-group">
        <label for="mail_notify"><%= jspUtil.label("knowledge.notify.conditions.myitem.like") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="1" name="myItemLike" <%= jspUtil.checked(1, "myItemLike") %> />
            <i class="fa fa-bell-o"></i>&nbsp;<%= jspUtil.label("knowledge.notify.on") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="0" name="myItemLike" <%= jspUtil.checked(0, "myItemLike") %> />
            <i class="fa fa-bell-slash-o"></i>&nbsp;<%= jspUtil.label("knowledge.notify.off") %>
        </label>
    </div>

    <div class="form-group">
        <label for="mail_notify"><%= jspUtil.label("knowledge.notify.conditions.to.save") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="1" name="toItemSave" <%= jspUtil.checked(1, "toItemSave") %> />
            <i class="fa fa-bell-o"></i>&nbsp;<%= jspUtil.label("knowledge.notify.on") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="0" name="toItemSave" <%= jspUtil.checked(0, "toItemSave") %> />
            <i class="fa fa-bell-slash-o"></i>&nbsp;<%= jspUtil.label("knowledge.notify.off") %>
        </label>
    </div>

    <div class="form-group">
        <label for="mail_notify"><%= jspUtil.label("knowledge.notify.conditions.to.comment") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="1" name="toItemComment" <%= jspUtil.checked(1, "toItemComment") %> />
            <i class="fa fa-bell-o"></i>&nbsp;<%= jspUtil.label("knowledge.notify.on") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="0" name="toItemComment" <%= jspUtil.checked(0, "toItemComment") %> />
            <i class="fa fa-bell-slash-o"></i>&nbsp;<%= jspUtil.label("knowledge.notify.off") %>
        </label>
    </div>

    <div class="form-group">
        <label for="mail_notify"><%= jspUtil.label("knowledge.notify.conditions.to.ignore.public") %></label><br/>
        <label class="radio-inline">
            <input type="radio" value="1" name="toItemIgnorePublic" <%= jspUtil.checked(1, "toItemIgnorePublic") %> />
            <i class="fa fa-bell-slash"></i>&nbsp;<%= jspUtil.label("knowledge.notify.ignore.on") %>
        </label>
        <label class="radio-inline">
            <input type="radio" value="0" name="toItemIgnorePublic" <%= jspUtil.checked(0, "toItemIgnorePublic") %>/>
            <i class="fa fa-bell"></i>&nbsp;<%= jspUtil.label("knowledge.notify.ignore.off") %>
        </label>
    </div>
    
    <div class="form-group">
        <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.update") %></button>
        <a href="<%=request.getContextPath()%>/protect.config/index/" class="btn btn-info">
            <i class="fa fa-undo"></i>&nbsp;<%= jspUtil.label("label.back") %>
        </a>
    </div>

</form>








</c:param>

</c:import>

