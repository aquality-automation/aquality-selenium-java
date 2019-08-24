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

public class JsonFile {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String fileCanonicalPath;
    private final String content;

    public JsonFile(File file) throws IOException {
        this.content = getFileContent(file.getCanonicalPath());
        fileCanonicalPath = file.getCanonicalPath();
    }

    public JsonFile(String resourceName) {
        ResourceFile resourceFile = new ResourceFile(resourceName);
        this.content = resourceFile.getFileContent();
        this.fileCanonicalPath = resourceFile.getFileCanonicalPath();
    }

    public Object getValue(String jsonPath){
        return getEnvValueOrDefault(jsonPath);
    }

    private Object getEnvValueOrDefault(String jsonPath){
        String key = jsonPath.replace("/",".").substring(1, jsonPath.length());
        String envVar = System.getProperty(key);
        if(envVar == null){
            JsonNode node = getJsonNode(jsonPath);
            if(node.isBoolean()){
                return node.asBoolean();
            }else if(node.isLong()){
                return node.asLong();
            }else if(node.isInt()){
                return node.asInt();
            }else{
                return node.asText();
            }
        }else {
            Logger.getInstance().debug(String.format("***** Using variable passed from environment %1$s=%2$s", key, envVar));
            return envVar;
        }
    }

    public List<String> getList(String jsonPath){
        List<String> list = new ArrayList<>();
        getJsonNode(jsonPath).elements().forEachRemaining(node -> list.add(node.asText()));
        return list;
    }

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

    public String getContent() {
        return content;
    }

    public String getFileCanonicalPath() {
        return fileCanonicalPath;
    }
}
