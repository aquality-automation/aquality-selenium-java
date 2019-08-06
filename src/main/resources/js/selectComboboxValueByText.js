var select = arguments[0];
for(var i = 0; i < select.options.length; i++) {
    if(select.options[i].text == arguments[1]) {
        select.options[i].selected = true;
    }
}
if ("createEvent" in document) {
    var evt = document.createEvent("HTMLEvents");
    evt.initEvent("change", false, true);
    arguments[0].dispatchEvent(evt);
} else {
    arguments[0].fireEvent("onchange");
}