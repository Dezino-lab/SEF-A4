package com.sefa4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BusRepositoryIntegrationTest {
    
    // Using a separate test file to avoid affecting actual data and to ensure test isolation
    private static final String TEST_FILE_NAME = "target" + File.separator + "test-bus-repository.txt";
    private BusRepository repo;

    @BeforeEach
    public void setUp() {
        File file = new File(TEST_FILE_NAME);

        if (file.exists()) {
            file.delete();
        }

        repo = new BusRepository(TEST_FILE_NAME);
    }

    @AfterEach
    public void cleanUp() {
        File file = new File(TEST_FILE_NAME);

        if (file.exists()) {
            file.delete();
        }
    }

    // verify that a valid bus can be stored and retrieved from the txt file
    @Test
    public void validBusShouldBeStoredAndRetrievedFromTxtFile() {
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");

        assertTrue(repo.add(bus));
        assertEquals(1, repo.count());

        List<Bus> storedBuses = repo.retrieve();

        assertEquals(1, storedBuses.size());
        assertEquals("12345678", storedBuses.get(0).getId());
        assertEquals(40, storedBuses.get(0).getCapacity());
        assertEquals(80.0, storedBuses.get(0).getFuelLevel());
        assertEquals("Diesel", storedBuses.get(0).getFuelType());
    }

    // verify that an update to a bus's capacity is persisted in the txt file
    @Test
    public void decreasedCapacityUpdateShouldPersistInTxtFile() {
        Bus originalBus = new Bus("87654321", 60, 80.0, "Diesel");
        assertTrue(repo.add(originalBus));

        Bus updatedBus = new Bus("87654321", 40, 70.0, "Hybrid");
        assertTrue(repo.update(updatedBus));

        List<Bus> storedBuses = repo.retrieve();

        assertEquals(1, storedBuses.size());
        assertEquals(40, storedBuses.get(0).getCapacity());
        assertEquals(70.0, storedBuses.get(0).getFuelLevel());
        assertEquals("Hybrid", storedBuses.get(0).getFuelType());
    }

    // Need 2 more tests
    //valid storage/retrival and persisted update

    // invalid or duplicate bus should not be stored and should not affect existing data in file
    @Test 
    public void duplicateBusShouldNotBeStoredOrAffectExisting() {
        // add first bus
        Bus bus = new Bus("87654321", 30, 90.0, "Electric");
        assertTrue(repo.add(bus));
        // verifies there is only 1 bus in the repo 
        assertEquals(1, repo.count());

        // add a duplicate of first bus
        Bus duplicateBus = new Bus("87654321", 30, 90.0, "Electric");
        // verifies that adding the duplicate bus fails
        assertFalse(repo.add(duplicateBus));

        // Verify that the count remains accurate and didn't change on failure
        assertEquals(1, repo.count());
    }
}
