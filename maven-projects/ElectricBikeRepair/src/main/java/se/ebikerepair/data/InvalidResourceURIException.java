package se.ebikerepair.data;

/**
 * Thrown when a resource URI is malformed and cannot be converted to a file path.
 */
public class InvalidResourceURIException extends RuntimeException{
    /**
     * Creates an exception indicating the resource URI is invalid.
     *
     * @param resourceName the name of the resource with the invalid URI
     * @param exception the underlying cause of the URI syntax error
     */
    public InvalidResourceURIException(String resourceName, Exception exception) {
        super(String.format("Invalid resource URI: %s", resourceName), exception);
    }

    /**
     * Creates an exception indicating the resource URI is invalid, with no underlying cause.
     *
     * @param resourceName the name of the resource with the invalid URI
     */
    public InvalidResourceURIException(String resourceName) {
        new InvalidResourceURIException(String.format("Invalid resource URI: %s", resourceName), null);
    }
}
