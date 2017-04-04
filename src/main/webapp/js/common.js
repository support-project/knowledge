$(document).ready(function() {
    var pagetop = $('.pagetop');
    $(window).scroll(function() {
        if ($(this).scrollTop() > 100) {
            pagetop.fadeIn();
        } else {
            pagetop.fadeOut();
        }
    });
    pagetop.click(function() {
        $('body, html').animate({
            scrollTop : 0
        }, 500);
        return false;
    });
    
    // セッションが切れないように、定期的にアクセスする(5分に1回)
    var accessInterval = function() {
        setInterval(function() {
            var url = _CONTEXT + '/open.interval/access';
            $.ajax({
                type : 'GET',
                url : url
            }).done(function(result, textStatus, xhr) {
                console.log('OK');
            }).fail(function(xhr, textStatus, error) {
                console.error(error);
            }).always(function( jqXHR, textStatus ) {
            });
        }, 1000 * 60 * 5);
    };
    accessInterval();
});
function logging(str, level) {
//    console.log(str);
    if (_LOGGING_NOTIFY_DESKTOP) {
        if (!level) {
            level = 'info';
        }
        var options = {
            className: level,
            autoHideDelay: 10000,
            globalPosition: 'bottom left'
        };
        $.notify(str, options);
    }
};


function insertAtCaret(target, str) {
    var obj = $(target);
    obj.focus();
    if (navigator.userAgent.match(/MSIE/)) {
        var r = document.selection.createRange();
        r.text = str;
        r.select();
    } else {
        var s = obj.val();
        var p = obj.get(0).selectionStart;
        var np = p + str.length;
        obj.val(s.substr(0, p) + str + s.substr(p));
        obj.get(0).setSelectionRange(np, np);
    }
}
function isString(obj) {
    return typeof (obj) == "string" || obj instanceof String;
};

var handleErrorResponse = function(xhr, textStatus, error) {
    console.log(error);
    console.log(xhr);
    if (xhr.responseJSON) {
        var msg = xhr.responseJSON;
        if (msg.children) {
            for (var i = 0; i < msg.children.length; i++) {
                var child = msg.children[i];
                console.log(child);
                $.notify(child.message, 'warn');
            }
        } else {
            $.notify('data load error. please try again.', 'warn');
        }
    } else {
        if (xhr) {
            $.notify(xhr.statusText, 'warn');
        } else {
            $.notify('data load error. please try again.', 'warn');
        }
    }
};
