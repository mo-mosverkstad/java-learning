package DataTools;

class StringColumn extends AbstractColumn<String> {
    @Override
    protected String cast(Object value) {
        return (String) value;
    }
}