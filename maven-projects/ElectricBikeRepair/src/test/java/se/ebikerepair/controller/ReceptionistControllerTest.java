package se.ebikerepair.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.model.ProblemDTO;
import se.ebikerepair.model.RepairOrderDTO;
import se.ebikerepair.model.RepairOrderState;
import se.ebikerepair.printer.Printer;

import static org.junit.jupiter.api.Assertions.*;

class ReceptionistControllerTest {
    private ReceptionistController controller;
    private RegistryCreator registryCreator;

    @BeforeEach
    void setUp() {
        registryCreator = new RegistryCreator();
        controller = new ReceptionistController(registryCreator, new Printer());
    }

    @Test
    void testSearchExistingCustomer() {
        CustomerDTO customer = controller.searchCustomer("0707654321");
        assertNotNull(customer);
        assertEquals("Astrid Johansson", customer.getName());
    }

    @Test
    void testSearchNonExistingCustomer() {
        assertThrows(IllegalArgumentException.class, () -> controller.searchCustomer("0700000000"));
    }

    @Test
    void testSearchInvalidPhoneNumber() {
        assertThrows(IllegalArgumentException.class, () -> controller.searchCustomer("abc"));
    }

    @Test
    void testCreateRepairOrder() {
        CustomerDTO customer = controller.searchCustomer("0707654321");
        BikeDTO bike = customer.getBikes().get(0);
        String id = controller.createRepairOrder("0707654321", new ProblemDTO("Broken chain", bike));
        assertNotNull(id);
    }

    @Test
    void testCreateRepairOrderCustomerNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
                controller.createRepairOrder("0700000000", new ProblemDTO("Issue", null)));
    }

    @Test
    void testAcceptOrder() {
        CustomerDTO customer = controller.searchCustomer("0707654321");
        BikeDTO bike = customer.getBikes().get(0);
        String id = controller.createRepairOrder("0707654321", new ProblemDTO("Broken chain", bike));

        controller.acceptOrder(id);

        RepairOrderDTO dto = controller.findRepairOrder("0707654321");
        assertEquals(RepairOrderState.Accepted, dto.repairOrderState());
    }

    @Test
    void testAcceptOrderNotFound() {
        assertThrows(IllegalStateException.class, () -> controller.acceptOrder("nonexistent-id"));
    }

    @Test
    void testRejectOrder() {
        CustomerDTO customer = controller.searchCustomer("0707654321");
        BikeDTO bike = customer.getBikes().get(0);
        String id = controller.createRepairOrder("0707654321", new ProblemDTO("Broken chain", bike));

        controller.rejectOrder(id);

        RepairOrderDTO dto = controller.findRepairOrder("0707654321");
        assertEquals(RepairOrderState.Rejected, dto.repairOrderState());
    }
}
