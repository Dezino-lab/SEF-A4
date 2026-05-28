package com.sefa4;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DriverTest {

    @BeforeAll
    static void resetDriverFile() {
        File file = new File(DriverRepository.getDataFilePath());
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void validDriverShouldBeStoredInTxt() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "29AB##12CD",
                "Driver",
                10,
                "Public Transport",
                "123|Bus Road|Melbourne|VIC|Australia",
                "11-03-1988"
        );

        assertTrue(repo.add(driver));
        assertEquals(before + 1, repo.count());
    }

    @Test
    public void duplicateDriverShouldBeOnlyStoredOnce() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver1 = new Driver(
                "38CD@@49EF",
                "Driver",
                10,
                "Public Transport",
                "123|Bus Road|Melbourne|VIC|Australia",
                "11-03-1988"
        );

        Driver driver2 = new Driver(
                "38CD@@49EF",
                "Driver",
                10,
                "Public Transport",
                "123|Bus Road|Melbourne|VIC|Australia",
                "11-03-1988"
        );

        assertTrue(repo.add(driver1));
        assertFalse(repo.add(driver2));
        assertEquals(before + 1, repo.count());
    }

    @Test
    public void invalidDriverIDShouldBeRejected() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "29abc#$AZ",
                "Driver",
                10,
                "Public Transport",
                "123|Bus Road|Melbourne|VIC|Australia",
                "11-03-1988"
        );

        assertFalse(repo.add(driver));
        assertEquals(before, repo.count());
    }

    @Test
    public void validDriverWithDifferentID() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "23@#45$6AB",
                "John",
                5,
                "Public Transport",
                "123|Queen St|Melbourne|VIC|Australia",
                "03-09-1966"
        );

        assertTrue(repo.add(driver));
        assertEquals(before + 1, repo.count());
    }

    @Test
    public void validDriverWithDifferentID1() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "46*78$96WZ",
                "Paul",
                6,
                "Public Transport",
                "345|King St|Melbourne|VIC|Australia",
                "07-08-1976"
        );

        assertTrue(repo.add(driver));
        assertEquals(before + 1, repo.count());
    }

    @Test
    public void validDriverWithDifferentID2() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "36%6^7*8QV",
                "Malcom",
                7,
                "Public Transport",
                "678|Spencer St|Melbourne|VIC|Australia",
                "12-11-1972"
        );

        assertTrue(repo.add(driver));
        assertEquals(before + 1, repo.count());
    }
    @Test
    public void invalidIDLength() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "23@#45AB",
                "John",
                5,
                "Public Transport",
                "123|Queen St|Melbourne|VIC|Australia",
                "03-09-1966"
        );

        assertFalse(repo.add(driver));
        assertEquals(before, repo.count());
    }

    @Test
    public void invalidFirstTwoChars() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "1A@#45$6AB",
                "John",
                5,
                "Public Transport",
                "123|Queen St|Melbourne|VIC|Australia",
                "03-09-1966"
        );

        assertFalse(repo.add(driver));
        assertEquals(before, repo.count());
    }

    @Test
    public void missingAddressField() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "23@#45$6AB",
                "John",
                5,
                "Public Transport",
                "Queen St|Melbourne|VIC|Australia",
                "03-09-1966"
        );

        assertFalse(repo.add(driver));
        assertEquals(before, repo.count());
    }

    @Test
    public void wrongDelimiter() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "23@#45$6AB",
                "John",
                5,
                "Public Transport",
                "123,Queen St,Melbourne,VIC,Australia",
                "03-09-1966"
        );

        assertFalse(repo.add(driver));
        assertEquals(before, repo.count());
    }

    @Test
    public void invalidBirthDate() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "23@#45$6AB",
                "John",
                5,
                "Public Transport",
                "123|Queen St|Melbourne|VIC|Australia",
                "1966-09-03"
        );

        assertFalse(repo.add(driver));
        assertEquals(before, repo.count());
    }
    
}
