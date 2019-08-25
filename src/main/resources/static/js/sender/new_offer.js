$(document).ready(function() {
    initMap();
    $("#isSorted").on('change', function () {
        if (this.checked) {
            $("#isSorted").attr("value", "true")
        } else {
            $("#isSorted").attr("value", "false")
        }
    });
});

function initMap(){
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 55.752030, lng: 37.633685},
        zoom: 12
    });
}


