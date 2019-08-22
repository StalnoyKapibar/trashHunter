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

                var offerRow=
                    '<div class="container-fluid">'+
                        '<div class="row">'+
                            '<div class="col-sm-12">'+
                                '<div class="container-fluid cards">'+
                                    '<div class="card" >'+
                                        '<div class="card-header"style="background-color: blanchedalmond">'+
                                            'Header'+
                                        '</div>'+
                                        '<div class="card-body"style="background-color: aliceblue">';
                                             JSON.parse(offer, function (key, value) {
                                                if (key != "") {
                                                offerRow +=  ' ! ' + value + ' ! ';
                                                }
                offerRow +=             '</div>'+
                                        '<div class="card-footer" style="background-color: orange">'+
                                            '<button class="btn btn-warning">'+
                                                Перейти +
                                            '</button>' +
                                        '</div>'+
                                    '</div>' +
                                '</div>'+
                            '</div>' +
                        '</div>'
                    '</div>';
                });
                offerRow+='<br>';
                // $.each(takers, function (i,taker) {
                //     offerRow+= taker.name +';';
                // })
                // offerRow+='</td>';



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
                // offerRow += '</tr>';

                $('#employerTable tbody').append(offerRow);

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