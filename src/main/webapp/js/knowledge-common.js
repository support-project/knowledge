hljs.initHighlightingOnLoad();

var emoji = window.emojiParser;

var codeHighlight = function(block) {
	var highlightPromises = [];
	block.find('pre code').each(function(i, block) {
		var jqobj = $(this);
		highlightPromises.push(new Promise(function(resolve, reject) {
			try {
				var text = jqobj.text();
				if (text.indexOf('://') != -1) {
					console.log('skip on hljs freeze word');
					console.log(text);
					return resolve();
				}
				var result = hljs.highlightAuto(text);
				jqobj.html(result.value);
				return resolve();
			} catch(err) {
				console.err(err);
				return reject(err);
			}
		}));
	});
	block.find('p code').each(function(i, block) {
		var jqobj = $(this);
		highlightPromises.push(new Promise(function(resolve, reject) {
			try {
				jqobj.addClass('hljs');
				jqobj.addClass('stylus');
				var text = jqobj.text();
				if (text.indexOf('://') != -1) {
					console.log('skip on hljs freeze word');
					console.log(text);
					return resolve();
				}
				var result = hljs.highlightAuto(text);
				jqobj.html(result.value);
				return resolve();
			} catch(err) {
				console.err(err);
				return reject(err);
			}
		}));
	});

	return Promise.all(highlightPromises);
};
