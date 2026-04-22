package se.ebikerepair.data;

public class NoDatabaseException extends RuntimeException {
    public NoDatabaseException(String databaseName){
        super(String.format("No database %s found or connected", databaseName));
    }
}
