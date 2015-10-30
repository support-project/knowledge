$(document).ready(function() {
//	hljs.initHighlightingOnLoad();
	marked.setOptions({
		langPrefix : '',
		highlight : function(code, lang) {
			console.log('[highlight]' + lang);
			return code;
		}
	});
	$('.content').each(function(i) {
		// var text = $(this).text();
		// $(this).html(marked(text));
	});
	
	/*
	$('.thumbnail').hover(function() {
		$(this).find('.discription').slideDown(250); // .fadeIn(250)
	}, function() {
		$(this).find('.discription').slideUp(250); // .fadeOut(205)
	});
	*/
	
	$('.thumbnail').hover(function() {
		$(this).css('border', '1px solid gray');
	}, function() {
		$(this).css('border', '0px solid #ccc');
	});
	
	$('#input_tags').on('beforeItemRemove', function(event) {
		event.cancel = true;
	});
	echo.init();
});

var showKnowledge = function(id, offset, keyword, tag, user) {
	//$('#discription_' + id).slideDown(20);
	var url = _CONTEXT + '/open.knowledge/view/' + id;
	
	var param = '';
	if (offset) {
		param += '?offset=' + offset;
	}
	if (keyword) {
		if (param.length > 0) {
			param += '&keyword=' + keyword;
		} else {
			param += '?keyword=' + keyword;
		}
	}
	if (tag) {
		if (param.length > 0) {
			param += '&tag=' + tag;
		} else {
			param += '?tag=' + tag;
		}
	}
	if (user) {
		if (param.length > 0) {
			param += '&user=' + user;
		} else {
			param += '?user=' + user;
		}
	}
	
	url += param;
	document.location.href = url;
};
