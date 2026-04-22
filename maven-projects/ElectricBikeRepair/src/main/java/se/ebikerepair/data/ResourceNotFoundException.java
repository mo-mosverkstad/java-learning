package se.ebikerepair.data;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resourceName) {
        super(String.format("%s not found in resources", resourceName));
    }
}
