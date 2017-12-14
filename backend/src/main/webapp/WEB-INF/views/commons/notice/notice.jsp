<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
    JspUtil jspUtil = new JspUtil(request, pageContext);
%>

<!-- Modal -->
<div class="modal fade" id="noticeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="notice_title_area"><%=jspUtil.label("knowledge.notice.title")%></h4>
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
                <button type="button" class="btn btn-success" disabled="disabled" id="notice_prev_button">
                    <li class="fa fa-chevron-circle-left"></li>&nbsp;Prev
                </button>
                <button type="button" class="btn btn-success" disabled="disabled" id="notice_next_button">
                    Next&nbsp;<li class="fa fa-chevron-circle-right"></li>
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal"><li class="fa fa-times"></li>&nbsp;Close</button>
            </div>
        </div>
    </div>
</div>
