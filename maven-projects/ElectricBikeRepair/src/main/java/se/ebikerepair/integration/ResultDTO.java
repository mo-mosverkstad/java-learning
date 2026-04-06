package se.ebikerepair.integration;

/**
 * Immutable data transfer object for a diagnostic result.
 *
 * @param checked whether the task has been checked
 * @param toBeRepaired whether repair is needed
 * @param description the result description
 */
public record ResultDTO(boolean checked, boolean toBeRepaired, String description) {

    @Override
    public String toString() {
        return String.format("Result: [checked: %s, to be repaired: %s] %s", checked, toBeRepaired, description);
    }
}
