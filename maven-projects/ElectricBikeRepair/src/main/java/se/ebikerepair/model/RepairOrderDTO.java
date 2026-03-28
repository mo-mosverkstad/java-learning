package se.ebikerepair.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.model.ProblemDTO;
import se.ebikerepair.model.RepairOrderState;
import se.ebikerepair.model.Cost;
import se.ebikerepair.model.DiagnosticReportDTO;

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
                totalCost, customerDTO.getName(), problemDTO, diagnosticReport, proposedRepairTasks);
    }

    static String format(String id, RepairOrderState state, Date created,
            Date estComplete, Cost cost, String customerName, ProblemDTO problem, DiagnosticReportDTO diagnosticReport, List<ProposedRepairTaskDTO> proposedRepairTasks) {
        String estCompleteStr = estComplete != null ? estComplete.toString() : "N/A";
        String diagStr = diagnosticReport != null ? diagnosticReport.toString() : "      (none)\n";
        String tasksStr = proposedRepairTasks == null || proposedRepairTasks.isEmpty() ? "      (none)\n" :
                proposedRepairTasks.stream().map(ProposedRepairTaskDTO::toString).collect(Collectors.joining());
        return String.format(PrintoutFormat.REPAIR_ORDER_PRINTOUT_FORMAT,
                id, state, created, estCompleteStr, cost, customerName, problem, diagStr, tasksStr);
    }
}
