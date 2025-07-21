package DataTools;

class StringColumn extends AbstractColumn<String> {
    @Override
    public void append() {
        data.add("");
    }

    @Override
    protected String cast(Object value) {
        return (String) value;
    }
}