package se.ebikerepair.controller;

import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.RegistryCreator;
import se.ebikerepair.integration.Printer;

import static org.junit.jupiter.api.Assertions.*;

class ControllerCreatorTest {

    @Test
    void testCreatesControllers() {
        RegistryCreator registryCreator = new RegistryCreator();
        ControllerCreator creator = new ControllerCreator(registryCreator, new Printer());
        assertNotNull(creator.getReceptionistController());
        assertNotNull(creator.getTechnicianController());
    }
}
