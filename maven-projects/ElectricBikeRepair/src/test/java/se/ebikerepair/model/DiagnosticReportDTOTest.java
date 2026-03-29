package se.ebikerepair.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiagnosticReportDTOTest {

    @Test
    void testConstructorLoadsTasks() {
        DiagnosticReportDTO report = new DiagnosticReportDTO();
        assertNotNull(report.getDiagnosticTasks());
        assertFalse(report.getDiagnosticTasks().isEmpty());
    }

    @Test
    void testDateIsSet() {
        DiagnosticReportDTO report = new DiagnosticReportDTO();
        assertNotNull(report.getDate());
    }

    @Test
    void testTasksHaveNames() {
        DiagnosticReportDTO report = new DiagnosticReportDTO();
        for (DiagnosticTaskDTO task : report.getDiagnosticTasks()) {
            assertNotNull(task.getName());
            assertFalse(task.getName().isEmpty());
        }
    }

    @Test
    void testTasksHaveCosts() {
        DiagnosticReportDTO report = new DiagnosticReportDTO();
        for (DiagnosticTaskDTO task : report.getDiagnosticTasks()) {
            assertNotNull(task.getCost());
            assertTrue(task.getCost().getAmount() > 0);
        }
    }

    @Test
    void testTasksHaveDefaultResults() {
        DiagnosticReportDTO report = new DiagnosticReportDTO();
        for (DiagnosticTaskDTO task : report.getDiagnosticTasks()) {
            assertNotNull(task.getResult());
            assertFalse(task.getResult().getChecked());
            assertFalse(task.getResult().getToBeRepaired());
        }
    }

    @Test
    void testDeepCopyIndependence() {
        DiagnosticReportDTO report1 = new DiagnosticReportDTO();
        DiagnosticReportDTO report2 = new DiagnosticReportDTO();

        report1.getDiagnosticTasks().get(0).getResult().update(new ResultDTO(true, true, "Modified"));

        assertFalse(report2.getDiagnosticTasks().get(0).getResult().getChecked());
        assertEquals("have not checked yet", report2.getDiagnosticTasks().get(0).getResult().getDescription());
    }

    @Test
    void testToString() {
        DiagnosticReportDTO report = new DiagnosticReportDTO();
        String str = report.toString();
        assertNotNull(str);
        assertTrue(str.contains("Date:"));
    }
}
