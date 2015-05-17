var groups = [];
var selectedGroups = [];

$(document).ready(function() {
	hljs.initHighlightingOnLoad();
	marked.setOptions({
		langPrefix : '',
		highlight : function(code, lang) {
			console.log('[highlight]' + lang);
			return code;
		}
	});
	
	var url = _CONTEXT + '/protect.file/upload';
	$('#fileupload').fileupload({
		url : url,
		dataType : 'json',
		autoUpload: true,
		maxFileSize: 5000000, // 5 MB
	}).on('fileuploaddone', function (e, data) {
		//$('#files').show();
		$.each(data.result.files, function(index, file) {
			console.log(file);
			var filediv = '<div class="filediv" id="file-' + file.fileNo + '">';
			filediv += '<div class="file-label">';
			filediv += '<img src="' + file.thumbnailUrl + '" />';
			filediv += '<a href="' + file.url + '">';
			filediv += file.name;
			filediv += '</a>';
			filediv += '</div>';
			
			filediv += '<input type="hidden" name="files" value="' + file.fileNo + '" />';
			filediv += '&nbsp;&nbsp;&nbsp;';
			filediv += '<button type="button" class="btn btn-danger" onclick="removeAddedFile(' + file.fileNo + ')">';
			filediv += '<i class="fa fa-remove"></i>';
			filediv += '&nbsp;' + _DELETE_LABEL + '</button>';
			filediv += '</div>';
			$('#files').append(filediv);
		});
		$.notify(_UPLOADED, 'success');
		setTimeout(function() {
			$('#progress').hide();
		}, 5000);
		
	 }).on('fileuploadprogressall', function (e, data) {
			$('#progress').show();
			var progress = parseInt(data.loaded / data.total * 100, 10);
			console.log('' + progress + '%');
			$('.progress .progress-bar').css('width', progress + '%');
			$('.progress .progress-bar').text(progress + '%');
			
	 }).on('fileuploadfail', function (e, data) {
		if (data && data.jqXHR && data.jqXHR.responseJSON && data.jqXHR.responseJSON.msg) {
			var msg = data.jqXHR.responseJSON.msg;
			$.each(data.files, function (index) {
				var filediv = '<div class="alert alert-warning alert-dismissible" role="alert">';
				filediv += '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
				filediv += '<strong>';
				filediv += '<i class="fa fa-remove"></i>&nbsp;';
				filediv += data.files[index].name;
				filediv += '</strong>&nbsp;&nbsp;';
				filediv += msg;
				filediv += '</div>';
				$('#files').append(filediv);
				$.notify(_FAIL_UPLOAD, 'warn');
			});
		} else {
			console.log(e);
			console.log(data);
			$.notify('アップロードに失敗しました', 'warn');
		}
	 }).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');
	
	var elt= $('#input_tags');
	elt.tagsinput();
	
	dispChangeGroupArea($('input[name="publicFlag"]:checked').val());
	$('input[name="publicFlag"]:radio').change( function() {
		dispChangeGroupArea($( this ).val());
	});
	
	var keyword = $('#groupKeyword').val();
	var offset = 0;
	$('#groupSelectModal').on('show.bs.modal', function (event) {
		getGroups(keyword, offset);
	});
	$('#groupSearchButton').click(function() {
		keyword = $('#groupKeyword').val();
		offset = 0;
		getGroups(keyword, offset);
	});
	$('#groupSearchPrevious').click(function() {
		keyword = $('#groupKeyword').val();
		offset--;
		if (offset < 0){
			offset = 0;
		}
		getGroups(keyword, offset);
	});
	$('#groupSearchNext').click(function() {
		keyword = $('#groupKeyword').val();
		offset++;
		getGroups(keyword, offset);
	});
	$('#groupDecision').click(function() {
		$('#groupSelectModal').modal('hide')
	});
	$('#clearSelectedGroup').click(function() {
		selectedGroups = [];
		viewGroup();
	});
	viewGroup();
});

var getGroups = function(keyword, offset) {
	$('#groupList').html('Now loading...');
	var url = _CONTEXT + '/protect.target/typeahead'
	var params = {
			keyword : keyword,
			offset: offset
	};
	
	$.get(url, params, function(result){
		groups = result;
		var html = '';
		if (result.length == 0) {
			html += 'empty';
		} else {
			html+= '<div class="list-group">';
			for (var int = 0; int < result.length; int++) {
				html += '<a href="#" class="list-group-item" onclick="selectGroup(' + int + ')">';
				html += result[int].label;
				html += '</a>';
			}
			html += '</div>';
		}
		$('#groupList').html(html);
		$('#groupPage').text('- page:' + (offset + 1) + ' -');
	});
};


var selectGroup = function(idx) {
	var exist = false;
	for (var i = 0; i < selectedGroups.length; i++) {
		var item = selectedGroups[i];
		if (item.value == groups[idx].value) {
			exist = true;
			break;
		}
	}
	if (!exist) {
		selectedGroups.push(groups[idx]);
	}
	viewGroup();
};

var viewGroup = function() {
	var values = [];
	var labels = [];
	for (var i = 0; i < selectedGroups.length; i++) {
		var item = selectedGroups[i];
		values.push(item.value);
		labels.push(item.label);
	}
	if (selectedGroups.length == 0) {
		$('#clearSelectedGroup').hide();
	} else {
		$('#clearSelectedGroup').show();
	}
	
	$('#selectedList').text(labels.join(','));
	$('#groupsLabel').text(labels.join(','));
	$('#groups').val(values.join(','));
};




var dispChangeGroupArea = function(val) {
	var grops_area = $('#grops_area');
	if (val == '2') {
		grops_area.show('normal');
	} else {
		grops_area.hide('normal');
	}
};


var removeAddedFile = function(fileNo) {
	var url = _CONTEXT + '/protect.file/delete';
	$.ajax({
		type : 'GET',
		url : url,
		data : 'fileNo=' + fileNo,
		success : function(data, dataType) {
			$.notify(_REMOVE_FILE, 'info');
			$('#file-' + fileNo).remove();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			//alert("error: " + textStatus);
			$.notify(_FAIL_REMOVE_FILE, 'warn');
		}
	});
};


var preview = function() {
	$.post(_CONTEXT + '/open.knowledge/escape', {
		title : $('#input_title').val(),
		content : $('#content').val()
	}, function(data) {
		var html = '<div class="row">';
		html += '<div class="col-sm-12">';
		html += '<div class="thumbnail">';
		html += '<div class="caption">';
		html += '<h3>';
		html += data.title;
		html += '</h3>';
		html += '<p style="word-break:break-all" id="content">';
		var emoji = window.emojiParser;
		var content = emoji(data.content, _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
		content = marked(content);
		html += content;
		html += '</p>';
		html += '</div>';
		html += '</div>';
		html += '</div>';
		html += '</div>';
		$('#preview').html(html);
		$('pre code').each(function(i, block) {
			hljs.highlightBlock(block);
		});
	});
};


function deleteKnowledge() {
	bootbox.confirm(_CONFIRM, function(result) {
		if (result) {
			$('#knowledgeForm').attr('action', _CONTEXT + '/protect.knowledge/delete');
			$('#knowledgeForm').submit();
		}
	}); 
};

