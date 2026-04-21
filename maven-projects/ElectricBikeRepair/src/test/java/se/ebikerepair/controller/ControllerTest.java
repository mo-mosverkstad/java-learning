package se.ebikerepair.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.integration.CustomerDTO;
import se.ebikerepair.integration.RepairOrderRegistry;
import se.ebikerepair.model.RepairOrder;
import se.ebikerepair.integration.RepairOrderDTO;

import java.util.List;

import se.ebikerepair.util.InvalidTelephoneNumberException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;
    private RepairOrderRegistry registry;
    private CustomerDTO customer;
    private BikeDTO bike;

    @BeforeEach
    void setUp() {
        registry = new RepairOrderRegistry();
        controller = new Controller(registry);
        bike = new BikeDTO("Monark", "E-Karin", "MO-001");
        customer = new CustomerDTO("Astrid", "+46707654321", "astrid@example.com", List.of(bike));
    }

    @Test
    void testFindRepairOrderReturnsNewest() throws InterruptedException {
        RepairOrder order1 = new RepairOrder(customer);
        order1.getProblem().setDescription("First issue");
        order1.getProblem().setBrokenBike(bike);
        registry.save(order1);

        Thread.sleep(50);

        RepairOrder order2 = new RepairOrder(customer);
        order2.getProblem().setDescription("Second issue");
        order2.getProblem().setBrokenBike(bike);
        registry.save(order2);

        assertDoesNotThrow(() -> {
            RepairOrderDTO dto = controller.findRepairOrder("+46707654321");
            assertEquals(order2.getId(), dto.id());
        });
    }

    @Test
    void testFindRepairOrderNoOrders() {
        assertThrows(IllegalArgumentException.class, () ->
                controller.findRepairOrder("+46707654321"));
    }

    @Test
    void testFindRepairOrderInvalidPhone() {
        assertThrows(InvalidTelephoneNumberException.class, () ->
                controller.findRepairOrder("abc"));
    }

    @Test
    void testFindRepairOrderSingleOrder() {
        RepairOrder order = new RepairOrder(customer);
        order.getProblem().setDescription("Broken chain");
        order.getProblem().setBrokenBike(bike);
        registry.save(order);

        assertDoesNotThrow(() -> {
            RepairOrderDTO dto = controller.findRepairOrder("+46707654321");
            assertNotNull(dto);
            assertEquals(order.getId(), dto.id());
            assertEquals("Astrid", dto.customerDTO().name());
        });
    }
}
