package DataTools;

class BooleanColumn extends AbstractColumn<Boolean> {

    @Override
    public void append() {
        data.add(false);
    }
    @Override
    protected Boolean cast(Object value) {
        if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        return (Boolean) value;
    }
}