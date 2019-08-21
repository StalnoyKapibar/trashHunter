$(document).ready(function () {
    // 'use strict';
    // feather.replace();

    getTable();
    // $('#sortBy option').each(function () {
    //     var param = $(this);
    //     if (location.href.indexOf(param.val()) !== -1) {
    //         param.prop('selected', true);
    //     }
    // });

});
function getTable() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: "/api/sender/my_offers",
        type: "GET",
        datatype:"json",
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        success: function (result) {
            $('#employerTable tbody').empty();
            $.each(result, function (i, offer) {
                var userRow = '<tr>' +
                    '<td>' + offer.id + '</td>' +
                    '<td>' + offer.weight + '</td>' +
                    '<td>' + offer.volume + '</td>' +
                    // '<td>' + offer.price + '</td>' +
                    // '<td>' + offer.trashType + '</td>' +
                    '<td>' + offer.active + '</td>' +
                    '<td>' + offer.closed + '</td>' +
                    '<td> \n' +
                    '<button th:onclick="confirmOffer(' + offer.id + ');" type="button" class="btn btn-primary">Подтвердить'+
                    '</button>' +
                    '</tr>';

                $('#employerTable tbody').append(userRow);

            });

        },
        error: function (message) {
            console.log(message);
        }
    });
}




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