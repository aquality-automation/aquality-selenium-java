package aquality.selenium.elements.interfaces;

import aquality.selenium.browser.AqualityServices;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.openqa.selenium.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ByImage extends By {
    private static boolean wasLibraryLoaded = false;
    private final Mat template;

    private static void loadLibrary() {
        if (!wasLibraryLoaded) {
            nu.pattern.OpenCV.loadShared();
            System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
            wasLibraryLoaded = true;
        }
    }

    public ByImage(File file) {
        loadLibrary();
        this.template = Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_UNCHANGED);
    }

    public ByImage(byte[] bytes) {
        loadLibrary();
        this.template = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_UNCHANGED);
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        byte[] screenshotBytes = getScreenshot(context);
        Mat source = Imgcodecs.imdecode(new MatOfByte(screenshotBytes), Imgcodecs.IMREAD_UNCHANGED);
        Mat result = new Mat();
        Imgproc.matchTemplate(source, template, result, Imgproc.TM_CCOEFF_NORMED);

        float threshold = 1 - AqualityServices.getConfiguration().getVisualizationConfiguration().getDefaultThreshold();
        Core.MinMaxLocResult minMaxLoc = Core.minMaxLoc(result);

        if (minMaxLoc.maxVal < threshold) {
            AqualityServices.getLogger().warn(String.format("No elements found by image [%s]", template));
            return new ArrayList<>(0);
        }

        return getElementsOnPoint(minMaxLoc.maxLoc, context);
    }

    private List<WebElement> getElementsOnPoint(Point matchLocation, SearchContext context) {
        int centerX = (int)(matchLocation.x + (template.width() / 2));
        int centerY = (int)(matchLocation.y + (template.height() / 2));

        JavascriptExecutor js;
        if (!(context instanceof JavascriptExecutor)) {
            AqualityServices.getLogger().debug("Current search context doesn't support executing scripts. " +
                    "Will take browser js executor instead");
            js = AqualityServices.getBrowser().getDriver();
        }
        else {
            js = (JavascriptExecutor) context;
        }

        //noinspection unchecked
        return (List<WebElement>) js.executeScript("return document.elementsFromPoint(arguments[0], arguments[1]);", centerX, centerY);
    }

    private byte[] getScreenshot(SearchContext context) {
        byte[] screenshotBytes;

        if (!(context instanceof TakesScreenshot)) {
            AqualityServices.getLogger().debug("Current search context doesn't support taking screenshots. " +
                    "Will take browser screenshot instead");
            screenshotBytes = AqualityServices.getBrowser().getScreenshot();
        } else {
            screenshotBytes = ((TakesScreenshot) context).getScreenshotAs(OutputType.BYTES);
        }

        return screenshotBytes;
    }
}
