// Adding csrf_token header into every ajax request

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function(){
    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
        if (options.type.toLowerCase() === "post") {
            // initialize csrf token in every request
            options.beforeSend = function (request) {
                request.setRequestHeader(header, token);
            }
        }
    });
});