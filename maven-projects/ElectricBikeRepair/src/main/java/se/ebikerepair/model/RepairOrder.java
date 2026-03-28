package se.ebikerepair.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private DiagnosticReportDTO diagnosticReport;
    private List<ProposedRepairTaskDTO> proposedRepairTasks;
    private String id;

    public RepairOrder(CustomerDTO customerDTO, ProblemDTO problemDTO){
        this.customerDTO = customerDTO;
        this.problemDTO = problemDTO;
        this.createdDate = new Date();
        this.totalCost = new Cost();
        this.repairOrderState = RepairOrderState.NewlyCreated;
        this.diagnosticReport = new DiagnosticReportDTO();
        this.proposedRepairTasks = new ArrayList<>();
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

    public void setDiagnosticReport(DiagnosticReportDTO diagnosticReport){
        this.diagnosticReport = diagnosticReport;
    }

    public DiagnosticReportDTO getDiagnosticReport(){
        return diagnosticReport;
    }

    public void addProposedRepairTask(ProposedRepairTaskDTO proposedRepairTask){
        proposedRepairTasks.add(proposedRepairTask);
        totalCost.calculate(proposedRepairTask.getCost());
        if (estimatedCompleteDate == null) {
            estimatedCompleteDate = createdDate;
        }
        estimatedCompleteDate = new Date(estimatedCompleteDate.getTime() + (long)proposedRepairTask.getEstimatedDays() * 24 * 60 * 60 * 1000);
    }

    public void calculateCostByDiagnosticTask(int diagnosticTaskIndex) {
        totalCost.calculate(diagnosticReport.getDiagnosticTasks().get(diagnosticTaskIndex).getCost());
    }

    public String getId(){
        return id;
    }

    public void accept(){
        repairOrderState = RepairOrderState.Accepted;
    }

    public void reject(){
        repairOrderState = RepairOrderState.Rejected;
    }

    public RepairOrderDTO toDTO(){
        return new RepairOrderDTO(
            customerDTO,
            problemDTO,
            createdDate,
            estimatedCompleteDate,
            totalCost,
            repairOrderState,
            diagnosticReport,
            proposedRepairTasks,
            id
        );
    }

    @Override
    public String toString() {
        return RepairOrderDTO.format(id, repairOrderState, createdDate,
                estimatedCompleteDate, totalCost, customerDTO.getName(), problemDTO, diagnosticReport, proposedRepairTasks);
    }
}
