<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="java.util.List"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.vo.UploadFile"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<% int MAX_COUNT = 5; %>

<div class="panel panel-primary">
    <div class="panel-heading">
        <h4 class="panel-title">
            <i class="fa fa-download"></i>&nbsp;<%= jspUtil.label("knowledge.view.label.attach") %> 
            &nbsp;&nbsp;&nbsp;
            <%
                int num = 0;
                List<UploadFile> attachs = jspUtil.getValue("files", List.class);
                for (int i = 0; i < attachs.size(); i++) {
                    UploadFile attach = attachs.get(i);
                    if (attach.getCommentNo() == null || attach.getCommentNo() == 0) {
                        num++;
                    }
                }
            %>
            - [<%= num %>]
        </h4>
    </div>
    <div class="panel-body">
        <% 
            int count = 0;
            String hide = "";
        %>
        <c:forEach var="file" items="${files}" >
            <% count++; %>
            <c:if test="${file.commentNo == 0}">
            <%
                if (count > MAX_COUNT) { 
                    hide = "hide";
                }
            %>
            <div class="row">
                <div class="downloadfile <%= hide %>">
                <div class="col-xs-1">
                    <a href="<%=jspUtil.out("file.url")%>">
                    <img src="<%=jspUtil.out("file.thumbnailUrl")%>" width="20"/> 
                    </a>
                </div>
                <div class="col-xs-10">
                    <a href="<%=jspUtil.out("file.url")%>"> <%=jspUtil.out("file.name", jspUtil.ESCAPE_HTML, 20)%></a>
                </div>
                </div>
            </div>
            </c:if>
        </c:forEach>
        <% if (count > MAX_COUNT) { %>
        <div class="text-right">
            <a id="more_attach">more...</a>
        </div>
        <% } %>
    </div>
</div>





