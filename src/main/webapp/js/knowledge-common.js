hljs.initHighlightingOnLoad();

var emoji = window.emojiParser;

var codeHighlight = function(block) {
    var highlightPromises = [];
    block.find('pre code').not('.lang-math').each(function(i, block) {
        var jqobj = $(this);
        highlightPromises.push(new Promise(function(resolve, reject) {
            try {
                var text = jqobj.text();
                if (text.indexOf('://') != -1) {
                    console.log('skip on hljs freeze word');
                    console.log(text);
                    return resolve();
                }
                var result = hljs.highlightAuto(text);
                jqobj.html(result.value);
                return resolve();
            } catch (err) {
                console.err(err);
                return reject(err);
            }
        }));
    });
    block.find('p code').not('.lang-math').each(function(i, block) {
        var jqobj = $(this);
        highlightPromises.push(new Promise(function(resolve, reject) {
            try {
                jqobj.addClass('hljs');
                jqobj.addClass('stylus');
                var text = jqobj.text();
                if (text.indexOf('://') != -1) {
                    console.log('skip on hljs freeze word');
                    console.log(text);
                    return resolve();
                }
                var result = hljs.highlightAuto(text);
                jqobj.html(result.value);
                return resolve();
            } catch (err) {
                console.err(err);
                return reject(err);
            }
        }));
    });

    return Promise.all(highlightPromises);
};


var processDecoration = function(target) {
    var jqObj = target;
    if (isString(target)) {
        jqObj = $(target);
    }
    jqObj.find('code').addClass('hljs');
    codeHighlight(jqObj)
    .then(function() {
        var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
        jqObj.html(content);
    }).then(function () {
        jqObj.find('a.oembed').oembed();
        // call slide.js
        showSlide(jqObj);
        // call MathJax
        MathJax.Hub.Queue(function() {
            jqObj.find('.lang-math').each(function(i, block) {
                var jqobj = $(this);
                jqobj.addClass('hljs');
                MathJax.Hub.Typeset(jqobj[0]);
            });
        });
    }).catch(function(err) {
        console.error(err);
    });
};

var doPreview = function(titleId, contentId, previewAreaId, titleAreaId) {
    parseMarkdown($(titleId).val(), $(contentId).val(), previewAreaId, titleAreaId);
};

var parseMarkdown = function(title, content, previewAreaId, titleAreaId) {
    $.post(_CONTEXT + '/open.knowledge/marked', {
        title : title,
        content : content
    }, function(data) {
        if (titleAreaId) {
            $(titleAreaId).html(data.title);
        }
        var html = '<div style="word-break:break-all" id="content">';
        var content = data.content;
        html += content;
        html += '</div>';
        
        var jqObj = $(previewAreaId);
        jqObj.html(html);
        
        processDecoration(previewAreaId);
    });
};



