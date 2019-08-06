return (function(element) {
    var array = new Array();
    for(i = 0; i < element.length; i++)
    {
        array.push(element[i].text);
    }
    return array;
})(arguments[0]);