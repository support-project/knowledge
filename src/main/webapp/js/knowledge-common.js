hljs.initHighlightingOnLoad();

var emoji = window.emojiParser;

/**
 * コードハイライト
 * @return Promise
 */
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

/**
 * 数式をMathJaxを使って表示する処理
 * @return Promise
 */
var processMathJax = function(target) {
    return new Promise(function(resolve, reject) {
        // call MathJax
        MathJax.Hub.Queue(function() {
            MathJax.Hub.Typeset(target);
            return resolve();
        });
    });
};


/**
 * 内部の別の記事へのリンクを生成
 * @return Promise
 */
var processLink = function(parent) {
    return new Promise(function(resolve, reject) {
        if (parent) {
            var jqObj = parent;
            if (isString(parent)) {
                jqObj = $(parent);
            }
            target = jqObj.find('.internallink');
        } else {
            target = $('.internallink');
        }
        target.each(function(i, block) {
            var knowledgeNo = $(this).text().substring(1);
            console.log(knowledgeNo);
            var link = '<a href="' + _CONTEXT + '/open.knowledge/view/' + knowledgeNo + '">';
            link += '#' + knowledgeNo;
            link += '</a>';
            $(this).html(link);
        });
        return resolve();
    });
};



/**
 * 1つのブロックの装飾処理
 * @return Promise
 */
var decoration = function(jqObj) {
    console.log(jqObj);
    return new Promise(function(resolve, reject) {
        return codeHighlight(jqObj)
        .then(function() {
            var content = emoji(jqObj.html().trim(), _CONTEXT + '/bower/emoji-parser/emoji', {classes: 'emoji-img'});
            jqObj.html(content);
            return Promise.resolve();
        }).then(function () {
            jqObj.find('a.oembed').oembed();
            return Promise.resolve();
        }).then(function () {
            // call slide.js
            showSlide(jqObj);
            return Promise.resolve();
        }).then(function () {
            // call parse internal link
            return processLink(jqObj);
        }).then(function () {
            return resolve();
        }).catch(function(err) {
            console.error(err);
            return reject(err);
        });
    });
};

/**
 * 装飾処理
 * @return Promise
 */
var processDecoration = function(target) {
    if (isString(target)) {
        target = $(target);
    }
    var promises = [];
    target.each(function(index, element){
        var jqObj = $(element);
        promises.push(decoration(jqObj));
    });
    return Promise.all(promises);
};

var doPreview = function(titleId, contentId, previewAreaId, titleAreaId) {
    return parseMarkdown($(titleId).val(), $(contentId).val(), previewAreaId, titleAreaId);
};
/**
 * Markdownを処理してプレビューを表示
 * @return Promise
 */
var parseMarkdown = function(title, content, previewAreaId, titleAreaId) {
    return new Promise(function(resolve, reject) {
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
            return processDecoration(previewAreaId).then(function() {
                return resolve();
            });
        });
    });
};



