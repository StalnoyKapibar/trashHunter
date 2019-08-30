var map;
var markers = [];
var infoWindow;
var geocoder;
var city = $("meta[name='defined_city']").attr("content");
var latitude;
var longitude;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 14,
        gestureHandling: 'cooperative',
        streetViewControl: false,
        // mapTypeControl: false,
        // mapTypeId: 'hybrid'
        mapTypeControlOptions: {
            position: google.maps.ControlPosition.BOTTOM_LEFT,
            mapTypeIds: ['hybrid', 'styled_map']
        }
    });

    geocoder = new google.maps.Geocoder();
    codeAddress();

    //Associate the styled map with the MapTypeId and set it to display.
    let styledMapType = new google.maps.StyledMapType(styledMapPropertiesArray, {name: 'Styled Map'});
    map.mapTypes.set('styled_map', styledMapType);
    map.setMapTypeId('styled_map');

    let card = document.getElementById('pac-card');
    let input = document.getElementById('pac-input');
    let filter = document.getElementById('filter-container');

    map.controls[google.maps.ControlPosition.TOP_RIGHT].push(card);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(filter);

    let options = {
        types: ['(cities)'],
        componentRestrictions: {country: "ru"}
    };

    let autocomplete = new google.maps.places.Autocomplete(input, options);

    infoWindow = new google.maps.InfoWindow();
    let infowindowContent = document.getElementById('infowindow-content');
    infoWindow.setContent(infowindowContent);
    let marker = new google.maps.Marker({
        map: map,
        anchorPoint: new google.maps.Point(0, -29)
    });

    autocomplete.addListener('place_changed', function () {
        infoWindow.close();
        marker.setVisible(false);
        let place = autocomplete.getPlace();
        if (!place.geometry) {
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

        let address = '';
        if (place.address_components) {
            address = [
                (place.address_components[0] && place.address_components[0].short_name || ''),
                (place.address_components[1] && place.address_components[1].short_name || ''),
                (place.address_components[2] && place.address_components[2].short_name || '')
            ].join(' ');
        }
        infoWindow.setContent(infowindowContent);
        infowindowContent.children['place-icon'].src = place.icon;
        infowindowContent.children['place-name'].textContent = place.name;
        infowindowContent.children['place-address'].textContent = address;
        infoWindow.open(map, marker);
    });

    setMarkers();

    // Create myGeolocation button
    let centerControlDiv = document.createElement('div');
    let centerControl = new CenterControl(centerControlDiv, map);
    centerControlDiv.index = 1;
    map.controls[google.maps.ControlPosition.RIGHT_CENTER].push(centerControlDiv);
}

function codeAddress() {
    let viborg = {lat: 60.704958391204265, lng: 28.753046876075004};
    geocoder.geocode({'address': city}, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            map.setCenter(results[0].geometry.location);
        } else {
            map.setCenter(viborg);
        }
    });
}
function createInfoOfferTable(tableID) {
    let tableRef = document.getElementById(tableID);
    let newRow = tableRef.insertRow(0);
    let newCell = newRow.insertCell(0);
    let newText = document.createTextNode('New top row');
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

function drawPoints(data) {
    $.each(data, function (id, offer) {
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
        let marker = new google.maps.Marker({
            position: {lat: offer.coordinates.latitude, lng: offer.coordinates.longitude},
            map: map,
            icon: {
                url: url
            }
        });
        markers.push(marker);
        marker.addListener('click', function () {
            let tableRef = document.getElementById('offerInfoTable');
            $("#offerInfoTable tr").remove();
            $.each(offer, function (key, value) {
                if (key != 'coordinates' && key != 'id') {
                    let newRow = tableRef.insertRow();
                    let newCell = newRow.insertCell();
                    let newText = document.createTextNode(key);
                    newCell.appendChild(newText);

                    newCell = newRow.insertCell();
                    newText = document.createTextNode(value);
                    newCell.appendChild(newText);
                }
                if (key == 'sender'){
                    let newRow = tableRef.insertRow();
                    let newCell = newRow.insertCell();
                    let newText = document.createTextNode(key);
                    newCell.appendChild(newText);

                    newCell = newRow.insertCell();
                    newText = document.createTextNode(value.name);
                    newCell.appendChild(newText);
                }
            });

            let linkToChatFromTaker = document.getElementById("linkToChatFromTaker");
            if (linkToChatFromTaker) {
                linkToChatFromTaker.href="/chat/" + offer.sender.id + "?offerId=" + offer.id;
                $('#linkToChatFromTaker').show();
            }
        });
    });
}

function deleteMarkers() {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
}

function CenterControl(controlDiv, map) {
    // Set CSS for the control border.
    let controlUI = document.createElement('div');
    controlUI.style.backgroundColor = '#fff';
    controlUI.style.border = '2px solid #fff';
    controlUI.style.borderRadius = '3px';
    controlUI.style.boxShadow = '0 2px 6px rgba(0,0,0,.3)';
    controlUI.style.cursor = 'pointer';
    controlUI.style.marginBottom = '22px';
    controlUI.style.textAlign = 'center';
    controlUI.style.marginRight = '10px';
    controlUI.title = 'Определить местоположение';
    controlDiv.appendChild(controlUI);

    // Set CSS for the control interior.
    let controlText = document.createElement('div');
    controlText.style.color = 'rgb(25,25,25)';
    controlText.style.fontFamily = 'Roboto,Arial,sans-serif';
    controlText.style.fontSize = '16px';
    controlText.style.lineHeight = '38px';
    controlText.style.paddingLeft = '5px';
    controlText.style.paddingRight = '5px';
    controlText.innerHTML = 'Find Me';
    controlUI.appendChild(controlText);

    controlUI.addEventListener('click', function () {
        setMyCoordinates();
    });
}
function doFilterInit(urlrequest) {
    deleteMarkers();
    drawPoints(doFilter(urlrequest));
}

// Button "Find Me"
function setMyCoordinates() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            let pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            infoWindow.setPosition(pos);
            infoWindow.setContent('Location found.');
            infoWindow.open(map);
            map.setCenter(pos);
        }, function () {
            handleLocationError(true, infoWindow, map.getCenter());
        });
    } else {
        // Browser doesn't support Geolocation
        handleLocationError(false, infoWindow, map.getCenter());
    }

    function handleLocationError(browserHasGeolocation, infoWindow, pos) {
        infoWindow.setPosition(pos);
        infoWindow.setContent(browserHasGeolocation ?
            'Error: The Geolocation service failed.' :
            'Error: Your browser doesn\'t support geolocation.');
        infoWindow.open(map);
    }
}