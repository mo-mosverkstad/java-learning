package DataTools;

class IntColumn extends AbstractColumn<Integer> {
    @Override
    protected Integer cast(Object value) {
        return (Integer) value;
    }
}