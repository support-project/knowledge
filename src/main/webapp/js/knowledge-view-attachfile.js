$(document).ready(function() {
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
            filediv += '<div class="file-image">';
            filediv += '<img src="' + file.thumbnailUrl + '" />';
            filediv += '</div>';
            filediv += '<div class="file-label">';
            filediv += '<a href="' + file.url + '">';
            filediv += file.name;
            filediv += '</a>';
            filediv += '</div>';
            filediv += '<br class="fileLabelBr"/>';
            
            filediv += '<input type="hidden" name="files" value="' + file.fileNo + '" />';
            filediv += '&nbsp;&nbsp;&nbsp;';
            filediv += '<button type="button" class="btn btn-success" onclick="setImagePath(\'' + file.url + '\', \'' + file.name + '\')">';
            filediv += '<i class="fa fa-file-image-o"></i>&nbsp;' + _SET_IMAGE_LABEL;
            filediv += '</button>';
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
    
    $('#more_attach').click(function() {
        $('.downloadfile').each(function() {
            $(this).removeClass('hide');
            $('#more_attach').addClass('hide');
        });
    });
    
});


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


var setImagePath = function(url, name) {
    var text = '\n![' + name + '](' + url + ')\n';
    var textarea = $('#comment');
    textarea.val(textarea.val() + text);
};





