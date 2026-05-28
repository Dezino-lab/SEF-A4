package com.sefa4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BusTest {
    @Test
    public void validBusIdShouldBeAccepted() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");

        assertEquals("12345678", bus.getId());

    }

    @Test
    public void busIdWithLessThan8DigitsShouldBeRejected() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Bus("1234567", 40, 80.0, "Diesel");
        });
    }

    @Test
    public void busIdWithMoreThan8DigitsShouldBeRejected() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Bus("123456789", 40, 80.0, "Diesel");
        });
    }

    @Test
    public void busIdWithNonDigitCharactersShouldBeRejected() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Bus("1234ABCD", 40, 80.0, "Diesel");
        });
    }

    @Test
    public void capacityCanDecreaseDuringUpdate() {
        Bus bus = new Bus("12345678", 50, 75.0, "Diesel");
        boolean result = bus.updateCapacity(40);
        assertTrue(result);
        assertEquals(40, bus.getCapacity());
    }

    @Test
    public void capacityCannotIncreaseDuringUpdate() {
        Bus bus = new Bus("12345678", 40, 75.0, "Diesel");
        boolean result = bus.updateCapacity(50);
        assertFalse(result);
        assertEquals(40, bus.getCapacity());
    }
}
