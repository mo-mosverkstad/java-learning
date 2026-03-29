package se.ebikerepair.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.model.ProblemDTO;
import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.model.RepairOrderDTO;
import se.ebikerepair.model.RepairOrderState;
import se.ebikerepair.model.Cost;
import se.ebikerepair.model.DiagnosticReportDTO;
import se.ebikerepair.model.ProposedRepairTaskDTO;
import se.ebikerepair.model.Result;
import se.ebikerepair.model.ResultDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepairOrderTest {

    @Test
    void testBasic() {
        List<BikeDTO> bikes = new ArrayList<>();
        BikeDTO brokenBike = new BikeDTO("Brand", "Model", "SerialNumber");
        bikes.add(brokenBike);
        CustomerDTO customer = new CustomerDTO("Alice", "0721312125", "alice@example.com", bikes);
        ProblemDTO problem = new ProblemDTO("Flat tire", brokenBike);

        RepairOrder order = new RepairOrder(customer, problem);

        assertEquals(customer, order.getCustomerDTO());
        // assertEquals(brokenBike, order.getBikeDTO());
        assertEquals(problem, order.getProblemDTO());

        assertNotNull(order.getCreatedDate());
        assertNotNull(order.getTotalCost());
        assertNotNull(order.getId());

        assertEquals(RepairOrderState.NewlyCreated, order.getRepairOrderState());
        assertNull(order.getEstimatedCompleteDate());
    }
    
    @Test
    void testToDTO() {
        List<BikeDTO> bikes = new ArrayList<>();
        BikeDTO brokenBike = new BikeDTO("Brand", "Model", "SerialNumber");
        bikes.add(brokenBike);
        CustomerDTO customer = new CustomerDTO("Bob", "0722382175", "bob@example.com", bikes);
        ProblemDTO problem = new ProblemDTO("Broken chain", brokenBike);

        RepairOrder order = new RepairOrder(customer, problem);

        RepairOrderDTO dto = order.toDTO();

        assertEquals(order.getCustomerDTO(), dto.customerDTO());
        assertEquals(order.getProblemDTO(), dto.problemDTO());
        assertEquals(order.getCreatedDate(), dto.createdDate());
        assertEquals(order.getTotalCost(), dto.totalCost());
        assertEquals(order.getRepairOrderState(), dto.repairOrderState());
        assertEquals(order.getId(), dto.id());
    }

    @Test
    void testAccept() {
        RepairOrder order = createOrder();
        order.accept();
        assertEquals(RepairOrderState.Accepted, order.getRepairOrderState());
    }

    @Test
    void testReject() {
        RepairOrder order = createOrder();
        order.reject();
        assertEquals(RepairOrderState.Rejected, order.getRepairOrderState());
    }

    @Test
    void testAddProposedRepairTask() {
        RepairOrder order = createOrder();
        ProposedRepairTaskDTO task = new ProposedRepairTaskDTO("Fix chain", "Replace chain", new Cost(500, "SEK"), 2);
        order.addProposedRepairTask(task);

        assertEquals(500.0F, order.getTotalCost().getAmount());
        assertNotNull(order.getEstimatedCompleteDate());
    }

    @Test
    void testAddMultipleProposedRepairTasks() {
        RepairOrder order = createOrder();
        order.addProposedRepairTask(new ProposedRepairTaskDTO("Task1", "Desc1", new Cost(300, "SEK"), 1));
        order.addProposedRepairTask(new ProposedRepairTaskDTO("Task2", "Desc2", new Cost(200, "SEK"), 3));

        assertEquals(500.0F, order.getTotalCost().getAmount());
    }

    @Test
    void testUpdateDiagnosticResult() {
        RepairOrder order = createOrder();
        ResultDTO result = new ResultDTO(true, true, "Needs repair");
        order.updateDiagnosticResult(0, result);

        Result taskResult = order.getDiagnosticReport().getDiagnosticTasks().get(0).getResult();
        assertTrue(taskResult.getChecked());
        assertTrue(taskResult.getToBeRepaired());
        assertEquals("Needs repair", taskResult.getDescription());
    }

    @Test
    void testCalculateCostByDiagnosticTask() {
        RepairOrder order = createOrder();
        float taskCost = order.getDiagnosticReport().getDiagnosticTasks().get(0).getCost().getAmount();
        order.calculateCostByDiagnosticTask(0);

        assertEquals(taskCost, order.getTotalCost().getAmount());
    }

    @Test
    void testSetDiagnosticReport() {
        RepairOrder order = createOrder();
        DiagnosticReportDTO newReport = new DiagnosticReportDTO();
        order.setDiagnosticReport(newReport);
        assertEquals(newReport, order.getDiagnosticReport());
    }

    @Test
    void testToString() {
        RepairOrder order = createOrder();
        String str = order.toString();
        assertNotNull(str);
        assertTrue(str.contains("Repair Order"));
        assertTrue(str.contains(order.getId()));
        assertTrue(str.contains("Customer:"));
        assertTrue(str.contains("- Name:"));
        assertTrue(str.contains("Test"));
        assertTrue(str.contains("+46701234567"));
        assertTrue(str.contains("test@example.com"));
    }

    private RepairOrder createOrder() {
        BikeDTO bike = new BikeDTO("Brand", "Model", "SN");
        CustomerDTO customer = new CustomerDTO("Test", "+46701234567", "test@example.com", List.of(bike));
        return new RepairOrder(customer, new ProblemDTO("Issue", bike));
    }

    /*
    @Test
    void testNullCustomer() {
        BikeDTO bike = new BikeDTO("Brand", "Model", "SN");
        ProblemDTO problem = new ProblemDTO("Issue", bike);

        assertThrows(NullPointerException.class, () -> {
            new RepairOrder(null, problem);
        });
    }
         */
}
