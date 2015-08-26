$(document).ready(function() {
	var elt= $('#input_tags');
	elt.tagsinput({
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
	elt.on('typeahead:selected', function(event, datum) {
		console.log(datum);
	});
	setUpTagSelect();
	$('#searchParamClear').click(function(){
		$('#searchkeyword').val('');
		elt.tagsinput('removeAll');
	});
});

