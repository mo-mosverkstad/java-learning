package se.ebikerepair.model;

import se.ebikerepair.model.Cost;
import se.ebikerepair.model.Result;

public class DiagnosticTaskDTO {
    public final String name;
    public final String description;
    public final Cost cost;
    public final Result result;

    public DiagnosticTaskDTO(String name, String description, Cost cost){
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.result = new Result();
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public Cost getCost(){
        return cost;
    }

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