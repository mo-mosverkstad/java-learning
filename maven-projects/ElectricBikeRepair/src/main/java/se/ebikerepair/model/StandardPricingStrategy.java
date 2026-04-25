package se.ebikerepair.model;

import se.ebikerepair.model.Cost;
import se.ebikerepair.model.PricingStrategy;
import se.ebikerepair.model.RepairOrder;

public class StandardPricingStrategy implements PricingStrategy {

    @Override
    public PricingResult calculateTotalCost(RepairOrder repairOrder) {
        Cost base = calculateBaseCost(repairOrder);
        return new PricingResult(base, new Cost(0), base);
    }

    private Cost calculateBaseCost(RepairOrder repairOrder) {
        Cost total = new Cost();
        total.calculate(repairOrder.getDiagnosticReport().getTotalCost());
        total.calculate(repairOrder.getRepairTaskCollection().getTotalCost());
        return total;
    }
}