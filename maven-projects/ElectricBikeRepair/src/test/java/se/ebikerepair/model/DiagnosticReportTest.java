package se.ebikerepair.model;

import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.DiagnosticReportDTO;
import se.ebikerepair.integration.ResultDTO;

import static org.junit.jupiter.api.Assertions.*;

class DiagnosticReportTest {

    @Test
    void testConstructorLoadsTasks() {
        DiagnosticReport report = new DiagnosticReport();
        assertNotNull(report.getDiagnosticTasks());
        assertFalse(report.getDiagnosticTasks().isEmpty());
    }

    @Test
    void testGetDescription() {
        DiagnosticReport report = new DiagnosticReport();
        assertTrue(report.getDescription().contains("pre-defined diagnostic tasks"));
    }

    @Test
    void testGetTotalDaysZeroWhenUnchecked() {
        DiagnosticReport report = new DiagnosticReport();
        assertEquals(0, report.getTotalDays());
    }

    @Test
    void testGetTotalDaysPositiveAfterCheck() {
        DiagnosticReport report = new DiagnosticReport();
        report.updateDiagnosticResult("Electrical", new ResultDTO(true, false, "OK"));
        assertTrue(report.getTotalDays() > 0);
    }

    @Test
    void testGetTotalCostZeroWhenUnchecked() {
        DiagnosticReport report = new DiagnosticReport();
        assertEquals(0, report.getTotalCost().getAmount());
    }

    @Test
    void testGetTotalCostPositiveAfterCheck() {
        DiagnosticReport report = new DiagnosticReport();
        report.updateDiagnosticResult("Electrical", new ResultDTO(true, false, "OK"));
        assertTrue(report.getTotalCost().getAmount() > 0);
    }

    @Test
    void testUpdateDiagnosticResult() {
        DiagnosticReport report = new DiagnosticReport();
        report.updateDiagnosticResult("Electrical", new ResultDTO(true, true, "Faulty wiring"));

        DiagnosticTask task = report.getDiagnosticTasks().get(0);
        assertTrue(task.getResult().getChecked());
        assertEquals("Faulty wiring", task.getResult().getDescription());
    }

    @Test
    void testUpdateDiagnosticResultNotFound() {
        DiagnosticReport report = new DiagnosticReport();
        assertThrows(IllegalArgumentException.class, () ->
                report.updateDiagnosticResult("NonExistent", new ResultDTO(true, true, "Test")));
    }

    @Test
    void testToDTO() {
        DiagnosticReport report = new DiagnosticReport();
        report.updateDiagnosticResult("Electrical", new ResultDTO(true, false, "OK"));

        DiagnosticReportDTO dto = report.toDTO();
        assertTrue(dto.days() > 0);
        assertTrue(dto.cost().getAmount() > 0);
        assertEquals(report.getDescription(), dto.description());
        assertEquals(report.getDiagnosticTasks().size(), dto.diagnosticTasks().size());
        assertTrue(dto.diagnosticTasks().get(0).result().checked());
    }

    @Test
    void testToDTODeepCopy() {
        DiagnosticReport report = new DiagnosticReport();
        DiagnosticReportDTO dto = report.toDTO();

        report.updateDiagnosticResult("Electrical", new ResultDTO(true, true, "Modified"));

        assertFalse(dto.diagnosticTasks().get(0).result().checked());
    }
}
