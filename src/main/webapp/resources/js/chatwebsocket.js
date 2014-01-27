$.urlParam = function(name){
    var results = new RegExp('[\\?&amp;]' + name + '=([^&amp;#]*)').exec(window.location.href);
    return results[1] || 0;
}

var url = "ws://" + document.location.host + "/semestralka/chat/"+ $.urlParam("videoid");
var websocket = new WebSocket(url);

console.log(document.location.host);
console.log(document.location.pathname);

websocket.onmessage = function(evt) {
    onMessage(evt);
};

function send() {
    console.log($.urlParam("videoid"));
    websocket.send($("#formmessage\\:message").val());
}

function onMessage(evt) {
    console.log(" " + evt.data);
    var newMessage = document.createElement("span");
    newMessage.innerHTML = evt.data;
    $("#chat").append(newMessage);
    $("#chat").append("<br/>");
}

