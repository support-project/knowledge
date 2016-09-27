$(document).ready(function() {
});


//var emoji = window.emojiParser;
var preview = function() {
    $.post(_CONTEXT + '/open.knowledge/marked', {
        title : $('#input_title').val(),
        content : $('#content').val()
    }, function(data) {
        var html = '<div style="word-break:break-all" id="content">';
        var content = data.content;
        html += content;
        html += '</div>';
        
        var jqObj = $('#preview');
        jqObj.html(html);
        jqObj.find('code').addClass('hljs');
        codeHighlight(jqObj)
        .then(function() {
            var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
            jqObj.html(content);
        }).then(function () {
            jqObj.find('a.oembed').oembed();
            // call slide.js
            showSlide('#preview');
            // call MathJax
            MathJax.Hub.Queue(function() {
                $('#preview').find('.lang-math').each(function(i, block) {
                    var jqobj = $(this);
                    MathJax.Hub.Typeset(jqobj[0]);
                });
            });
        });
    });
};


