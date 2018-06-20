var loadPinsList = function() {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: _CONTEXT + '/admin.pin/list',
            type: 'GET',
            timeout: 10000,
        }).done(function(result, textStatus, xhr) {
            console.log(result);
            return resolve(result);
        }).fail(function(xhr, textStatus, error) {
            return reject(xhr);
        }).always(function( jqXHR, textStatus ) {
        });
    });
}

var writeList = function (arry) {
    return Promise.try(function() {
        var html = '<ul class="list-group">';
        arry.forEach(function(item) {
            html += '<li class="list-group-item">' + item.knowledgeId;
            html += '&nbsp;&nbsp;<button class="btn btn-sm btn-info" onclick="doRemovePin(' + item.knowledgeId + ')">remove</button>'
            html += '</li>';
        });
        html += '</ul>';
        $('#list').html(html);
    });
};

var addPin = function() {
    var id = $('#knowledgeId').val();
    if (!id) {
        return;
    }
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: _CONTEXT + '/admin.pin/add/' + id,
            type: 'POST',
            timeout: 10000,
        }).done(function(result, textStatus, xhr) {
            console.log(result);
            return resolve(result);
        }).fail(function(xhr, textStatus, error) {
            return reject(xhr);
        }).always(function( jqXHR, textStatus ) {
        });
    });
};

var removePin = function(id) {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: _CONTEXT + '/admin.pin/remove/' + id,
            type: 'DELETE',
            timeout: 10000,
        }).done(function(result, textStatus, xhr) {
            console.log(result);
            return resolve(result);
        }).fail(function(xhr, textStatus, error) {
            return reject(xhr);
        }).always(function( jqXHR, textStatus ) {
        });
    });
};

var doRemovePin = function(id) {
    return Promise.try(function() {
        return removePin(id);
    }).then(function(result) {
        return loadPinsList();
    }).then(function(result) {
        return writeList(result);
    }).catch(function(err) {
        $.notify('The operation failed', 'warn');
    });
};


$(document).ready(function() {
    $('#addPin').click(function() {
        return Promise.try(function() {
            return addPin();
        }).then(function(result) {
            return loadPinsList();
        }).then(function(result) {
            return writeList(result);
        }).catch(function(err) {
            $.notify('The operation failed', 'warn');
        });
    });
    loadPinsList().then(function(result) {
        return writeList(result);
    }).catch(function(err) {
        $.notify('The operation failed', 'warn');
    });
});
