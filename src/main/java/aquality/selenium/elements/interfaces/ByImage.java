package aquality.selenium.elements.interfaces;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.JavaScript;
import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Locatable;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Locator to search elements by image.
 * Takes screenshot and finds match using openCV.
 * Performs screenshot scaling if devicePixelRatio != 1.
 * Then finds elements by coordinates using javascript.
 */
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

    /**
     * Constructor accepting image file.
     *
     * @param file image file to locate element by.
     */
    public ByImage(File file) {
        loadLibrary();
        this.template = Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_UNCHANGED);
    }

    /**
     * Constructor accepting image file.
     *
     * @param bytes image bytes to locate element by.
     */
    public ByImage(byte[] bytes) {
        loadLibrary();
        this.template = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_UNCHANGED);
    }

    @Override
    public String toString() {
        return "ByImage: " + new Dimension(template.width(), template.height());
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        Mat source = getScreenshot(context);
        Mat result = new Mat();
        Imgproc.matchTemplate(source, template, result, Imgproc.TM_CCOEFF_NORMED);

        float threshold = 1 - AqualityServices.getConfiguration().getVisualizationConfiguration().getDefaultThreshold();
        Core.MinMaxLocResult minMaxLoc = Core.minMaxLoc(result);

        int matchCounter = Math.abs((result.width() - template.width() + 1) * (result.height() - template.height() + 1));
        List<Point> matchLocations = new ArrayList<>();
        while (matchCounter > 0 && minMaxLoc.maxVal >= threshold) {
            matchCounter--;
            Point matchLocation = minMaxLoc.maxLoc;
            matchLocations.add(matchLocation);
            Imgproc.rectangle(result, new Point(matchLocation.x, matchLocation.y), new Point(matchLocation.x + template.cols(),
                    matchLocation.y + template.rows()), new Scalar(0, 0, 0), -1);
            minMaxLoc = Core.minMaxLoc(result);
        }

        return matchLocations.stream().map(matchLocation -> getElementOnPoint(matchLocation, context)).collect(Collectors.toList());
    }

    /**
     * Gets a single element on point (find by center coordinates, then select closest to matchLocation).
     *
     * @param matchLocation location of the upper-left point of the element.
     * @param context       search context.
     *                      If the searchContext is Locatable (like WebElement), adjust coordinates to be absolute coordinates.
     * @return the closest found element.
     */
    protected WebElement getElementOnPoint(Point matchLocation, SearchContext context) {
        if (context instanceof Locatable) {
            final org.openqa.selenium.Point point = ((Locatable) context).getCoordinates().onPage();
            matchLocation.x += point.getX();
            matchLocation.y += point.getY();
        }
        int centerX = (int) (matchLocation.x + (template.width() / 2));
        int centerY = (int) (matchLocation.y + (template.height() / 2));
        //noinspection unchecked
        List<WebElement> elements = (List<WebElement>) AqualityServices.getBrowser()
                .executeScript(JavaScript.GET_ELEMENTS_FROM_POINT, centerX, centerY);
        elements.sort(Comparator.comparingDouble(e -> distanceToPoint(matchLocation, e)));
        return elements.get(0);
    }

    /**
     * Calculates distance from element to matching point.
     *
     * @param matchLocation matching point.
     * @param element       target element.
     * @return distance in pixels.
     */
    protected static double distanceToPoint(Point matchLocation, WebElement element) {
        org.openqa.selenium.Point elementLocation = element.getLocation();
        return Math.sqrt(Math.pow(matchLocation.x - elementLocation.x, 2) + Math.pow(matchLocation.y - elementLocation.y, 2));
    }

    /**
     * Takes screenshot from searchContext if supported, or from browser.
     *
     * @param context search context for element location.
     * @return captured screenshot as Mat object.
     */
    protected Mat getScreenshot(SearchContext context) {
        byte[] screenshotBytes = context instanceof TakesScreenshot
                ? ((TakesScreenshot) context).getScreenshotAs(OutputType.BYTES)
                : AqualityServices.getBrowser().getScreenshot();
        boolean isBrowserScreenshot = context instanceof WebDriver || !(context instanceof TakesScreenshot);
        Mat source = Imgcodecs.imdecode(new MatOfByte(screenshotBytes), Imgcodecs.IMREAD_UNCHANGED);
        long devicePixelRatio = (long) AqualityServices.getBrowser().executeScript(JavaScript.GET_DEVICE_PIXEL_RATIO);
        if (devicePixelRatio != 1 && isBrowserScreenshot) {
            int scaledWidth = (int) (source.width() / devicePixelRatio);
            int scaledHeight = (int) (source.height() / devicePixelRatio);
            Imgproc.resize(source, source, new Size(scaledWidth, scaledHeight), 0, 0, Imgproc.INTER_AREA);
        }
        return source;
    }
}
