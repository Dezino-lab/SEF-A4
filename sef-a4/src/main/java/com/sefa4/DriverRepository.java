package com.sefa4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {

    private static final String DEFAULT_FILE_NAME = resolveDefaultFilePath();
    private final String fileName;

    private static String resolveDefaultFilePath() {
        File cwd = new File(System.getProperty("user.dir"));

        // Running from sef-a4 module (pom.xml in cwd)
        if (new File(cwd, "pom.xml").isFile()) {
            return new File(cwd, "src" + File.separator + "driver.txt").getAbsolutePath();
        }

        // Running from repo root (SEF-A4)
        File fromRoot = new File(cwd, "sef-a4" + File.separator + "src" + File.separator + "driver.txt");
        if (fromRoot.getParentFile() != null && new File(cwd, "sef-a4" + File.separator + "pom.xml").isFile()) {
            return fromRoot.getAbsolutePath();
        }

        return new File(cwd, "src" + File.separator + "driver.txt").getAbsolutePath();
    }

    public static String getDataFilePath() {
        return DEFAULT_FILE_NAME;
    }

    public DriverRepository() {
        this(DEFAULT_FILE_NAME);
    }

    public DriverRepository(String fileName) {
        this.fileName = fileName;
    }

    //add driver
    public boolean add(Driver driver) {

        List<Driver> drivers = loadFromFile();

        //ID must be unique
        for (Driver d : drivers) {
            if (d.getDriverID().equals(driver.getDriverID())) {
                return false;
            }
        }

        //only if follows all formats and validation
        if (!isValidDriverID(driver.getDriverID())) return false;
        if (!isValidAddress(driver.getAddress())) return false;
        if (!isValidBirthdate(driver.getBirthdate())) return false;

        drivers.add(driver);
        return saveToFile(drivers);
    }

    public List<Driver> retrieve() {
        return loadFromFile();
    }

    public int count() {
        return loadFromFile().size();
    }

    //Update driver
    public boolean update(Driver updatedDriver) {

        List<Driver> drivers = loadFromFile();
        boolean found = false;

        for (int i = 0; i < drivers.size(); i++) {

            Driver existing = drivers.get(i);

            if (existing.getDriverID().equals(updatedDriver.getDriverID())) {

                // D5: immutable fields
                if (!existing.getName().equals(updatedDriver.getName())) {
                    return false;
                }

                if (!existing.getLicense().equals(updatedDriver.getLicense())) {
                    if ((existing.getExperience() > 10) || (updatedDriver.getExperience() > 10)) {
                        return false;
                    }
                }

                drivers.set(i, updatedDriver);
                found = true;
                break;
            }
        }

        if (!found) return false;

        return saveToFile(drivers);
    }

    //Load from txt file
    private List<Driver> loadFromFile() {

        List<Driver> drivers = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
        return drivers;
    }


        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                // Format: id|name|experience|license|street|streetName|city|state|country|birthdate
                if (parts.length < 6) continue;

                String driverId = parts[0];
                String name = parts[1];
                int experience = Integer.parseInt(parts[2]);
                String license = parts[3];
                String birthdate = parts[parts.length - 1];
                String address = String.join("|", java.util.Arrays.copyOfRange(parts, 4, parts.length - 1));

                Driver d = new Driver(driverId, name, experience, license, address, birthdate);
                drivers.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return drivers;
    }

    //Print in TXT File
    private boolean saveToFile(List<Driver> drivers) {

    try {
        File file = new File(fileName);

        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Driver d : drivers) {
                bw.write(
                        d.getDriverID() + "|" +
                        d.getName() + "|" +
                        d.getExperience() + "|" +
                        d.getLicense() + "|" +
                        d.getAddress() + "|" +
                        d.getBirthdate()
                );
                bw.newLine();
            }
        }
        return true;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    private boolean isValidDriverID(String id) {

        //ID must be 10 char
        if (id == null || id.length() != 10) return false;
        
        //First 2 char must be 2-9
        if (!id.substring(0, 2).matches("[2-9]{2}")) return false;
        
        //Last 2 char must be A-Z
        if (!id.substring(8, 10).matches("[A-Z]{2}")) return false;
        
        //At least 2 special char between 3-8
        String middle = id.substring(2, 8);
        int special = 0;

        for (char c : middle.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) special++;
        }

        return special >= 2;
    }

    private boolean isValidAddress(String address) {

        if (address == null) return false;
        //use "|" for formatting
        String[] parts = address.split("\\|");
        //Street Number|Street Name|City|State|Country
        return parts.length == 5;
    }

    private boolean isValidBirthdate(String birthdate) {

        if (birthdate == null) return false;
        //The birthdate must follow the format: DD-MM-YYYY 
        return birthdate.matches("\\d{2}-\\d{2}-\\d{4}");
    }
}



