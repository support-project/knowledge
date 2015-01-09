<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>
<c:param name="PARAM_SCRIPTS">
<script>
var withdrawalFromKnowledge = function() {
	bootbox.confirm("本当に退会を実行してよろしいですか？<br/>この処理は取り消せません。", function(result) {
		if (result) {
			$('#withdrawalForm').attr('action', '<%= request.getContextPath()%>/protect.account/delete');
			$('#withdrawalForm').submit();
		}
	}); 
};

</script>

</c:param>


<c:param name="PARAM_CONTENT">
<form action="<%= request.getContextPath()%>/protect.account" method="post" role="form" id="withdrawalForm">
	
	<div class="form-group">
	Knowledgeのサービスから退会します。<br/>
	今まで登録したナレッジをどうしますか？<br/>
	</div>
	
	<div class="form-group">
		<label class="radio-inline">
			<input type="radio" value="1" name="knowledge_remove" checked="checked" />
			<i class="fa fa-eraser"></i>&nbsp;削除する
		</label>
		<label class="radio-inline">
			<input type="radio" value="2" name="knowledge_remove"/>
			<i class="fa fa-gift"></i>&nbsp;残す（登録者名は「削除済ユーザー」になります）
		</label>
	</div>
	
	<div class="form-group">
	<button type="button" class="btn btn-danger" onclick="withdrawalFromKnowledge();"><i class="fa fa-save"></i>&nbsp;退会する（取り消せません）</button>
	<a href="<%= request.getContextPath() %>/protect.account"
	class="btn btn-warning" role="button"><i class="fa fa-undo"></i>&nbsp;キャンセル</a>
	</div>
	
</form>


</c:param>

</c:import>

