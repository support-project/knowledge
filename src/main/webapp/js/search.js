$(document).ready(function() {
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

	$('#searchParamClear').click(function(){
		$('#searchkeyword').val('');
		tagElt.tagsinput('removeAll');
		groupElt.tagsinput('removeAll');
	});
});