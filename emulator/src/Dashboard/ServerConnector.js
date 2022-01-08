const http = require('http');
const { BrowserWindow } = require('electron')

class ServerConnector {
    constructor(window) {
        this.default_port = 8080;
        this.requestListener = function (req, res) {
            let body = '';

            req.on('data', function (data) {
              body += data;
            });

            req.on('end', function () {
                // console.log(body);
              });

            switch (req.url) {
                case "/new_connection":
                    window.webContents.send('asynchronous-message', "hello renderer");
                    res.writeHead(200);
                    res.end('Hello from \'new_connection\'');
                    break;
                default: 
                    res.writeHead(200);
                    res.end('Hello from \'' + req.url + '\'');
                    break;
            }
          }
    }

    start() {
        const server = http.createServer(this.requestListener);
        server.listen(this.default_port);
    }
}

module.exports = ServerConnector

