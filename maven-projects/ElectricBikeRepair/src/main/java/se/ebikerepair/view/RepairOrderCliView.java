package se.ebikerepair.view;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.model.RepairOrderObserver;

public class RepairOrderCliView implements RepairOrderObserver {

    @Override
    public void updateRepairOrder(RepairOrderDTO repairOrderDTO){
        System.out.println();
        System.out.println(String.format("%s --- REPAIR ORDER MODIFICATION NOTIFICATION (BEGIN) ---%s", PrintoutFormat.ANSI_YELLOW, PrintoutFormat.ANSI_RESET));
        System.out.println(String.format("%sTHE REPAIR ORDER HAS BEEN MODIFIED     : %s%s", PrintoutFormat.ANSI_BLUE, repairOrderDTO.id(), PrintoutFormat.ANSI_RESET));
        System.out.println(String.format("%sPLEASE USE TELE NUMBER TO CHECK DETAILS: %s (%s)%s",
            PrintoutFormat.ANSI_BLUE,
            repairOrderDTO.customerDTO().telephoneNumber(),
            repairOrderDTO.customerDTO().name(),
            PrintoutFormat.ANSI_RESET));
        System.out.println(String.format("%s --- REPAIR ORDER MODIFICATION NOTIFICATION -(END)- ---%s", PrintoutFormat.ANSI_YELLOW, PrintoutFormat.ANSI_RESET));
        System.out.println();
    }
}
