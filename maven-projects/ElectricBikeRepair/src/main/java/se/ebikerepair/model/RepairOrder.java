package se.ebikerepair.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import se.ebikerepair.model.RepairOrderObserver;
import se.ebikerepair.model.MembershipPricingStrategy;
import se.ebikerepair.model.PricingResult;
import se.ebikerepair.model.PricingStrategy;
import se.ebikerepair.model.StandardPricingStrategy;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.MembershipDTO;
import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.integration.ProblemDTO;
import se.ebikerepair.integration.ResultDTO;
import se.ebikerepair.integration.RepairTaskDTO;


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
    private List<RepairOrderObserver> repairOrderObservers;

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
        this.repairOrderObservers = new ArrayList<RepairOrderObserver>();
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
        /*
        Cost total = new Cost();
        total.calculate(diagnosticReport.getTotalCost());
        total.calculate(repairTaskCollection.getTotalCost());
        return total;
        */
        return getPricingResult().getFinalCost();
    }

    public PricingResult getPricingResult(){
        MembershipDTO membership = getCustomerDTO().membership();
        PricingStrategy pricingStrategy = membership.active() ? new MembershipPricingStrategy(membership.repairCount()) : new StandardPricingStrategy();
        return pricingStrategy.calculateTotalCost(this);
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

    /**
     * Returns the repair task collection.
     *
     * @return the repair task collection
     */
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
     * Updates the problem with values from a ProblemDTO.
     *
     * @param problemDTO the DTO containing the new problem values
     */
    public void updateProblem(ProblemDTO problemDTO) {
        this.problem.update(problemDTO);
        notifyRepairOrderObservers();
    }

    /**
     * Updates a diagnostic task result identified by name.
     *
     * @param name the diagnostic task name (partial match)
     * @param result the result to apply to the diagnostic task
     * @throws IllegalArgumentException if no diagnostic task matches the given name
     */
    public void updateDiagnosticResult(String name, ResultDTO result) {
        getDiagnosticReport().updateDiagnosticResult(name, result);
        notifyRepairOrderObservers();
    }

    /**
     * Adds a repair task to the collection from a RepairTaskDTO.
     *
     * @param repairTask the repair task DTO to add
     */
    public void addRepairTask(RepairTaskDTO repairTask) {
        getRepairTaskCollection().addRepairTask(repairTask);
        notifyRepairOrderObservers();
    }

    /**
     * Sets the repair order state to Accepted.
     */
    public void accept() {
        repairOrderState = RepairOrderState.Accepted;
        notifyRepairOrderObservers();
    }

    /**
     * Sets the repair order state to Rejected.
     */
    public void reject() {
        repairOrderState = RepairOrderState.Rejected;
        notifyRepairOrderObservers();
    }

    /**
     * The specified observer will be notified when this repair order state has changed.
     * 
     * @param repairOrderObserver The observer to notify. 
     */
    public void addRepairOrderObserver(RepairOrderObserver repairOrderObserver){
        repairOrderObservers.add(repairOrderObserver);
    }

    /**
     * All the specified observers will be notified when this repair order state has changed.
     * 
     * @param observers The observers to notify. 
     */
    public void addRepairOrderObservers(List<RepairOrderObserver> observers) {
        repairOrderObservers.addAll(observers);
    }

    private void notifyRepairOrderObservers() {
        for (RepairOrderObserver repairOrderObserver : repairOrderObservers) {
            repairOrderObserver.updateRepairOrder(toDTO());
        }
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
            getPricingResult(),
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
