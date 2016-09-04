var slideIndex = 1;
var showSlide = function() {
    var url = _CONTEXT + '/open.file/slide/';
    $('.slideshow').each(function(i, block) {
        var fileNo = $(this).attr('slide');
        if (fileNo) {
            console.log(fileNo);
            $.ajax({
                type : 'GET',
                url : url + fileNo,
                success : function(data, dataType) {
                    console.log(data);
                    showSlides(slideIndex);
                },
                error: function(XMLHttpRequest, status, errorThrown){
                    console.log(status)
                }
            });
        }
    });
};

function plusSlides(n) {
    showSlides(slideIndex += n);
}

function currentSlide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("mySlides");
    var dots = document.getElementsByClassName("dot");
    if (n > slides.length) {slideIndex = 1} 
    if (n < 1) {slideIndex = slides.length}
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none"; 
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }
    slides[slideIndex-1].style.display = "block"; 
    dots[slideIndex-1].className += " active";
}


