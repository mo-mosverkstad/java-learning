package se.ebikerepair.model;

import se.ebikerepair.model.Cost;

public class PricingResult {
    private final Cost baseCost;
    private final Cost discount;
    private final Cost finalCost;

    public PricingResult(Cost baseCost, Cost discount, Cost finalCost) {
        this.baseCost = baseCost;
        this.discount = discount;
        this.finalCost = finalCost;
    }

    public Cost getBaseCost() { return baseCost; }
    public Cost getDiscount() { return discount; }
    public Cost getFinalCost() { return finalCost; }

    @Override
    public String toString(){
        return String.format("base = %s, discount = %s, finalCost = %s", baseCost, discount, finalCost);
    }
}