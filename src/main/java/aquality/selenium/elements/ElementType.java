package aquality.selenium.elements;

import aquality.selenium.elements.interfaces.*;

public enum ElementType {
    BUTTON(IButton.class),
    CHECKBOX(ICheckBox.class),
    COMBOBOX(IComboBox.class),
    LABEL(ILabel.class),
    LINK(ILink.class),
    RADIOBUTTON(IRadioButton.class),
    TEXTBOX(ITextBox.class);

    private final Class<? extends IElement> clazz;

    <T extends IElement> ElementType(Class<T> clazz){
        this.clazz = clazz;
    }

    public <T extends IElement> Class<T> getClazz() {
        //noinspection unchecked
        return (Class<T>) clazz;
    }
}
