package Exception;
public class ExceptionMyException extends Exception {

    public ExceptionMyException(String message) {
        super(message);
    }

    public ExceptionMyException(String message, Throwable cause) {
        super(message, cause);
    }
}
