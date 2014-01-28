$.urlParam = function(name) {
    var results = new RegExp('[\\?&amp;]' + name + '=([^&amp;#]*)').exec(window.location.href);
    return results[1] || 0;
}

var url = "ws://" + document.location.host + "/semestralka/chat/" + $.urlParam("videoid");
var websocket = new WebSocket(url);

console.log(document.location.host);
console.log(document.location.pathname);

websocket.onmessage = function(evt) {
    onMessage(evt);
};

websocket.onerror = function(evt) {console.log('ERROT')};

function send() {
    var message = {"text": $("#formmessage\\:message").val()};
    var jsonString = JSON.stringify(message);
    console.log(jsonString);
    websocket.send(jsonString);
}

function onMessage(evt) {
    var json = JSON.parse(evt.data);
    console.log(" " + evt.data);        
//    var newMessage = document.createElement("span");
//    newMessage.innerHTML = evt.data;
//    newMessage.innerHTML = evt.data;
    var newMessage = $('<div>'
            + '<div>' + json.author + '</div>'
            + '<div>' + json.date + '</div>'
            + '<div>' + json.text + '</div>'
            +'</div><br/>');
    
    $("#chat").append(newMessage);


}

