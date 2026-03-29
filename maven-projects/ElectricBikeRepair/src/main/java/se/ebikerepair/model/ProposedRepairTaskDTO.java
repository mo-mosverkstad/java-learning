package se.ebikerepair.model;

/**
 * Data transfer object representing a proposed repair task with name, description, cost, and estimated days.
 */
public class ProposedRepairTaskDTO {
    private final String name;
    private final String description;
    private final Cost cost;
    private final ProposedRepairTaskState state;
    private final int estimatedDays;

    /**
     * Creates a proposed repair task with an initial state of Incompleted.
     *
     * @param name the task name
     * @param description the task description
     * @param cost the estimated cost
     * @param estimatedDays the estimated number of days to complete
     */
    public ProposedRepairTaskDTO(String name, String description, Cost cost, int estimatedDays){
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.state = ProposedRepairTaskState.Incompleted;
        this.estimatedDays = estimatedDays;
    }

    /** @return the task name */
    public String getName(){
        return name;
    }

    /** @return the task description */
    public String getDescription(){
        return description;
    }

    /** @return the estimated cost */
    public Cost getCost(){
        return cost;
    }

    /** @return the estimated number of days to complete */
    public int getEstimatedDays(){
        return estimatedDays;
    }

    @Override
    public String toString() {
        String status = state == ProposedRepairTaskState.Completed ? "X" : " ";
        return String.format(se.ebikerepair.constant.PrintoutFormat.PROPOSED_REPAIR_TASK_PRINTOUT_FORMAT,
                status, name, description, cost, estimatedDays);
    }
}
