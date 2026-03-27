package se.ebikerepair.model;

import java.util.Date;
import java.util.UUID;

import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.model.ProblemDTO;
import se.ebikerepair.model.RepairOrderState;
import se.ebikerepair.model.Cost;
import se.ebikerepair.model.RepairOrderDTO;
import se.ebikerepair.model.DiagnosticReportDTO;

public class RepairOrder {
    private CustomerDTO customerDTO;
    private ProblemDTO problemDTO;
    private Date createdDate;
    private Date estimatedCompleteDate;
    private Cost totalCost;
    private RepairOrderState repairOrderState;
    private DiagnosticReportDTO diagnosticReportDTO;
    private String id;

    public RepairOrder(CustomerDTO customerDTO, ProblemDTO problemDTO){
        this.customerDTO = customerDTO;
        this.problemDTO = problemDTO;
        this.createdDate = new Date();
        this.totalCost = new Cost();
        this.repairOrderState = RepairOrderState.NewlyCreated;
        this.id = UUID.randomUUID().toString();
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public ProblemDTO getProblemDTO() {
        return problemDTO;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getEstimatedCompleteDate() {
        return estimatedCompleteDate;
    }

    public Cost getTotalCost() {
        return totalCost;
    }

    public RepairOrderState getRepairOrderState(){
        return repairOrderState;
    }

    public void setDiagnosticReportDTO(DiagnosticReportDTO diagnosticReportDTO){
        this.diagnosticReportDTO = diagnosticReportDTO;
    }

    public DiagnosticReportDTO getDiagnosticReportDTO(){
        return diagnosticReportDTO;
    }

    public String getId(){
        return id;
    }

    public RepairOrderDTO toDTO(){
        return new RepairOrderDTO(
            customerDTO,
            problemDTO,
            createdDate,
            estimatedCompleteDate,
            totalCost,
            repairOrderState,
            diagnosticReportDTO,
            id
        );
    }

    @Override
    public String toString() {
        return RepairOrderDTO.format(id, repairOrderState, createdDate,
                estimatedCompleteDate, totalCost, customerDTO.getName(), problemDTO, diagnosticReportDTO);
    }
}
