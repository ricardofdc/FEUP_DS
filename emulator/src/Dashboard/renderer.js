const { ipcRenderer } = require("electron");

ipcRenderer.on('asynchronous-message', function (evt, message) {
    console.log(message);
});