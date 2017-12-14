/* global indexMap:false, showSlides:false, currentSlide:false, html2canvas:false, jsPDF:false */
var setHeight = function() {
    var width = $('#presentation').width();
    var height = width * 9 / 16;
    //logging(width + ':' + height);
    $('.markdownSlide').height(height);
};

var getOptions = function(contentJqObj) {
    var options = {};
    var general = true;
    var option = {};
    options.general = option;
    var pagecount = 0;
    contentJqObj.children().filter(function(){
        if (this.tagName.toLowerCase() == 'hr') {
            return true;
        }
        if (this.tagName.toLowerCase() == 'p') {
            if ($(this).find('var').length > 0) {
                return true;
            }
        }
        return false;
    }).each(function(i, elem) {
        if (elem.tagName.toLowerCase() == 'hr') {
            if (!general) {
                general = false;
            }
            pagecount++;
            option = {};
            options[pagecount] = option;
        } else if (elem.tagName.toLowerCase() == 'p') {
            $(elem).find('var').each(function(i, v) {
                if ($(v).attr('transition')) {
                    option.transition = $(v).attr('transition');
                }
                if ($(v).attr('centered')) {
                    option.centered = $(v).attr('centered');
                }
            });
        }
    });
    return options;
}

var createSection = function(options, pagecount) {
    console.log(options);
    var slideHtmlBase = '<div class="mySlides markdownSlide';
    if (options[pagecount] && options[pagecount].transition) {
        slideHtmlBase += ' animated ' + options[pagecount].transition;
    } else if (options.general.transition) {
        slideHtmlBase += ' animated ' + options.general.transition;
    }
    slideHtmlBase += '"></div>';
    var section = $(slideHtmlBase);
    return section;
}
var appendContent = function(section, options, pagecount) {
    var content = $('<div><div>');
    section.append(content);
    if (pagecount === 0 || options[pagecount] && options[pagecount].centered) {
        content.addClass('centered');
    }
    return content;
}

var slideLength;
var createPresentation = function(contentJqObj) {// eslint-disable-line no-unused-vars
    return Promise.try(function() {
        $(window).resize(function(){
            setHeight();
        });
        logging('createPresentation');
        var presentationArea = $('#presentationArea');
        var options = getOptions(contentJqObj);
        
        var add = false;
        var pagecount = 0;
        var sections = [];
        var section = createSection(options);
        var content = appendContent(section, options, pagecount);
        
        //console.log(contentJqObj);
        contentJqObj.children().each(function(i, elem) {
            if (elem.tagName.toLowerCase() == 'hr') {
                sections.push(section);
                pagecount++;
                section = createSection(options, pagecount);
                content = appendContent(section, options, pagecount);
                add = false;
            } else {
                content.append($(elem).clone());
                add = true;
            }
        });
        if (add) {
            sections.push(section);
        }
        
        slideLength = sections.length;
        var slideId = 'presentation';
        
        indexMap[slideId] = 1;
        var slidehtml = '<div class="slideshow-area" id="' + slideId + '">';
        slidehtml += '<div class="slideshow-container" id="sheets"></div>'; // この中にスライドが入る
        slidehtml += '<br/>';
        slidehtml += '<div class="slideshow-control">';
        slidehtml += '<a class="prev" onclick="plusSlides(-1, \'' + slideId + '\')">&#10094; prev</a>';
        slidehtml += '<a class="next" onclick="plusSlides(1, \'' + slideId + '\')">next &#10095;</a>';

        slidehtml += '<div style="text-align:center">';
        slidehtml += '<div class="numbertext"><span class="current">1</span> / ' + slideLength;
        
        slidehtml += '&nbsp;&nbsp;&nbsp;<a onclick="requestFullscreen(\'' + slideId + '\');">';
        slidehtml += '<i class="full fa fa-television fa-2x" aria-hidden="true"></i></a>';
        
        /* いったんPDFダウンロードボタンはコメントアウト
        slidehtml += '&nbsp;&nbsp;&nbsp;<a id="createPdfButton" onclick="downloadPdf();">';
        slidehtml += '<i class="full fa fa-download fa-2x" aria-hidden="true"></i></a>';
        slidehtml += '&nbsp;<span id="createPdfProgress" class="hide"><i class="fa fa-spinner fa-pulse fa-2x fa-fw"></i></span>';
        */
        
        slidehtml += '</div>';
        
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
        
        // console.log(presentationArea.html());
        showSlides(indexMap[slideId], slideId);
        setHeight();
    });
    
    
};

var loadCanvus = function(doc, width, height) {
    return new Promise(function(resolve) {
        setTimeout(function() {
            var target = document.getElementById('sheets');
            html2canvas(target, {
                onrendered: function(canvas) {
                    var imgData = canvas.toDataURL('image/png');
                    doc.addImage(imgData, 'png', 0, 0, width, height);
                    return resolve();
                }
            });
        }, 50);
    });
}
var downloadPdf = function() {// eslint-disable-line no-unused-vars
    var width = $('#sheets').width();
    var height = $('#sheets').height();
    $('#createPdfProgress').removeClass('hide');
    var doc = new jsPDF('landscape', 'mm', [width, height]);
    var slideId = 'presentation';
    
    /* 全てのページをPDF化しようと思ったが、途中で止まってしまう（多分メモリの問題）
     * 1ページだけダウンロードできるようにしようかと思ったが、解像度もいまいちだし
     * 実用に耐えないため、いったん実施しないようにする（↑でPDFダウンロードボタンをコメントアウト）
    var pages = [];
    for (var i = 1; i <= slideLength; i++) {
        pages.push(i);
    }
    return Promise.mapSeries(pages, function(page, i) {
        //console.log('page:' + page);
        if (i > 0) {
            doc.addPage();
        }
        return currentSlide(page, slideId).then(function() {
            //console.log('page shown.');
            return loadCanvus(doc, width, height);
        });
    }).then(function() {
        $('#createPdfProgress').addClass('hide');
        doc.save('presentation.pdf');
    });
    */
    loadCanvus(doc, width, height).then(function() {
        $('#createPdfProgress').addClass('hide');
        doc.save('presentation.pdf');
    });
};
