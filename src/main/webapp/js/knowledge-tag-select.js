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
        freeInput: true
    });
    elt.on('typeahead:selected', function(event, datum) {
        console.log(datum);
    });
    // call tagselect.js
    setUpTagSelect();
});


