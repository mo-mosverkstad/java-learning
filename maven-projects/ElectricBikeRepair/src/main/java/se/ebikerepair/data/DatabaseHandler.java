package se.ebikerepair.data;

import java.util.List;

/**
 * A singleton data handler that simulates a database connection.
 * All operations throw {@link NoDatabaseException} since no actual database is available.
 */
public class DatabaseHandler implements DataHandler {
    private static final DatabaseHandler INSTANCE = new DatabaseHandler();
    private String databaseName;

    /**
     * Returns the singleton DatabaseHandler instance.
     *
     * @return the single DatabaseHandler instance
     */
    public static DatabaseHandler getInstance() {
        return INSTANCE;
    }

    /**
     * No-arg constructor kept for reflection-based instantiation via {@link DataHandlerFactory}.
     */
    public DatabaseHandler() {}

    /**
     * Attempts to initialize the database connection.
     *
     * @param resourceName the name of the database resource
     * @throws NoDatabaseException always, since no database is available
     */
    public void initDataHandler(String resourceName){
        databaseName = resourceName;
        throw new NoDatabaseException(databaseName);
    }

    /**
     * Returns whether the database handler is initialized.
     *
     * @return always {@code false} since no database is available
     */
    public boolean isInitialized(){
        return false;
    }

    /**
     * Reads a list of objects from the database.
     *
     * @param <T> the type of objects to read
     * @param clazz the class of the objects to deserialize
     * @return never returns normally
     * @throws NoDatabaseException always, since no database is available
     */
    public <T> List<T> readList(Class<T> clazz){
        throw new NoDatabaseException(databaseName);
    }

    /**
     * Writes a list of objects to the database.
     *
     * @param <T> the type of objects to write
     * @param list the list of objects to persist
     * @throws NoDatabaseException always, since no database is available
     */
    public <T> void writeList(List<T> list){
        throw new NoDatabaseException(databaseName);
    }
}
