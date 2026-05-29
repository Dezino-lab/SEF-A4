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
    public void addressWrongDelimiter() {

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
    
    @Test
    public void birthdateWrongDelimiter() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "23@#45$6AB",
                "John",
                5,
                "Public Transport",
                "123|Queen St|Melbourne|VIC|Australia",
                "03/09/1966"
        );

        assertFalse(repo.add(driver));
        assertEquals(before, repo.count());
    }

    @Test
    public void validLicenseUpdateLessThan10() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "45$8&6*8SD",
                "Jim",
                8,
                "Heavy",
                "90|La Trobe St|Melbourne|VIC|Australia",
                "21-06-1992"
        );

        assertTrue(repo.add(driver));
        assertEquals(before + 1, repo.count());
        
        before = repo.count();

        Driver uDriver = new Driver(
                "45$8&6*8SD",
                "Jim",
                8,
                "Public Transport",
                "90|La Trobe St|Melbourne|VIC|Australia",
                "21-06-1992"
        );

        assertTrue(repo.update(uDriver));
        assertEquals(before, repo.count());
    }

    @Test
    public void validLicenseUpdateExactly10() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "9758%**8DP",
                "Joe",
                10,
                "Light",
                "100|Flinders Ave|Melbourne|VIC|Australia",
                "30-03-2000"
        );

        assertTrue(repo.add(driver));
        assertEquals(before + 1, repo.count());
        
        before = repo.count();

        Driver uDriver = new Driver(
                "9758%**8DP",
                "Joe",
                10,
                "Public Transport",
                "100|Flinders Ave|Melbourne|VIC|Australia",
                "30-03-2000"
        );

        assertTrue(repo.update(uDriver));
        assertEquals(before, repo.count());
    }

    @Test
    public void invalidLicenseUpdateMoreThan10() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "9311%*08AS",
                "Dan",
                12,
                "Medium",
                "110|Bourke St|Melbourne|VIC|Australia",
                "31-12-1999"
        );

        assertTrue(repo.add(driver));
        assertEquals(before + 1, repo.count());
        
        before = repo.count();

        Driver uDriver = new Driver(
                "9311%*08AS",
                "Dan",
                12,
                "Public Transport",
                "110|Bourke St|Melbourne|VIC|Australia",
                "31-12-1999"
        );

        assertFalse(repo.update(uDriver));
        assertEquals(before, repo.count());
    }

    @Test
    public void invalidLicenseUpdateMoreThan10AfterUpdate() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "8833%%11BS",
                "Mike",
                10,
                "Heavy",
                "84|Dockland Rd|Melbourne|VIC|Australia",
                "10-10-1967"
        );

        assertTrue(repo.add(driver));
        assertEquals(before + 1, repo.count());
        
        before = repo.count();

        Driver uDriver = new Driver(
                "8833%%11BS",
                "Mike",
                11,
                "Public Transport",
                "84|Dockland Rd|Melbourne|VIC|Australia",
                "10-10-1967"
        );

        assertFalse(repo.update(uDriver));
        assertEquals(before, repo.count());
    }

    @Test
    public void validAddressUpdate() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "44^1629^PC",
                "Daniel",
                4,
                "Public Transport",
                "20|Southern Cct|Melbourne|VIC|Australia",
                "17-08-1973"
        );

        assertTrue(repo.add(driver));
        assertEquals(before + 1, repo.count());
        
        before = repo.count();

        Driver uDriver = new Driver(
                "44^1629^PC",
                "Daniel",
                4,
                "Public Transport",
                "24|Myers St|Geelong|VIC|Australia",
                "17-08-1973"
        );

        assertFalse(repo.update(uDriver));
        assertEquals(before, repo.count());
    }
}
