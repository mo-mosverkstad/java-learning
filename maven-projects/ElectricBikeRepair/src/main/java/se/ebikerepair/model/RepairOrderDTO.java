package se.ebikerepair.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.integration.CustomerDTO;

public record RepairOrderDTO(
        CustomerDTO customerDTO,
        ProblemDTO problemDTO,
        Date createdDate,
        Date estimatedCompleteDate,
        Cost totalCost,
        RepairOrderState repairOrderState,
        DiagnosticReportDTO diagnosticReport,
        List<ProposedRepairTaskDTO> proposedRepairTasks,
        String id
    ) {

    @Override
    public String toString() {
        return format(id, repairOrderState, createdDate, estimatedCompleteDate,
                totalCost, customerDTO, problemDTO, diagnosticReport, proposedRepairTasks);
    }

    /**
     * Finds diagnostic task index by a search name.
     * @param name Search name (partial match)
     * @return the index of the task
     * @throws IllegalArgumentException if no task matches the given name
     */
    public int findTaskIndexByName(String name){
        List<DiagnosticTaskDTO> tasks = diagnosticReport.getDiagnosticTasks();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getName().contains(name)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Diagnostic task not found: " + name);
    }

    /**
     * Formats repair order data into a printable string. Package-private, shared by
     * {@link RepairOrder#toString()} and {@link RepairOrderDTO#toString()}.
     *
     * @param id the repair order ID
     * @param state the current order state
     * @param created the creation date
     * @param estComplete the estimated completion date, or null
     * @param cost the total cost
     * @param customer the customer DTO
     * @param problem the problem description
     * @param diagnosticReport the diagnostic report, or null
     * @param proposedRepairTasks the list of proposed repair tasks, or null/empty
     * @return the formatted printout string
     */
    static String format(String id, RepairOrderState state, Date created,
            Date estComplete, Cost cost, CustomerDTO customer, ProblemDTO problem, DiagnosticReportDTO diagnosticReport, List<ProposedRepairTaskDTO> proposedRepairTasks) {
        String estCompleteStr = estComplete != null ? estComplete.toString() : "N/A";
        String diagStr = diagnosticReport != null ? diagnosticReport.toString() : "      (none)\n";
        String tasksStr = proposedRepairTasks == null || proposedRepairTasks.isEmpty() ? "      (none)\n" :
                proposedRepairTasks.stream().map(ProposedRepairTaskDTO::toString).collect(Collectors.joining());
        return String.format(PrintoutFormat.REPAIR_ORDER_PRINTOUT_FORMAT,
                id, state, created, estCompleteStr, cost, customer.toInlineString(), problem, diagStr, tasksStr);
    }
}
