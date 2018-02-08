$(document).ready(function() {
    var knowledgeId = $('#knowledgeId').val();
    if (_LOGIN_USER_ID) {
        $.ajax({
            type : 'GET',
            url : _CONTEXT + '/protect.survey/load/' + knowledgeId
        }).done(function(data) {
            console.log(data);
            if (data.msg || !data.exist) {
                return;
            }
            $('#btnAnswerSurvey').removeClass('hide');
            $('#modalAnswerSurveyLabel').text(data.title);
            $('#surveyDescription').text(data.description);
            document.__add_Template_Edit_Item(data);
        }).fail(function(err) {
            console.log(err);
        });
    }
    
    $('#saveSurveyButton').click(function(){
        $('#answerForm').submit();
    });
    
    // フォームのサブミットは禁止
    $('#answerForm').submit(function(event) {
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
            $('#modalAnswerSurvey').modal('hide');
        }).fail(function(xhr, textStatus, error) {
            handleErrorResponse(xhr, textStatus, error);
        }).always(function( jqXHR, textStatus ) {
            // ボタンを有効化し、再送信を許可
            $button.attr('disabled', false);
        });
        return false;
    });
    
    
    
});
