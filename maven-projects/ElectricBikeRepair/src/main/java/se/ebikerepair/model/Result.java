package se.ebikerepair.model;

import se.ebikerepair.integration.ResultDTO;

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

    /**
     * Returns whether the task has been checked.
     *
     * @return whether the task has been checked
     */
    public boolean getChecked() {
        return checked;
    }

    /**
     * Returns whether repair is needed.
     *
     * @return whether repair is needed
     */
    public boolean getToBeRepaired() {
        return toBeRepaired;
    }

    /**
     * Returns the result description.
     *
     * @return the result description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets whether the task has been checked.
     *
     * @param checked whether the task has been checked
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * Sets whether repair is needed.
     *
     * @param toBeRepaired whether repair is needed
     */
    public void setToBeRepaired(boolean toBeRepaired) {
        this.toBeRepaired = toBeRepaired;
    }

    /**
     * Sets the result description.
     *
     * @param description the result description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Updates this result with values from a ResultDTO.
     *
     * @param result the ResultDTO containing the new values
     */
    public void update(ResultDTO result) {
        this.checked = result.checked();
        this.toBeRepaired = result.toBeRepaired();
        this.description = result.description();
    }

    /**
     * Converts this result to an immutable DTO.
     *
     * @return a ResultDTO snapshot of this result
     */
    public ResultDTO toDTO() {
        return new ResultDTO(checked, toBeRepaired, description);
    }
}
