<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

            <c:forEach var="file" items="${files}">
                <c:if test="${file.commentNo == 0}">
                    <div class="downloadfile">
                        <img src="<%=jspUtil.out("file.thumbnailUrl")%>" /> <a href="<%=jspUtil.out("file.url")%>"> <%=jspUtil.out("file.name")%>
                        </a>
                    </div>
                </c:if>
            </c:forEach>


