package se.ebikerepair.model;

import se.ebikerepair.model.Cost;

public class ProposedRepairTaskDTO {
    private final String description;
    private final Cost cost;

    public ProposedRepairTaskDTO(String description, Cost cost){
        this.description = description;
        this.cost = cost;
    }

    public String getDescription(){
        return description;
    }

    public Cost getCost(){
        return cost;
    }

    @Override
    public String toString() {
        return String.format("ProposedRepairTaskDTO{description='%s', cost=%s}", description, cost);
    }
}
