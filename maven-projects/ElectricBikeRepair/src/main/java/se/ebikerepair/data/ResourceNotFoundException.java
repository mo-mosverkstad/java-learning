package se.ebikerepair.data;

/**
 * Thrown when a classpath resource file cannot be found.
 */
public class ResourceNotFoundException extends RuntimeException{
    /**
     * Creates an exception indicating the resource was not found.
     *
     * @param resourceName the name of the resource that was not found
     */
    public ResourceNotFoundException(String resourceName) {
        super(String.format("%s not found in resources", resourceName));
    }
}
