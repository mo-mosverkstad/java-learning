package se.ebikerepair.controller;

/**
 * Thrown when a controller operation fails due to an underlying infrastructure error,
 * such as a database or file system failure.
 */
public class FailedOperationException extends Exception{
    /**
     * Creates an exception wrapping an infrastructure failure at the controller abstraction level.
     *
     * @param message a description of the failed operation
     * @param exception the underlying infrastructure cause
     */
    public FailedOperationException(String message, Exception exception){
        super(message, exception);
    }
}
