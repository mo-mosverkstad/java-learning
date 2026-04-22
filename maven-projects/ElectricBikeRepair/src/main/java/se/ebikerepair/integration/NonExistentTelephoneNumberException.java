package se.ebikerepair.integration;

/**
 * Thrown when a search is made for a telephone number that does not exist in the customer registry.
 */
public class NonExistentTelephoneNumberException extends Exception {
    /**
     * Creates an exception indicating the given telephone number was not found.
     *
     * @param telephoneNumber the telephone number that was not found
     */
    public NonExistentTelephoneNumberException(String telephoneNumber) {
        super(String.format("No customer found for telephone number: %s", telephoneNumber));
    }
}
