if(document.createEvent){
    var evObj = document.createEvent('MouseEvents');
    evObj.initEvent('mouseover', true, false);
    arguments[0].dispatchEvent(evObj);
    }
else if(document.createEventObject) {
    arguments[0].fireEvent('onmouseover');
}
