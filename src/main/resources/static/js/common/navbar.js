$(document).ready(function () {
    let path='#';
    console.log(window.location.pathname.endsWith);
    if (window.location.pathname.endsWith('my_offers')) {
        path+='myoffersnav';
    }
    if (window.location.pathname.endsWith('profile')) {
        path+='profilenav';
    }
    if (window.location.pathname.endsWith('/')) {
        path+='mapnav';
    }
    if (window.location.pathname.endsWith('favorites')) {
        path+='favnav';
    }
    if (window.location.pathname.endsWith('chat')) {
        path+='chatnav';
    }
    if (window.location.pathname.endsWith('new_offer')) {
        path+='newoffernav';
    }
    $(path).css("text-shadow", " 20px 20px 40px black, 0 0 2em black");
    $(path).css("color", "white");
    $(path).css("font-size", "1.2em");
});