package DataTools.Tables;

public class BooleanColumn extends AbstractColumn<Boolean> {

    public BooleanColumn(String name) {
        super(name);
    }

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

    @Override
    public boolean validate(Object value) {
        return value instanceof Boolean;
    }
}