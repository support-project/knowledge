var indexMap = {};
var full = false;
var slideCount = 0;

var requestFullscreen = function(id) {// eslint-disable-line no-unused-vars
    if (full) {
        if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen();
            full = false;
        } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
            full = false;
        } else if (document.msExitFullscreen) {
            document.msExitFullscreen();
            full = false;
        } else if (document.exitFullscreen) {
            document.exitFullscreen();
            full = false;
        }
    } else {
        var target = document.getElementById(id);
        if (target.requestFullscreen) {
            target.requestFullscreen(); // HTML5 Fullscreen API仕様
            full = true;
        } else if (target.webkitRequestFullscreen) {
            target.webkitRequestFullscreen(); //Chrome15+, Safari5.1+, Opera15+
            full = true;
        } else if (target.mozRequestFullScreen) {
            target.mozRequestFullScreen(); //FF10+
            full = true;
        } else if (target.msRequestFullscreen) {
            target.msRequestFullscreen(); //IE11+
            full = true;
        } else {
            $.notify('Fullscreen view is not supported.', 'warn');
            return;
        }
    }
};

var showSlide = function(parent) {// eslint-disable-line no-unused-vars
//    console.log(parent);
    var url = _CONTEXT + '/open.file/slide/';
    var target;
    if (parent) {
        var jqObj = parent;
        if (isString(parent)) {
            jqObj = $(parent);
        }
        target = jqObj.find('.slideshow');
    } else {
        target = $('.slideshow');
    }
    target.each(function() {
        var fileNo = $(this).attr('slide');
        var transition = $(this).attr('transition');
        var slideArea = $(this);
        if (fileNo) {
            slideCount++;
            var slideId = 'slide-area-' + slideCount;
            indexMap[slideId] = 1;
            $.ajax({
                type : 'GET',
                url : url + fileNo,
                success : function(data) {
                    //console.log(data);
                    if (data.files && data.files.length > 0) {
                        var i;
                        var slidehtml = '<div class="slideshow-area" id="' + slideId + '">';
                        slidehtml += '<div class="slideshow-container">';
                        for (i = 0; i < data.files.length; i++) {
                            slidehtml += '<div class="mySlides';
                            if (transition) {
                                slidehtml += ' animated ' + transition;
                            }
                            slidehtml += '">';
                            slidehtml += '<img src="' + _CONTEXT + '/images/loader.gif" lagy="' + _CONTEXT + '/open.file/slide/' + data.fileNo + '/';
                            slidehtml += data.files[i] + '" alt="slide-' + i + '" class="slide-image slide-image-' + fileNo + '" />';
                            slidehtml += '</div>';
                        }
                        slidehtml += '</div><br/>';
                        slidehtml += '<div class="slideshow-control">';
                        slidehtml += '<a class="prev" onclick="plusSlides(-1, \'' + slideId + '\')">&#10094; prev</a>';
                        slidehtml += '<a class="next" onclick="plusSlides(1, \'' + slideId + '\')">next &#10095;</a>';
                        slidehtml += '<div style="text-align:center">';
                        slidehtml += '<div class="numbertext"><span class="current">1</span> / ' + data.files.length;
                        slidehtml += '&nbsp;&nbsp;&nbsp;<a onclick="requestFullscreen(\'' + slideId + '\');">';
                        slidehtml += '<i class="full fa fa-television fa-2x" aria-hidden="true"></i></a></div>';
                        if (data.files.length < 60) {
                            slidehtml += '<div class="dotArea">';
                            for (i = 0; i < data.files.length; i++) {
                                slidehtml += '<span class="dot" onclick="currentSlide(' + (i+1) + ', \'' + slideId + '\')"></span> ';
                            }
                            slidehtml += '</div>';
                        }
                        slidehtml += '</div></div></div>';
                        slideArea.html(slidehtml);
                        showSlides(indexMap[slideId], slideId);
                    }
                },
                error: function(XMLHttpRequest, status){
                    console.log(status)
                }
            });
        }
    });
};

function plusSlides(n, slideId) {
    return showSlides(indexMap[slideId] += n, slideId);
}

function currentSlide(n, slideId) {// eslint-disable-line no-unused-vars
    return showSlides(indexMap[slideId] = n, slideId);
}

function showSlides(n, slideId) {
    return new Promise(function(resolve) {
        var slideIndex = indexMap[slideId];
        //console.log(slideIndex);
        var i;
        var slideArea = document.getElementById(slideId);
        var slides = slideArea.getElementsByClassName('mySlides');
        var dots = slideArea.getElementsByClassName('dot');
        if (n > slides.length) {slideIndex = 1} 
        if (n < 1) {slideIndex = slides.length}
        indexMap[slideId] = slideIndex;

        // 表示の切り替えは、次のスライド画像の読み込みが終わってから行う
        // (読み込み中にスライドの高さが0になり、下のコンテンツが上下に動くのを防ぐ)
        var swap = function() {
            if (indexMap[slideId] !== slideIndex) {
                return resolve(); // 連打された場合は最後の操作だけを反映する
            }
            for (i = 0; i < slides.length; i++) {
                slides[i].style.display = 'none'; 
            }
            for (i = 0; i < dots.length; i++) {
                if (dots[i]) {
                    dots[i].className = dots[i].className.replace(' active', '');
                }
            }
            slides[slideIndex-1].style.display = 'block';
            if (dots[slideIndex-1]) {
                dots[slideIndex-1].className += ' active';
            }
            $('#' + slideId).find('.current').html(slideIndex);
            return resolve();
        };

        var slideimg = $(slides[slideIndex-1]).find('img').first();
        var src = slideimg.attr('lagy');
        var shown = false; // 既に表示中のスライドがあるか(初期表示時はfalse)
        for (i = 0; i < slides.length; i++) {
            if (slides[i].style.display === 'block') {
                shown = true;
            }
        }
        if (!src || slideimg.attr('src') === src || !shown) {
            if (src) {
                slideimg.attr('src', src);
            }
            return swap();
        }
        var preload = new Image();
        preload.onload = preload.onerror = function() {
            slideimg.attr('src', src);
            swap();
        };
        preload.src = src;
    });
}

$(document).on({
    'mouseenter': function () {
        var id = $(this).attr('id');
        $(window).on('keydown', function (e) {
            e.preventDefault();

            if (37 == e.keyCode) {
                plusSlides(-1, id);
            }

            if (39 == e.keyCode) {
                plusSlides(1, id);
            }
        });
    },
    'mouseleave': function () {
        $(window).off('keydown');
    }
}, '.slideshow-area');
