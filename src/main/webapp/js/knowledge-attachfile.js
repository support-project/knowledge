$(document).ready(function() {
    var url = _CONTEXT + '/protect.file/upload';
    $('#fileupload').fileupload({
        url : url,
        dataType : 'json',
        autoUpload: true,
        maxFileSize: 5000000, // 5 MB
        dropZone: '#drop_target',
    }).on('fileuploaddone', function (e, data) {
        //$('#files').show();
        uploadedFiles(data.result.files);
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
});

var uploadedFiles = function(files) {
    console.log(files);
    $.each(files, function(index, file) {
        //console.log(file);
        var filediv = '<div class="filediv row" id="file-' + file.fileNo + '">';
        
        filediv += '<div class="file-image col-xs-1">';
        filediv += '<img src="' + file.thumbnailUrl + '" width="20"/>';
        filediv += '</div>';
        
        filediv += '<div class="file-label col-xs-6">';
        filediv += '<a href="' + file.url + '&amp;attachment=true">';
        var filename = file.name;
        if (filename.length > 20) {
            filename = filename.substring(0, 17) + '...';
        }
        filediv += filename;
        filediv += '</a>';
        filediv += '</div>';
        
        filediv += '<input type="hidden" name="files" value="' + file.fileNo + '" />';
        
        filediv += '<div class="file-buttons col-xs-4">';
        if (file.type === 'image') {
            filediv += '<button type="button" class="btn btn-success btn-xs" onclick="setImagePath(\'' + file.url + '\', \'' + file.name + '\')">';
            filediv += '<i class="fa fa-file-image-o"></i>&nbsp;' + _SET_IMAGE_LABEL;
            filediv += '</button><br/>';
        } else if (file.type === 'slide') {
            filediv += '<button type="button" class="btn btn-success btn-xs" onclick="setSlidePath(\'' + file.fileNo + '\', \'' + file.name + '\')">';
            filediv += '<i class="fa fa-television"></i>&nbsp;' + _SET_SLIDE_LABEL;
            filediv += '</button><br/>';
        }
        filediv += '<button type="button" class="btn btn-danger btn-xs" onclick="removeAddedFile(' + file.fileNo + ')">';
        filediv += '<i class="fa fa-remove"></i>';
        filediv += '&nbsp;' + _DELETE_LABEL + '</button>';
        filediv += '</div>';
        
        filediv += '</div>';
        $('#files').append(filediv);
    });
    $.notify(_UPLOADED, 'success');
    
    // 下書き保存も実行
    var $form = $('#knowledgeForm');
    saveKnowledge($form, _CONTEXT + '/protect.knowledge/draft', false);
};



var setImagePath = function(url, name) {
    var text = '\n![' + name + '](' + url + ')\n';
    insertAtCaret('#content', text)
};


var setSlidePath = function(fileNo, name) {
    var text = '[slide ' + fileNo + ']\n';
    insertAtCaret('#content', text)
};

