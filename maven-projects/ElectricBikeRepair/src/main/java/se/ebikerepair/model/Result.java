package se.ebikerepair.model;

/**
 * Mutable result of a diagnostic task, tracking whether it has been checked and if repair is needed.
 */
public class Result {
    private boolean checked;
    private boolean toBeRepaired;
    private String description;

    /**
     * Creates a result with the specified values.
     *
     * @param checked whether the task has been checked
     * @param toBeRepaired whether repair is needed
     * @param description the result description
     */
    public Result(boolean checked, boolean toBeRepaired, String description){
        this.checked = checked;
        this.toBeRepaired = toBeRepaired;
        this.description = description;
    }

    /**
     * Creates a default unchecked result.
     */
    public Result(){
        this(false, false, "have not checked yet");
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

    /** @param checked whether the task has been checked */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /** @param toBeRepaired whether repair is needed */
    public void setToBeRepaired(boolean toBeRepaired) {
        this.toBeRepaired = toBeRepaired;
    }

    /** @param description the result description */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Updates this result with values from a ResultDTO.
     *
     * @param result the ResultDTO containing the new values
     */
    public void update(ResultDTO result) {
        this.checked = result.getChecked();
        this.toBeRepaired = result.getToBeRepaired();
        this.description = result.getDescription();
    }

    public String toString() {
        return String.format("Result: [checked: %s, to be repaired: %s] %s", checked, toBeRepaired, description);
    }
}
