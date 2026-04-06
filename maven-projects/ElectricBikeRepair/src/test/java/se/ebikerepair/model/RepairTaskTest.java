package se.ebikerepair.model;

import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.RepairTaskDTO;

import static org.junit.jupiter.api.Assertions.*;

class RepairTaskTest {

    @Test
    void testConstructor() {
        RepairTask task = new RepairTask("Replace chain", "Install new chain", new Cost(500, "SEK"), 2);
        assertEquals("Replace chain", task.getName());
        assertEquals("Install new chain", task.getDescription());
        assertEquals(500, task.getCost().getAmount());
        assertEquals(ProposedRepairTaskState.Incompleted, task.getState());
        assertEquals(2, task.getEstimatedDays());
    }

    @Test
    void testToDTO() {
        RepairTask task = new RepairTask("Replace chain", "Install new chain", new Cost(500, "SEK"), 2);
        RepairTaskDTO dto = task.toDTO();

        assertEquals("Replace chain", dto.name());
        assertEquals("Install new chain", dto.description());
        assertEquals(500, dto.cost().getAmount());
        assertEquals(ProposedRepairTaskState.Incompleted, dto.state());
        assertEquals(2, dto.estimatedDays());
    }

    @Test
    void testToDTODeepCopiesCost() {
        Cost cost = new Cost(500, "SEK");
        RepairTask task = new RepairTask("Task", "Desc", cost, 1);
        RepairTaskDTO dto = task.toDTO();

        cost.calculate(new Cost(100, "SEK"));
        assertEquals(500, dto.cost().getAmount());
    }

    @Test
    void testConstructorFromDTO() {
        RepairTaskDTO dto = new RepairTaskDTO("Fix brakes", "Replace pads", new Cost(300, "SEK"), 1);
        RepairTask task = new RepairTask(dto);

        assertEquals("Fix brakes", task.getName());
        assertEquals("Replace pads", task.getDescription());
        assertEquals(300, task.getCost().getAmount());
        assertEquals(1, task.getEstimatedDays());
    }
}
