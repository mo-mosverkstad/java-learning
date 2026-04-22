package se.ebikerepair.integration;

/**
 * Thrown when a data handler cannot be instantiated via reflection.
 */
public class CannotCreateDataHandlerException extends RuntimeException{
    /**
     * Creates an exception indicating the data handler class could not be created.
     *
     * @param dataHandlerClassName the fully qualified class name that failed to instantiate
     * @param exception the underlying cause of the failure
     */
    public CannotCreateDataHandlerException(String dataHandlerClassName, Exception exception){
        super(String.format("Cannot create the data handler %s", dataHandlerClassName), exception);
    }
}
