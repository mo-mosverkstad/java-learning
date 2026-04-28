package se.ebikerepair.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.integration.ProblemDTO;
import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.integration.RepairTaskDTO;
import se.ebikerepair.integration.ResultDTO;
import se.ebikerepair.integration.Printer;
import se.ebikerepair.model.Cost;
import se.ebikerepair.util.InvalidTelephoneNumberException;
import se.ebikerepair.integration.NotFoundCustomerException;
import se.ebikerepair.integration.NoExistedRepairOrderException;

import static org.junit.jupiter.api.Assertions.*;

class TechnicianControllerTest {
    private TechnicianController techController;
    private ReceptionistController recController;
    private String repairOrderId;

    @BeforeEach
    void setUp() throws NotFoundCustomerException, InvalidTelephoneNumberException, FailedOperationException {
        RegistryCreator registryCreator = new RegistryCreator();
        recController = new ReceptionistController(registryCreator, new Printer());
        techController = new TechnicianController(registryCreator);

        CustomerDTO customer = recController.searchCustomer("0707654321");
        BikeDTO bike = customer.bikes().get(0);
        repairOrderId = recController.createRepairOrder("0707654321", new ProblemDTO("Broken chain", bike));
    }

    @Test
    void testAddDiagnosticResult() {
        assertDoesNotThrow(() -> {
            ResultDTO result = new ResultDTO(true, true, "Needs repair");
            techController.addDiagnosticResult(repairOrderId, "Electrical", result);

            RepairOrderDTO dto = techController.findRepairOrder("0707654321");
            assertNotNull(dto.diagnosticReport());
        });
    }

    @Test
    void testAddDiagnosticResultOrderNotFound() {
        assertThrows(NoExistedRepairOrderException.class, () ->
                techController.addDiagnosticResult("nonexistent", "Electrical", new ResultDTO(true, true, "Test")));
    }

    @Test
    void testAddDiagnosticResultTaskNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
                techController.addDiagnosticResult(repairOrderId, "NonExistentTask", new ResultDTO(true, true, "Test")));
    }

    @Test
    void testAddRepairTask() {
        assertDoesNotThrow(() -> {
            RepairTaskDTO task = new RepairTaskDTO("Replace chain", "Replace worn chain", new Cost(500, "SEK"), 2);
            techController.addRepairTask(repairOrderId, task);

            RepairOrderDTO dto = techController.findRepairOrder("0707654321");
            assertEquals(1, dto.repairTaskCollection().repairTasks().size());
            assertEquals("Replace chain", dto.repairTaskCollection().repairTasks().get(0).name());
        });
    }

    @Test
    void testAddRepairTaskOrderNotFound() {
        assertThrows(NoExistedRepairOrderException.class, () ->
                techController.addRepairTask("nonexistent", new RepairTaskDTO("Fix", "Fix it", new Cost(100, "SEK"), 1)));
    }

    @Test
    void testAddMultipleRepairTasks() {
        assertDoesNotThrow(() -> {
            RepairTaskDTO task1 = new RepairTaskDTO("Task1", "Desc1", new Cost(300, "SEK"), 1);
            RepairTaskDTO task2 = new RepairTaskDTO("Task2", "Desc2", new Cost(200, "SEK"), 2);
            techController.addRepairTask(repairOrderId, task1);
            techController.addRepairTask(repairOrderId, task2);

            RepairOrderDTO dto = techController.findRepairOrder("0707654321");
            assertEquals(2, dto.repairTaskCollection().repairTasks().size());
        });
    }
}
