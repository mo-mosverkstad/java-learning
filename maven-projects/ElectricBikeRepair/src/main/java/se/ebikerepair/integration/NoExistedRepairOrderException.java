package se.ebikerepair.integration;

/**
 * Thrown when no repair order is found for the given identifier.
 */
public class NoExistedRepairOrderException extends Exception {
    private final String identifier;

    /**
     * Creates an exception indicating no repair order was found.
     *
     * @param identifier the identifier used to search (e.g., order ID or telephone number)
     */
    public NoExistedRepairOrderException(String identifier) {
        super("Repair order not found");
        this.identifier = identifier;
    }

    /**
     * Returns the identifier that was used in the failed lookup.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }
}
