package se.ebikerepair.model;

import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.util.JsonFileHandler;

public class DiagnosticReportDTO {
    private final Date date;
    private final List<DiagnosticTaskDTO> diagnosticTasks;
    
    public DiagnosticReportDTO(){
        this.date = new Date();
        this.diagnosticTasks = new JsonFileHandler("diagnosticTasks.json").readList(DiagnosticTaskDTO.class);
    }

    public Date getDate(){
        return date;
    }

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
