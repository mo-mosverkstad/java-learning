package se.ebikerepair.integration;

/**
 * Thrown when no customer is found for the given telephone number in the customer registry.
 */
public class NotFoundCustomerException extends Exception {
    /**
     * Creates an exception indicating no customer was found for the given telephone number.
     *
     * @param telephoneNumber the telephone number that was not found
     */
    public NotFoundCustomerException(String telephoneNumber) {
        super(String.format("No customer found for telephone number: %s", telephoneNumber));
    }
}
