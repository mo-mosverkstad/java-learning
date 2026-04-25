package se.ebikerepair.model;

import se.ebikerepair.model.Cost;
import se.ebikerepair.model.PricingResult;
import se.ebikerepair.model.RepairOrder;

public interface PricingStrategy {
    PricingResult calculateTotalCost(RepairOrder repairOrder);
}
