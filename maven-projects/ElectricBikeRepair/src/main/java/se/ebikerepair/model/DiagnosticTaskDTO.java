package se.ebikerepair.model;

import se.ebikerepair.model.Cost;
import se.ebikerepair.model.Result;

/**
 * Data transfer object representing a diagnostic task with name, description, cost, and result.
 */
public class DiagnosticTaskDTO {
    public final String name;
    public final String description;
    public final Cost cost;
    public final Result result;

    /**
     * Creates a diagnostic task with a default unchecked result.
     *
     * @param name the task name
     * @param description the task description
     * @param cost the estimated cost of the task
     */
    public DiagnosticTaskDTO(String name, String description, Cost cost){
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.result = new Result();
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

    /** @return the diagnostic result */
    public Result getResult(){
        return result;
    }

    public String toString(){
        String status = result.getChecked() ? "X" : " ";
        String repairStatus = result.getToBeRepaired() ? "TO BE REPAIRED" : " ";
        return String.format(se.ebikerepair.constant.PrintoutFormat.DIAGNOSTIC_TASK_PRINTOUT_FORMAT,
                status, name, description, repairStatus, cost, result.getDescription());
    }
}