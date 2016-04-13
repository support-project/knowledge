<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.common.config.INT_FLAG"%>
<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    JspUtil jspUtil = new JspUtil(request, pageContext);
%>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

    <c:param name="PARAM_HEAD">
        <link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/markdown.css") %>" />
    </c:param>

    <c:param name="PARAM_SCRIPTS">
        <script type="text/javascript" src="<%= request.getContextPath() %>/bower/emoji-parser/main.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/bower/moment/min/moment.min.js"></script>
        <script type="text/javascript" src="<%=jspUtil.mustReloadFile("/js/knowledge-common.js")%>"></script>
        <script type="text/javascript" src="<%=jspUtil.mustReloadFile("/js/notice-list.js")%>"></script>
    </c:param>

    <c:param name="PARAM_CONTENT">
        <h4 class="title"><%=jspUtil.label("knowledge.notice.title")%></h4>
        
        <div id="notices" class="list-group"></div>

        <form id="editForm">
            <!-- Modal -->
            <div class="modal fade" id="noticeModal" tabindex="-1" role="dialog" aria-labelledby="noticeModalLabel">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title" id="notice_title_area"></h4>
                        </div>
                        <div class="modal-body">
                            <div class="preview markdown" id="notice_content_area">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <% if (jspUtil.logined()) { %>
                            <label>
                                <input type="checkbox" id="showagain"><%=jspUtil.label("knowledge.notice.label.showagain")%>
                            </label>
                            <% } %>
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>

    </c:param>

</c:import>

