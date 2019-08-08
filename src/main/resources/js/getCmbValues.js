return (function(element) {
    var array = [];
    for(var i = 0; i < element.length; i++)
    {
        array.push(element[i].text);
    }
    return array;
})(arguments[0]);
