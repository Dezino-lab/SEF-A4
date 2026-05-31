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
        Bus bus = new Bus("12345678", 50, 75.0, "Diesel");
        boolean result = bus.updateCapacity(60);

        assertFalse(result);
        assertEquals(50, bus.getCapacity());
    }

    @Test
    public void capacityCanRemainSameDuringUpdate() {
        Bus bus = new Bus("12345678", 50, 75.0, "Diesel");
        boolean result = bus.updateCapacity(50);

        assertTrue(result);
        assertEquals(50, bus.getCapacity());
    }

    @Test
    public void driverAgeIs45CapIs60ShouldBeAccepted() {
        Bus bus = new Bus("12345678", 60, 100.0, "Diesel");
        Driver driver = new Driver("D0000001", "John", 10, "PublicTransport", "123 St", "01-01-1981");

        assertTrue(bus.canAssignDriver(driver));
    }

    @Test
    public void driverAgeOver50CapOver50ShouldBeRejected() {
        Bus bus = new Bus("12345678", 60, 100.0, "Diesel");
        Driver driver = new Driver("D0000001", "John", 10, "PublicTransport", "123 St", "01-01-1971");

        assertFalse(bus.canAssignDriver(driver));
    }

    @Test
    public void driverAgeIs50CapIs50ShouldBeAccepted() {
        Bus bus = new Bus("12345678", 50, 100.0, "Diesel");
        Driver driver = new Driver("D0000001", "John", 10, "PublicTransport", "123 St", "01-01-1976");

        assertTrue(bus.canAssignDriver(driver));
    }

    @Test 
    public void electricBusOver5YearsXPShouldBeAccepted() {
        Bus bus = new Bus("12345678", 50, 100.0, "Electric");
        Driver driver = new Driver("D0000001", "John", 7, "PublicTransport", "123 St", "01-01-1990");
        
        assertTrue(bus.canAssignDriver(driver));
    }

    @Test 
    public void electricBus2yearsXPShouldBeRejected() {
        Bus bus = new Bus("12345678", 50, 100.0, "Electric");
        Driver driver = new Driver("D0000001", "John", 2, "PublicTransport", "123 St", "01-01-1990");
        
        assertFalse(bus.canAssignDriver(driver));
    }

    @Test 
    public void electricBusMinYearsXPShouldBeAccepted() {
        Bus bus = new Bus("12345678", 50, 100.0, "Electric");
        Driver driver = new Driver("D0000001", "John", 5, "PublicTransport", "123 St", "01-01-1990");
        
        assertTrue(bus.canAssignDriver(driver));
    }

    @Test
    public void canDriveElectricBusShouldBeAccepted() {
        Bus bus = new Bus("12345678", 50, 100.0, "Electric");
        Driver driver = new Driver("D0000001", "John", 5, "PublicTransport", "123 St", "01-01-1990");
        assertTrue(bus.canAssignDriver(driver));
    }

    @Test
    public void canDriveHybridBusShouldBeAccepted() {
        Bus bus = new Bus("12345678", 50, 100.0, "Hybrid");
        Driver driver = new Driver("D0000001", "John", 5, "Heavy", "123 St", "01-01-1990");
        assertTrue(bus.canAssignDriver(driver));
    }
    
    @Test
    public void canDriveHybridBusShouldBeRejected() {
        Bus bus = new Bus("12345678", 50, 100.0, "Hybrid");
        Driver driver = new Driver("D0000001", "John", 5, "Standard", "123 St", "01-01-1990");
        assertFalse(bus.canAssignDriver(driver));
    }

    

    @Test
    public void canDriveElectricBusShouldBeRejected() {
        Bus bus = new Bus("12345678", 50, 100.0, "Electric");
        Driver driver = new Driver("D0000001", "John", 2, "Standard", "123 St", "01-01-1990");
        assertFalse(bus.canAssignDriver(driver));
    }
}

