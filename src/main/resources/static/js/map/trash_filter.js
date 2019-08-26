let result;
function doFilter(){
    let trashType = [];
    $('input[type=checkbox]').each(function () {
        if (this.checked) {
            trashType.push($(this).attr("id"))
        }
    });
    let weightFrom = $("#weightFrom").val();
    let weightTo = $("#weightTo").val();
    let weight = weightFrom + "-" + weightTo;

    let volumeFrom = $("#volumeFrom").val();
    let volumeTo = $("#volumeTo").val();
    let volume = volumeFrom + "-" + volumeTo;

    let isSorted = $("#isSorted option:selected").attr("value");
    let isFree = $("#isFree option:selected").attr("value");

    let filter = {};
    if (trashType.length!==0) {
        filter["trashType"] = trashType;
    }
    if (weight.length>=2 && (weightFrom !== "" || weightFrom !== "")) {
        filter["weight"] = weight;
    }
    if (volume.length>=2 && (volumeFrom !== "" || volumeFrom !== "")) {
        filter["volume"] = volume;
    }
    if (isSorted!=="") {
        filter["isSorted"] = isSorted;
    }
    if (isFree!=="") {
        filter["isFree"] = isFree;
    }

    $.ajax({
        url: "/api/offer",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        async: false,
        dataType: "json",
        data: JSON.stringify(filter),
        success: function (data) {
           result = data;
        }
    });
    return result;
}

function openFilter() {
    document.getElementById("filter").style.height = "400px";
    document.getElementById("filter-open-btn").style.height = "0";
}

function closeFilter() {
    document.getElementById("filter").style.height = "0";
    document.getElementById("filter-open-btn").style.height = "50px";
}
