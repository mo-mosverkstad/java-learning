package se.ebikerepair.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRegistryTest {

    private CustomerRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new CustomerRegistry();
    }

    @Test
    void testGetExistingCustomer() {
        CustomerDTO customer = registry.find("+46701234567");
        assertNotNull(customer);
        assertEquals("Erik Lindqvist", customer.getName());
        assertEquals("erik.lindqvist@example.com", customer.getEmailAddress());
    }

    @Test
    void testGetNonExistingCustomer() {
        assertNull(registry.find("+46700000000"));
    }

    @Test
    void testCustomerHasBikes() {
        CustomerDTO astrid = registry.find("+46707654321");
        assertNotNull(astrid);
        List<BikeDTO> bikes = astrid.getBikes();
        assertNotNull(bikes);
        assertEquals(2, bikes.size());
    }

    @Test
    void testBikeDetails() {
        CustomerDTO erik = registry.find("+46701234567");
        BikeDTO bike = erik.getBikes().get(0);
        assertEquals("Crescent", bike.getBrand());
        assertEquals("Elda", bike.getModel());
        assertEquals("CR-2024-001", bike.getSerialNumber());
    }

    @Test
    void testSaveAndRetrieveCustomer() {
        BikeDTO bike = new BikeDTO("CAKE", "Åik", "CK-2024-200");
        CustomerDTO newCustomer = new CustomerDTO("Ingrid Svensson", "+46709999999", "ingrid.svensson@example.com", List.of(bike));
        registry.save(newCustomer);

        CustomerDTO retrieved = registry.find("+46709999999");
        assertNotNull(retrieved);
        assertEquals("Ingrid Svensson", retrieved.getName());
        assertEquals(1, retrieved.getBikes().size());
        assertEquals("CAKE", retrieved.getBikes().get(0).getBrand());
    }
}
