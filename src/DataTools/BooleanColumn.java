package DataTools;

import java.util.List;
import java.util.ArrayList;

class BooleanColumn implements Column {
    private final List<Boolean> data = new ArrayList<>();

    public BooleanColumn() {
        
    }

    public void append(Object value) {
        data.add((Boolean) value);
    }

    public Object get(int index) {
        return data.get(index);
    }

    public int size() {
        return data.size();
    }
}