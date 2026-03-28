package se.ebikerepair.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void testDefaultValues() {
        Result result = new Result();
        assertFalse(result.getChecked());
        assertFalse(result.getToBeRepaired());
        assertEquals("have not checked yet", result.getDescription());
    }

    @Test
    void testCustomValues() {
        Result result = new Result(true, true, "Needs repair");
        assertTrue(result.getChecked());
        assertTrue(result.getToBeRepaired());
        assertEquals("Needs repair", result.getDescription());
    }

    @Test
    void testSetters() {
        Result result = new Result();
        result.setChecked(true);
        result.setToBeRepaired(true);
        result.setDescription("Updated");
        assertTrue(result.getChecked());
        assertTrue(result.getToBeRepaired());
        assertEquals("Updated", result.getDescription());
    }

    @Test
    void testUpdateFromResultDTO() {
        Result result = new Result();
        ResultDTO dto = new ResultDTO(true, false, "Checked, no repair needed");
        result.update(dto);
        assertTrue(result.getChecked());
        assertFalse(result.getToBeRepaired());
        assertEquals("Checked, no repair needed", result.getDescription());
    }

    @Test
    void testToString() {
        Result result = new Result(true, true, "Broken");
        String str = result.toString();
        assertTrue(str.contains("true"));
        assertTrue(str.contains("Broken"));
    }
}
