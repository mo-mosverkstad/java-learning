package DataTools.Tables;

public record CollationEntry(String name, CollationTypes type) {

    public boolean validate(Object object) {
        if (type == CollationTypes.BOOLEAN) {
            return object instanceof Boolean;
        } else if (type == CollationTypes.INTEGER) {
            return object instanceof Integer;
        } else if (type == CollationTypes.STRING) {
            return object instanceof String;
        }
        return false;
    }
}
