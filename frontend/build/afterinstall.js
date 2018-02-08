var fs = require('fs-extra');

const path = require('path');

var source = path.resolve('node_modules/bootstrap/dist');
var dest = path.resolve('static/lib/bootstrap/dist');

fs.mkdirsSync(dest);
fs.copySync(source, dest);

source = path.resolve('node_modules/font-awesome');
dest = path.resolve('static/lib/font-awesome');

fs.mkdirsSync(dest);
fs.copySync(source, dest);

source = path.resolve('node_modules/jquery/dist');
dest = path.resolve('static/lib/jquery/dist');

fs.mkdirsSync(dest);
fs.copySync(source, dest);

source = path.resolve('node_modules/admin-lte/dist');
dest = path.resolve('static/lib/admin-lte/dist');

fs.mkdirsSync(dest);
fs.copySync(source, dest);

source = path.resolve('node_modules/pace-progress/themes');
dest = path.resolve('static/lib/pace-progress/themes');

fs.mkdirsSync(dest);
fs.copySync(source, dest);

source = path.resolve('node_modules/pace-progress/pace.min.js');
dest = path.resolve('static/lib/pace-progress/pace.min.js');

fs.copySync(source, dest);

source = path.resolve('node_modules/html5shiv/dist');
dest = path.resolve('static/lib/html5shiv/dist');

fs.mkdirsSync(dest);
fs.copySync(source, dest);

source = path.resolve('node_modules/respond.js/dest');
dest = path.resolve('static/lib/respond.js/dest');

fs.mkdirsSync(dest);
fs.copySync(source, dest);

source = path.resolve('node_modules/highlight.js/styles');
dest = path.resolve('static/lib/highlight.js/styles');

fs.mkdirsSync(dest);
fs.copySync(source, dest);
