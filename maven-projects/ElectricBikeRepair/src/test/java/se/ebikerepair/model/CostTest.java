package se.ebikerepair.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import se.ebikerepair.model.Cost;

import static org.junit.jupiter.api.Assertions.*;

public class CostTest {
    @Test
    void addTwo(){
        Cost cost1 = new Cost(120.0F, "SEK");
        Cost cost2 = new Cost(530.0F, "SEK");
        cost1.calculate(cost2);

        assertEquals(cost1.getAmount(), 120.0F+530.0F);
    }

    @Test
    void addNegative(){
        Cost cost1 = new Cost(120.0F, "SEK");
        Cost cost2 = new Cost(-32.0F, "SEK");
        cost1.calculate(cost2);

        assertEquals(cost1.getAmount(), 120.0F-32.0F);
    }

    @Test
    void addZero(){
        Cost cost1 = new Cost(120.0F, "SEK");
        Cost cost2 = new Cost(0.0F, "SEK");
        cost1.calculate(cost2);

        assertEquals(cost1.getAmount(), 120.0F);
    }

    @Test
    void addNull(){
        Cost cost1 = new Cost(120.0F, "SEK");
        Cost cost2 = null;
        assertThrows(NullPointerException.class, () -> cost1.calculate(cost2));
        assertEquals(cost1.getAmount(), 120.0F);
    }

    @Test
    void addLarge(){
        Cost cost1 = new Cost(120.0F, "SEK");
        Cost cost2 = new Cost(Float.MAX_VALUE, "SEK");
        cost1.calculate(cost2);

        assertEquals(cost1.getAmount(), Float.MAX_VALUE);
    }
}
