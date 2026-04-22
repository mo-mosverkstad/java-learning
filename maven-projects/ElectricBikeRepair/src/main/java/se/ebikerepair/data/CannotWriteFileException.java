package se.ebikerepair.data;

public class CannotWriteFileException extends RuntimeException {
    public CannotWriteFileException(String name, Exception exception){
        super(String.format("Failed to write %s : %s", name, exception.getMessage()), exception);
    }
}
