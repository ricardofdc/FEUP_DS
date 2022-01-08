const http = require('http');

class DashboardConnector {
    constructor(){
        this.DEFAULT_HTTP_OPTIONS = {
            hostname: '127.0.0.1',
            port: 8080,
            path: '/',
            method: 'POST'
        };
    }

    sendJSONMessage(endpoint, json_message) {
        const data = new TextEncoder().encode(JSON.stringify(json_message))
        const http_options = this.DEFAULT_HTTP_OPTIONS;
        http_options.path = endpoint;
        http_options.headers = {
        'Content-Type': 'application/json',
        'Content-Length': data.length
        }

        const req = http.request(http_options, res => {      
        res.on('data', d => {
            process.stdout.write(d)
        })})

        req.on('error', error => {
            console.error(error)
        })

        req.write(data)
        req.end()
    }
}

module.exports = DashboardConnector