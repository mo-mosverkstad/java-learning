package DataTools;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractColumn<T> implements Column {
    protected final List<T> data = new ArrayList<>();

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

    protected abstract T cast(Object value);
}

