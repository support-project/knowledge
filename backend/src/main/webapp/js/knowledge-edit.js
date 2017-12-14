var timer;
var titleValue = '';
var contentValue = '';
var initTimer = function() {
    if (timer) {
        clearInterval(timer);
    }
    titleValue = $("#input_title").val();
    contentValue = $("#content").val();
    timer = setInterval(function() {
        if (titleValue !== $("#input_title").val() || contentValue !== $("#content").val()) {
            titleValue = $("#input_title").val();
            contentValue = $("#content").val();
            $.notify('auto saving...', 'info');
            saveDraft();
        }
    }, 1000 * 60 * 1); // 1分に1回確認する
};
var saveDraft = function() {
    // 操作対象のフォーム要素を取得
    var $form = $('#knowledgeForm');
    saveKnowledge($form, _CONTEXT + '/protect.knowledge/draft');
    return false;
};
var deleteKnowledge = function() {
    bootbox.confirm(_CONFIRM, function(result) {
        if (result) {
            $('#knowledgeDeleteForm').attr('action', _CONTEXT + '/protect.knowledge/delete');
            $('#knowledgeDeleteForm').submit();
        }
    }); 
};
var saveKnowledge = function($form, url, notifyNone) {
    return new Promise(function(resolve, reject) {
        // 下書き保存のタイマーは初期化する
        initTimer();
        // 送信ボタンを取得
        var $button = $form.find('button');
        // ボタンを無効化し、二重送信を防止
        $button.attr('disabled', true);
        $.ajax({
            url: url,
            type: $form.attr('method'),
            data: $form.serialize(),
            timeout: 10000,  // 単位はミリ秒
        }).done(function(result, textStatus, xhr) {
            // 入力値を初期化
            console.log(result);
            if (!notifyNone) {
                $.notify(result.message, 'info');
            }
            
            if (url == _CONTEXT + '/protect.knowledge/save') {
                var knowledgeId = result.result;
                $('#knowledgeId').val(knowledgeId);
                $('#knowledgeIdForDelete').val(knowledgeId);
                
                $('#savebutton').html('<i class="fa fa-save"></i>&nbsp;' + _LABEL_UPDATE);
                $('#title_msg').text(_UPDATE_TITLE);
                
                $('#cancelButton').removeClass('hide');
                $('#cancelButton').attr('href', _CONTEXT + '/open.knowledge/view/' + knowledgeId);
                $('#deleteButton').removeClass('hide');
                
                $('#draftId').val('');
                $('#draft_flag').addClass('hide');
                $('#draftDeleteButton').addClass('hide');
                $('#updateContent').val('');
                
                // 画面遷移（表示画面へ）
                setTimeout(function() {
                    window.location.href = _CONTEXT + '/open.knowledge/view/' + knowledgeId;
                }, 500);
            } else {
                var draftId = result.result;
                $('#draftId').val(draftId);
                $('#draft_flag').removeClass('hide');
                $('#draftDeleteButton').removeClass('hide');
            }
            return resolve();
        }).fail(function(xhr, textStatus, error) {
            // 入力値を初期化
            if (xhr.responseJSON) {
                console.log(xhr.responseJSON);
                var msg = xhr.responseJSON;
                if (msg.children && msg.children.length > 0) {
                    for (var i = 0; i < msg.children.length; i++) {
                        var child = msg.children[i];
                        console.log(child);
                        $.notify(child.message, 'warn');
                    }
                } else {
                    $.notify(msg.message, 'warn');
                }
            } else {
                $.notify(xhr.statusText + ' [' + xhr.status + ']', 'warn');
            }
            return reject();
        }).always(function( jqXHR, textStatus ) {
            // ボタンを有効化し、再送信を許可
            $button.attr('disabled', false);
        });
    });
};


$(document).ready(function() {
    $('#releasebutton').click(function() {
        var $form = $('#knowledgeForm');
        saveKnowledge($form, _CONTEXT + '/protect.knowledge/save');
    });
    $('#knowledgeForm').submit(function(event) {
        // ページ遷移を禁止して、Ajaxで保存
        event.preventDefault();
        return saveDraft();
    });
    // 自動保存のタイマーをセット
    initTimer();
    // タイトル時にenterを押して保存されないようenterを無効化する
    $("#input_title").keypress(function(e) {
        return ! ((e.which && e.which === 13) || (e.keyCode && e.keyCode === 13));
    });

    // タイトル、コンテンツを変更した場合のみ、「記事の内容を更新した」と判定する
    $("#input_title").change(function() {
        $('#updateContent').val('true');
    });
    $("#content").change(function() {
        $('#updateContent').val('true');
    });
    // 下書き保存削除
    var removeDraft = function() {
        var draftId = $('#draftId').val();
        console.log(draftId);
        if (draftId) {
            bootbox.confirm(_CONFIRM, function(result) {
                if (result) {
                    $.ajax({
                        url: _CONTEXT + '/protect.draft/delete/' + draftId,
                        type: 'DELETE',
                        timeout: 10000,
                    }).done(function(result, textStatus, xhr) {
                        $.notify(result.message, 'info');
                        $('#draftId').val('');
                        $('#draft_flag').addClass('hide');
                        $('#draftDeleteButton').addClass('hide');
                    }).fail(function(xhr, textStatus, error) {
                        // 入力値を初期化
                        if (xhr.responseJSON) {
                            console.log(xhr.responseJSON);
                            var msg = xhr.responseJSON;
                            if (msg.children) {
                                for (var i = 0; i < msg.children.length; i++) {
                                    var child = msg.children[i];
                                    console.log(child);
                                    $.notify(child.message, 'warn');
                                }
                            }
                        } else {
                            $.notify(xhr.statusText + ' [' + xhr.status + ']', 'warn');
                        }
                    });
                }
            }); 
        }
    };
    $('#draftDeleteButton').click(function() {
        removeDraft();
    });
    // Auto complete
    setAutoComplete($("#content"));
});


