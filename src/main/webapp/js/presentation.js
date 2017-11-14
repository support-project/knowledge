var setHeight = function() {
    var width = $('#presentation').width();
    var height = width * 9 / 16;
    logging(width + ':' + height);
    $('.markdownSlide').height(height);
    if ($('#presentation').innerWidth() > 1024) {
        // 全画面表示と判定
        $('.markdownSlide').css('padding', '6vh');
        $('.markdownSlide').css('font-size', '5vh');
        $('.markdownSlide p').css('font-size', '5vh');
        $('.markdownSlide h1').css('font-size', '10vh');
        $('.markdownSlide h2').css('font-size', '8vh');
        $('.markdownSlide h3').css('font-size', '6vh');
        $('.markdownSlide h4').css('font-size', '5vh');
        $('.markdownSlide p').css('line-height', '6vh');
        $('.markdownSlide h1').css('line-height', '12vh');
        $('.markdownSlide h2').css('line-height', '10vh');
        $('.markdownSlide h3').css('line-height', '6vh');
        $('.markdownSlide h4').css('line-height', '6vh');
    } else {
        $('.markdownSlide').css('padding', '3vh');
        $('.markdownSlide').css('font-size', '3vh');
        $('.markdownSlide p').css('font-size', '3vh');
        $('.markdownSlide h1').css('font-size', '5vh');
        $('.markdownSlide h2').css('font-size', '4vh');
        $('.markdownSlide h3').css('font-size', '3vh');
        $('.markdownSlide h4').css('font-size', '3vh');
        $('.markdownSlide p').css('line-height', '4vh');
        $('.markdownSlide h1').css('line-height', '6vh');
        $('.markdownSlide h2').css('line-height', '5vh');
        $('.markdownSlide h3').css('line-height', '4vh');
        $('.markdownSlide h4').css('line-height', '4vh');
    }
};

var createPresentation = function(targetId) {
    return Promise.try(function() {
        $(window).resize(function(){
            setHeight();
        });
        
        logging('createPresentation');
        var presentationArea = $('#presentationArea');
        
        var sections = [];
        var section = $('<div class="mySlides markdownSlide slide-fade in"></div>');
        var add = false;
        $(targetId).children().each(function(i, elem) {
            if (elem.tagName.toLowerCase() == 'hr') {
                sections.push(section);
                section = $('<div class="mySlides markdownSlide slide-fade in"></div>');
                add = false;
            } else {
                section.append($(elem).clone());
                add = true;
            }
        });
        if (add) {
            sections.push(section);
        }
        
        var slideLength = sections.length;
        var slideId = 'presentation';
        
        indexMap[slideId] = 1;
        var slidehtml = '<div class="slideshow-area" id="' + slideId + '">';
        slidehtml += '<div class="slideshow-container" id="sheets"></div>';
        slidehtml += '<br/>';
        slidehtml += '<div class="slideshow-control">';
        slidehtml += '<a class="prev" onclick="plusSlides(-1, \'' + slideId + '\')">&#10094; prev</a>';
        slidehtml += '<a class="next" onclick="plusSlides(1, \'' + slideId + '\')">next &#10095;</a>';
        slidehtml += '<div style="text-align:center">';
        slidehtml += '<div class="numbertext"><span class="current">1</span> / ' + slideLength;
        
        slidehtml += '&nbsp;&nbsp;&nbsp;<a onclick="requestFullscreen(\'' + slideId + '\');">';
        
        slidehtml += '<i class="full fa fa-television fa-2x" aria-hidden="true"></i></a></div>';
        if (slideLength < 60) {
            slidehtml += '<div class="dotArea">';
            for (var i = 0; i < slideLength; i++) {
                slidehtml += '<span class="dot" onclick="currentSlide(' + (i+1) + ', \'' + slideId + '\')"></span> ';
            }
            slidehtml += '</div>';
        }
        slidehtml += '</div></div></div>';
        presentationArea.html(slidehtml);
        
        sections.forEach(function(s) {
            $('#sheets').append(s);
        });
        
        logging(presentationArea.html());
        showSlides(indexMap[slideId], slideId);
        setHeight();
    });
    
    
};

