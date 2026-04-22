package se.ebikerepair.data;

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
public class JsonFileHandler implements DataHandler{
    private File jsonFile;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Creates a JSON file handler for the given resource file.
     *
     * @param resourceName the name of the JSON resource file on the classpath
     */
    public JsonFileHandler() {
        // initDataHandler(resourceName);
    }

    public void initDataHandler(String resourceName) {
        URL resource = getClass().getClassLoader().getResource(String.format("%s.json", resourceName));
        if (resource == null) {
            throw new ResourceNotFoundException(resourceName);
        }
        File file;
        try {
            file = new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new InvalidResourceURIException(resourceName, e);
        }
        jsonFile = file;
    }

    public boolean isInitialized() {
        return jsonFile != null && jsonFile.exists();
    }

    /**
     * Reads a list of objects from the JSON file.
     *
     * @param <T> the type of objects to deserialize
     * @param clazz the class of the objects
     * @return the list of deserialized objects, or empty list if file is missing or empty
     */
    public <T> List<T> readList(Class<T> clazz) {
        if (jsonFile == null) throw new InvalidResourceURIException(jsonFile.getName());
        try (Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            List<T> list = GSON.fromJson(reader, listType);
            return list != null ? list : Collections.emptyList();
        } catch (IOException e) {
            throw new CannotReadFileException(jsonFile.getName(), e);
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
            throw new InvalidResourceURIException(jsonFile.getName());
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(jsonFile), StandardCharsets.UTF_8)) {
            GSON.toJson(list, writer);
        } catch (IOException e) {
            throw new CannotWriteFileException(jsonFile.getName(), e);
        }
    }
}
