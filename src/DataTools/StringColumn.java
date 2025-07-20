package DataTools;

import java.util.List;
import java.util.ArrayList;

class StringColumn implements Column {
    private final List<String> data = new ArrayList<>();

    public StringColumn() {
        
    }

    public void append(Object value) {
        data.add((String) value);
    }

    public Object get(int index) {
        return data.get(index);
    }

    public int size() {
        return data.size();
    }
}