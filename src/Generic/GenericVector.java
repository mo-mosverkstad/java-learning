package Generic;

import java.util.Arrays;

public class GenericVector <T> {

    private T[] vector;
    @SuppressWarnings("unchecked")
    public void setValues(T... values) {
        vector = values;
    }
    public T[] getValues() {
        return vector;
    }

    public String toString() {
        return "GenericVector{" +
                "vector=" + Arrays.toString(vector) +
                '}';
    }
}
