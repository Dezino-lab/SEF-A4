package com.sefa4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class BusRepository {
    
    private static final String DEFAULT_FILE_NAME = resolveDefaultFilePath();
    private final String fileName;

    private static String resolveDefaultFilePath() {
        File cwd = new File(System.getProperty("user.dir"));

        // Running from sef-a4 module (pom.xml in cwd)
        if (new File(cwd, "pom.xml").isFile()) {
            return new File(cwd, "src" + File.separator + "bus.txt").getAbsolutePath();
        }

        // Running from repo root (SEF-A4)
        File fromRoot = new File(cwd, "sef-a4" + File.separator + "src" + File.separator + "bus.txt");

        if (fromRoot.getParentFile() != null && new File(cwd, "sef-a4" + File.separator + "pom.xml").isFile()) {
            return fromRoot.getAbsolutePath();
        }

        return new File(cwd, "src" + File.separator + "bus.txt").getAbsolutePath();
    }

    public static String getDataFilePath() {
        return DEFAULT_FILE_NAME;
    }

    public BusRepository() {
        this(DEFAULT_FILE_NAME);
    }

    public BusRepository(String fileName) {
        this.fileName = fileName;
    }

    // add bus only if ID is unique and follows all formats and validation
    public boolean add(Bus bus) {

        if (bus == null || !Bus.isValidBusID(bus.getId())) {
            return false;
        }

        List<Bus> buses = loadFromFile();

        for (Bus existingBus : buses) {
            if (existingBus.getId().equals(bus.getId())) {
                return false;
            }
        }

        buses.add(bus);
        saveToFile(buses);
        return true;
    
    }

    public List<Bus> retrieve() {
        return loadFromFile();
    }

    public int count() {
        return loadFromFile().size();
    }

    public boolean update(Bus updatedBus) {
        if (updatedBus == null || !Bus.isValidBusID(updatedBus.getId())) {
            return false;
        }

        List<Bus> buses = loadFromFile();

        for (int i = 0; i < buses.size(); i++) {
            Bus existingBus = buses.get(i);

            if(existingBus.getId().equals(updatedBus.getId())) {
                // Only allow capacity to decrease or remain the same
                if (!existingBus.updateCapacity(updatedBus.getCapacity())) {
                    return false;
                }

                existingBus.setFuelLevel(updatedBus.getFuelLevel());
                existingBus.setFuelType(updatedBus.getFuelType());

                buses.set(i, existingBus);
                saveToFile(buses);  
                return true;
            }
        }
        return false;
    }

    // loads buses from file
    private List<Bus> loadFromFile() {
        List<Bus> buses = new ArrayList<>();
        File file = new File(fileName);

        if(!file.exists()) {
            return buses;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                // Expected format: busId|capacity|fuelLevel|fuelType
                if (parts.length != 4) {
                    continue; // Skip malformed lines
                }

                String busId = parts[0];
                int capacity = Integer.parseInt(parts[1]);
                double fuelLevel = Double.parseDouble(parts[2]);
                String fuelType = parts[3];

                buses.add(new Bus(busId, capacity, fuelLevel, fuelType));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buses;
    }

    // saves buses to file
    private boolean saveToFile(List<Bus> buses) {
        try {
            File file = new File(fileName);

            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (Bus bus : buses) {
                    bw.write (
                        bus.getId() + "|" +
                        bus.getCapacity() + "|" +
                        bus.getFuelLevel() + "|" +
                        bus.getFuelType()
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
}