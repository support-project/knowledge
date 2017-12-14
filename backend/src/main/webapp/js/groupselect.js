var getGroupDatas = function(page) {
    var url = _CONTEXT + '/protect.group/json/' + page;
    var params = {
        keyword : $('#groupselectKeyword').val()
    };
    $.get(url, params, function(results){
        console.log(results);
        var html = '';
        if (!results || results.length <= 0) {
            html += 'no datas';
        } else {
            for (var i = 0; i < results.length; i++) {
                var group = results[i];
                html += '<li class="list-group-item">';
                html += '<span class="name cursor-pointer"><i class="fa fa-group"></i>&nbsp;';
                html += group.groupName;
                html += '</span></li>';
            }
        }
        
        $('#groupDatas').html(html);
    
        $('#groupDatas').find('.name').each(function(i, block) {
            $(this).click(function(event) {
                var val = $(this).text();
                var grouparea = $('#input_groups');
                grouparea.tagsinput('add', val);
            });
        });
        
    });
    
    var viewPage = page + 1;
    $('#groupselectPageNo').text(viewPage);
};

var setUpGroupSelect = function() {
    var pageNo = 0;
    $('#groupSelectModal').on('shown.bs.modal', function (event) {
        getGroupDatas(pageNo);
    });
    $('.groupselectPagerPrev').click(function() {
        pageNo--;
        if (pageNo < 0) {
            pageNo = 0;
        }
        getGroupDatas(pageNo);
    });
    $('.groupselectPagerNext').click(function() {
        pageNo++;
        getGroupDatas(pageNo);
    });
    $('.groupselectSearchButton').click(function() {
        pageNo = 0;
        getGroupDatas(pageNo);
    });
};