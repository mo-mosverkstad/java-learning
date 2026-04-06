package se.ebikerepair.model;

import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.integration.DiagnosticReportDTO;
import se.ebikerepair.integration.DiagnosticTaskDTO;
import se.ebikerepair.integration.ResultDTO;
import se.ebikerepair.util.JsonFileHandler;

/**
 * Mutable entity representing a diagnostic report containing pre-defined diagnostic tasks.
 */
public class DiagnosticReport {
    private static final String DEFAULT_DESCRIPTION = "This report contains pre-defined diagnostic tasks for e-bike inspection. Please check each task and record the result.";
    private static final List<DiagnosticTask> DIAGNOSTIC_TASKS = new JsonFileHandler("diagnosticTasks.json").readList(DiagnosticTask.class);

    private String description = DEFAULT_DESCRIPTION;
    private List<DiagnosticTask> diagnosticTasks;

    /**
     * Creates a diagnostic report and loads diagnostic tasks from JSON.
     */
    public DiagnosticReport() {
        this.diagnosticTasks = DIAGNOSTIC_TASKS.stream()
                .map(t -> new DiagnosticTask(t.getName(), t.getDescription(),
                        new Cost(t.getCost().getAmount(), t.getCost().getCurrency()),
                        t.getDays()))
                .collect(Collectors.toList());
    }

    /**
     * Returns the description of the report.
     *
     * @return the description of the report
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the list of diagnostic tasks.
     *
     * @return the list of diagnostic tasks
     */
    public List<DiagnosticTask> getDiagnosticTasks() {
        return diagnosticTasks;
    }

    /**
     * Returns the total estimated days across all diagnostic tasks.
     *
     * @return the total estimated days
     */
    public int getTotalDays() {
        int totalDays = 0;
        for (DiagnosticTask task : diagnosticTasks) {
            if (task.getResult().getChecked()){
                totalDays += task.getDays();
            }
        }
        return totalDays;
    }

    /**
     * Returns the total cost accumulated from all diagnostic tasks.
     *
     * @return the total cost
     */
    public Cost getTotalCost() {
        Cost total = new Cost();
        for (DiagnosticTask task : diagnosticTasks) {
            if (task.getResult().getChecked()){
                total.calculate(task.getCost());
            }
        }
        return total;
    }

    /**
     * Finds diagnostic task index by a search name.
     *
     * @param name search name (partial match)
     * @return the index of the task
     * @throws IllegalArgumentException if no task matches the given name
     */
    private int findTaskIndexByName(String name) {
        for (int i = 0; i < diagnosticTasks.size(); i++) {
            if (diagnosticTasks.get(i).getName().contains(name)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Diagnostic task not found: " + name);
    }

    /**
     * Updates the diagnostic task result.
     *
     * @param name search name (partial match)
     * @param result the result to update with
     */
    protected void updateDiagnosticResult(String name, ResultDTO result) {
        int index = findTaskIndexByName(name);
        diagnosticTasks.get(index).getResult().update(result);
    }

    /**
     * Converts this diagnostic report to an immutable DTO with deep-copied tasks.
     *
     * @return a DiagnosticReportDTO snapshot of this report
     */
    public DiagnosticReportDTO toDTO() {
        List<DiagnosticTaskDTO> taskDTOs = diagnosticTasks.stream()
                .map(DiagnosticTask::toDTO)
                .collect(Collectors.toList());
        return new DiagnosticReportDTO(getTotalDays(), getTotalCost(), description, taskDTOs);
    }
}
