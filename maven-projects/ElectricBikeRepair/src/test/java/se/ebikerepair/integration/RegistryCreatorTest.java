package se.ebikerepair.integration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistryCreatorTest {

    @Test
    void testCreatesRegistries() {
        RegistryCreator creator = new RegistryCreator();
        assertNotNull(creator.getCustomerRegistry());
        assertNotNull(creator.getRepairOrderRegistry());
    }
}
