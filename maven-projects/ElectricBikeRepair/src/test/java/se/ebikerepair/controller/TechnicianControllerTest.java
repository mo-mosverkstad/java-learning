package se.ebikerepair.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.model.*;
import se.ebikerepair.integration.Printer;

import static org.junit.jupiter.api.Assertions.*;

class TechnicianControllerTest {
    private TechnicianController techController;
    private ReceptionistController recController;
    private String repairOrderId;

    @BeforeEach
    void setUp() {
        RegistryCreator registryCreator = new RegistryCreator();
        recController = new ReceptionistController(registryCreator, new Printer());
        techController = new TechnicianController(registryCreator);

        CustomerDTO customer = recController.searchCustomer("0707654321");
        BikeDTO bike = customer.getBikes().get(0);
        repairOrderId = recController.createRepairOrder("0707654321", new ProblemDTO("Broken chain", bike));
    }

    @Test
    void testCreateDiagnosticReport() {
        DiagnosticReportDTO report = new DiagnosticReportDTO();
        techController.createDiagnosticReport(repairOrderId, report);

        RepairOrderDTO dto = techController.findRepairOrder("0707654321");
        assertNotNull(dto.diagnosticReport());
    }

    @Test
    void testCreateDiagnosticReportOrderNotFound() {
        assertThrows(IllegalStateException.class, () ->
                techController.createDiagnosticReport("nonexistent", new DiagnosticReportDTO()));
    }

    @Test
    void testAddDiagnosticResult() {
        DiagnosticReportDTO report = new DiagnosticReportDTO();
        techController.createDiagnosticReport(repairOrderId, report);

        ResultDTO result = new ResultDTO(true, true, "Needs repair");
        techController.addDiagnosticResult(repairOrderId, 0, result);

        RepairOrderDTO dto = techController.findRepairOrder("0707654321");
        Result taskResult = dto.diagnosticReport().getDiagnosticTasks().get(0).getResult();
        assertTrue(taskResult.getChecked());
        assertTrue(taskResult.getToBeRepaired());
        assertEquals("Needs repair", taskResult.getDescription());
    }

    @Test
    void testAddProposedRepairTask() {
        ProposedRepairTaskDTO task = new ProposedRepairTaskDTO("Replace chain", "Replace worn chain", new Cost(500, "SEK"), 2);
        techController.addProposedRepairTask(repairOrderId, task);

        RepairOrderDTO dto = techController.findRepairOrder("0707654321");
        assertEquals(1, dto.proposedRepairTasks().size());
        assertEquals("Replace chain", dto.proposedRepairTasks().get(0).getName());
    }

    @Test
    void testAddProposedRepairTaskOrderNotFound() {
        assertThrows(IllegalStateException.class, () ->
                techController.addProposedRepairTask("nonexistent", new ProposedRepairTaskDTO("Fix", "Fix it", new Cost(100, "SEK"), 1)));
    }

    @Test
    void testAddDiagnosticResultInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () ->
                techController.addDiagnosticResult(repairOrderId, 999, new ResultDTO(true, true, "Test")));
    }

    @Test
    void testAddDiagnosticResultOrderNotFound() {
        assertThrows(IllegalStateException.class, () ->
                techController.addDiagnosticResult("nonexistent", 0, new ResultDTO(true, true, "Test")));
    }

    @Test
    void testAddMultipleProposedRepairTasks() {
        ProposedRepairTaskDTO task1 = new ProposedRepairTaskDTO("Task1", "Desc1", new Cost(300, "SEK"), 1);
        ProposedRepairTaskDTO task2 = new ProposedRepairTaskDTO("Task2", "Desc2", new Cost(200, "SEK"), 2);
        techController.addProposedRepairTask(repairOrderId, task1);
        techController.addProposedRepairTask(repairOrderId, task2);

        RepairOrderDTO dto = techController.findRepairOrder("0707654321");
        assertEquals(2, dto.proposedRepairTasks().size());
        assertEquals(500.0F, dto.totalCost().getAmount());
    }

    @Test
    void testFindRepairOrderByName() {
        RepairOrderDTO dto = techController.findRepairOrder("0707654321");
        int index = dto.findTaskIndexByName("Electrical");
        assertTrue(index >= 0);
    }

    @Test
    void testFindRepairOrderByNameNotFound() {
        RepairOrderDTO dto = techController.findRepairOrder("0707654321");
        assertThrows(IllegalArgumentException.class, () -> dto.findTaskIndexByName("NonExistentTask"));
    }
}
