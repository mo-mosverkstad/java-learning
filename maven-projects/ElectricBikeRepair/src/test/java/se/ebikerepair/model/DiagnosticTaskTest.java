package se.ebikerepair.model;

import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.DiagnosticTaskDTO;
import se.ebikerepair.integration.ResultDTO;

import static org.junit.jupiter.api.Assertions.*;

class DiagnosticTaskTest {

    @Test
    void testConstructor() {
        DiagnosticTask task = new DiagnosticTask("Check brakes", "Inspect brake pads", new Cost(200, "SEK"), 1);
        assertEquals("Check brakes", task.getName());
        assertEquals("Inspect brake pads", task.getDescription());
        assertEquals(200, task.getCost().getAmount());
        assertEquals(1, task.getDays());
        assertFalse(task.getResult().getChecked());
    }

    @Test
    void testToDTO() {
        DiagnosticTask task = new DiagnosticTask("Check brakes", "Inspect brake pads", new Cost(200, "SEK"), 1);
        task.getResult().update(new ResultDTO(true, true, "Worn pads"));

        DiagnosticTaskDTO dto = task.toDTO();
        assertEquals("Check brakes", dto.name());
        assertEquals("Inspect brake pads", dto.description());
        assertEquals(200, dto.cost().getAmount());
        assertTrue(dto.result().checked());
        assertTrue(dto.result().toBeRepaired());
        assertEquals("Worn pads", dto.result().description());
    }

    @Test
    void testToDTODeepCopiesCost() {
        Cost cost = new Cost(300, "SEK");
        DiagnosticTask task = new DiagnosticTask("Task", "Desc", cost, 2);
        DiagnosticTaskDTO dto = task.toDTO();

        cost.calculate(new Cost(100, "SEK"));
        assertEquals(300, dto.cost().getAmount());
    }
}
