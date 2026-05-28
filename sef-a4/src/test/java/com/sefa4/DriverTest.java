package com.sefa4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DriverTest {
    @Test
    public void validDriverIdShouldBeAccepted() {
        Driver driver = new Driver("29abcd#$AZ", "Driver", 10, "Public Transport", "123 Bus Road, Vehicles North, VIC 8980", "11/03/1988");
        
        assertEquals("29abcd#$AZ", driver.getDriverID());
    }

    @Test
    public void driverIDLongerThan10ShouldBeRejected() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Driver("29abcd#$AZ6", "Driver", 10, "Public Transport", "123 Bus Road, Vehicles North, VIC 8980", "11/03/1988");
        });
    }

    @Test
    public void driverIDShorterThan10ShouldBeRejected() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Driver("29abc#$AZ", "Driver", 10, "Public Transport", "123 Bus Road, Vehicles North, VIC 8980", "11/03/1988");
        }); 
    }
}
