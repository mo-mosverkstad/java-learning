package se.ebikerepair.view;

import se.ebikerepair.model.AbstractRepairOrderObserver;
import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.integration.RepairOrderDTO;

public class RepairOrderViewTemplate extends AbstractRepairOrderObserver{
    public RepairOrderViewTemplate(){}

    @Override
    protected void doHandleRepairOrderUpdate() throws Exception {
        RepairOrderDTO repairOrderDTO = getRepairOrderDTO();
        if (repairOrderDTO == null) throw new Exception("No repair order DTO");
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

    @Override
    protected void handleErrors(Exception e){
        System.out.println(String.format("ERROR: %s", e));
    }
}
