package se.ebikerepair.model;

/**
 * Immutable data transfer object for a diagnostic result.
 */
public class ResultDTO {
    private final boolean checked;
    private final boolean toBeRepaired;
    private final String description;

    /**
     * Creates a result DTO.
     *
     * @param checked whether the task has been checked
     * @param toBeRepaired whether repair is needed
     * @param description the result description
     */
    public ResultDTO(boolean checked, boolean toBeRepaired, String description){
        this.checked = checked;
        this.toBeRepaired = toBeRepaired;
        this.description = description;
    }

    /** @return whether the task has been checked */
    public boolean getChecked() {
        return checked;
    }

    /** @return whether repair is needed */
    public boolean getToBeRepaired() {
        return toBeRepaired;
    }

    /** @return the result description */
    public String getDescription() {
        return description;
    }

    public String toString() {
        return String.format("Result: [checked: %s, to be repaired: %s] %s", checked, toBeRepaired, description);
    }
}
