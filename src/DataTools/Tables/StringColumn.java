package DataTools.Tables;

public class StringColumn extends AbstractColumn<String> {
    public StringColumn(String name) {
        super(name);
    }

    @Override
    public void append() {
        data.add("");
    }

    @Override
    protected String cast(Object value) {
        return (String) value;
    }

    @Override
    public boolean validate(Object value) {
        return value instanceof String;
    }
}