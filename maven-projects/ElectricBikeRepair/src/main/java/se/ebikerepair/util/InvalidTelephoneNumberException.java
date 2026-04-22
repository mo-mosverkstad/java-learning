package se.ebikerepair.util;

/**
 * Thrown when a telephone number has an invalid format and cannot be parsed.
 */
public class InvalidTelephoneNumberException extends Exception {
    /**
     * Creates an exception indicating the given telephone number is invalid.
     *
     * @param telephoneNumber the telephone number that could not be parsed
     */
    public InvalidTelephoneNumberException(String telephoneNumber){
        super(String.format("The phone number %s is not a valid phone number", telephoneNumber));
    }
}
