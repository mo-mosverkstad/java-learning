package OodPrinciples.DependencyInversionPrinciple;

import java.util.HashMap;

public class MatrixConfig implements MatrixConfigable {

    private HashMap<String, String> configMap = new HashMap<>();

    public MatrixConfig() {

        configMap.put(MatrixConstances.MATRIX_ENTRY, "*");
        configMap.put(MatrixConstances.MATRIX_WIDTH, "4");
        configMap.put(MatrixConstances.MATRIX_HEIGHT, "2");
    }

    @Override
    public String getConfig(String key) {
        return configMap.get(key);
    }

}
