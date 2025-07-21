package DataTools;

class IntColumn extends AbstractColumn<Integer> {

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
}