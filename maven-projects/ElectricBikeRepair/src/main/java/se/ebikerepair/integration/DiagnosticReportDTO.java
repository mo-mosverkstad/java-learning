package se.ebikerepair.integration;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.model.Cost;
import se.ebikerepair.data.JsonFileHandler;

/**
 * Immutable data transfer object representing a diagnostic report containing pre-defined diagnostic tasks.
 *
 * @param date the date the report was created
 * @param description the description of the report
 * @param diagnosticTasks the list of diagnostic tasks
 */
public record DiagnosticReportDTO(int days, Cost cost, String description, List<DiagnosticTaskDTO> diagnosticTasks) {

    @Override
    public String toString() {
        String tasksStr = diagnosticTasks.isEmpty() ? "      (none)\n" :
                diagnosticTasks.stream().map(DiagnosticTaskDTO::toString).collect(Collectors.joining());
        return String.format(PrintoutFormat.DIAGNOSTIC_REPORT_PRINTOUT_FORMAT, days, cost, description, tasksStr);
    }
}
