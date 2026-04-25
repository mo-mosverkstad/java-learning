package se.ebikerepair.model;

import se.ebikerepair.model.Cost;
import se.ebikerepair.model.PricingStrategy;
import se.ebikerepair.model.RepairOrder;

public class MembershipPricingStrategy implements PricingStrategy {
    private final float MEMBERSHIP_THIRD_DISCOUNT = .15F;
    private int repairCount;

    public MembershipPricingStrategy(int repairCount){
        this.repairCount = repairCount;
    }

    @Override
    public PricingResult calculateTotalCost(RepairOrder order) {
        Cost base = calculateBaseCost(order);
        Cost discount = new Cost(0, base.getCurrency());
        if (repairCount % 3 == 0) {
            discount = new Cost(base.getAmount() * MEMBERSHIP_THIRD_DISCOUNT, base.getCurrency());
        }
        Cost finalCost = new Cost(base);
        finalCost.calculate(new Cost(-discount.getAmount(), base.getCurrency()));
        return new PricingResult(base, discount, finalCost);
    }

    private Cost calculateBaseCost(RepairOrder order) {
        Cost total = new Cost();
        total.calculate(order.getDiagnosticReport().getTotalCost());
        total.calculate(order.getRepairTaskCollection().getTotalCost());
        return total;
    }
}