<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"
	errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_SCRIPTS">
<script>
$(document).ready(function(){
	hljs.initHighlightingOnLoad();
	marked.setOptions({
		langPrefix: '',
		highlight: function(code, lang) {
			console.log('[highlight]' + lang);
			return code;
		}
	});
	$('#content').html(marked($('#content').text()));
});
</script>
</c:param>

	<c:param name="PARAM_CONTENT">

		<div class="jumbotron">
			<h2><i class="fa fa-book"></i>&nbsp;Knowledge</h2>
			<p></p>
			<p>Knowledgeはフリーの情報共有サービスです</p>
			<p>
				<a class="btn btn-info btn-lg" role="button" href="<%= request.getContextPath() %>/open.knowledge/list" >
				<i class="fa fa-eye"></i>&nbsp;見てみる
				</a>
			</p>
		</div>
		
<div id="content">
#### 特徴
- Markdown記法で情報登録
- 記事のタイトル/本文を全文検索で目的の情報を探せます
- 情報の公開範囲を、「公開」「非公開（自分のみ）」の指定ができます
- 情報に付けたタグで、情報の種別の管理を行えます
- 添付ファイルを登録できます
- 添付ファイルの中身でも検索できます
</div>

	</c:param>

</c:import>

