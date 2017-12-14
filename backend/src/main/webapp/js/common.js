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
    
    var $win = $(window);
    $win.on('load resize', function() {  
        if (window.matchMedia('(max-width:767px)').matches) {
            $('.navListButtonText').show();
        } else if (window.matchMedia('(max-width:1200px)').matches) {
            $('.navListButtonText').hide();
        } else {
            $('.navListButtonText').show();
        }
    });
    
    $(window).load(function(){
        var oldSSB = $.fn.modal.Constructor.prototype.setScrollbar;
        $.fn.modal.Constructor.prototype.setScrollbar = function () 
        {
            oldSSB.apply(this);
            if(this.bodyIsOverflowing && this.scrollbarWidth) 
            {
                $('.navbar-fixed-top, .navbar-fixed-bottom').css('padding-right', this.scrollbarWidth);
            }
        }

        var oldRSB = $.fn.modal.Constructor.prototype.resetScrollbar;
        $.fn.modal.Constructor.prototype.resetScrollbar = function () 
        {
            oldRSB.apply(this);
            $('.navbar-fixed-top, .navbar-fixed-bottom').css('padding-right', '');
        }
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

// ファイルをドロップしたときのブラウザデフォルトの動作を抑止する。
$(document).on('drop dragover', function (e) {
    e.stopPropagation();
    e.preventDefault();
});

function logging(str, level) {
    console.log(str);
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

function unescapeHTML(str) {
    // エスケープしない
    return str;
};

function escapeLink(url) {
    if (url.toLowerCase().indexOf('javascript:') != -1) {
        var conv = '';
        conv += url.substring(0, url.toLowerCase().indexOf('javascript:'));
        conv += encodeURIComponent(url.substring(url.toLowerCase().indexOf('javascript:')));
        return conv;
    } else {
        return url;
    }
}

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

var getCookies = function() {
    var result = new Array();
    var allcookies = document.cookie;
    if( allcookies != '' ) {
        var cookies = allcookies.split( '; ' );
        for( var i = 0; i < cookies.length; i++ ) {
            var cookie = cookies[ i ].split( '=' );
            result[ cookie[ 0 ] ] = decodeURIComponent( cookie[ 1 ] );
        }
    }
    return result;
};

var setCookie = function(c_name, value, expiredays, path) {
    var extime = new Date().getTime();
    var cltime = new Date(extime + (60 * 60 * 24 * 1000 * expiredays));
    var exdate = cltime.toUTCString();
    var s = '';
    s += c_name + '=' + escape(value) + ';';
    if (expiredays) {
        s += ' expires=' + exdate + ';';
    }
    if (path) {
        s += ' path=' + path + ';';
    } else {
        s += ' path=' + _CONTEXT + '/;';
    }
    
    document.cookie = s;
};
