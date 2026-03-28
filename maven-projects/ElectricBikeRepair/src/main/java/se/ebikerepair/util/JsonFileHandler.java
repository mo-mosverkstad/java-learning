package se.ebikerepair.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * Utility for reading and writing JSON files from the classpath resources.
 */
public class JsonFileHandler {
    private final File jsonFile;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Creates a JSON file handler for the given resource file.
     *
     * @param resourceName the name of the JSON resource file on the classpath
     */
    public JsonFileHandler(String resourceName) {
        URL resource = getClass().getClassLoader().getResource(resourceName);
        if (resource == null) {
            System.err.println(resourceName + " not found in resources");
            jsonFile = null;
            return;
        }
        File file;
        try {
            file = new File(resource.toURI());
        } catch (URISyntaxException e) {
            System.err.println("Invalid resource URI: " + e.getMessage());
            file = null;
        }
        jsonFile = file;
    }

    /**
     * Reads a list of objects from the JSON file.
     *
     * @param <T> the type of objects to deserialize
     * @param clazz the class of the objects
     * @return the list of deserialized objects, or empty list if file is missing or empty
     */
    public <T> List<T> readList(Class<T> clazz) {
        if (jsonFile == null) return Collections.emptyList();
        try (Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            List<T> list = GSON.fromJson(reader, listType);
            return list != null ? list : Collections.emptyList();
        } catch (IOException e) {
            System.err.println("Failed to read " + jsonFile.getName() + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Writes a list of objects to the JSON file.
     *
     * @param <T> the type of objects to serialize
     * @param list the list of objects to write
     */
    public <T> void writeList(List<T> list) {
        if (jsonFile == null) {
            System.err.println("JSON file path not available");
            return;
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(jsonFile), StandardCharsets.UTF_8)) {
            GSON.toJson(list, writer);
        } catch (IOException e) {
            System.err.println("Failed to write " + jsonFile.getName() + ": " + e.getMessage());
        }
    }
}
