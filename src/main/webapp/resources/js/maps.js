/**
 * Created by Tomáš on 2.2.14.
 */
var map;
function initialize() {
    var element = document.getElementById('map-canvas');
    var location = element.getAttribute("data-location");
    var res = location.split(",");
    var mapOptions = {
        zoom: 8,
        center: new google.maps.LatLng(res[0], res[1])
    };
    map = new google.maps.Map(element,
        mapOptions);
}

google.maps.event.addDomListener(window, 'load', initialize);