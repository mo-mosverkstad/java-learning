package se.ebikerepair.model;

import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.MembershipDTO;
import se.ebikerepair.model.PricingStrategy;

public class PricingStrategyFactory {
    private PricingStrategyFactory(){}

    public static PricingStrategy create(CustomerDTO customer){
        MembershipDTO membership = customer.membership();
        return membership.active() ? new MembershipPricingStrategy(membership.repairCount()) : new StandardPricingStrategy();
    }

}
