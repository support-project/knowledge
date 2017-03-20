$(document).ready(function() {
    $('#fm').submit(function(event) {
        // 操作対象のフォーム要素を取得
        var $form = $(this);
        // ページ遷移を禁止して、Ajaxで保存
        event.preventDefault();
        save($form, _CONTEXT + '/admin.customservice/save');
        // フォームのサブミットは禁止
        return false;
    });
    var save = function($form, url) {
        return new Promise(function(resolve, reject) {
            // 送信ボタンを取得
            var $button = $form.find('button');
            // ボタンを無効化し、二重送信を防止
            $button.attr('disabled', true);
            $.ajax({
                url: url,
                type: $form.attr('method'),
                data: $form.serialize(),
                timeout: 10000,
            }).done(function(result, textStatus, xhr) {
                $.notify(result.message, 'info');
                return resolve();
            }).fail(function(xhr, textStatus, error) {
                $.notify(xhr.statusText + ' [' + xhr.status + ']', 'warn');
                return reject();
            }).always(function( jqXHR, textStatus ) {
                // ボタンを有効化し、再送信を許可
                $button.attr('disabled', false);
            });
        });
    };
});
