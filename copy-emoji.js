console.log('copy emoji')

var fs = require('fs-extra');

var copy = function (oldPath, newPath) {
    return new Promise(function(resolve, reject) {
        fs.copy(oldPath, newPath, function (err) {
            if (err) {
                return reject(err);
            }
            return resolve();
        });
    });
}

var mkdirs = function(path) {
    return new Promise(function(resolve, reject) {
        fs.mkdirs(path, function(err) {
            if (err) {
                return reject(err);
            }
            return resolve();
        });
    });
};

mkdirs('src/main/webapp/bower/emoji-parser').then(function() {
    return copy('node_modules/emoji-parser/parse.js', 'src/main/webapp/bower/emoji-parser/main.min.js');
}).then(function() {
    return copy('node_modules/emoji-images/pngs', 'src/main/webapp/bower/emoji-parser/emoji/');
}).then(function() {
    console.log('Copied emoji-parser from node_modules to bower.')
}).catch(function(err) {
    console.error(err);
});

