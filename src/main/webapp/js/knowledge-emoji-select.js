$(document).ready(function() {
    $('#emojiPeopleModal').on('loaded.bs.modal', function (event) {
        emojiSelect('#emojiPeopleModal');
    });
    $('#emojiNatureModal').on('loaded.bs.modal', function (event) {
        emojiSelect('#emojiNatureModal');
    });
    $('#emojiObjectsModal').on('loaded.bs.modal', function (event) {
        emojiSelect('#emojiObjectsModal');
    });
    $('#emojiPlacesModal').on('loaded.bs.modal', function (event) {
        emojiSelect('#emojiPlacesModal');
    });
    $('#emojiSymbolsModal').on('loaded.bs.modal', function (event) {
        emojiSelect('#emojiSymbolsModal');
    });
    $('#helpMarkdownModal').on('shown.bs.modal', function (event) {
        $.post(_CONTEXT + '/open.knowledge/marked', {
            title : 'Markdown Sample',
            content : $('#sampleMarkdownText').val()
        }, function(data) {
            var html = '<div style="word-break:break-all" id="content">';
            var content = data.content;
            html += content;
            html += '</div>';
            
            var jqObj = $('#markdownSamplePreview');
            jqObj.html(html);
            jqObj.find('code').addClass('hljs');
            codeHighlight(jqObj)
            .then(function() {
                var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
                jqObj.html(content);
            }).then(function () {
                jqObj.find('a.oembed').oembed();
            });
        });
    });
});

var emojiSelect = function(id) {
    $(id).find('.name').each(function(i, block) {
        $(this).click(function(event) {
            var val = ' :' + $(this).text() + ': ';
            insertAtCaret('#content', val);
            $(id).modal('hide');
        });
    });
};
