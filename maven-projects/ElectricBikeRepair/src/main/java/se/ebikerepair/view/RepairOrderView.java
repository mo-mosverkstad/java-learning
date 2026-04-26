package se.ebikerepair.view;

import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.model.RepairOrderObserver;

public class RepairOrderView implements RepairOrderObserver {

    @Override
    public void updateRepairOrder(RepairOrderDTO repairOrderDTO){
        System.out.println();
        System.out.println(" --- REPAIR ORDER MODIFICATION BEGIN ---");
        System.out.println(repairOrderDTO);
        System.out.println(" --- END ---");
        System.out.println();
    }
}
