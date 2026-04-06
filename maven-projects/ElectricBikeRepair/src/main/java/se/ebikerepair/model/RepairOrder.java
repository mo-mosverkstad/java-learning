package se.ebikerepair.model;

import java.util.Date;
import java.util.UUID;

import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.RepairOrderDTO;


/**
 * Represents a repair order with customer, problem, diagnostic report, proposed tasks, and lifecycle state.
 */
public class RepairOrder {
    private static final int MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

    private CustomerDTO customerDTO;
    private Problem problem;
    private Date createdDate;
    private RepairOrderState repairOrderState;
    private DiagnosticReport diagnosticReport;
    private RepairTaskCollection repairTaskCollection;
    private String id;

    /**
     * Creates a new repair order with the given customer.
     *
     * @param customerDTO the customer who owns the repair order
     */
    public RepairOrder(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
        this.problem = new Problem();
        this.createdDate = new Date();
        this.repairOrderState = RepairOrderState.NewlyCreated;
        this.diagnosticReport = new DiagnosticReport();
        this.repairTaskCollection = new RepairTaskCollection();
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Returns the customer DTO.
     *
     * @return the customer DTO
     */
    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    /**
     * Returns the problem entity.
     *
     * @return the problem entity
     */
    public Problem getProblem() {
        return problem;
    }

    /**
     * Returns the date the order was created.
     *
     * @return the date the order was created
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Returns the estimated completion date based on diagnostic and repair task days.
     *
     * @return the estimated completion date, or null if no tasks exist
     */
    public Date getEstimatedCompleteDate() {
        int totalDays = diagnosticReport.getTotalDays() + repairTaskCollection.getTotalDays();
        if (totalDays == 0) {
            return null;
        }
        return new Date(createdDate.getTime() + (long) totalDays * MILLIS_PER_DAY);
    }

    /**
     * Returns the total cost from diagnostic tasks and repair tasks combined.
     *
     * @return the total cost
     */
    public Cost getTotalCost() {
        Cost total = new Cost();
        total.calculate(diagnosticReport.getTotalCost());
        total.calculate(repairTaskCollection.getTotalCost());
        return total;
    }

    /**
     * Returns the current state of the repair order.
     *
     * @return the current state of the repair order
     */
    public RepairOrderState getRepairOrderState() {
        return repairOrderState;
    }

    /**
     * Returns the diagnostic report.
     *
     * @return the diagnostic report
     */
    public DiagnosticReport getDiagnosticReport() {
        return diagnosticReport;
    }

    public RepairTaskCollection getRepairTaskCollection() {
        return repairTaskCollection;
    }
    
    /**
     * Returns the unique repair order ID.
     *
     * @return the unique repair order ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the repair order state to Accepted.
     */
    public void accept() {
        repairOrderState = RepairOrderState.Accepted;
    }

    /**
     * Sets the repair order state to Rejected.
     */
    public void reject() {
        repairOrderState = RepairOrderState.Rejected;
    }

    /**
     * Converts this repair order to an immutable DTO with deep copies of all nested entities.
     *
     * @return a RepairOrderDTO snapshot of this repair order
     */
    public RepairOrderDTO toDTO() {
        return new RepairOrderDTO(
            customerDTO,
            problem.toDTO(),
            new Date(createdDate.getTime()),
            getEstimatedCompleteDate(),
            getTotalCost(),
            repairOrderState,
            diagnosticReport.toDTO(),
            repairTaskCollection.toDTO(),
            id
        );
    }

    @Override
    public String toString() {
        return toDTO().toString();
    }
}
