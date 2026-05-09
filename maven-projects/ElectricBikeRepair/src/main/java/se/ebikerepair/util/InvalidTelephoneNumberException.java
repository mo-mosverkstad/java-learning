package se.ebikerepair.util;

/**
 * Thrown when a telephone number has an invalid format and cannot be parsed.
 */
public class InvalidTelephoneNumberException extends Exception {
    private final String telephoneNumber;

    /**
     * Creates an exception indicating the given telephone number is invalid.
     *
     * @param telephoneNumber the telephone number that could not be parsed
     */
    public InvalidTelephoneNumberException(String telephoneNumber) {
        super("Invalid telephone number");
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * Returns the invalid telephone number.
     *
     * @return the telephone number
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }
}
