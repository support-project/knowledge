var emojisList = $.map(emojis, function(value, i) {
    return {'id':i, 'name':value};
});

var setAutoComplete = function(target) {
    target.atwho({
        at: ':',
        displayTpl: "<li><img src='" + _CONTEXT + "/bower/emoji-parser/emoji/${name}.png' height='20' width='20'/> ${name} </li>",
        insertTpl: ":${name}:",
        data: emojisList
    }).atwho({
        at: "#", 
        displayTpl: function(k) {
            return '<li> #' + k.knowledgeId + '&nbsp;<small>' + k.title + '</small></li>';
        },
        insertTpl: function(k) {
            return '#' + k.knowledgeId;
        },
        searchKey : 'knowledgeId',
        callbacks: {
            remoteFilter: function(query, callback) {
                $.getJSON(_CONTEXT + "/open.knowledge/items", {q: query}, function(data) {
                    callback(data.items);
                });
            }
        }
    });
    
};
