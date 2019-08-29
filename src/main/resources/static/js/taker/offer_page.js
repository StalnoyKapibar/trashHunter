var map;
var city = $("meta[name='city']").attr("content");
var trType = $("meta[name='trType']").attr("content");
var latitude = parseFloat($("meta[name='latitude']").attr("content"));
var longitude = parseFloat($("meta[name='longitude']").attr("content"));

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: latitude, lng: longitude},
        zoom: 13,
        gestureHandling: 'cooperative',
        streetViewControl: false,
        mapTypeControlOptions: {
            position: google.maps.ControlPosition.BOTTOM_LEFT,
            mapTypeIds: ['hybrid', 'styled_map']
        }
    });

    let styledMapType = new google.maps.StyledMapType(styledMapPropertiesArray, {name: 'Styled Map'});
    map.mapTypes.set('styled_map', styledMapType);
    map.setMapTypeId('styled_map');

    let options = {
        types: ['(cities)'],
        componentRestrictions: {country: "ru"}
    };
    let input = document.getElementById('address');
    let autocomplete = new google.maps.places.Autocomplete(input, options);

    input.addEventListener("change", function () {
        input.value = "";
    });

    autocomplete.bindTo('bounds', map);

    let marker = new google.maps.Marker({
        position: {lat: latitude, lng: longitude},
        map: map,
    });

}

function getAddressByCoords(lat, lng) {
    var address;
    $.ajax({
        url: "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=AIzaSyDhmsuY2GsI23ECoyUqAaVtWVDz3GleyTo&libraries",
        type: "GET",
        async: false,
        success: function (data) {
            address = data.results[0].formatted_address;
        }
    });
    return address;
}

$(document).ready(function () {
    initMap();

    $("#address").val(getAddressByCoords(latitude, longitude));

    $("#trashType").val(trType).attr('selected', 'selected');

    $("#isSorted").on('change', function () {
        if (this.checked) {
            $("#isSorted").attr("value", "true")
        } else {
            $("#isSorted").attr("value", "false")
        }
    });

    $("#isFree").on('change', function () {
        if (this.checked) {
            $("#price").val(0);
            $("#price").attr("disabled", "disabled")
        } else {
            $("#price").val("");
            $("#price").removeAttr("disabled");
        }
    });
});

