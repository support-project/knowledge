$(document).ready(function() {
    // Tags
    var tagElt= $('#input_tags');
    tagElt.tagsinput({
        typeahead: {
            source: _TAGS,
            displayText: function(item) {
                if (item) {
                    return item.name || item;
                }
                return '';
            }
        },
        freeInput: false
    });
    tagElt.on('typeahead:selected', function(event, datum) {
        console.log(datum);
    });
    setUpTagSelect();

    // Groups
    var groupElt= $('#input_groups');
    groupElt.tagsinput({
        typeahead: {
            source: _GROUPS,
            displayText: function(item) {
                if (item) {
                    return item.label || item;
                }
                return '';
            }
        },
        freeInput: false
    });
    groupElt.on('typeahead:selected', function(event, datum) {
        console.log(datum);
    });
    setUpGroupSelect();

    
    // Creators
    var getUsers = function(page) {
        var url = _CONTEXT + '/open.api/users';
        var params = {
            keyword : $('#searchUserKeyword').val(),
            offset: page
        };
        $.get(url, params, function(results){
            console.log(results);
            var html = '';
            if (!results || results.length <= 0) {
                html += 'no datas';
            } else {
                for (var i = 0; i < results.length; i++) {
                    var user = results[i];
                    html += '<li class="list-group-item">';
                    html += '<span class="name cursor-pointer"><i class="fa fa-group"></i>&nbsp;';
                    html += user.userName;
                    html += '</span></li>';
                }
            }
            $('#searchUserData').html(html);
            $('#searchUserData').find('.name').each(function(i, block) {
                $(this).click(function(event) {
                    var val = $(this).text().trim();
                    $('#creators').tagsinput('add', val);
                });
            });
            
        });
        var viewPage = page + 1;
        $('#searchUserPageNo').text(viewPage);
    };
    var setUpUserSelect = function() {
        var pageNo = 0;
        $('#searchUserModal').on('shown.bs.modal', function (event) {
            getUsers(pageNo);
        });
        $('#searchUserPrev').click(function() {
            pageNo--;
            if (pageNo < 0) {
                pageNo = 0;
            }
            getUsers(pageNo);
        });
        $('#searchUserNext').click(function() {
            pageNo++;
            getUsers(pageNo);
        });
        $('#searchUserSearch').click(function() {
            pageNo = 0;
            getUsers(pageNo);
        });
    };    
    var creatorsElt = $('#creators');
    creatorsElt.tagsinput({
        typeahead: {
            source: _GROUPS,
            displayText: function(item) {
                if (item) {
                    return item.label || item;
                }
                return '';
            }
        },
        freeInput: false
    });
    setUpUserSelect();
    
    // Reset
    $('#searchParamClear').click(function(){
        $('#searchkeyword').val('');
        tagElt.tagsinput('removeAll');
        groupElt.tagsinput('removeAll');
        creatorsElt.tagsinput('removeAll');
    });
    
    
    
    
    
    
    
});