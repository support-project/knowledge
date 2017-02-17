$(document).ready(function() {
    var url = _CONTEXT + '/protect.file/imgupload';
    
    var cropperDialog = function() {
        var dialog = bootbox.confirm({
            title: _IMAGE_UPLOAD,
            message: '<input type="file" name="image" id="image" />',
            buttons: {
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if (result) {
                    var cropcanvas = $('.picedit_canvas_box .picedit_canvas').find('canvas')[0];
                    var croppng = cropcanvas.toDataURL("image/png");
                    logging(croppng);
                    
                    $.ajax({
                        type: 'POST',
                        url: url,
                        data: {
                            fileimg: croppng
                        },
                        success: function(data) {
                            uploadedFiles(data.files);
                            writeImageLink(data, $('#content'));
                        },
                        error: function(err) {
                            $.notify('Save error', 'error');
                        }
                    });
                }
            }
        });
        dialog.init(function(){
            dialog.find("div.modal-content").addClass("confirmWidth");
            var width = 700;
            if (document.body.clientWidth < 768) {
                width = 250;
            }
            logging('width: ' + width);
            setTimeout(function(){
                $('#image').picEdit({
                    maxWidth: width,
                    imageUpdated: function(img){
                        // logging('Image updated!');
                    }
                });
            }, 100);
        });
    };
    $('#previewClipbordImage').click(function() {
        logging('select clip button');
        cropperDialog();
    });
});
