<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>
    <%-- 目次のダイアログ表示場所
    <div id="panel_target"></div>
    --%>

    <div class="panel panel-success" id="ipop">
      <div class="panel-heading" style="cursor: move;" id="ipop_title">
        <button type="button" class="close" data-dismiss="window" aria-hidden="true" id="ipop_close">x</button>
        <h4 class="panel-title">
        <i class="fa fa-list"></i>&nbsp;
        <%= jspUtil.label("knowledge.view.label.toc") %>
        </h4>
      </div>
      <div class="panel-body" id="toc"></div>
    </div>
