package aquality.selenium.utils;

import aquality.selenium.logger.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Provides methods to get info from JSON files.
 * Note that the value can be overriden via Environment variable with the same name
 * (e.g. for json path ".timeouts.timeoutScript" you can set environment variable "timeouts.timeoutScript")
 */
public class JsonFile {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileCanonicalPath;
    private final String content;

    /**
     * Inistantiates class using desired JSON file.
     * @param file JSON file.
     * @throws IOException if file is not found
     */
    public JsonFile(File file) throws IOException {
        this.content = getFileContent(file.getCanonicalPath());
        fileCanonicalPath = file.getCanonicalPath();
    }

    /**
     * Inistantiates class using desired JSON file.
     * @param resourceName path to JSON relatively resources
     */
    public JsonFile(String resourceName) {
        ResourceFile resourceFile = new ResourceFile(resourceName);
        this.content = resourceFile.getFileContent();
        this.fileCanonicalPath = resourceFile.getFileCanonicalPath();
    }

    /**
     * Gets value from JSON.
     * Note that the value can be overriden via Environment variable with the same name
     * (e.g. for json path ".timeouts.timeoutScript" you can set environment variable "timeouts.timeoutScript")
     * @param jsonPath Relative jsonPath to the value.
     * @return Value from JSON/Environment by jsonPath.
     */
    public Object getValue(String jsonPath){
        return getEnvValueOrDefault(jsonPath);
    }

    private Object getEnvValueOrDefault(String jsonPath){
        String key = jsonPath.replace("/",".").substring(1, jsonPath.length());
        String envVar = System.getProperty(key);
        if(envVar != null){
            Logger.getInstance().debug(String.format("***** Using variable passed from environment %1$s=%2$s", key, envVar));
        }
        JsonNode node = getJsonNode(jsonPath);
        if(node.isBoolean()){
            return envVar == null ? node.asBoolean() : Boolean.parseBoolean(envVar);
        }else if(node.isLong()){
            return envVar == null ? node.asLong() : Long.parseLong(envVar);
        }else if(node.isInt()){
            return envVar == null ? node.asInt() : Integer.parseInt(envVar);
        }else{
            return envVar == null ? node.asText() : envVar;
        }
    }

    /**
     * Gets list of values from JSON.
     * @param jsonPath Relative JsonPath to the values.
     * @return Values from JSON/Environment by jsonPath.
     */
    public List<String> getList(String jsonPath){
        List<String> list = new ArrayList<>();
        getJsonNode(jsonPath).elements().forEachRemaining(node -> list.add(node.asText()));
        return list;
    }

    /**
     * Gets map of values from JSON.
     * Note that the value can be overriden via Environment variable with the same name
     * (e.g. for json path ".timeouts.timeoutScript" you can set environment variable "timeouts.timeoutScript")
     * @param jsonPath
     * @return
     */
    public Map<String, Object> getMap(String jsonPath) {
        Iterator<Map.Entry<String, JsonNode>> iterator = getJsonNode(jsonPath).fields();
        final Map<String, Object> result = new HashMap<>();
        iterator.forEachRemaining(entry -> result.put(entry.getKey(), getValue(jsonPath + "/" + entry.getKey())));
        return result;
    }

    private JsonNode getJsonNode(String jsonPath){
        try{
            JsonNode node = mapper.readTree(getContent());
            return node.at(jsonPath);
        }catch (IOException e){
            throw new UncheckedIOException(String.format("Json field by json-path %1$s was not found in the file %2$s", jsonPath, getContent()),e);
        }
    }

    private String getFileContent(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("Content of file %1$s can't be read as String", filename), e);
        }
    }

    /**
     * Gets content of JsonFile
     * @return content of the file
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets canonical path to the file
     * @return canonical path to the file
     */
    public String getFileCanonicalPath() {
        return fileCanonicalPath;
    }
}
