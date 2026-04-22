package se.ebikerepair.data;

/**
 * Thrown when a file cannot be written due to an I/O error.
 */
public class CannotWriteFileException extends RuntimeException {
    /**
     * Creates an exception indicating the file could not be written.
     *
     * @param name the name of the file that could not be written
     * @param exception the underlying I/O cause
     */
    public CannotWriteFileException(String name, Exception exception){
        super(String.format("Failed to write %s : %s", name, exception.getMessage()), exception);
    }
}
