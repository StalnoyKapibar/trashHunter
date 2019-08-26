$(document).ready(function () {
    initMap();
});

function initMap() {
    let options = {
        types: ['(cities)'],
        componentRestrictions: {country: "ru"}
    };

    let input = document.getElementById('address');
    let autocomplete = new google.maps.places.Autocomplete(input, options);

    input.addEventListener("change", function () {
        input.value = "";
    });

    autocomplete.addListener('place_changed', function () {
        let place = autocomplete.getPlace();
        document.getElementById('address').value = place.formatted_address;
    });
}