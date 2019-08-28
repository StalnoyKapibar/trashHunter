$(document).ready(function () {
    isStar($("#a_rating").text());
});

function isStar(rating) {
    if (rating>=1 && rating<=2) {
        $("#stars").append("<span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>")
    } else if (rating>=2 && rating<=3) {
        $("#stars").append("<span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>");
    } else if (rating>=3 && rating<=4) {
        $("#stars").append("<span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star\"></span>");
    } else {
        $("#stars").append("<span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>\n" +
            "                                <span class=\"fa fa-star checked\"></span>");
    }
}