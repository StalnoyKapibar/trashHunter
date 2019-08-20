

function doFilter(){
    let types = [];
    $('input[type=checkbox]').each(function () {
        if (this.checked) {
            types.push($(this).attr("id"))
        }
    });
    let weight = $("#weightFrom").val() + "-" + $("#weightTo").val();
    let volume = $("#volumeFrom").val() + "-" + $("#volumeTo").val();
    let isSorted = $("#isSorted option:selected").attr("value");
    let isFree = $("#isFree option:selected").attr("value");

    let filter = {};
    filter["type"] = types;
    filter["weight"] = weight;
    filter["volume"] = volume;
    filter["isSorted"] = isSorted;
    filter["isFree"] = isFree;


    console.log(filter);

}