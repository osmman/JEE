$.urlParam = function (name) {
    var results = new RegExp('[\\?&amp;]' + name + '=([^&amp;#]*)').exec(window.location.href);
    return results[1] || 0;
}

var url = "ws://" + document.location.host + "/semestralka/chat/" + $.urlParam("videoid");
var websocket = new WebSocket(url);

console.log(document.location.host);
console.log(document.location.pathname);

websocket.onmessage = function (evt) {
    onMessage(evt);
};

websocket.onerror = function (evt) {
    console.log('ERROT')
};

function send() {
    var message = {"authorID" : $("#loggedUserID").text(), "text": $("#formmessage\\:message").val()};
    var jsonString = JSON.stringify(message);
    console.log(jsonString);
    websocket.send(jsonString);
}

function onMessage(evt) {
    var json = JSON.parse(evt.data);
    console.log(" " + evt.data);

    var newMessage = $('<div class="media">'
        + '<div class="media-body">'
        + '<h4 class="media-heading">' + json.author
        + " " + json.date
        + '</h4>'
        + json.text + '</br>');

    $("#chat-body").append(newMessage);
}
