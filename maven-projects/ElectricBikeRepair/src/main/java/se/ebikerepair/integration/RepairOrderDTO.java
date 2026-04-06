package se.ebikerepair.integration;

import java.util.Date;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.model.Cost;
import se.ebikerepair.model.RepairOrderState;

/**
 * Immutable data transfer object representing a repair order snapshot.
 *
 * @param customerDTO the customer
 * @param problemDTO the problem description
 * @param createdDate the creation date
 * @param estimatedCompleteDate the estimated completion date, or null
 * @param totalCost the total cost
 * @param repairOrderState the current order state
 * @param diagnosticReport the diagnostic report
 * @param repairTaskCollection the collection of proposed repair tasks
 * @param id the repair order ID
 */
public record RepairOrderDTO(
        CustomerDTO customerDTO,
        ProblemDTO problemDTO,
        Date createdDate,
        Date estimatedCompleteDate,
        Cost totalCost,
        RepairOrderState repairOrderState,
        DiagnosticReportDTO diagnosticReport,
        RepairTaskCollectionDTO repairTaskCollection,
        String id
    ) {

    @Override
    public String toString() {
        String estCompleteStr = estimatedCompleteDate != null ? estimatedCompleteDate.toString() : "N/A";
        String diagStr = diagnosticReport != null ? diagnosticReport.toString() : "      (none)\n";
        String tasksStr = repairTaskCollection != null ? repairTaskCollection.toString() : "      (none)\n";
        return String.format(PrintoutFormat.REPAIR_ORDER_PRINTOUT_FORMAT,
                id, repairOrderState, createdDate, estCompleteStr, totalCost,
                customerDTO.toInlineString(), problemDTO, diagStr, tasksStr);
    }
}
