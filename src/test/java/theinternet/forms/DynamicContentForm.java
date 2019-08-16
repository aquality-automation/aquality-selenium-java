package theinternet.forms;

import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;

public class DynamicContentForm extends TheInternetForm {

    private final String tmpContentItemXpath = "//div[@id='content']//div[@class='large-10 columns'][%1$s]";

    public DynamicContentForm(){
        super(By.id("content"), "Dynamic Content");
    }

    public ILabel getFirstContentItem(){
        return getContentItem(1);
    }

    private ILabel getContentItem(int itemNum){
        return getElementFactory().getLabel(By.xpath(String.format(tmpContentItemXpath, itemNum)), "Content item #" + itemNum);
    }

    @Override
    protected String getUri() {
        return "/dynamic_content";
    }
}
