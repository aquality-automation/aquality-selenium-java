package aquality.selenium.browser;

import aquality.selenium.core.logging.Logger;
import org.apache.logging.log4j.core.util.IOUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static java.lang.String.format;

/**
 * List of JS files which are available in `src/test/resources/js`.
 */
public enum JavaScript {
    AUTO_ACCEPT_ALERTS("autoAcceptAlerts.js"),
    BORDER_ELEMENT("borderElement.js"),
    CLICK_ELEMENT("clickElement.js"),
    ELEMENT_IS_ON_SCREEN("elementIsOnScreen.js"),
    GET_CHECKBOX_STATE("getCheckBxState.js"),
    GET_COMBOBOX_SELECTED_TEXT("getCmbText.js"),
    GET_COMBOBOX_TEXTS("getCmbValues.js"),
    GET_ELEMENT_BY_XPATH("getElementByXpath.js"),
    GET_ELEMENT_XPATH("getElementXPath.js"),
    GET_ELEMENT_TEXT("getElementText.js"),
    GET_TEXT_FIRST_CHILD("getTextFirstChild.js"),
    MOUSE_HOVER("mouseHover.js"),
    SCROLL_TO_BOTTOM("scrollToBottom.js"),
    SCROLL_TO_ELEMENT("scrollToElement.js"),
    SCROLL_TO_ELEMENT_CENTER("scrollToElementCenter.js"),
    SCROLL_TO_TOP("scrollToTop.js"),
    SELECT_COMBOBOX_VALUE_BY_TEXT("selectComboboxValueByText.js"),
    SET_FOCUS("setFocus.js"),
    SET_VALUE("setValue.js"),
    SET_ATTRIBUTE("setAttribute.js"),
    SCROLL_BY("scrollBy.js"),
    IS_PAGE_LOADED("isPageLoaded.js"),
    SCROLL_WINDOW_BY("scrollWindowBy.js"),
    SET_INNER_HTML("setInnerHTML.js"),
    GET_VIEWPORT_COORDINATES("getViewPortCoordinates.js"),
    GET_SCREEN_OFFSET("getScreenOffset.js"),
    OPEN_IN_NEW_TAB("openInNewTab.js"),
    OPEN_NEW_TAB("openNewTab.js"),
    EXPAND_SHADOW_ROOT("expandShadowRoot.js");

    private final String filename;

    JavaScript(String filename) {
        this.filename = filename;
    }

    /**
     * Get script template as a String.
     *
     * @return script template as String
     */
    public String getScript() {
        URL scriptFile = getClass().getResource("/js/" + filename);
        if (scriptFile != null) {
            try (InputStream stream = scriptFile.openStream()) {
                return readScript(stream);
            } catch (IOException e) {
                logScriptAbsence(filename, e);
            }
        }
        return "";
    }

    static String readScript(final File file) {
        try (InputStream stream = Files.newInputStream(file.toPath())) {
            return readScript(stream);
        } catch (IOException e) {
            logScriptAbsence(file.getName(), e);
            return "";
        }
    }

    private static void logScriptAbsence(String filename, IOException e) {
        Logger.getInstance().fatal(format("Couldn't find the script \"%s\"", filename), e);
    }

    private static String readScript(InputStream stream) throws IOException {
        return IOUtils.toString(new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)));
    }
}
