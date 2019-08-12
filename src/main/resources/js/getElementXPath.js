function WebElement_XPath(element) {
    if (element.tagName === 'HTML') {
        return '/html';
    }
    if (element===document.body) {
        return '/html/body';
    }
    // calculate position among siblings
    var position = 0;
    // Gets all siblings of that element.
    var siblings = element.parentNode.childNodes;
    for (var i = 0; i < siblings.length; i++) {
        var sibling = siblings[i];
        // Check sibling with our element if match then recursively call for its parent element.
        if (sibling === element) {
            return WebElement_XPath(element.parentNode)+'/'+element.tagName+'['+(position+1)+']';
        }
       // if it is a sibling & element-node then only increments position.
        var type = sibling.nodeType;
        if (type === 1 && sibling.tagName === element.tagName) {
            position++;
        }
    }
}
return WebElement_XPath(arguments[0]);
