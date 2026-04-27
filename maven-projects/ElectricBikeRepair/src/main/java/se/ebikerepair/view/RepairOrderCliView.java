package se.ebikerepair.view;

import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.model.RepairOrderObserver;

public class RepairOrderCliView implements RepairOrderObserver {

    @Override
    public void updateRepairOrder(RepairOrderDTO repairOrderDTO){
        System.out.println();
        System.out.println("\033[33m --- REPAIR ORDER MODIFICATION NOTIFICATION (BEGIN) ---\033[0m");
        System.out.println(String.format("\033[34mTHE REPAIR ORDER HAS BEEN MODIFIED     : %s\033[0m", repairOrderDTO.id()));
        System.out.println(String.format("\033[34mPLEASE USE TELE NUMBER TO CHECK DETAILS: %s (%s)\033[0m",
            repairOrderDTO.customerDTO().telephoneNumber(),
            repairOrderDTO.customerDTO().name()));
        System.out.println("\033[33m --- REPAIR ORDER MODIFICATION NOTIFICATION -(END)- ---\033[0m");
        System.out.println();
    }
}
