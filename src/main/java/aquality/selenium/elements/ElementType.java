package aquality.selenium.elements;

import aquality.selenium.elements.interfaces.IElement;

import java.util.Arrays;

public enum ElementType {
    BUTTON(Button.class),
    CHECKBOX(CheckBox.class),
    COMBOBOX(ComboBox.class),
    LABEL(Label.class),
    LINK(Link.class),
    RADIOBUTTON(RadioButton.class),
    TEXTBOX(TextBox.class);

    private Class<? extends IElement> clazz;

    <T extends IElement> ElementType(Class<T> clazz){
        this.clazz = clazz;
    }

    Class<? extends IElement> getClazz() {
        return clazz;
    }

    static ElementType getTypeByClass(Class<? extends IElement> clazz){
        return Arrays.stream(values()).filter(elementType -> clazz.isAssignableFrom(elementType.getClazz())).findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
