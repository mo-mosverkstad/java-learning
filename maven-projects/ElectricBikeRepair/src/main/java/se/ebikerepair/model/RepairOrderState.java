package se.ebikerepair.model;

/**
 * Represents the lifecycle states of a repair order.
 */
public enum RepairOrderState {
    NewlyCreated,
    ReadyForApproval,
    Rejected,
    Accepted,
    Completed,
    Payed
}
