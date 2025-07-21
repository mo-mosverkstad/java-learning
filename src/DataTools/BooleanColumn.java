package DataTools;

class BooleanColumn extends AbstractColumn<Boolean> {
    @Override
    protected Boolean cast(Object value) {
        return (Boolean) value;
    }
}