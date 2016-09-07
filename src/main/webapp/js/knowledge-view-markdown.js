$(document).ready(function() {
    var jqObj = $('#content');
    codeHighlight(jqObj)
    .then(function() {console.log('finish codeHighlight.'); return;})
    .then(function () {
        //console.log($('#content').html());
        var html = emoji(jqObj.html(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
        jqObj.html(html);
        //console.log($('#content').html());
    }).then(function () {
        jqObj.find('a.oembed').oembed();
        // call slide.js
        showSlide();
    });
    
    $('.arrow_question').each(function(i, block) {
        var content = $(this).html().trim();
        var jqObj = $(this);
        jqObj.html(content);
        codeHighlight(jqObj)
        .then(function() {
            var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
            jqObj.html(content);
        }).then(function () {
            jqObj.find('a.oembed').oembed();
            // call slide.js
            showSlide();
        });
    });
    $('.arrow_answer').each(function(i, block) {
        var content = $(this).html().trim();
        var jqObj = $(this);
        jqObj.html(content);
        codeHighlight(jqObj)
        .then(function() {
            var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
            jqObj.html(content);
            return;
        }).then(function () {
            jqObj.find('a.oembed').oembed();
            // call slide.js
            showSlide();
        });
    });
});
