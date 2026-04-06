package se.ebikerepair.integration;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.model.Cost;
import se.ebikerepair.model.ProposedRepairTaskState;

/**
 * Immutable data transfer object representing a proposed repair task.
 *
 * @param name the task name
 * @param description the task description
 * @param cost the estimated cost
 * @param state the task completion state
 * @param estimatedDays the estimated number of days to complete
 */
public record RepairTaskDTO(String name, String description, Cost cost, ProposedRepairTaskState state, int estimatedDays) {

    /**
     * Creates a repair task DTO with an initial state of Incompleted.
     *
     * @param name the task name
     * @param description the task description
     * @param cost the estimated cost
     * @param estimatedDays the estimated number of days to complete
     */
    public RepairTaskDTO(String name, String description, Cost cost, int estimatedDays) {
        this(name, description, cost, ProposedRepairTaskState.Incompleted, estimatedDays);
    }

    @Override
    public String toString() {
        String status = state == ProposedRepairTaskState.Completed ? "X" : " ";
        return String.format(PrintoutFormat.PROPOSED_REPAIR_TASK_PRINTOUT_FORMAT,
                status, name, description, cost, estimatedDays);
    }
}
