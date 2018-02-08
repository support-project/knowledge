<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

    <div class="file-group">
        <div id="fileupload">
            <label for="input_fileupload"><%= jspUtil.label("knowledge.add.label.files") %></label>
            <br/>
            <span class="btn btn-info fileinput-button btn-xs">
                <i class="fa fa-cloud-upload"></i>&nbsp;<span><%= jspUtil.label("knowledge.add.label.select.file") %></span>
                <input type="file" name="files[]" multiple>
            </span>
        </div>
        <div style="margin-top: 2px;">
            <button type="button" class="btn btn-info btn-xs" id="previewClipbordImage">
                <i class="fa fa-clipboard"></i>&nbsp; <%= jspUtil.label("knowledge.edit.image.upload") %>
            </button>
        </div>
    </div>
    
    <div class="file-group tips_info" id="drop_target">
        <%= jspUtil.label("knowledge.add.label.area.upload") %>
        <%= jspUtil.label("knowledge.add.label.attach.limit", jspUtil.out("uploadMaxMBSize")) %>
    </div>
    <div class="" style="display: none;" id="progress">
        <div class="progress">
            <div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
            0%
            </div>
        </div>
    </div>
    
    <div class="file-group" id="files">
    <c:forEach var="file" items="${files}" >
        <div class="filediv row" id="file-<%= jspUtil.out("file.fileNo") %>">
            <div class="file-image col-xs-1"><img src="<%= jspUtil.out("file.thumbnailUrl") %>" width="20"/></div>
            <div class="file-label col-xs-6"><a href="<%= jspUtil.out("file.url") %>&amp;attachment=true"><%= jspUtil.out("file.name", jspUtil.ESCAPE_HTML, 20) %></a></div>
            <input type="hidden" name="files" value="<%= jspUtil.out("file.fileNo") %>" />
            <div class="file-buttons col-xs-4">
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
    



