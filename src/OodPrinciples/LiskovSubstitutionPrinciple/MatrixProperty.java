package OodPrinciples.LiskovSubstitutionPrinciple;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class MatrixProperty extends MatrixConfig {

    public MatrixProperty() {

        try (InputStream inputStream = new FileInputStream("./src/OodPrinciples/LiskovSubstitutionPrinciple/matrix.property")) {
            Properties properties = new Properties();
            properties.load(inputStream);

            Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                String key = (String) propertyNames.nextElement();
                String value = properties.getProperty(key);
                configMap.put(key, value);
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
