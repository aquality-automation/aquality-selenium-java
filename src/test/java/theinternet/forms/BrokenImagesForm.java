package theinternet.forms;

import aquality.selenium.elements.interfaces.ByImage;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;
import utils.FileUtil;

import java.util.List;

public class BrokenImagesForm extends TheInternetForm {
    private final By imageLocator = new ByImage(FileUtil.getResourceFileByName("brokenImage.png"), true);

    public BrokenImagesForm(){
        super(By.id("content"), "Broken Images form");
    }

    public ILabel getLabelByImage(){
        return getElementFactory().getLabel(imageLocator, "broken image");
    }

    public List<ILabel> getLabelsByImage(){
        return getElementFactory().findElements(imageLocator, "broken image", ILabel.class);
    }

    public List<ILabel> getChildLabelsByImage(){
        return getFormLabel().findChildElements(imageLocator, "broken image", ILabel.class);
    }

    @Override
    protected String getUri() {
        return "/broken_images";
    }
}
