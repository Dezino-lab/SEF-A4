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
    
    //integration test 1 driver with all valid info are stored
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
    
    //integration test 2 update driver info updating address and dob
    @Test
    public void updateDriverInfo() {
        
        DriverRepository repo = new DriverRepository();
        Driver driver = new Driver(
            "23@#45$6AB",
            "John",
            5,
            "Public Transport",
            "123|Queen St|Melbourne|VIC|Australia",
            "03-09-1966"
        );

        repo.add(driver);
        int before = repo.count();
    
        Driver updatedDriver = new Driver(
            "23@#45$6AB",
            "John",
            5,
            "Public Transport",
            "999|Queen Street|Melbourne|VIC|Australia",
            "01-01-1970"
        );

        assertTrue(repo.update(updatedDriver));
        assertEquals(before, repo.count());
    }

    // Integration test 3: driver with invalid ID is not stored
    @Test
    public void invalidDriverIDShouldBeRejected() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "29ABC#12CD",
                "Driver",
                10,
                "Public Transport",
                "123|Bus Road|Melbourne|VIC|Australia",
                "11-03-1988"
        );

        assertFalse(repo.add(driver));
        assertEquals(before, repo.count());
    }

    //integration test 4 driver with duplicate ID is only stored once in TXT file
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
    
    //test case 1,4,7 - store driver with validID, address and DOB 
    @Test
    public void validID() {

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

        assertFalse(repo.add(driver));
        assertEquals(before, repo.count());
    }
    

    //test case 2 - driver with wrong ID length is rejected and not stored in TXT file
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
    
    //test case 3 - driver with ID that has invalid first two characters is rejected and not stored in TXT file
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
    
    //test case 5 - driver with missing address field is rejected and not stored in TXT file
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
    
    //test case 6 - driver with wrong address delimiter is rejected and not stored in TXT file
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
    
    //test case 8 - driver with invalid DOB is rejected and not stored in TXT file
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
    
    //additional test cases to store drivers in TXT file
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
    //additional test cases to store drivers in TXT file
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
    
    // Unit test case 9 - driver with DOB using incorrect delimiters is rejected and not stored in TXT file
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

    // Unit test case 10 - driver details update when experienceYears is less than 10
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

    // Unit test case 11 - driver details update when experienceYears is exactly than 10
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

    // Unit test case 12 - driver details do not update when experienceYears is more than 10
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

    // Unit test case 13 - driver details do not update when experienceYears is more than 10 after update
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

    // Unit test case 14 - driver details update when only address is changed
    @Test
    public void validUpdate() {

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

        assertTrue(repo.update(uDriver));
        assertEquals(before, repo.count());
    }

    // Unit test case 15 - driver details do not update when driverID is changed
    @Test
    public void invalidUpdateDriverID() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "55&376*4BW",
                "Brian",
                8,
                "Public Transport",
                "55|Heather Cct|Melbourne|VIC|Australia",
                "04-04-1962"
        );

        assertTrue(repo.add(driver));
        assertEquals(before + 1, repo.count());
        
        before = repo.count();

        Driver uDriver = new Driver(
                "98&%76#5ZY",
                "Brian",
                8,
                "Public Transport",
                "55|Heather Cct|Melbourne|VIC|Australia",
                "04-04-1962"
        );

        assertFalse(repo.update(uDriver));
        assertEquals(before, repo.count());
    }

    // Unit test case 16 - driver details do not update when name is changed
    @Test
    public void invalidUpdateName() {

        DriverRepository repo = new DriverRepository();
        int before = repo.count();

        Driver driver = new Driver(
                "939876#@GF",
                "Shaun",
                2,
                "Public Transport",
                "12|Drawer Ct|Melbourne|VIC|Australia",
                "12-12-1980"
        );

        assertTrue(repo.add(driver));
        assertEquals(before + 1, repo.count());
        
        before = repo.count();

        Driver uDriver = new Driver(
                "939876#@GF",
                "David",
                2,
                "Public Transport",
                "12|Drawer Ct|Melbourne|VIC|Australia",
                "12-12-1980"
        );

        assertFalse(repo.update(uDriver));
        assertEquals(before, repo.count());
    }
}