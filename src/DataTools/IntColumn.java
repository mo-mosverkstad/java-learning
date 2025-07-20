package DataTools;

import java.util.List;
import java.util.ArrayList;

class IntColumn implements Column {
    private final List<Integer> data = new ArrayList<>();

    public IntColumn() {}

    public void append(Object value) {
        data.add((Integer) value);
    }

    public Object get(int index) {
        return data.get(index);
    }

    public int size() {
        return data.size();
    }
}