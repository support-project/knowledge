var shownAgenda = false;
var agenda;
var createAgenda = function() {
    console.log('createAgenda');
    var navbarHeight = $('nav.navbar-fixed-top').outerHeight(true);
    var toc = $('#toc');
    var exists = false;
    $('.markdown h1, .markdown h2, .markdown h3').each(function() {
        exists = true;
        var targetId = $(this).attr("id");
        // level
        var tagName = $(this).prop('tagName').toLowerCase();

        // link
        var $a = $('<a href=""></a>');
        $a.text('- ' + $(this).text());
        $a.attr('href', '#' + targetId);

        $a.click(function() {
            $('body, html').animate({
                scrollTop : $('#' + targetId).offset().top - navbarHeight
            }, 500);
            return false;
        });

        // link div
        var $div = $('<div class="toc-h toc-' + tagName + '"></div>');
        $a.appendTo($div);

        // append
        toc.append($div);
    });
    if (!exists) {
        toc.html(_MSG_TOC_EMPTY);
    }
    agenda = true;
};
var showAgenda = function() {
    if (!agenda) {
        createAgenda();
    }
    if (!shownAgenda) {
        $.ipop();
    } else {
        $('#ipop').fadeOut(100);
    }
    shownAgenda = !shownAgenda;
};

(function($) {
    $.ipop = function() {
        var wx, wy; // ウインドウの左上座標

        // ターゲットの場所に表示
        var target = $('#buttonToc');
        wx = target.offset().left;
        wy = 50;

        // ポップアップウインドウを表示する。
        $('#ipop').css({
            top : wy,
            left : wx
        }).fadeIn(100);

        // 閉じるボタンを押したとき
        $('#ipop_close').click(function() {
            shownAgenda = !shownAgenda;
            $('#ipop').fadeOut(100);
        });

        // タイトルバーをドラッグしたとき
        $('#ipop_title').mousedown(function(e) {
            var mx = e.pageX;
            var my = e.pageY;
            $(document).on('mousemove.ipop', function(e) {
                wx += e.pageX - mx;
                wy += e.pageY - my;
                $('#ipop').css({
                    top : wy,
                    left : wx
                });
                mx = e.pageX;
                my = e.pageY;
                return false;
            }).one('mouseup', function(e) {
                $(document).off('mousemove.ipop');
            });
            return false;
        });
    }
})(jQuery);
