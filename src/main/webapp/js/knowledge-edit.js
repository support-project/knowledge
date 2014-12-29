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
			filediv += '&nbsp;削除</button>';
			filediv += '</div>';
			$('#files').append(filediv);
		});
		$.notify('ファイルをアップロードしました', 'success');
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
			$.notify('アップロードに失敗したファイルがあります', 'warn');
		});
	 }).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');
	
});

var removeAddedFile = function(fileNo) {
	var url = _CONTEXT + '/protect.file/delete';
	$.ajax({
		type : 'GET',
		url : url,
		data : 'fileNo=' + fileNo,
		success : function(data, dataType) {
			$.notify('ファイルを削除しました', 'info');
			$('#file-' + fileNo).remove();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			//alert("error: " + textStatus);
			$.notify('ファイルの削除に失敗しました', 'warn');
		}
	});
};


var preview = function() {
	$.get(_CONTEXT + '/open.knowledge/escape', {
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
		html += marked(data.content);
		html += '</p>';
		html += '</div>';
		html += '</div>';
		html += '</div>';
		html += '</div>';

		$('#preview').html(html);
	});
};


function deleteKnowledge() {
	bootbox.confirm("本当に削除しますか?", function(result) {
		if (result) {
			$('#knowledgeForm').attr('action', _CONTEXT + '/protect.knowledge/delete');
			$('#knowledgeForm').submit();
		}
	}); 
};

