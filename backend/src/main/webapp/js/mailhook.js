$(document).ready(function() {
    var elt= $('#input_tags');
    elt.tagsinput({
        freeInput: true
    });
    
    $('#mailForm').submit(function(event) {
        console.log('submit');
        // 操作対象のフォーム要素を取得
        var $form = $(this);
        // ページ遷移を禁止して、Ajaxで保存
        event.preventDefault();
        
        // 送信ボタンを取得
        // （後で使う: 二重送信を防止する。）
        var $button = $form.find('button');
        
        // 送信
        $.ajax({
            url: $form.attr('action'),
            type: $form.attr('method'),
            data: $form.serialize(),
            timeout: 10000,  // 単位はミリ秒

            // 送信前
            beforeSend: function(xhr, settings) {
                // ボタンを無効化し、二重送信を防止
                $button.attr('disabled', true);
            },
            // 応答後
            complete: function(xhr, textStatus) {
                // ボタンを有効化し、再送信を許可
                $button.attr('disabled', false);
            },
            
            // 通信成功時の処理
            success: function(result, textStatus, xhr) {
                // 入力値を初期化
                console.log(result);
                $.notify(result.message, 'info');
                
                if ('deleted' === result.result) {
                    setTimeout(function() {
                        window.location.href = _CONTEXT + '/admin.mailhook/';
                    }, 500);
                    return;
                }
                
                $('#conditionNo').val(result.result);
                
                
                /*
                $form.attr('action', _CONTEXT + '/protect.knowledge/update');
                
                var knowledgeId = result.result;
                
                $('#knowledgeIdForDelete').val(knowledgeId);
                $('#savebutton').html('<i class="fa fa-save"></i>&nbsp;' + _LABEL_UPDATE);
                $('#title_msg').text(_UPDATE_TITLE);
                $('#deleteButton').removeClass('hide');
                $('#cancelButton').removeClass('hide');
                $('#cancelButton').attr('href', _CONTEXT + '/open.knowledge/view/' + knowledgeId);
                */
                
                
            },
            // 通信失敗時の処理
            error: function(xhr, textStatus, error) {
                // 入力値を初期化
                console.log(xhr.responseJSON);
                var msg = xhr.responseJSON;
                if (msg.children) {
                    for (var i = 0; i < msg.children.length; i++) {
                        var child = msg.children[i];
                        console.log(child);
                        $.notify(child.message, 'warn');
                    }
                }
            }
        });
        return false;
    });

});