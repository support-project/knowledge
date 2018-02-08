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
        parseMarkdown(
            'Markdown Sample',
            $('#sampleMarkdownText').val(),
            '#markdownSamplePreview'
        ).then(function() {
            return processMathJax('#sampleMarkdownText');
        });
    });
    
    $('#sampleMarkdownCheck').click(function() {
        var text = $('#sampleMarkdownText').val();
        var textarea = $('#comment');
        textarea.val(text);
        preview();
        $('#helpMarkdownModal').modal('hide');
        var p = $("#preview").offset().top - 60;
        $('html,body').animate({ scrollTop: p }, 'fast');
    });
    
    // Auto complete
    setAutoComplete($("#comment"));
});

var emoji = window.emojiParser;
var preview = function() {
    $.post(_CONTEXT + '/open.knowledge/marked', {
        title : $('#input_title').val(),
        content : $('#comment').val()
    }, function(data) {
        var html = '<div class="row">';
        html += '<div class="col-sm-12">';
        html += '<div class="thumbnail">';
        html += '<div class="caption">';
        html += '[preview]';
        html += '<h3>';
        html += data.title;
        html += '</h3><hr/>';
        html += '<div style="word-break:normal" id="content">';
        var content = data.content;
        html += content;
        html += '</div>';
        html += '</div>';
        html += '</div>';
        html += '</div>';
        html += '</div>';
        
        var target = '#preview';
        var jqObj = $(target);
        jqObj.html(html);
        processDecoration(target).then(function() {
            return processMathJax(target);
        });
    });
};
    
var emojiSelect = function(id) {
    $(id).find('.name').each(function(i, block) {
        $(this).click(function(event) {
            var val = ' :' + $(this).text() + ': ';
            var textarea = $('#comment');
            textarea.val(textarea.val() + val);
            $(id).modal('hide');
        });
    });
};

function deleteComment() {
    bootbox.confirm(_CONFIRM, function(result) {
        if (result) {
            $('#commentForm').attr('action', _CONTEXT + '/protect.knowledge/delete_comment/' + $('#commentNo').val());
            $('#commentForm').attr('method', 'GET');
            $('#commentForm').submit();
        }
    }); 
};

