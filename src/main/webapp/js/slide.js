var indexMap = {};

var showSlide = function() {
    var url = _CONTEXT + '/open.file/slide/';
    $('.slideshow').each(function(i, block) {
        var fileNo = $(this).attr('slide');
        var slideArea = $(this);
        if (fileNo) {
            var slideId = 'slide-' + fileNo;
            indexMap[slideId] = 1;
            console.log(fileNo);
            $.ajax({
                type : 'GET',
                url : url + fileNo,
                success : function(data, dataType) {
                    console.log(data);
                    if (data.files && data.files.length > 0) {
                        var slidehtml = '<div class="slideshow-area">';
                        slidehtml += '<div class="slideshow-container">';
                        for (var i = 0; i < data.files.length; i++) {
                            slidehtml += '<div class="mySlides fade in">';
                            slidehtml += '<img src="' + _CONTEXT + '/open.file/slide/' + fileNo + '/';
                            slidehtml += data.files[i] + '" alt="slide-' + i + '" style="width:100%" />';
                            slidehtml += '</div>';
                        }
                        slidehtml += '</div><br/>';
                        slidehtml += '<div class="slideshow-control">';
                        slidehtml += '<a class="prev" onclick="plusSlides(-1, \'' + slideId + '\')">&#10094; prev</a>';
                        slidehtml += '<a class="next" onclick="plusSlides(1, \'' + slideId + '\')">next &#10095;</a>';
                        slidehtml += '</div>';
                        slidehtml += '<div style="text-align:center">';
                        slidehtml += '<div class="numbertext"><span class="current">1</span> / ' + data.files.length + '</div>';
                        if (data.files.length < 30) {
                            for (var i = 0; i < data.files.length; i++) {
                                slidehtml += '<span class="dot" onclick="currentSlide(' + (i+1) + ', \'' + slideId + '\')"></span> ';
                            }
                        }
                        slidehtml += '</div></div>';
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
    console.log(slideIndex);
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


