package se.ebikerepair.model;

import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.integration.RepairTaskDTO;
import se.ebikerepair.integration.ResultDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepairOrderTest {

    @Test
    void testBasic() {
        RepairOrder order = createOrder();

        assertNotNull(order.getCustomerDTO());
        assertEquals("Flat tire", order.getProblem().getDescription());
        assertNotNull(order.getCreatedDate());
        assertNotNull(order.getTotalCost());
        assertNotNull(order.getId());
        assertEquals(RepairOrderState.NewlyCreated, order.getRepairOrderState());
    }

    @Test
    void testToDTO() {
        RepairOrder order = createOrder();
        RepairOrderDTO dto = order.toDTO();

        assertEquals(order.getCustomerDTO(), dto.customerDTO());
        assertEquals(order.getProblem().getDescription(), dto.problemDTO().description());
        assertEquals(order.getProblem().getBrokenBike(), dto.problemDTO().brokenBike());
        assertEquals(order.getCreatedDate(), dto.createdDate());
        assertEquals(order.getRepairOrderState(), dto.repairOrderState());
        assertEquals(order.getId(), dto.id());
        assertNotNull(dto.diagnosticReport());
        assertNotNull(dto.repairTaskCollection());
    }

    @Test
    void testToDTODeepCopy() {
        RepairOrder order = createOrder();
        RepairOrderDTO dto = order.toDTO();

        assertNotSame(order.getCreatedDate(), dto.createdDate());
        assertEquals(order.getCreatedDate(), dto.createdDate());
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
    void testAddRepairTask() {
        RepairOrder order = createOrder();
        order.getRepairTaskCollection().addRepairTask(new RepairTaskDTO("Fix chain", "Replace chain", new Cost(500, "SEK"), 2));

        assertTrue(order.getTotalCost().getAmount() > 0);
        assertEquals(500.0F, order.getPricingResult().getBaseCost().getAmount());
        assertNotNull(order.getEstimatedCompleteDate());
    }

    @Test
    void testAddMultipleRepairTasks() {
        RepairOrder order = createOrder();
        order.getRepairTaskCollection().addRepairTask(new RepairTaskDTO("Task1", "Desc1", new Cost(300, "SEK"), 1));
        order.getRepairTaskCollection().addRepairTask(new RepairTaskDTO("Task2", "Desc2", new Cost(200, "SEK"), 3));

        assertEquals(500.0F, order.getPricingResult().getBaseCost().getAmount());
        assertTrue(order.getTotalCost().getAmount() > 0);
        assertTrue(order.getTotalCost().getAmount() <= 500.0F);
    }

    @Test
    void testUpdateDiagnosticResult() {
        RepairOrder order = createOrder();
        ResultDTO result = new ResultDTO(true, true, "Needs repair");
        order.getDiagnosticReport().updateDiagnosticResult("Electrical", result);

        DiagnosticTask task = order.getDiagnosticReport().getDiagnosticTasks().get(0);
        assertTrue(task.getResult().getChecked());
        assertTrue(task.getResult().getToBeRepaired());
        assertEquals("Needs repair", task.getResult().getDescription());
    }

    @Test
    void testToString() {
        RepairOrder order = createOrder();
        String str = order.toString();
        assertNotNull(str);
        assertTrue(str.contains("Repair Order"));
        assertTrue(str.contains(order.getId()));
        assertTrue(str.contains("Test"));
        assertTrue(str.contains("+46701234567"));
    }

    private RepairOrder createOrder() {
        BikeDTO bike = new BikeDTO("Brand", "Model", "SN");
        CustomerDTO customer = new CustomerDTO("Test", "+46701234567", "test@example.com", List.of(bike));
        RepairOrder order = new RepairOrder(customer);
        order.getProblem().setDescription("Flat tire");
        order.getProblem().setBrokenBike(bike);
        return order;
    }
}
