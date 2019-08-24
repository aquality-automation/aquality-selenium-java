package aquality.selenium.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

class ResourceFile {
    private final String resourceName;
    private final String fileCanonicalPath;
    private final String fileContent;

    ResourceFile(String resourceName){
        this.resourceName = resourceName;
        fileCanonicalPath = getResourcePath(resourceName);
        fileContent = getResourceFileContent(resourceName);
    }

    String getResourceFileContent(final String resourceName) {
        InputStreamReader inputStream = new InputStreamReader(Objects.requireNonNull(JsonFile.class.getClassLoader().getResourceAsStream(resourceName)), StandardCharsets.UTF_8);
        try (BufferedReader br = new BufferedReader(inputStream)) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("Reading of resource file '%1$s' was failed", resourceName), e);
        }
    }

    static String getResourcePath(final String resourceName) {
        try{
            URL resourceURL = JsonFile.class.getClassLoader().getResource(resourceName);
            return Objects.requireNonNull(resourceURL).getPath();
        }catch (NullPointerException e){
            throw new IllegalArgumentException(String.format("Resource file %1$s was not found or cannot be loaded", resourceName), e);
        }
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFileCanonicalPath() {
        return fileCanonicalPath;
    }

    public String getFileContent() {
        return fileContent;
    }
}
