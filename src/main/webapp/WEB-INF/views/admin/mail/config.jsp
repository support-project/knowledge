<%@page import="redcomet.common.config.INT_FLAG"%>
<%@page import="redcomet.knowledge.vo.Roles"%>
<%@page import="redcomet.web.util.JspUtil"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtil jspUtil = new JspUtil(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_SCRIPTS">
<script>
function deleteMail() {
	bootbox.confirm("本当に削除しますか?", function(result) {
		if (result) {
			$('#mailForm').attr('action', '<%= request.getContextPath()%>/admin.mail/delete');
			$('#mailForm').submit();
		}
	}); 
};
</script>

</c:param>



<c:param name="PARAM_CONTENT">

<form action="<%= request.getContextPath()%>/admin.mail/save" method="post" role="form" id="mailForm">

	<div class="form-group">
		<label for="host">SMTP HOST名</label>
		<input type="text" class="form-control" name="host" id="host" placeholder="SMTP HOST" value="${host}" />
	</div>
	<div class="form-group">
		<label for="port">SMTP PORT番号</label>
		<input type="text" class="form-control" name="port" id="port" placeholder="SMTP PORT" value="${port}" />
	</div>
	<div class="form-group">
		<label for="authType_lock">認証</label><br/>
		<label class="radio-inline">
			<input type="radio" value="<%= INT_FLAG.ON.getValue() %>" name="authType" 
				id="authType_lock" <%= jspUtil.checked(String.valueOf(INT_FLAG.ON.getValue()), "authType", true) %>/>
			<i class="fa fa-lock"></i>&nbsp;認証する
		</label>
		<label class="radio-inline">
			<input type="radio" value="<%= INT_FLAG.OFF.getValue() %>" name="authType" 
				id="authType_unlock" <%= jspUtil.checked(String.valueOf(INT_FLAG.OFF.getValue()), "authType", true) %>/>
			<i class="fa fa-unlock"></i>&nbsp;認証しない
		</label>
	</div>
	<div class="form-group">
		<label for="smtpId">SMTP 認証用ID(認証する場合のみ)</label>
		<input type="text" class="form-control" name="smtpId" id="smtpId" placeholder="SMTP ID" value="${smtpId}" />
	</div>
	<div class="form-group">
		<label for="smtpPassword">SMTP 認証用パスワード(認証する場合のみ)</label>
		<input type="password" class="form-control" name="smtpPassword" id="smtpPassword" placeholder="SMTP PASSWORD" value="${smtpPassword}" />
	</div>
	<div class="form-group">
		<label for="fromAddress">送信元アドレス</label>
		<input type="text" class="form-control" name="fromAddress" id="fromAddress" placeholder="送信元アドレス" value="${fromAddress}" />
	</div>
	<div class="form-group">
		<label for="fromName">送信者名</label>
		<input type="text" class="form-control" name="fromName" id="fromName" placeholder="送信者名" value="${fromName}" />
	</div>
	
	<input type="hidden" name="systemName" value="${systemName}" />
	
	<button type="submit" class="btn btn-primary"><i class="fa fa-save"></i>&nbsp;登録</button>
	<button type="button" class="btn btn-danger" onclick="deleteMail();"><i class="fa fa-remove"></i>&nbsp;削除</button>
</form>


</c:param>

</c:import>

