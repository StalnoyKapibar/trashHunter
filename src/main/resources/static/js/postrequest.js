function fire_ajax_submit(userId) {

    let form = $('#fileUploadForm')[0];

    let data = new FormData(form);

    data.append("CustomField", "This is some extra data, testing");

    $("#imageSubmit").prop("disabled", true);


    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: '/upload/img',
        data: data,
        //http://api.jquery.com/jQuery.ajax/
        //https://developer.mozilla.org/en-US/docs/Web/API/FormData/Using_FormData_Objects
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data, textStatus, xhr) {

            $("#result").text(data);
            console.log("SUCCESS : ", data);
            $("#imageSubmit").prop("disabled", false);

            if (xhr.status === 205) {
                reloadImage(userId);
            }
        },
        error: function (xhr, textStatus, error) {
            $("#result").text(error.responseText);
            console.log("ERROR : ", error);
            $("#imageSubmit").prop("disabled", false);
        }

    });

}
