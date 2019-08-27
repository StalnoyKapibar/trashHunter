function loadMap() {
    let address = $("#address").val();
    if (address === "") {
        return;
    }
    window.location.replace("/?city=" + address);
}
