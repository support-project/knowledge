var getTagDatas = function(page) {
    var url = _CONTEXT + '/open.tag/json/' + page;
    var params = {
        keyword : $('#tagselectKeyword').val()
    };
    $.get(url, params, function(results){
        //console.log(results);
        var html = '';
        if (!results || results.length <= 0) {
            html += 'no datas';
        } else {
            for (var i = 0; i < results.length; i++) {
                var tag = results[i];
                html += '<li class="list-group-item">';
                html += '<span class="name"><i class="fa fa-tag"></i>&nbsp;';
                html += tag.tagName;
                html += '</span></li>';
            }
        }
        
        $('#tagDatas').html(html);
    
        $('#tagDatas').find('.name').each(function(i, block) {
            $(this).click(function(event) {
                var val = $(this).text();
                var tagarea = $('#input_tags');
                tagarea.tagsinput('add', val);
            });
        });
        
    });
    
    var viewPage = page + 1;
    $('#tagselectPageNo').text(viewPage);
};

var setUpTagSelect = function() {
    var pageNo = 0;
    $('#tagSelectModal').on('shown.bs.modal', function (event) {
        getTagDatas(pageNo);
    });
    $('.tagselectPagerPrev').click(function() {
        pageNo--;
        if (pageNo < 0) {
            pageNo = 0;
        }
        getTagDatas(pageNo);
    });
    $('.tagselectPagerNext').click(function() {
        pageNo++;
        getTagDatas(pageNo);
    });
    $('.tagselectSearchButton').click(function() {
        pageNo = 0;
        getTagDatas(pageNo);
    });
    $('.tagselectAddButton').click(function() {
        var val = $('#tagselectKeyword').val();
        if (val) {
            var tagarea = $('#input_tags');
            tagarea.tagsinput('add', val);
        }
    });
};



