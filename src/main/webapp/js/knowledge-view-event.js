$(document).ready(function() {
    logging($('#typeId').val());
    var participations = [];
    
    var isParticipation = function() {
        $.ajax({
            url: _CONTEXT + '/open.event/participation/' + $('#knowledgeId').val(),
            type: 'GET',
            timeout: 10000,
        }).done(function(result, textStatus, xhr) {
            console.log(result);
            participations = result.participations;
            if (result.status > 0) {
                $('#btnNonparticipation').removeClass('hide');
                $('#btnParticipation').addClass('hide');
            } else {
                $('#btnParticipation').removeClass('hide');
                $('#btnNonparticipation').addClass('hide');
            }
            var eventInfo = result.count + ' / ' + result.limit;
            $('#eventStatus').html(eventInfo);
        }).fail(function(xhr, textStatus, error) {
            handleErrorResponse(xhr, textStatus, error);
        }).always(function( jqXHR, textStatus ) {
            $('#loading').addClass('hide');
        });

    };
    
    if ($('#typeId').val() === '-101') {
        isParticipation();
    }
    
    $('#eventInfoLink').click(function() {
        var list = '';
        if (participations.length === 0) {
            list += 'Empty';
        } else {
            list += '<ul class="list-group">';
            for (var i = 0; i < participations.length; i++) {
                var p = participations[i];
                list += '<li class="list-group-item">';
                if (p.status === 1) {
                    list += '<span class="badge">' + _LABEL_STATUS_WAIT_CANCEL + '</span>';
                } else {
                    list += '<span class="badge">' + _LABEL_STATUS_PARTICIPANT + '</span>';
                }
                list += p.userName;
                list += '</li>';
            }
            list += '</ul>';
        }
        
        var dialog = bootbox.dialog({
            title: _LABEL_PARTICIPANTS,
            message: list,
            closeButton: true,
            buttons: {
                cancel: {
                    label: 'Close',
                    className: 'btn-default'
                }
            }
        });
    });    
    
    
    $('#btnParticipation').click(function() {
        $.ajax({
            url: _CONTEXT + '/protect.event/participation/' + $('#knowledgeId').val(),
            type: 'PUT',
            timeout: 10000,
        }).done(function(result, textStatus, xhr) {
            isParticipation();
            if (result.value == 'true') {
                $.notify(result.label, 'success');
            } else {
                $.notify(result.label, 'warn');
            }
        }).fail(function(xhr, textStatus, error) {
            handleErrorResponse(xhr, textStatus, error);
        }).always(function( jqXHR, textStatus ) {
        });
    });
    $('#btnNonparticipation').click(function() {
        bootbox.confirm(_MSG_CONFIRM_CANCEL, function(result) {
            if (result) {
                $.ajax({
                    url: _CONTEXT + '/protect.event/nonparticipation/' + $('#knowledgeId').val(),
                    type: 'DELETE',
                    timeout: 10000,
                }).done(function(result, textStatus, xhr) {
                    isParticipation();
                    $.notify(result.msg, 'success');
                }).fail(function(xhr, textStatus, error) {
                    handleErrorResponse(xhr, textStatus, error);
                }).always(function( jqXHR, textStatus ) {
                });
            }
        });
    });
    
    
    
});