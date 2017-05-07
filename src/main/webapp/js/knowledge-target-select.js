var groups = [];
var selectedGroups = [];
var editors = [];
var selectedEditors = [];

$(document).ready(function() {
    dispChangeGroupArea($('input[name="publicFlag"]:checked').val());
    $('input[name="publicFlag"]:radio').change( function() {
        dispChangeGroupArea($( this ).val());
    });
    
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
        selectedGroups = [];
        viewGroup();
    });
    viewGroup();
    
    // editor
    var editorKeyword = $('#editorKeyword').val();
    var offset = 0;
    $('#editorSelectModal').on('show.bs.modal', function (event) {
        getGroups(keyword, offset, '#editorList', '#editorPage', 'selectEditor');
    });
    $('#editorSearchButton').click(function() {
        keyword = $('#editorKeyword').val();
        offset = 0;
        getGroups(keyword, offset, '#editorList', '#editorPage', 'selectEditor');
    });
    $('#editorSearchPrevious').click(function() {
        keyword = $('#editorKeyword').val();
        offset--;
        if (offset < 0){
            offset = 0;
        }
        getGroups(keyword, offset, '#editorList', '#editorPage', 'selectEditor');
    });
    $('#editorSearchNext').click(function() {
        keyword = $('#editorKeyword').val();
        offset++;
        getGroups(keyword, offset, '#editorList', '#editorPage', 'selectEditor');
    });
    $('#clearSelectedEditor').click(function() {
        selectedEditors = [];
        viewEditor();
    });
    viewEditor();
    
});


// functions
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
            offset: offset
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
                html += '<a href="javascript:void(0);" class="list-group-item" onclick="' + selectFunc + '(' + int + ')">';
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
    for (var i = 0; i < selectedGroups.length; i++) {
        var item = selectedGroups[i];
        if (item.value == groups[idx].value) {
            exist = true;
            break;
        }
    }
    if (!exist) {
        selectedGroups.push(groups[idx]);
    }
    viewGroup();
};

var viewGroup = function() {
    var values = [];
    var labels = [];
    for (var i = 0; i < selectedGroups.length; i++) {
        var item = selectedGroups[i];
        values.push(item.value);
        labels.push(item.label);
    }
    if (selectedGroups.length == 0) {
        $('#clearSelectedGroup').hide();
    } else {
        $('#clearSelectedGroup').show();
    }
    
    $('#selectedList').text(labels.join(','));
    $('#groupsLabel').text(labels.join(','));
    $('#groups').val(values.join(','));
};

var selectEditor = function(idx) {
    var exist = false;
    for (var i = 0; i < selectedEditors.length; i++) {
        var item = selectedEditors[i];
        if (item.value == editors[idx].value) {
            exist = true;
            break;
        }
    }
    if (!exist) {
        selectedEditors.push(editors[idx]);
    }
    viewEditor();
};

var viewEditor = function() {
    var values = [];
    var labels = [];
    for (var i = 0; i < selectedEditors.length; i++) {
        var item = selectedEditors[i];
        values.push(item.value);
        labels.push(unescapeHTML(item.label));
    }
    if (selectedEditors.length == 0) {
        $('#clearSelectedEditor').hide();
    } else {
        $('#clearSelectedEditor').show();
    }
    
    $('#selectedEditorList').text(labels.join(','));
    $('#editorsLabel').text(labels.join(','));
    $('#editors').val(values.join(','));
};

var dispChangeGroupArea = function(val) {
    var grops_area = $('#grops_area');
    if (val == '2') {
        grops_area.show('normal');
    } else {
        grops_area.hide('normal');
    }
};
