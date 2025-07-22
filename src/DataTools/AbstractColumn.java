package DataTools;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractColumn<T> implements ColumnInterface {
    protected String name;
    protected final List<T> data = new ArrayList<>();

    protected AbstractColumn(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public void append(Object value) {
        data.add(cast(value));
    }

    @Override
    public void set(int index, Object value){
        data.set(index, cast(value));
    }

    @Override
    public Object get(int index) {
        return data.get(index);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder(data.size() * 16);
        content.append("Column[");
        for (T t : data) {
            content.append(t.toString()).append(", ");
        }
        content.setLength(content.length() - 2);
        content.append("]");
        return content.toString();
    }

    protected abstract T cast(Object value);
}