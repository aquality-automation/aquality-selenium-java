package aquality.selenium.elements;

import aquality.selenium.elements.interfaces.*;

public enum ElementType {
    BUTTON(IButton.class),
    CHECKBOX(ICheckBox.class),
    COMBOBOX(IComboBox.class),
    MULTICHOICECOMBOBOX(IMultiChoiceComboBox.class),
    LABEL(ILabel.class),
    LINK(ILink.class),
    RADIOBUTTON(IRadioButton.class),
    TEXTBOX(ITextBox.class);

    private Class<? extends IElement> clazz;

    <T extends IElement> ElementType(Class<T> clazz){
        this.clazz = clazz;
    }

    public <T extends IElement> Class<T> getClazz() {
        return (Class<T>) clazz;
    }
}
