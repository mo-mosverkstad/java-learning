package OodPrinciples.LiskovSubstitutionPrinciple;

import java.util.HashMap;

public class MatrixConfig {

    public static final String MATRIX_ENTRY = "matrix.entry";
    public static final String MATRIX_WIDTH = "matrix.width";
    public static final String MATRIX_HEIGHT = "matrix.height";

    protected HashMap<String, String> configMap = new HashMap<>();

    public MatrixConfig() {
        configMap.put(MATRIX_ENTRY, "*");
        configMap.put(MATRIX_WIDTH, "4");
        configMap.put(MATRIX_HEIGHT, "2");
    }

    public String getConfig(String key) {
        return configMap.get(key);
    }
}
