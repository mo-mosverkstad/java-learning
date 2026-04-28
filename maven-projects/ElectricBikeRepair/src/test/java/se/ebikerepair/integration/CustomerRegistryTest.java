package se.ebikerepair.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRegistryTest {

    private CustomerRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new CustomerRegistry();
    }

    @AfterEach
    void cleanUp() {
        registry = null;
    }

    @Test
    void testGetExistingCustomer() {
        assertDoesNotThrow(() -> {
            CustomerDTO customer = registry.find("+46701234567");
            assertNotNull(customer);
            assertEquals("Erik Lindqvist", customer.name());
            assertEquals("erik.lindqvist@example.com", customer.emailAddress());
        });
    }

    @Test
    void testGetNonExistingCustomer() {
        assertThrows(NotFoundCustomerException.class, () -> registry.find("+46700000000"));
    }

    @Test
    void testCustomerHasBikes() {
        assertDoesNotThrow(() -> {
            CustomerDTO astrid = registry.find("+46707654321");
            assertNotNull(astrid);
            List<BikeDTO> bikes = astrid.bikes();
            assertNotNull(bikes);
            assertEquals(2, bikes.size());
        });
    }

    @Test
    void testBikeDetails() {
        assertDoesNotThrow(() -> {
            CustomerDTO erik = registry.find("+46701234567");
            BikeDTO bike = erik.bikes().get(0);
            assertEquals("Crescent", bike.brand());
            assertEquals("Elda", bike.model());
            assertEquals("CR-2024-001", bike.serialNumber());
        });
    }

    @Test
    void testSaveAndRetrieveCustomer() {
        BikeDTO bike = new BikeDTO("CAKE", "Åik", "CK-2024-200");
        CustomerDTO newCustomer = new CustomerDTO("Ingrid Svensson", "+46709999999", "ingrid.svensson@example.com", List.of(bike));
        registry.save(newCustomer);

        assertDoesNotThrow(() -> {
            CustomerDTO retrieved = registry.find("+46709999999");
            assertNotNull(retrieved);
            assertEquals("Ingrid Svensson", retrieved.name());
            assertEquals(1, retrieved.bikes().size());
            assertEquals("CAKE", retrieved.bikes().get(0).brand());
        });
    }
}
