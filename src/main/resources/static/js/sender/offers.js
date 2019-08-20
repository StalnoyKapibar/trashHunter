$(document).ready(function () {
    'use strict';
    feather.replace();


    // $('#sortBy option').each(function () {
    //     var param = $(this);
    //     if (location.href.indexOf(param.val()) !== -1) {
    //         param.prop('selected', true);
    //     }
    // });

});

// $("#sortBy").change(function () {
//     var direction = $("#sortBy option:selected").val();
//     location.href = '/sender/my_offers/';
//     alert(direction);
// });

function sort() {
    if (document.getElementById('sort').checked) {
        $('#sort').addClass("is-valid");
        location.href = '/sender/my_offers?active=true';
    } else {
        $('#sort').removeClass("is-valid");
    }
}

function confirmOffer(id) {
    $.ajax({
        url: '/sender/confirmOffer/' + id,
        type: 'GET',
        success: function () {
            location.href = '/sender/my_offers';
        }
    });
}