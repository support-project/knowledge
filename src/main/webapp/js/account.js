$(document).ready(function() {
    var url = _CONTEXT + '/protect.account/iconupload';
    
    var cropperDialog = function(blobUrl) {
        var dialog = bootbox.confirm({
            title: 'Crop your new profile picture',
            message: '<p><i class="fa fa-spin fa-spinner"></i> Loading...</p>',
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
                    // 範囲選択したものを送信
                    var cropcanvas = $('#img').cropper('getCroppedCanvas');
                    var croppng = cropcanvas.toDataURL("image/png");
                    
// トリミング画像を表示
//                    $('#icon').remove();
//                    var img = '<img id="icon" src="';
//                    img += croppng + '" ';
//                    img += 'width="64" height="64" />';
//                    $('#icondiv').append(img);
                    
                    $.ajax({
                        type: 'POST',
                        url: url,
                        data: {
                            fileimg: croppng
                        },
                        success: function(file) {
                            $.notify('Saved', 'success');
                            // 保存されたものを表示
                            $('#icon').remove();
                            var img = '<img id="icon" src="';
                            img += file.thumbnailUrl + '" ';
                            img += 'width="64" height="64" />';
                            $('#icondiv').append(img);
                        },
                        error: function(err) {
                            $.notify('Save error', 'error');
                        }
                    });
                }
            }
        });
        dialog.init(function(){
            setTimeout(function(){
                dialog.find('.bootbox-body').html('<img src="' + blobUrl + '" width="200" id="img">');
                $('#img').cropper({
                    aspectRatio: 4 / 4 // ここでアスペクト比の調整 ワイド画面にしたい場合は 16 / 9
                });
            }, 1000);
        });
    };
    
    
    document.getElementById( "file" ).addEventListener( "change", function() {
        // Blob URLの作成
        var blobUrl = window.URL.createObjectURL( this.files[0] ) ;
        cropperDialog(blobUrl);
    });
    
    
    var drop = document.getElementById("icondiv");
    var drapEnter = function() {
        drop.style.border = "5px solid gray";
    };
    var drapFinish = function() {
        drop.style.border = "none";
    };
    drop.addEventListener("drop", function (event) {
        event.preventDefault();
        var filelist = event.dataTransfer.files;
        if (filelist.length > 0) {
            var reader = new FileReader();
            reader.readAsDataURL(filelist[0]);
            reader.onload = function (event) {
                cropperDialog(reader.result);
            }
        }
        drapFinish();
    }, true);
    drop.addEventListener("dragenter", function (event) {
        event.preventDefault();
        drapEnter();
    }, true);
    drop.addEventListener("dragleave", function (event) {
        event.preventDefault();
        drapFinish();
    }, true);
    drop.addEventListener("dragover", function (event) {
        event.preventDefault();
        drapEnter();
    }, true);
    
    
});



