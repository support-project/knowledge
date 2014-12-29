$(document).ready(function() {
	hljs.initHighlightingOnLoad();
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
	
	$('.thumbnail').hover(function() {
		$(this).find('.discription').slideDown(250); // .fadeIn(250)
	}, function() {
		$(this).find('.discription').slideUp(250); // .fadeOut(205)
	});

});

var showKnowledge = function(url, offset, keyword, tag) {
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
	url += param;
	document.location.href = url;
};
