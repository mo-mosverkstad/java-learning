package se.ebikerepair.integration;

/**
 * Thrown when no customer is found for the given telephone number in the customer registry.
 */
public class NotFoundCustomerException extends Exception {
    private final String telephoneNumber;

    /**
     * Creates an exception indicating no customer was found for the given telephone number.
     *
     * @param telephoneNumber the telephone number that was not found
     */
    public NotFoundCustomerException(String telephoneNumber) {
        super("Customer not found");
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * Returns the telephone number that was not found.
     *
     * @return the telephone number
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }
}
