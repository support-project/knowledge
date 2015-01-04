$(document).ready(function() {
	var url = _CONTEXT + '/protect.account/iconupload';
	$('#fileupload').fileupload({
		url : url,
		dataType : 'json',
		autoUpload: true,
		acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
		maxFileSize: 5000000, // 5 MB
	}).on('fileuploadadd', function (e, data) {
		$('#icon').remove();
	}).on('fileuploaddone', function (e, data) {
		$.each(data.result.files, function(index, file) {
			var img = '<img id="icon" src="';
			img += file.thumbnailUrl + '" ';
			img += 'width="64" height="64" />';
			$('#icondiv').append(img);
		});
		
		$.notify('画像を更新しました', 'success');
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
		console.log(e);
		console.log(data);
		$.notify('画像の更新に失敗しました', 'warn');
	 }).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');
	
});



