<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.web.util.JspUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

    <div class="modal fade" id="stockModal" tabindex="-1" role="dialog" aria-labelledby="stockModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="stockModalLabel"><%= jspUtil.label("knowledge.view.stock.modal.title") %></h4>
                </div>
                <div class="modal-body">
                    <%-- コンテンツ --%>
                    <div id="stockSelect"></div>
                    <span id="stockLink" style="display: none;"> <%= jspUtil.label("knowledge.stock.empty") %><br /> <a
                        href="<%= request.getContextPath() %>/protect.stock/mylist"><%= jspUtil.label("knowledge.stock.label.link") %></a>
                    </span>
                    
                    Page: <span id="stockPage"></span>
                    <nav>
                        <ul class="pager">
                            <li class="">
                                <a onclick="getStockInfoPrevious();">
                                <span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
                                </a>
                            </li>
                            <li class="">
                                <a onclick="getStockInfoNext();"> <%= jspUtil.label("label.next") %>
                                <span aria-hidden="true">&rarr;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
                <div class="modal-footer">
                    <div class="form-group">
                        <input type="text" class="form-control" name="stockComment" id="stockComment"
                            placeholder="<%= jspUtil.label("label.comment") %>" value="">
                    </div>

                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        <i class="fa fa-close"></i>&nbsp;<%= jspUtil.label("label.close") %></button>
                    <button type="button" class="btn btn-primary" id="saveStockButton" onclick="saveStocks(<%= jspUtil.out("knowledgeId") %>);">
                        <i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.save") %></button>
                </div>
            </div>
        </div>
    </div>

    
    
    