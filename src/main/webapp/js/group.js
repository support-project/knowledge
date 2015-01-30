function deleteGroup() {
	bootbox.confirm(_CONFIRM_DELETE, function(result) {
		if (result) {
			$('#groupForm').attr('action', _CONTEXT + '/protect.group/delete');
			$('#groupForm').submit();
		}
	}); 
};



