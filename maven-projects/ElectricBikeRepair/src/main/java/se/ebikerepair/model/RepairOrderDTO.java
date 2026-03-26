package se.ebikerepair.model;

import java.util.Date;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.model.ProblemDTO;
import se.ebikerepair.model.RepairOrderState;
import se.ebikerepair.model.Cost;

public record RepairOrderDTO(
        CustomerDTO customerDTO,
        ProblemDTO problemDTO,
        Date createdDate,
        Date estimatedCompleteDate,
        Cost totalCost,
        RepairOrderState repairOrderState,
        String id
    ) {

    @Override
    public String toString() {
        return format(id, repairOrderState, createdDate, estimatedCompleteDate,
                totalCost, customerDTO.getName(), problemDTO);
    }

    static String format(String id, RepairOrderState state, Date created,
            Date estComplete, Cost cost, String customerName, ProblemDTO problem) {
        String estCompleteStr = estComplete != null ? estComplete.toString() : "N/A";
        return String.format(PrintoutFormat.REPAIR_ORDER_PRINTOUT_FORMAT,
                id, state, created, estCompleteStr, cost, customerName, problem);
    }
}
