package aquality.selenium.browser.devtools;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.v140.dom.model.RGBA;
import org.openqa.selenium.devtools.v140.emulation.Emulation;
import org.openqa.selenium.devtools.v140.emulation.model.MediaFeature;
import org.openqa.selenium.devtools.v140.emulation.model.ScreenOrientation;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of version-independent emulation DevTools commands.
 * Currently, only non-experimental extensions are implemented.
 * For more information, @see <a href="https://chromedevtools.github.io/devtools-protocol/tot/Emulation/">DevTools Emulation documentation</a>.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class EmulationHandling {
    private final DevToolsHandling tools;

    /**
     * Initializes a new instance of the {@link NetworkHandling} class.
     *
     * @param tools Instance of {@link DevToolsHandling}.
     */
    public EmulationHandling(DevToolsHandling tools) {
        this.tools = tools;
    }

    /**
     * Tells whether emulation is supported.
     *
     * @return true if the emulation is supported, false otherwise.
     */
    @Deprecated
    public boolean canEmulate() {
        return tools.sendCommand(Emulation.canEmulate());
    }

    /**
     * Overrides the GeoLocation Position or Error. Omitting any of the parameters emulates position unavailable.
     *
     * @param latitude  Latitude of location
     * @param longitude Longitude of location
     * @param accuracy  Accuracy of the location
     */
    public void setGeolocationOverride(double latitude, double longitude, double accuracy) {
        setGeolocationOverride(Optional.of(latitude), Optional.of(longitude), Optional.of(accuracy), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());
    }

    /**
     * Overrides the GeoLocation Position. Accuracy of the geoLocation is set to 1 meaning 100% accuracy.
     * Omitting any of the parameters emulates position unavailable.
     *
     * @param latitude  Latitude of location
     * @param longitude Longitude of location
     */
    public void setGeolocationOverride(double latitude, double longitude) {
        setGeolocationOverride(latitude, longitude, 1);
    }

    /**
     * Overrides the GeoLocation Position or Error. Omitting any of the parameters emulates position unavailable.
     *
     * @param latitude         Latitude of location
     * @param longitude        Longitude of location
     * @param altitude         Altitude of location
     * @param accuracy         Accuracy of the location
     * @param altitudeAccuracy Altitude accuracy of the location
     * @param heading          Heading of location
     * @param speed            Speed of location
     */
    public void setGeolocationOverride(Optional<Number> latitude, Optional<Number> longitude, Optional<Number> accuracy,
                                       Optional<Number> altitude, Optional<Number> altitudeAccuracy, Optional<Number> heading,
                                       Optional<Number> speed) {
        tools.sendCommand(Emulation.setGeolocationOverride(latitude, longitude, accuracy, altitude, altitudeAccuracy, heading, speed));
    }

    /**
     * Clears the overridden GeoLocation Position and Error.
     */
    public void clearGeolocationOverride() {
        tools.sendCommand(Emulation.clearGeolocationOverride());
    }

    /**
     * Overrides the values of device screen dimensions.
     *
     * @param params Version-specific set of parameters. For example, take a look at {@link Emulation#setDeviceMetricsOverride}
     */
    public void setDeviceMetricsOverride(Map<String, Object> params) {
        tools.sendCommand(new Command<Void>("Emulation.setDeviceMetricsOverride", params));
    }

    /**
     * Overrides the values of device screen dimensions.
     *
     * @param width             Value to override window.screen.width
     * @param height            Value to override window.screen.height
     * @param deviceScaleFactor Overriding device scale factor value. 0 disables the override.
     * @param mobile            Whether to emulate mobile device. This includes viewport meta tag, overlay scrollbars, text auto-sizing and more.
     */
    public void setDeviceMetricsOverride(Integer width, Integer height, Number deviceScaleFactor, Boolean mobile) {
        setDeviceMetricsOverride(width, height, deviceScaleFactor, mobile, Optional.empty(), Optional.empty());
    }

    /**
     * Overrides the values of device screen dimensions.
     *
     * @param width                  Value to override window.screen.width
     * @param height                 Value to override window.screen.height
     * @param deviceScaleFactor      Overriding device scale factor value. 0 disables the override.
     * @param mobile                 Whether to emulate mobile device. This includes viewport meta tag, overlay scrollbars, text auto-sizing and more.
     * @param screenOrientationType  Orientation type.
     *                               Allowed Values (in any case): portraitPrimary, portraitSecondary, landscapePrimary, landscapeSecondary.
     * @param screenOrientationAngle Orientation angle. Set only if orientation type was set.
     */
    public void setDeviceMetricsOverride(Integer width, Integer height, Number deviceScaleFactor, Boolean mobile,
                                         Optional<String> screenOrientationType, Optional<Integer> screenOrientationAngle) {
        Optional<ScreenOrientation> screenOrientation = Optional.empty();
        if (screenOrientationType.isPresent() && StringUtils.isNotEmpty(screenOrientationType.get())) {
            Integer angle = 0;
            if (screenOrientationAngle.isPresent()) {
                angle = screenOrientationAngle.get();
            }
            screenOrientation = Optional.of(new ScreenOrientation(ScreenOrientation.Type.fromString(screenOrientationType.get()), angle));
        }
        tools.sendCommand(Emulation.setDeviceMetricsOverride(width, height, deviceScaleFactor, mobile, Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), screenOrientation, Optional.empty(), Optional.empty(), Optional.empty()));
    }

    /**
     * Clears the overridden device metrics.
     */
    public void clearDeviceMetricsOverride() {
        tools.sendCommand(Emulation.clearDeviceMetricsOverride());
    }

    /**
     * Overrides the values of user agent.
     *
     * @param params Version-specific set of parameters.
     *               For example, take a look at {@link Emulation#setUserAgentOverride}
     */
    public void setUserAgentOverride(Map<String, Object> params) {
        tools.sendCommand(new Command<Void>("Emulation.setUserAgentOverride", params));
    }

    /**
     * Overrides the values of user agent.
     *
     * @param userAgent User agent to use.
     */
    public void setUserAgentOverride(String userAgent) {
        setUserAgentOverride(userAgent, Optional.empty(), Optional.empty());
    }

    /**
     * Overrides the values of user agent.
     *
     * @param userAgent      User agent to use.
     * @param acceptLanguage Browser language to emulate.
     * @param platform       The platform navigator.platform should return.
     */
    public void setUserAgentOverride(String userAgent, Optional<String> acceptLanguage, Optional<String> platform) {
        tools.sendCommand(Emulation.setUserAgentOverride(userAgent, acceptLanguage, platform, Optional.empty()));
    }

    /**
     * Disables script execution in the page.
     */
    public void setScriptExecutionDisabled() {
        setScriptExecutionDisabled(true);
    }

    /**
     * Switches script execution in the page.
     *
     * @param value Whether script execution should be disabled in the page.
     */
    public void setScriptExecutionDisabled(boolean value) {
        tools.sendCommand(Emulation.setScriptExecutionDisabled(value));
    }

    /**
     * Enables touch on platforms which do not support them.
     */
    public void setTouchEmulationEnabled() {
        setTouchEmulationEnabled(true);
    }

    /**
     * Enables touch on platforms which do not support them.
     *
     * @param enabled Whether the touch event emulation should be enabled.
     */
    public void setTouchEmulationEnabled(boolean enabled) {
        setTouchEmulationEnabled(enabled, Optional.empty());
    }

    /**
     * Enables touch on platforms which do not support them.
     *
     * @param enabled        Whether the touch event emulation should be enabled.
     * @param maxTouchPoints Maximum touch points supported. Defaults to one.
     */
    public void setTouchEmulationEnabled(boolean enabled, Optional<Integer> maxTouchPoints) {
        tools.sendCommand(Emulation.setTouchEmulationEnabled(enabled, maxTouchPoints));
    }

    /**
     * Emulates the given media type or media feature for CSS media queries.
     *
     * @param params Version-specific set of parameters. For example, take a look at {@link Emulation#setEmulatedMedia}
     */
    public void setEmulatedMedia(Map<String, Object> params) {
        tools.sendCommand(new Command<Void>("Emulation.setEmulatedMedia", params));
    }

    /**
     * Emulates the given media type or media feature for CSS media queries.
     *
     * @param media         Media type to emulate. Empty string disables the override.
     *                      Possible values: braille, embossed, handheld, print, projection, screen, speech, tty, tv.
     * @param mediaFeatures Media features to emulate.
     */
    public void setEmulatedMedia(String media, Map<String, String> mediaFeatures) {
        setEmulatedMedia(Optional.of(media), Optional.of(mediaFeatures));
    }

    /**
     * Emulates the given media type or media feature for CSS media queries.
     *
     * @param media         Media type to emulate. Empty string disables the override.
     *                      Possible values: braille, embossed, handheld, print, projection, screen, speech, tty, tv.
     * @param mediaFeatures Media features to emulate.
     */
    public void setEmulatedMedia(Optional<String> media, Optional<Map<String, String>> mediaFeatures) {
        List<MediaFeature> featureList = mediaFeatures.orElse(Collections.emptyMap()).entrySet().stream()
                .map((Map.Entry<String, String> entry) -> new MediaFeature(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        tools.sendCommand(Emulation.setEmulatedMedia(media, Optional.of(featureList)));
    }

    /**
     * Disables emulated media override.
     */
    public void disableEmulatedMediaOverride() {
        setEmulatedMedia(Collections.singletonMap("media", ""));
    }

    /**
     * Sets an override of the default background color of the frame. This override is used if the content does not specify one.
     *
     * @param red   The red component, in the [0-255] range.
     * @param green The green component, in the [0-255] range.
     * @param blue  The blue component, in the [0-255] range.
     */
    public void setDefaultBackgroundColorOverride(int red, int green, int blue) {
        setDefaultBackgroundColorOverride(red, green, blue, Optional.empty());
    }

    /**
     * Sets an override of the default background color of the frame. This override is used if the content does not specify one.
     *
     * @param red   The red component, in the [0-255] range.
     * @param green The green component, in the [0-255] range.
     * @param blue  The blue component, in the [0-255] range.
     * @param alpha The alpha component, in the [0-1] range (default: 1).
     */
    public void setDefaultBackgroundColorOverride(int red, int green, int blue, Optional<Number> alpha) {
        tools.sendCommand(Emulation.setDefaultBackgroundColorOverride(Optional.of(new RGBA(red, green, blue, alpha))));
    }

    /**
     * Clears an override of the default background color of the frame.
     */
    public void clearDefaultBackgroundColorOverride() {
        tools.sendCommand(Emulation.setDefaultBackgroundColorOverride(Optional.empty()));
    }
}
