$(document).ready(function () {
    // 'use strict';
    // feather.replace();

    getTable('all');
    // $('#sortBy option').each(function () {
    //     var param = $(this);
    //     if (location.href.indexOf(param.val()) !== -1) {
    //         param.prop('selected', true);
    //     }
    // });

});

function getTable(check) {

    $.ajax({
        url: "/api/sender/my_offers/" + check,
        type: "GET",
        success: function (result) {
            let res = result;
            $('#employerTable tbody').empty();
            $.each(result, function (offer, takers) {
                var userRow = '<tr>'
                JSON.parse(offer, function (key,value) {
                    userRow+='<td>' + value + '</td>';
                });
                // JSON.parse('{"1": 1, "2": 2, "3": {"4": 4, "5": {"6": 6}}}', function(k, v) {
                //     console.log(k); // пишем имя текущего свойства, последним именем будет ""
                //     return v;       // возвращаем неизменённое значение свойства
                // });
                // var userRow = '<tr>' +
                //     '<td>' + offer.id + '</td>' +
                //     '<td>' + offer.weight + '</td>' +
                //     '<td>' + offer.volume + '</td>' +
                //     '<td>' + offer.price + '</td>' +
                //     '<td>' + offer.trashType + '</td>' +
                //     '<td>' + offer.active + '</td>' +
                //     '<td>' + offer.closed + '</td>' +
                //     '<td>' +
                //     '<button type="button" class="btn btn-primary" onclick="confirmOffer(' + offer.id + ')" >Подтвердить' +
                //     '</button>' +
                //     '</tr>';
                userRow+='</tr>';

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
        $('#employerTable tbody').empty();
        getTable('active');

    } else {
        $('#sort').removeClass("is-valid");
        $('#employerTable tbody').empty();
        getTable('all');
    }
}

function confirmOffer(id) {
    $.ajax({
        url: '/api/sender/confirmOffer/' + id,
        type: 'GET',
        success: function () {
            if (document.getElementById('sort').checked) {
                $('#employerTable tbody').empty();
                getTable('active');
            } else {
                $('#employerTable tbody').empty();
                getTable('all');
            }
        }
    });
}