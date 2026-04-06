package se.ebikerepair.model;

import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.RepairTaskCollectionDTO;
import se.ebikerepair.integration.RepairTaskDTO;

import static org.junit.jupiter.api.Assertions.*;

class RepairTaskCollectionTest {

    @Test
    void testEmptyCollection() {
        RepairTaskCollection collection = new RepairTaskCollection();
        assertEquals(0, collection.getTotalDays());
        assertEquals(0, collection.getTotalCost().getAmount());
    }

    @Test
    void testAddRepairTask() {
        RepairTaskCollection collection = new RepairTaskCollection();
        collection.addRepairTask(new RepairTaskDTO("Task1", "Desc1", new Cost(300, "SEK"), 2));

        assertEquals(2, collection.getTotalDays());
        assertEquals(300, collection.getTotalCost().getAmount());
    }

    @Test
    void testAddMultipleRepairTasks() {
        RepairTaskCollection collection = new RepairTaskCollection();
        collection.addRepairTask(new RepairTaskDTO("Task1", "Desc1", new Cost(300, "SEK"), 1));
        collection.addRepairTask(new RepairTaskDTO("Task2", "Desc2", new Cost(200, "SEK"), 3));

        assertEquals(4, collection.getTotalDays());
        assertEquals(500, collection.getTotalCost().getAmount());
    }

    @Test
    void testToDTO() {
        RepairTaskCollection collection = new RepairTaskCollection();
        collection.addRepairTask(new RepairTaskDTO("Task1", "Desc1", new Cost(300, "SEK"), 1));
        collection.addRepairTask(new RepairTaskDTO("Task2", "Desc2", new Cost(200, "SEK"), 2));

        RepairTaskCollectionDTO dto = collection.toDTO();
        assertEquals(2, dto.repairTasks().size());
        assertEquals("Task1", dto.repairTasks().get(0).name());
        assertEquals("Task2", dto.repairTasks().get(1).name());
    }

    @Test
    void testToDTODeepCopy() {
        RepairTaskCollection collection = new RepairTaskCollection();
        collection.addRepairTask(new RepairTaskDTO("Task1", "Desc1", new Cost(300, "SEK"), 1));

        RepairTaskCollectionDTO dto = collection.toDTO();
        collection.addRepairTask(new RepairTaskDTO("Task2", "Desc2", new Cost(200, "SEK"), 2));

        assertEquals(1, dto.repairTasks().size());
    }
}
