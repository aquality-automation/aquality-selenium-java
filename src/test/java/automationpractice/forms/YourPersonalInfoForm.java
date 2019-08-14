package automationpractice.forms;

import aquality.selenium.elements.ElementState;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.ElementsCount;
import aquality.selenium.elements.interfaces.*;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.util.List;

public class YourPersonalInfoForm extends Form {

    public YourPersonalInfoForm(){
        super(By.xpath("//input[@name='id_gender']"), "Your Personal Info");
    }

    private static final String TMP_RB_GENDER = "id_gender%1$d";
    private static final String XPATH_SELECT_DAYS = "//select[@id='days']/option";

    private final ITextBox txbFirstName = getElementFactory().getTextBox(By.id("customer_firstname"), "first name");
    private final IComboBox cmbState = getElementFactory().getComboBox(By.id("id_state"), "State", ElementState.EXISTS_IN_ANY_STATE);
    private final IComboBox cmbSelectDay = getElementFactory().getComboBox(By.name("days"), "Days", ElementState.EXISTS_IN_ANY_STATE);
    private final ICheckBox chbNews = getElementFactory().getCheckBox(By.id("newsletter"), "newsletter", ElementState.EXISTS_IN_ANY_STATE);

    public void selectGender(Integer genderId){
        IRadioButton rbGender = getElementFactory().getRadioButton(By.id(String.format(TMP_RB_GENDER, genderId)), "Gender Id " + genderId, ElementState.EXISTS_IN_ANY_STATE);
        rbGender.click();
    }

    public void setFirstName(String firstName){
        txbFirstName.type(firstName);
    }

    public Integer getNumOfDays(){
        List<ILabel> lblDays = getElementFactory().findElements(By.xpath(XPATH_SELECT_DAYS), ElementType.LABEL, ElementState.EXISTS_IN_ANY_STATE, ElementsCount.MORE_THEN_ZERO);
        return lblDays.size();
    }

    public void selectState(String state){
        cmbState.selectByText(state);
    }

    public void selectDay(Integer day){
        cmbSelectDay.clickAndSelectByValue(String.valueOf(day));
    }

    public boolean getNewsCheckBoxState(){
        return chbNews.isChecked();
    }

    public boolean isNewsCheckboxChecked(){
        return chbNews.isChecked();
    }

    public void setNewsChb(){
        chbNews.getJsActions().check();
    }
}
