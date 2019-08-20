const header = $("meta[name='_csrf_header']").attr("content");
const token = $("meta[name='_csrf']").attr("content");

function doFilter(){
    let trashType = [];
    $('input[type=checkbox]').each(function () {
        if (this.checked) {
            trashType.push($(this).attr("id"))
        }
    });
    let weight = $("#weightFrom").val() + "-" + $("#weightTo").val();
    let volume = $("#volumeFrom").val() + "-" + $("#volumeTo").val();
    let isSorted = $("#isSorted option:selected").attr("value");
    let isFree = $("#isFree option:selected").attr("value");

    let filter = {};
    if (trashType.length!==0) {
        filter["trashType"] = trashType;
    }
    if (weight.length>=2) {
        filter["weight"] = weight;
    }
    if (volume.length>=2) {
        filter["volume"] = volume;
    }
    if (isSorted!=="") {
        filter["isSorted"] = isSorted;
    }
    if (isFree!=="") {
        filter["isFree"] = isFree;
    }

    console.log(filter);

    $.ajax({
        url: "/api/offer",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        async: false,
        dataType: "json",
        data: JSON.stringify(filter),
        beforeSend: function (request) {
            return request.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log(data);
        }
    });
}