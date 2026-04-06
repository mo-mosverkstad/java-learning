package se.ebikerepair.model;

import se.ebikerepair.integration.RepairTaskDTO;

/**
 * Mutable entity representing a proposed repair task with name, description, cost, state, and estimated days.
 */
public class RepairTask {
    private String name;
    private String description;
    private Cost cost;
    private ProposedRepairTaskState state;
    private int estimatedDays;

    /**
     * Creates a proposed repair task with an initial state of Incompleted.
     *
     * @param name the task name
     * @param description the task description
     * @param cost the estimated cost
     * @param estimatedDays the estimated number of days to complete
     */
    public RepairTask(String name, String description, Cost cost, int estimatedDays) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.state = ProposedRepairTaskState.Incompleted;
        this.estimatedDays = estimatedDays;
    }

    /**
     * Creates a repair task from a RepairTaskDTO.
     *
     * @param repairTaskDTO the DTO to create the repair task from
     */
    public RepairTask(RepairTaskDTO repairTaskDTO) {
        this(repairTaskDTO.name(), repairTaskDTO.description(), repairTaskDTO.cost(), repairTaskDTO.estimatedDays());
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
     * Returns the task completion state.
     *
     * @return the task completion state
     */
    public ProposedRepairTaskState getState() {
        return state;
    }

    /**
     * Returns the estimated number of days to complete.
     *
     * @return the estimated number of days to complete
     */
    public int getEstimatedDays() {
        return estimatedDays;
    }

    /**
     * Converts this repair task to an immutable DTO with deep-copied cost.
     *
     * @return a RepairTaskDTO snapshot of this repair task
     */
    public RepairTaskDTO toDTO() {
        return new RepairTaskDTO(name, description, new Cost(cost.getAmount(), cost.getCurrency()), state, estimatedDays);
    }
}
