package se.ebikerepair.model;

import se.ebikerepair.model.Cost;

public class ProposedRepairTaskDTO {
    private final String name;
    private final String description;
    private final Cost cost;
    private final ProposedRepairTaskState state;
    private final int estimatedDays;

    public ProposedRepairTaskDTO(String name, String description, Cost cost, int estimatedDays){
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.state = ProposedRepairTaskState.Incompleted;
        this.estimatedDays = estimatedDays;
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
