<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="java.util.List"%>
<%@page import="org.support.project.knowledge.entity.KnowledgesEntity"%>
<%@page import="org.support.project.knowledge.entity.CommentsEntity"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

    <%
        List commentList = jspUtil.getValue("comments", List.class);
                if (commentList != null && !commentList.isEmpty()) {
    %>
    <hr />
    <h5 id="comments">
        <i class="fa fa-comments-o"></i>&nbsp;<%=jspUtil.label("knowledge.view.comment.label")%>
    </h5>
    <c:forEach var="comment" items="${comments}" varStatus="status">
        <%
            CommentsEntity comment = jspUtil.getValue("comment", CommentsEntity.class);
                            Integer knowledge = jspUtil.getValue("insertUser", Integer.class);
                            if (!comment.getInsertUser().equals(knowledge)) {
        %>
        <div class="row">
            <div class="col-sm-12">
                [<%=jspUtil.label("label.registration")%>]
                <%=jspUtil.date("comment.updateDatetime")%>
                [<%=jspUtil.out("comment.insertUserName")%>]
                <%
                if (comment.isUpdate()) {
            %>
                <br />[<%=jspUtil.label("label.update")%>]
                <%=jspUtil.date("comment.updateDatetime")%>
                [<%=jspUtil.out("comment.updateUserName")%>]
                <%
                    }
                %>

                <%
                    if (jspUtil.isAdmin() || jspUtil.is(jspUtil.id(), "comment.insertUser") || (Boolean) request.getAttribute("edit")) {
                %>
                &nbsp; <a class="btn btn-primary btn-xs"
                    href="<%=request.getContextPath()%>/protect.knowledge/edit_comment/<%=comment.getCommentNo()%>"> <i class="fa fa-edit"></i>
                    <%=jspUtil.label("label.edit")%>
                </a> &nbsp;
                <button class="btn btn-info btn-xs <%if (comment.getCommentStatus() == 1) {%>hide<%}%>"
                    onclick="collapse('<%=comment.getCommentNo()%>', 1);" id="collapse_on_<%=comment.getCommentNo()%>">
                    <i class="fa fa-minus-square-o"></i>
                    <%=jspUtil.label("knowledge.view.comment.collapse")%>
                </button>
                &nbsp;
                <button class="btn btn-warning btn-xs <%if (comment.getCommentStatus() != 1) {%>hide<%}%>"
                    onclick="collapse('<%=comment.getCommentNo()%>', 0);" id="collapse_off_<%=comment.getCommentNo()%>">
                    <i class="fa fa-plus-square-o"></i>
                    <%=jspUtil.label("knowledge.view.comment.open")%>
                </button>
                <%
                    }
                %>

            </div>
        </div>
        <div class="question_Box <%if (comment.getCommentStatus() == 1) {%>hide<%}%>" id="comment_<%=comment.getCommentNo()%>">
            <div class="question_image">
                <img src="<%=request.getContextPath()%>/images/loader.gif"
                    data-echo="<%=request.getContextPath()%>/open.account/icon/<%=jspUtil.out("comment.insertUser")%>" alt="icon" width="64"
                    height="64" />
            </div>
            <div class="arrow_question markdown">
                <%=jspUtil.out("comment.comment", JspUtil.ESCAPE_NONE)%>
                <!-- コメントに付けた添付ファイルの表示 -->
                <c:forEach var="file" items="${files}">
                    <c:if test="${file.commentNo == comment.commentNo}">
                        <div class="downloadfile">
                            <img src="<%=jspUtil.out("file.thumbnailUrl")%>" /> <a href="<%=jspUtil.out("file.url")%>"> <%=jspUtil.out("file.name")%>
                            </a>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <!-- /.arrow_question -->
        </div>
        <!-- /.question_Box -->

        <div class="<%if (comment.getCommentStatus() != 1) {%>hide<%}%> text-left collapse_comment"
            id="comment_collapse_<%=comment.getCommentNo()%>">
            <%=jspUtil.label("knowledge.view.comment.collapsed")%>
            <br />
        </div>

        <%
            } else {
        %>
        <div class="row">
            <div class="col-sm-12" style="text-align: right;">

                <%
                    if (jspUtil.isAdmin() || jspUtil.is(jspUtil.id(), "comment.insertUser") || (Boolean) request.getAttribute("edit")) {
                %>
                &nbsp; <a class="btn btn-primary btn-xs"
                    href="<%=request.getContextPath()%>/protect.knowledge/edit_comment/<%=comment.getCommentNo()%>"> <i class="fa fa-edit"></i>
                    <%=jspUtil.label("label.edit")%>
                </a> &nbsp;
                <button class="btn btn-info btn-xs <%if (comment.getCommentStatus() == 1) {%>hide<%}%>"
                    onclick="collapse('<%=comment.getCommentNo()%>', 1);" id="collapse_on_<%=comment.getCommentNo()%>">
                    <i class="fa fa-minus-square-o"></i>
                    <%=jspUtil.label("knowledge.view.comment.collapse")%>
                </button>
                &nbsp;
                <button class="btn btn-warning btn-xs <%if (comment.getCommentStatus() != 1) {%>hide<%}%>"
                    onclick="collapse('<%=comment.getCommentNo()%>', 0);" id="collapse_off_<%=comment.getCommentNo()%>">
                    <i class="fa fa-plus-square-o"></i>
                    <%=jspUtil.label("knowledge.view.comment.open")%>
                </button>
                <%
                    }
                %>

                [<%=jspUtil.label("label.registration")%>]
                <%=jspUtil.date("comment.updateDatetime")%>
                [<%=jspUtil.out("comment.insertUserName")%>]
                <%
                    if (comment.isUpdate()) {
                %>
                <br />[<%=jspUtil.label("label.update")%>]
                <%=jspUtil.date("comment.updateDatetime")%>
                [<%=jspUtil.out("comment.updateUserName")%>]
                <%
                    }
                %>

            </div>
        </div>
        <div class="question_Box <%if (comment.getCommentStatus() == 1) {%>hide<%}%>" id="comment_<%=comment.getCommentNo()%>">
            <div class="answer_image">
                <img src="<%=request.getContextPath()%>/images/loader.gif"
                    data-echo="<%=request.getContextPath()%>/open.account/icon/<%=jspUtil.out("comment.insertUser")%>" alt="icon" width="64"
                    height="64" />
            </div>
            <div class="arrow_answer markdown">
                <%=jspUtil.out("comment.comment", JspUtil.ESCAPE_NONE)%>
                <!-- コメントに付けた添付ファイルの表示 -->
                <c:forEach var="file" items="${files}">
                    <c:if test="${file.commentNo == comment.commentNo}">
                        <div class="downloadfile">
                            <img src="<%=jspUtil.out("file.thumbnailUrl")%>" /> <a href="<%=jspUtil.out("file.url")%>"> <%=jspUtil.out("file.name")%>
                            </a>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <!-- /.arrow_answer -->
        </div>
        <!-- /.question_Box -->

        <div class="<%if (comment.getCommentStatus() != 1) {%>hide<%}%> text-right collapse_comment"
            id="comment_collapse_<%=comment.getCommentNo()%>">
            <%=jspUtil.label("knowledge.view.comment.collapsed")%>
            <br />
        </div>
        <%
            }
        %>
    </c:forEach>
    <br />
    <br />
    <%
        }
    %>


    