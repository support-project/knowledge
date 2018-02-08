var http = require('http');
http.createServer(function (req, res) {
  
  if (req.method === 'POST') {
    var data = '';
    req.on('readable', function(chunk) {
        data += req.read() || '';
    });
    req.on('end', function() {
      res.writeHead(200, {'Content-Type': 'text/plain'});
      //console.log(data);
      console.log(new Date());
      console.log(JSON.stringify(JSON.parse(data), null, '  '));
      res.end(data);
    });
  } else {
    res.writeHead(200, {'Content-Type': 'text/plain'});
    res.end('Hello world!\n');
  }
}).listen(8081);
