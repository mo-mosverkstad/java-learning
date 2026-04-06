package se.ebikerepair.model;

import se.ebikerepair.integration.DiagnosticTaskDTO;

/**
 * Mutable entity representing a diagnostic task with name, description, cost, result, and estimated days.
 */
public class DiagnosticTask {
    private String name;
    private String description;
    private Cost cost;
    private Result result;
    private int days;

    /**
     * Creates a diagnostic task with a default unchecked result.
     *
     * @param name the task name
     * @param description the task description
     * @param cost the estimated cost
     * @param days the estimated number of days to complete
     */
    public DiagnosticTask(String name, String description, Cost cost, int days) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.result = new Result();
        this.days = days;
    }

    /**
     * Returns the task name.
     *
     * @return the task name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the task description.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the estimated cost.
     *
     * @return the estimated cost
     */
    public Cost getCost() {
        return cost;
    }

    /**
     * Returns the diagnostic result.
     *
     * @return the diagnostic result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Returns the estimated number of days to complete.
     *
     * @return the estimated number of days
     */
    public int getDays() {
        return days;
    }

    /**
     * Converts this diagnostic task to an immutable DTO with deep-copied cost and result.
     *
     * @return a DiagnosticTaskDTO snapshot of this diagnostic task
     */
    public DiagnosticTaskDTO toDTO() {
        return new DiagnosticTaskDTO(name, description, new Cost(cost.getAmount(), cost.getCurrency()), getDays(), result.toDTO());
    }
}
