package se.ebikerepair.model;

import org.junit.jupiter.api.Test;

import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.integration.ProblemDTO;

import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {

    @Test
    void testDefaultValues() {
        Problem problem = new Problem();
        assertNull(problem.getDescription());
        assertNull(problem.getBrokenBike());
    }

    @Test
    void testCustomValues() {
        BikeDTO bike = new BikeDTO("Monark", "E-Karin", "MO-001");
        Problem problem = new Problem("Flat tire", bike);
        assertEquals("Flat tire", problem.getDescription());
        assertEquals(bike, problem.getBrokenBike());
    }

    @Test
    void testSetters() {
        Problem problem = new Problem();
        BikeDTO bike = new BikeDTO("Crescent", "Elda", "CR-001");
        problem.setDescription("Broken chain");
        problem.setBrokenBike(bike);
        assertEquals("Broken chain", problem.getDescription());
        assertEquals(bike, problem.getBrokenBike());
    }

    @Test
    void testUpdateFromProblemDTO() {
        Problem problem = new Problem();
        BikeDTO bike = new BikeDTO("Monark", "E-Karin", "MO-001");
        ProblemDTO dto = new ProblemDTO("Battery issue", bike);
        problem.update(dto);
        assertEquals("Battery issue", problem.getDescription());
        assertEquals(bike, problem.getBrokenBike());
    }

    @Test
    void testToStringWithBike() {
        BikeDTO bike = new BikeDTO("Monark", "E-Karin", "MO-001");
        Problem problem = new Problem("Flat tire", bike);
        ProblemDTO dto = problem.toDTO();
        String str = dto.toString();
        assertTrue(str.contains("Flat tire"));
        assertTrue(str.contains("Monark"));
        assertTrue(str.contains("MO-001"));
    }

    @Test
    void testToStringWithoutBike() {
        Problem problem = new Problem("Unknown issue", null);
        ProblemDTO dto = problem.toDTO();
        String str = dto.toString();
        assertTrue(str.contains("Unknown issue"));
        assertTrue(str.contains("no bike"));
    }

    @Test
    void testToDTO() {
        BikeDTO bike = new BikeDTO("Monark", "E-Karin", "MO-001");
        Problem problem = new Problem("Flat tire", bike);
        ProblemDTO dto = problem.toDTO();
        assertEquals("Flat tire", dto.description());
        assertEquals(bike, dto.brokenBike());
    }
}
