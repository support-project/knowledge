function deleteGroup() {
    bootbox.confirm(_CONFIRM_DELETE, function(result) {
        if (result) {
            $('#groupForm').attr('action', _CONTEXT + '/protect.group/delete');
            $('#groupForm').submit();
        }
    }); 
};

var slectedUser = [];
var slectedUserName = [];

$(document).ready(function() {
    // targets
    var keyword = $('#groupKeyword').val();
    var offset = 0;
    $('#groupSelectModal').on('show.bs.modal', function (event) {
        getGroups(keyword, offset);
    });
    $('#groupSearchButton').click(function() {
        keyword = $('#groupKeyword').val();
        offset = 0;
        getGroups(keyword, offset);
    });
    $('#groupSearchPrevious').click(function() {
        keyword = $('#groupKeyword').val();
        offset--;
        if (offset < 0){
            offset = 0;
        }
        getGroups(keyword, offset);
    });
    $('#groupSearchNext').click(function() {
        keyword = $('#groupKeyword').val();
        offset++;
        getGroups(keyword, offset);
    });
    $('#groupDecision').click(function() {
        $('#groupSelectModal').modal('hide')
    });
    $('#clearSelectedGroup').click(function() {
        slectedUser = [];
        slectedUserName = [];
        $('#selectedList').text(slectedUserName.join(','));
    });
    $('#groupAdd').click(function() {
        $('#loading').html('<i class="fa fa-spinner fa-spin"></i>');
        
        // 選択されているユーザをグループに追加
        $.post(_CONTEXT + '/protect.group/addusers', {
            group : $('#groupId').val(),
            users : slectedUser.join(',')
        }, function(data) {
            console.log(data);
            //再読み込みすれば追加したメンバーを表示
            window.location.href = _CONTEXT + '/protect.group/view/' + $('#groupId').val();
            //$('#groupSelectModal').modal('hide');
        });
    });

    $("#wizardModal").wizard({
        onfinish:function(){
            window.location.href = _CONTEXT + '/protect.group/view/' + $('#groupId').val();
            return false;
        },
        validateNext: function(step) {
            console.log(step);
            return new Promise(function(resolve) {
                if (step === 1) {
                    $.post(_CONTEXT + '/protect.group/extractemail', {
                        str : $('#inputEmail').val()
                    }, function(data) {
                        console.log(data);
                        $('#mailCount').html(data.list.length);
                        $('#emails').val(data.list.join('\n'));
                        return resolve(true);
                    });
                } else if (step === 2) {
                    if ($('#emails').val().length === 0) {
                        return resolve(false);
                    }
                    $.post(_CONTEXT + '/protect.group/addUsersOnEmail'.toLowerCase(), {
                        group : $('#groupId').val(),
                        emails : $('#emails').val()
                    }, function(data) {
                        console.log(data);
                        $('#msgResult').val(data.message);
                        return resolve(true);
                    });
                } else {
                    return resolve(true);
                }
            });
        }
    });


});


var getGroups = function(keyword, offset, listId, pageId, selectFunc) {
    if (!listId) {
        listId = '#groupList';
        pageId = '#groupPage';
        selectFunc = 'selectGroup';
    }
    $(listId).html('Now loading...');
    var url = _CONTEXT + '/protect.target/typeahead'
    var params = {
        keyword : keyword,
        offset: offset,
        filter: 'user'
    };
    
    $.get(url, params, function(result){
        groups = result;
        editors = result;
        var html = '';
        if (result.length == 0) {
            html += 'empty';
        } else {
            html+= '<div class="list-group">';
            for (var int = 0; int < result.length; int++) {
                html += '<a class="list-group-item" onclick="' + selectFunc + '(' + int + ')">';
                html += result[int].label;
                html += '</a>';
            }
            html += '</div>';
        }
        $(listId).html(html);
        $(pageId).text('- page:' + (offset + 1) + ' -');
    });
};


var selectGroup = function(idx) {
    var exist = false;
    for (var i = 0; i < slectedUser.length; i++) {
        var item = slectedUser[i];
        if (item == groups[idx].value) {
            exist = true;
            break;
        }
    }
    if (!exist) {
        console.log(groups[idx]);
        slectedUser.push(groups[idx].value);
        slectedUserName.push(groups[idx].label);
        $('#selectedList').text(slectedUserName.join(','));
    }
};

