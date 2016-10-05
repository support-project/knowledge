var indexMap = {};
var full = false;
var slideCount = 0;

function requestFullscreen(id) {
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
}

var showSlide = function(parent) {
    console.log(parent);
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
    target.each(function(i, block) {
        var fileNo = $(this).attr('slide');
        var slideArea = $(this);
        if (fileNo) {
            slideCount++;
            var slideId = 'slide-area-' + slideCount;
            indexMap[slideId] = 1;
            $.ajax({
                type : 'GET',
                url : url + fileNo,
                success : function(data, dataType) {
                    //console.log(data);
                    if (data.files && data.files.length > 0) {
                        var slidehtml = '<div class="slideshow-area" id="' + slideId + '">';
                        slidehtml += '<div class="slideshow-container">';
                        for (var i = 0; i < data.files.length; i++) {
                            slidehtml += '<div class="mySlides fade in">';
                            slidehtml += '<img src="' + _CONTEXT + '/open.file/slide/' + data.fileNo + '/';
                            slidehtml += data.files[i] + '" alt="slide-' + i + '" class="slide-image" />';
                            slidehtml += '</div>';
                        }
                        slidehtml += '</div><br/>';
                        slidehtml += '<div class="slideshow-control">';
                        slidehtml += '<a class="prev" onclick="plusSlides(-1, \'' + slideId + '\')">&#10094; prev</a>';
                        slidehtml += '<a class="next" onclick="plusSlides(1, \'' + slideId + '\')">next &#10095;</a>';
                        slidehtml += '<div style="text-align:center">';
                        slidehtml += '<div class="numbertext"><span class="current">1</span> / ' + data.files.length;
                        slidehtml += '&nbsp;&nbsp;&nbsp;<a href="#" onclick="requestFullscreen(\'' + slideId + '\');">';
                        slidehtml += '<i class="full fa fa-television fa-2x" aria-hidden="true"></i></a></div>';
                        if (data.files.length < 60) {
                            slidehtml += '<div class="dotArea">';
                            for (var i = 0; i < data.files.length; i++) {
                                slidehtml += '<span class="dot" onclick="currentSlide(' + (i+1) + ', \'' + slideId + '\')"></span> ';
                            }
                            slidehtml += '</div>';
                        }
                        slidehtml += '</div></div></div>';
                        slideArea.html(slidehtml);
                        showSlides(indexMap[slideId], slideId);
                    }
                },
                error: function(XMLHttpRequest, status, errorThrown){
                    console.log(status)
                }
            });
        }
    });
};

function plusSlides(n, slideId) {
    showSlides(indexMap[slideId] += n, slideId);
}

function currentSlide(n, slideId) {
    showSlides(indexMap[slideId] = n, slideId);
}

function showSlides(n, slideId) {
    var slideIndex = indexMap[slideId];
    //console.log(slideIndex);
    var i;
    var slideArea = document.getElementById(slideId);
    var slides = slideArea.getElementsByClassName("mySlides");
    var dots = slideArea.getElementsByClassName("dot");
    if (n > slides.length) {slideIndex = 1} 
    if (n < 1) {slideIndex = slides.length}
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none"; 
    }
    for (i = 0; i < dots.length; i++) {
        if (dots[i]) {
            dots[i].className = dots[i].className.replace(" active", "");
        }
    }
    slides[slideIndex-1].style.display = "block"; 
    if (dots[slideIndex-1]) {
        dots[slideIndex-1].className += " active";
    }
    
    $('#' + slideId).find('.current').html(slideIndex);
    indexMap[slideId] = slideIndex;
}


