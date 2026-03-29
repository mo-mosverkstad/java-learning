package se.ebikerepair.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import se.ebikerepair.integration.CustomerDTO;


/**
 * Represents a repair order with customer, problem, diagnostic report, proposed tasks, and lifecycle state.
 */
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

    /**
     * Creates a new repair order with the given customer and problem.
     *
     * @param customerDTO the customer who owns the repair order
     * @param problemDTO the problem description
     */
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

    /** @return the customer DTO */
    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    /** @return the problem DTO */
    public ProblemDTO getProblemDTO() {
        return problemDTO;
    }

    /** @return the date the order was created */
    public Date getCreatedDate() {
        return createdDate;
    }

    /** @return the estimated completion date, or null if not yet estimated */
    public Date getEstimatedCompleteDate() {
        return estimatedCompleteDate;
    }

    /** @return the total cost of the repair order */
    public Cost getTotalCost() {
        return totalCost;
    }

    /** @return the current state of the repair order */
    public RepairOrderState getRepairOrderState(){
        return repairOrderState;
    }

    /**
     * Sets the diagnostic report for this repair order.
     *
     * @param diagnosticReport the diagnostic report to attach
     */
    public void setDiagnosticReport(DiagnosticReportDTO diagnosticReport){
        this.diagnosticReport = diagnosticReport;
    }

    /** @return the diagnostic report */
    public DiagnosticReportDTO getDiagnosticReport(){
        return diagnosticReport;
    }

    /**
     * Adds a proposed repair task and updates the total cost and estimated completion date.
     *
     * @param proposedRepairTask the proposed repair task to add
     */
    public void addProposedRepairTask(ProposedRepairTaskDTO proposedRepairTask){
        proposedRepairTasks.add(proposedRepairTask);
        totalCost.calculate(proposedRepairTask.getCost());
        if (estimatedCompleteDate == null) {
            estimatedCompleteDate = createdDate;
        }
        estimatedCompleteDate = new Date(estimatedCompleteDate.getTime() + (long)proposedRepairTask.getEstimatedDays() * 24 * 60 * 60 * 1000);
    }

    /**
     * Updates the diagnostic task
     *
     * @param index the index of the diagnostic task to update
     * @param result the result to update with
     */
    public void updateDiagnosticResult(int index, ResultDTO result){
        diagnosticReport.getDiagnosticTasks().get(index).getResult().update(result);
    }

    /**
     * Adds the cost of a diagnostic task to the total cost.
     *
     * @param diagnosticTaskIndex the index of the diagnostic task
     */
    public void calculateCostByDiagnosticTask(int diagnosticTaskIndex) {
        totalCost.calculate(diagnosticReport.getDiagnosticTasks().get(diagnosticTaskIndex).getCost());
    }

    /** @return the unique repair order ID */
    public String getId(){
        return id;
    }

    /**
     * Sets the repair order state to Accepted.
     */
    public void accept(){
        repairOrderState = RepairOrderState.Accepted;
    }

    /**
     * Sets the repair order state to Rejected.
     */
    public void reject(){
        repairOrderState = RepairOrderState.Rejected;
    }

    /**
     * Converts this repair order to an immutable DTO.
     *
     * @return a RepairOrderDTO snapshot of this repair order
     */
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
