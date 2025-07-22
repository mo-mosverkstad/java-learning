package DataTools;

class IntColumn extends AbstractColumn<Integer> {

    public IntColumn(String name) {
        super(name);
    }

    @Override
    public void append() {
        data.add(0);
    }

    @Override
    protected Integer cast(Object value) {
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        return (Integer) value;
    }

    @Override
    public boolean validate(Object value) {
        return value instanceof Integer;
    }
}