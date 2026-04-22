package se.ebikerepair.data;

/**
 * Thrown when a file cannot be read due to an I/O error.
 */
public class CannotReadFileException extends RuntimeException {
    /**
     * Creates an exception indicating the file could not be read.
     *
     * @param name the name of the file that could not be read
     * @param exception the underlying I/O cause
     */
    public CannotReadFileException(String name, Exception exception){
        super(String.format("Failed to read %s : %s", name, exception.getMessage()), exception);
    }
}
