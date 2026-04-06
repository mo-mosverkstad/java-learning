package se.ebikerepair.integration;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.model.Cost;

/**
 * Immutable data transfer object representing a diagnostic task with name, description, cost, and result.
 *
 * @param name the task name
 * @param description the task description
 * @param cost the estimated cost of the task
 * @param result the diagnostic result
 */
public record DiagnosticTaskDTO(String name, String description, Cost cost, int days, ResultDTO result) {

    /**
     * Creates a diagnostic task with a default unchecked result.
     *
     * @param name the task name
     * @param description the task description
     * @param cost the estimated cost of the task
     */
    public DiagnosticTaskDTO(String name, String description, Cost cost, int days) {
        this(name, description, cost, days, new ResultDTO(false, false, "have not checked yet"));
    }

    @Override
    public String toString() {
        String status = result.checked() ? "X" : " ";
        String repairStatus = result.toBeRepaired() ? "TO BE REPAIRED" : " ";
        return String.format(PrintoutFormat.DIAGNOSTIC_TASK_PRINTOUT_FORMAT,
                status, name, description, repairStatus, cost, days, result.description());
    }
}
