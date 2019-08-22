var map;
var markers = [];

function initMap() {
    //todo
    var geocoder = new google.maps.Geocoder;

    var viborg = {lat: 60.70768064991953, lng: 28.753881993229232};
    map = new google.maps.Map(document.getElementById('map'), {
        center: viborg,
        zoom: 13,
        gestureHandling: 'cooperative',
        streetViewControl: false,
        mapTypeControl: false
    });
    var card = document.getElementById('pac-card');
    var input = document.getElementById('pac-input');
    var filter = document.getElementById('filter-container');

    map.controls[google.maps.ControlPosition.TOP_RIGHT].push(card);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(filter);

    var autocomplete = new google.maps.places.Autocomplete(input);

    var infowindow = new google.maps.InfoWindow();
    var infowindowContent = document.getElementById('infowindow-content');
    infowindow.setContent(infowindowContent);
    var marker = new google.maps.Marker({
        map: map,
        anchorPoint: new google.maps.Point(0, -29)
    });

    autocomplete.addListener('place_changed', function () {
        infowindow.close();
        marker.setVisible(false);
        var place = autocomplete.getPlace();
        if (!place.geometry) {
            // User entered the name of a Place that was not suggested and
            // pressed the Enter key, or the Place Details request failed.
            window.alert("По адресу ничего не найдено: '" + place.name + "'");
            return;
        }

        // If the place has a geometry, then present it on a map.
        if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
        } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17);
        }
        marker.setPosition(place.geometry.location);
        marker.setVisible(true);

        var address = '';
        if (place.address_components) {
            address = [
                (place.address_components[0] && place.address_components[0].short_name || ''),
                (place.address_components[1] && place.address_components[1].short_name || ''),
                (place.address_components[2] && place.address_components[2].short_name || '')
            ].join(' ');
        }

        infowindowContent.children['place-icon'].src = place.icon;
        infowindowContent.children['place-name'].textContent = place.name;
        infowindowContent.children['place-address'].textContent = address;
        infowindow.open(map, marker);


    });

    setMarkers();

    function createInfoOfferTable(tableID) {
        var tableRef = document.getElementById(tableID);
        var newRow = tableRef.insertRow(0);
        var newCell = newRow.insertCell(0);
        var newText = document.createTextNode('New top row');
        newCell.appendChild(newText);
    }

    function setMarkers() {
        $.ajax({
            url: "/api/offer",
            dataType: "json",
            type: "GET",
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (data) {
                drawPoints(data);
            }
        });
    }
}

function drawPoints(data) {
    $.each(data, function (id, offer) {
        console.log(offer);
        let url = null;
        if (offer.trashType == 'PAPER') {
            url = "/img/paper.png";
        } else if (offer.trashType == 'WOOD') {
            url = "/img/wood.png";
        } else if (offer.trashType == 'METAL') {
            url = "/img/metall.png";
        } else if (offer.trashType == 'PLASTIC') {
            url = "/img/plastic.png";
        } else if (offer.trashType == 'GLASS') {
            url = "/img/glass.png";
        } else if (offer.trashType == 'FOOD') {
            url = "/img/food.png";
        } else {
            url = "/img/blank.png";
        }
        var marker = new google.maps.Marker({
            position: {lat: offer.coordinates.latitude, lng: offer.coordinates.longitude},
            map: map,
            icon: {
               url: url
            }
        });
        markers.push(marker);
        marker.addListener('click', function() {
            var tableRef = document.getElementById('offerInfoTable');
            $("#offerInfoTable tr").remove();
            $.each(offer, function (key, value) {
                if (key != 'coordinates' && key != 'id') {
                    var newRow = tableRef.insertRow();
                    var newCell = newRow.insertCell();
                    var newText = document.createTextNode(key);
                    newCell.appendChild(newText);

                    var newCell = newRow.insertCell();
                    var newText = document.createTextNode(value);
                    newCell.appendChild(newText);
                }
            });
        });
    });
}

function deleteMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
}