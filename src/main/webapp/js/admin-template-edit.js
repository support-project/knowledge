$(document).ready(function() {
    if ($('#typeId').val() && $('#typeId').val() !== '-1') {
        $('#templateForm').attr('action', _CONTEXT + '/admin.template/update');
        var id = $('#typeId').val();
        this.__load_survey(_CONTEXT + '/admin.template/load/' + id);
    } else {
        $('.editbtn').removeClass('hide');
        document._TEMPLATE.editable = true;
    }
    
    // フォームのサブミットは禁止
    $('#templateForm').submit(function(event) {
        logging('submit. ' + $('#typeId').val());
        // 操作対象のフォーム要素を取得
        var $form = $(this);
        // ページ遷移を禁止して、Ajaxで保存
        event.preventDefault();
        
        // 送信ボタンを取得
        // （後で使う: 二重送信を防止する。）
        var $button = $form.find('button');
        
        // ボタンを無効化し、二重送信を防止
        $button.attr('disabled', true);
        // 送信
        $.ajax({
            url: $form.attr('action'),
            type: $form.attr('method'),
            data: $form.serialize(),
            timeout: 10000,  // 単位はミリ秒
        }).done(function(result, textStatus, xhr) {
            // 入力値を初期化
            console.log(result);
            $.notify(result.message, 'info');
            if ($form.attr('action') == (_CONTEXT + '/admin.template/delete')) {
                setTimeout(function() {
                    location.href = _CONTEXT + '/admin.template/list';
                }, 500);
            }
            var typeid = result.result;
            $('#typeId').val(typeid);
            $form.attr('action', _CONTEXT + '/admin.template/update');
            $('#deletebutton').removeClass('hide');
        }).fail(function(xhr, textStatus, error) {
            handleErrorResponse(xhr, textStatus, error);
        }).always(function( jqXHR, textStatus ) {
            // ボタンを有効化し、再送信を許可
            $button.attr('disabled', false);
        });
        return false;
    });
    
    $('#deletebutton').click(function(){
        bootbox.confirm("Are you sure delete this data?", function(result) {
            if (result) {
                $('#templateForm').attr('action', _CONTEXT + '/admin.template/delete');
                $('#templateForm').submit();
            }
        }); 
    });
    
});