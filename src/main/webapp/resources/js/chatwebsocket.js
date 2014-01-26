
var url = "ws://"+document.location.host+"/semestralka/chat";
var websocket = new WebSocket(url);

console.log(document.location.host);
console.log(document.location.pathname);

websocket.onmessage = function(evt) {
    onMessage(evt)
};

function send() {
    websocket.send($("#formmessage\\:message").val());
}

function onMessage(evt){
    console.log(" "+ evt.data);
}