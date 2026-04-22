package se.ebikerepair.data;

public class CannotReadFileException extends RuntimeException {
    public CannotReadFileException(String name, Exception exception){
        super(String.format("Failed to read %s : %s", name, exception.getMessage()), exception);
    }
}
