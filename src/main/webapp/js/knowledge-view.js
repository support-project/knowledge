$(document).ready(function(){
	hljs.initHighlightingOnLoad();
	marked.setOptions({
		langPrefix: '',
		highlight: function(code, lang) {
			console.log('[highlight]' + lang);
			return code;
		}
	});
	$('#content').html(marked($('#content_text').text()));
	echo.init();
	
	$('#commentsLink').click(function(){
		var speed = 500;
		var href= $(this).attr("href");
		//var target = $(href == "#" || href == "" ? 'html' : href);
		var target = $(href);
		var position = target.offset().top;
		$("html, body").animate({scrollTop:position}, speed, "swing");
		return false;
	});
	
	$('#input_tags').on('beforeItemRemove', function(event) {
		event.cancel = true;
	});
	
});




var addlike = function(knowledgeId) {
	var url = _CONTEXT + '/open.knowledge/like/' + knowledgeId;
	$.ajax({
		type : 'GET',
		url : url,
		success : function(data, dataType) {
			console.log(data);
			$('#like_count').text(data.count);
			$.notify(LABEL_LIKE, 'info');
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			console.log(textStatus);
		}
	});
};



var viewProtect = function(knowledgeId) {
	var url = _CONTEXT + '/protect.knowledge/view_targets/' + knowledgeId;
	$.ajax({
		type : 'GET',
		url : url,
		success : function(datas, dataType) {
			var targets = '';
			for ( var i = 0; i < datas.length; i++) {
				var data = datas[i];
//				console.log(data);
				targets += data.label;
			}
			bootbox.alert(targets);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			console.log(textStatus);
		}
	});
};
