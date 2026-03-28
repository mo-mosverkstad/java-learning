package se.ebikerepair.model;

import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.util.JsonFileHandler;

/**
 * Data transfer object representing a diagnostic report containing pre-defined diagnostic tasks.
 * Tasks are loaded from diagnosticTasks.json on creation.
 */
public class DiagnosticReportDTO {
    private final Date date;
    private final List<DiagnosticTaskDTO> diagnosticTasks;
    
    /**
     * Creates a diagnostic report with the current date and loads diagnostic tasks from JSON.
     */
    public DiagnosticReportDTO(){
        this.date = new Date();
        this.diagnosticTasks = new JsonFileHandler("diagnosticTasks.json").readList(DiagnosticTaskDTO.class);
    }

    /** @return the date the report was created */
    public Date getDate(){
        return date;
    }

    /** @return the list of diagnostic tasks */
    public List<DiagnosticTaskDTO> getDiagnosticTasks(){
        return diagnosticTasks;
    }

    @Override
    public String toString(){
        String tasksStr = diagnosticTasks.isEmpty() ? "      (none)\n" :
                diagnosticTasks.stream().map(DiagnosticTaskDTO::toString).collect(Collectors.joining());
        return String.format(PrintoutFormat.DIAGNOSTIC_REPORT_PRINTOUT_FORMAT, date, tasksStr);
    }
}
