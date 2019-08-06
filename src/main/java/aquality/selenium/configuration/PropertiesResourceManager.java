package aquality.selenium.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Class-wrapper for working with aqa.properties-files. Uses relative path.
 */
public final class PropertiesResourceManager {
    private Properties properties;

    /**
     * Constructor
     *
     * @param resourceName Name of resource
     */
    public PropertiesResourceManager(String resourceName) {
        properties = appendFromResource(new Properties(), resourceName);
    }

    /**
     * Merging of two aqa.properties-files (parameters from the second override parameters from the first)
     *
     * @param objProperties Properties
     * @param resourceName  Resource Name
     * @return Properties
     */
    private Properties appendFromResource(final Properties objProperties, final String resourceName) {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);

        if (inStream != null) {
            try {
                Reader reader = new InputStreamReader(inStream, StandardCharsets.UTF_8.name());
                objProperties.load(reader);
                inStream.close();
            } catch (IOException e) {
                throw new IllegalArgumentException(resourceName, e);
            }
        } else {
            throw new IllegalArgumentException(String.format("Resource \"%1$s\" could not be found", resourceName));
        }
        return objProperties;
    }

    /**
     * Get value by key
     *
     * @param key Key
     * @return Value
     */
    public String getProperty(String key) {
        return System.getProperty(key, this.properties.getProperty(key));
    }

    /**
     * Get value by key
     *
     * @param key          Key
     * @param defaultValue Default Value
     * @return Value
     */
    public String getProperty(String key, String defaultValue) {
        return System.getProperty(key, this.properties.getProperty(key, defaultValue));
    }

}
