package se.ebikerepair.integration;

import org.junit.jupiter.api.Test;

import se.ebikerepair.model.Cost;
import se.ebikerepair.model.DiagnosticReport;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiagnosticReportDTOTest {

    @Test
    void testFromEntity() {
        DiagnosticReportDTO dto = new DiagnosticReport().toDTO();
        assertNotNull(dto.diagnosticTasks());
        assertFalse(dto.diagnosticTasks().isEmpty());
    }

    @Test
    void testDaysIsZeroWhenUnchecked() {
        DiagnosticReportDTO dto = new DiagnosticReport().toDTO();
        assertEquals(0, dto.days());
    }

    @Test
    void testDaysIsPositiveAfterCheck() {
        DiagnosticTaskDTO checkedTask = new DiagnosticTaskDTO("Task", "Desc", new Cost(300, "SEK"), 2, new ResultDTO(true, false, "OK"));
        DiagnosticReportDTO dto = new DiagnosticReportDTO(2, new Cost(300, "SEK"), "Report", List.of(checkedTask));
        assertTrue(dto.days() > 0);
    }

    @Test
    void testCostIsZeroWhenUnchecked() {
        DiagnosticReportDTO dto = new DiagnosticReport().toDTO();
        assertNotNull(dto.cost());
        assertEquals(0, dto.cost().getAmount());
    }

    @Test
    void testCostIsPositiveAfterCheck() {
        DiagnosticTaskDTO checkedTask = new DiagnosticTaskDTO("Task", "Desc", new Cost(300, "SEK"), 2, new ResultDTO(true, false, "OK"));
        DiagnosticReportDTO dto = new DiagnosticReportDTO(2, new Cost(300, "SEK"), "Report", List.of(checkedTask));
        assertTrue(dto.cost().getAmount() > 0);
    }

    @Test
    void testDescriptionIsSet() {
        DiagnosticReportDTO dto = new DiagnosticReport().toDTO();
        assertNotNull(dto.description());
        assertTrue(dto.description().contains("pre-defined diagnostic tasks"));
        assertTrue(dto.description().contains("Only tasks marked with [X]"));
    }

    @Test
    void testTasksHaveNames() {
        DiagnosticReportDTO dto = new DiagnosticReport().toDTO();
        for (DiagnosticTaskDTO task : dto.diagnosticTasks()) {
            assertNotNull(task.name());
            assertFalse(task.name().isEmpty());
        }
    }

    @Test
    void testTasksHaveCosts() {
        DiagnosticReportDTO dto = new DiagnosticReport().toDTO();
        for (DiagnosticTaskDTO task : dto.diagnosticTasks()) {
            assertNotNull(task.cost());
            assertTrue(task.cost().getAmount() > 0);
        }
    }

    @Test
    void testTasksHaveDefaultResults() {
        DiagnosticReportDTO dto = new DiagnosticReport().toDTO();
        for (DiagnosticTaskDTO task : dto.diagnosticTasks()) {
            assertNotNull(task.result());
            assertFalse(task.result().checked());
            assertFalse(task.result().toBeRepaired());
        }
    }

    @Test
    void testCanonicalConstructor() {
        DiagnosticTaskDTO task = new DiagnosticTaskDTO("Task", "Desc", new Cost(100, "SEK"), 3);
        DiagnosticReportDTO dto = new DiagnosticReportDTO(3, new Cost(100, "SEK"), "Test report", List.of(task));

        assertEquals(3, dto.days());
        assertEquals(100, dto.cost().getAmount());
        assertEquals("Test report", dto.description());
        assertEquals(1, dto.diagnosticTasks().size());
    }

    @Test
    void testToString() {
        DiagnosticReportDTO dto = new DiagnosticReport().toDTO();
        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("Days:"));
        assertTrue(str.contains("Cost:"));
        assertTrue(str.contains("Description:"));
        assertTrue(str.contains("pre-defined diagnostic tasks"));
    }
}
