package se.ebikerepair.data;

public class InvalidResourceURIException extends RuntimeException{
    public InvalidResourceURIException(String resourceName, Exception exception) {
        super(String.format("Invalid resource URI: %s", resourceName), exception);
    }

    public InvalidResourceURIException(String resourceName) {
        new InvalidResourceURIException(String.format("Invalid resource URI: %s", resourceName), null);
    }
}
