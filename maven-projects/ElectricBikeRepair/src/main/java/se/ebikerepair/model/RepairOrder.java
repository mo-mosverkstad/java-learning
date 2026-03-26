package se.ebikerepair.model;

import java.util.Date;

import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.model.ProblemDTO;
import se.ebikerepair.model.RepairOrderState;

public class RepairOrder {
    private CustomerDTO customerDTO;
    private ProblemDTO problemDTO;
    private Date createdDate;
    private Date estimatedCompleteDate;
    private float totalCost;
    private RepairOrderState repairOrderState;
    private String id;

    public RepairOrder(CustomerDTO customerDTO, ProblemDTO problemDTO){
        this.customerDTO = customerDTO;
        this.problemDTO = problemDTO;
        this.createdDate = new Date();
        this.repairOrderState = RepairOrderState.NewlyCreated;
        this.id = String.valueOf(System.currentTimeMillis()); // extract method later
    }

    public String getId(){
        return id;
    }

    // add functions later
}
