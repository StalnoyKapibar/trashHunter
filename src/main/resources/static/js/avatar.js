$(document).ready(() => {
    $("#btnSubmit").click((event) => {

        event.preventDefault();
        doAjax();
    });
});
function doAjax() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var input = $("#uploadimage");
    var data = new FormData;
    data.append('img', input.prop('files')[0]);
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/editPhoto",
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        success: (data) => {
            window.location.reload();
        },
        error: (e) => {
            alert(e.responseText);
        }
    });
}