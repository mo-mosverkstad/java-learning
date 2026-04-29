package se.ebikerepair.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.ProblemDTO;
import se.ebikerepair.integration.MembershipDTO;
import se.ebikerepair.model.RepairOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepairOrderRegistryTest {
    private RepairOrderRegistry registry;
    private RepairOrder order1;
    private RepairOrder order2;

    @BeforeEach
    void setUp() {
        registry = new RepairOrderRegistry();
        BikeDTO bike1 = new BikeDTO("Monark", "E-Karin", "MO-001");
        CustomerDTO customer1 = new CustomerDTO("Alice", "+46701234567", "alice@example.com", new MembershipDTO(false, 0), List.of(bike1));
        order1 = new RepairOrder(customer1);
        order1.getProblem().setDescription("Flat tire");
        order1.getProblem().setBrokenBike(bike1);
        registry.save(order1);

        BikeDTO bike2 = new BikeDTO("Crescent", "Elda", "CR-001");
        CustomerDTO customer2 = new CustomerDTO("Bob", "+46707654321", "bob@example.com", new MembershipDTO(false, 0), List.of(bike2));
        order2 = new RepairOrder(customer2);
        order2.getProblem().setDescription("Broken chain");
        order2.getProblem().setBrokenBike(bike2);
        registry.save(order2);
    }

    @Test
    void testFindByRepairOrderId() throws NoExistedRepairOrderException {
        RepairOrder found = registry.findByRepairOrderId(order1.getId());
        assertNotNull(found);
        assertEquals(order1.getId(), found.getId());
    }

    @Test
    void testFindByRepairOrderIdNotFound() {
        assertThrows(NoExistedRepairOrderException.class, () -> registry.findByRepairOrderId("nonexistent-id"));
    }

    @Test
    void testFindByTelephoneNumber() {
        List<RepairOrder> orders = registry.findRepairOrdersByTelephoneNumber("+46701234567");
        assertEquals(1, orders.size());
        assertEquals(order1.getId(), orders.get(0).getId());
    }

    @Test
    void testFindByTelephoneNumberNotFound() {
        List<RepairOrder> orders = registry.findRepairOrdersByTelephoneNumber("+46700000000");
        assertTrue(orders.isEmpty());
    }

    @Test
    void testSaveUpdatesExisting() throws NoExistedRepairOrderException {
        order1.accept();
        registry.save(order1);
        RepairOrder found = registry.findByRepairOrderId(order1.getId());
        assertEquals(se.ebikerepair.model.RepairOrderState.Accepted, found.getRepairOrderState());
    }
}
