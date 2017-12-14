<%@page import="org.support.project.web.config.CommonWebParameter"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<!-- build:css(src/main/webapp) css/page-comment.css -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-file-upload/css/jquery.fileupload-ui.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/At.js/dist/css/jquery.atwho.min.css" />

<link rel="stylesheet" href="<%= request.getContextPath() %>/css/knowledge-edit.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/markdown.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/slide.css" />
<!-- endbuild -->
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/x-mathjax-config">
MathJax.Hub.Config({
    tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]},
    skipStartupTypeset: true
  });
</script>

<script>
var _CONFIRM = '<%= jspUtil.label("knowledge.edit.label.confirm.delete") %>';
var _UPLOADED = '<%= jspUtil.label("knowledge.edit.label.uploaded") %>';
var _DELETE_LABEL= '<%= jspUtil.label("label.delete") %>';
var _FAIL_UPLOAD = '<%= jspUtil.label("knowledge.edit.label.fail.upload") %>';
var _REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.delete.upload") %>';
var _FAIL_REMOVE_FILE = '<%= jspUtil.label("knowledge.edit.label.fail.delete.upload") %>';
var _CONFIRM = '<%= jspUtil.label("knowledge.edit.label.confirm.delete") %>';
var _SET_IMAGE_LABEL= '<%= jspUtil.label("knowledge.edit.set.image.path") %>';
var _SET_SLIDE_LABEL= '<%= jspUtil.label("knowledge.edit.set.slide.path") %>';
</script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML,Safe"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/emoji-parser/main.min.js"></script>

<!-- build:js(src/main/webapp) js/page-comment.js -->
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/jquery-file-upload/js/jquery.iframe-transport.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/bower/Caret.js/dist/jquery.caret.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/At.js/dist/js/jquery.atwho.min.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/js/knowledge-common.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/comment.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/knowledge-view-attachfile.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/slide.js"></script>

<script type="text/javascript" src="<%= request.getContextPath() %>/js/emojilist.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/js/autocomplete.js"></script>
<!-- endbuild -->

</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.comment.edit") %></h4>

<form action="<%= request.getContextPath()%>/protect.knowledge/update_comment" method="post" role="form" id="commentForm" enctype="multipart/form-data">

    <div class="form-group">
        <label for="comment">Comment
        <span class="helpMarkdownLabel">
        <a data-toggle="modal" data-target="#helpMarkdownModal">Markdown supported</a>
        </span>
        </label>
        <input type="hidden" name="commentNo" value="<%= jspUtil.out("commentNo") %>" id="commentNo"/>
        <textarea class="form-control" name="comment" rows="8" 
        placeholder="<%= jspUtil.label("knowledge.add.label.content") %>" id="comment"><%= jspUtil.out("comment") %></textarea>
        <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/people" data-target="#emojiPeopleModal">people</a>
        <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/nature" data-target="#emojiNatureModal">nature</a>
        <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/objects" data-target="#emojiObjectsModal">objects</a>
        <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/places" data-target="#emojiPlacesModal">places</a>
        <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/symbols" data-target="#emojiSymbolsModal">symbols</a>
    </div>
    
    
        <div class="form-group">
            <label for="input_fileupload"><%= jspUtil.label("knowledge.add.label.files") %></label><br/>
            <div id="fileupload">
                <span class="btn btn-info fileinput-button">
                    <i class="fa fa-cloud-upload"></i>&nbsp;<span><%= jspUtil.label("knowledge.add.label.select.file") %></span>
                    <input type="file" name="files[]" multiple>
                </span>
            </div>
        </div>
        <div class="form-group" id="drop_target">
            <%= jspUtil.label("knowledge.add.label.area.upload") %>
        </div>
        <div class="form-group" style="display: none;" id="progress">
            <div class="progress">
                <div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
                0%
                </div>
            </div>
        </div>
        <div class="form-group" id="files">
        <c:forEach var="file" items="${comment_files}" >
            <div class="filediv row" id="file-<%= jspUtil.out("file.fileNo") %>">
                <div class="file-image col-xs-1"><img src="<%= jspUtil.out("file.thumbnailUrl") %>" /></div>
                <div class="file-label col-xs-6"><a href="<%= jspUtil.out("file.url") %>&amp;attachment=true"><%= jspUtil.out("file.name") %></a></div>
                <input type="hidden" name="files" value="<%= jspUtil.out("file.fileNo") %>" />
                <div class="col-xs-4">
                <% if (jspUtil.is("image", "file.type")) { %>
                <button type="button" class="btn btn-success btn-xs" onclick="setImagePath('<%= jspUtil.out("file.url") %>', '<%= jspUtil.out("file.name") %>')">
                    <i class="fa fa-file-image-o"></i>&nbsp;<%= jspUtil.label("knowledge.edit.set.image.path") %>
                </button>
                <br/>
                <% } else if (jspUtil.is("slide", "file.type")) { %>
                <button type="button" class="btn btn-success btn-xs" onclick="setSlidePath('<%= jspUtil.out("file.fileNo") %>', '<%= jspUtil.out("file.name") %>')">
                    <i class="fa fa-television"></i>&nbsp;<%= jspUtil.label("knowledge.edit.set.slide.path") %>
                </button>
                <br/>
                <% } %>
                <button type="button" class="btn btn-danger btn-xs" onclick="removeAddedFile(<%= jspUtil.out("file.fileNo") %>)">
                    <i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %>
                </button>
                </div>
            </div>
        </c:forEach>
        </div>
    
    
    
    <button type="submit" class="btn btn-primary">
        <i class="fa fa-save"></i>&nbsp;
        <%= jspUtil.label("label.update") %>
    </button>
    
    <button type="button" class="btn btn-info" onclick="preview();"><i class="fa fa-play-circle"></i>&nbsp;<%= jspUtil.label("label.preview") %></button>
    
    <button type="button" class="btn btn-danger" onclick="deleteComment();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
    
    <a href="<%= request.getContextPath()%>/protect.knowledge/view/<%= jspUtil.out("knowledgeId") %>" class="btn btn-warning">
        <i class="fa fa-undo"></i>&nbsp;
        <%= jspUtil.label("label.cancel") %>
    </a>
    
</form>

<br/>
<p class="preview markdown" id="preview"></p>
<span style="display: none;" id="comment_text">
</span>


<jsp:include page="markdown.jsp"></jsp:include>
<jsp:include page="../../open/emoji/cheatsheet.jsp"></jsp:include>

</c:param>

</c:import>


