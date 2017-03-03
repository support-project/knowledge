$(document).ready(function() {
    var templates = [];
    var selected = 'notify_insert_knowledge';
    var changeTemplate = function(t) {
        selected = t.templateId;
        $('#template_description').text(t.description);
        $('#en_title').val(t.en.title);
        $('#en_content').val(t.en.content);
        $('#ja_title').val(t.ja.title);
        $('#ja_content').val(t.ja.content);
    };
    
    var loadTemplates = function() {
        return new Promise(function(resolve, reject) {
            var url = _CONTEXT + '/admin.mailtemplate/list';
            $.ajax({
                url: url,
                type: 'GET',
                timeout: 10000,
            }).done(function(result, textStatus, xhr) {
                console.log(result);
                templates = result;
                var $select = $('#templateId');
                $select.children().remove();
                var options = $.map(result, function (t) {
                    var isSelected = t.templateId == selected;
                    var $option = $('<option>', { value: t.templateId, text: t.templateTitle, selected: isSelected });
                    if (isSelected) {
                        changeTemplate(t);
                    }
                    return $option;
                });
                $select.append(options);
                return resolve();
            }).fail(function(xhr, textStatus, error) {
                handleErrorResponse(xhr, textStatus, error);
                return reject(error);
            }).always(function( jqXHR, textStatus ) {
            });
       });
    };
    loadTemplates();
    
    $('#templateId').change(function() {
        $('#template_description').text('');
        var str = $(this).val();
        $.map(templates, function (t) {
            if (t.templateId === str) {
                changeTemplate(t);
            }
        });
    });
    
    $('#mailTemplateForm').submit(function(event) {
        // 操作対象のフォーム要素を取得
        var $form = $(this);
        // ページ遷移を禁止して、Ajaxで保存
        event.preventDefault();
        save($form, _CONTEXT + '/admin.mailtemplate/save');
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
                $.notify(result.msg, 'info');
                loadTemplates();
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
    
    
    $('#initializeBtn').click(function() {
        bootbox.confirm('Are you sure to you will initialize this template data?', function(result) {
            var url = _CONTEXT + '/admin.mailtemplate/initialize';
            if (result) {
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: $('#mailTemplateForm').serialize(),
                    timeout: 10000,
                }).done(function(result, textStatus, xhr) {
                    $.notify(result.msg, 'info');
                    loadTemplates();
                }).fail(function(xhr, textStatus, error) {
                    handleErrorResponse(xhr, textStatus, error);
                }).always(function( jqXHR, textStatus ) {
                    // ボタンを有効化し、再送信を許可
                    $button.attr('disabled', false);
                });
            }
        }); 
    });
    
});



