package se.ebikerepair.data;

/**
 * Thrown when a database server cannot be reached or is not running.
 */
public class NoDatabaseException extends RuntimeException {
    /**
     * Creates an exception indicating the database is unavailable.
     *
     * @param databaseName the name of the database that could not be reached
     */
    public NoDatabaseException(String databaseName){
        super(String.format("No database %s found or connected", databaseName));
    }
}
