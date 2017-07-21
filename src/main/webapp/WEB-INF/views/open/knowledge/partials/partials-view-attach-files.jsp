<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="java.util.List"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.vo.UploadFile"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<% int MAX_COUNT = 6; %>

<%
    int num = 0;
    List<UploadFile> attachs = jspUtil.getValue("files", List.class);
    for (int i = 0; i < attachs.size(); i++) {
        UploadFile attach = attachs.get(i);
        if (attach.getCommentNo() == null || attach.getCommentNo() == 0) {
            num++;
        }
    }
    String e = "hide";
    if (num > 0) {
        e = "";
    }
%>

<div id="attachFilesPanel"></div>

<div class="panel panel-info <%= e %>">
    <div class="panel-heading">
        <h4 class="panel-title">
            <i class="fa fa-download"></i>&nbsp;<%= jspUtil.label("knowledge.view.label.attach") %> 
            &nbsp;&nbsp;&nbsp;
            - [<%= num %>]
        </h4>
    </div>
    <div class="panel-body">
        <% 
            int count = 0;
            String hide = "";
        %>
        <div class="row">
        <c:forEach var="file" items="${files}" >
            <% count++; %>
            <c:if test="${file.commentNo == 0}">
            <%
                if (count > MAX_COUNT) { 
                    hide = "hide";
                }
            %>
            <div class="downloadfile <%= hide %>">
                <div class="col-xs-6">
                    <a href="<%=jspUtil.out("file.url")%>&amp;attachment=true">
                    <img src="<%=jspUtil.out("file.thumbnailUrl")%>" width="20"/> 
                    </a>
                    <a href="<%=jspUtil.out("file.url")%>&amp;attachment=true"> <%=jspUtil.out("file.name", jspUtil.ESCAPE_HTML, 100)%></a>
                </div>
            </div>
            </c:if>
        </c:forEach>
        </div>
        <% if (count > MAX_COUNT) { %>
        <div class="text-right">
            <a id="more_attach">more...</a>
        </div>
        <% } %>
    </div>
</div>





