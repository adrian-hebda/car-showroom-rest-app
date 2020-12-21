package lab.java10.repositories;


import lab.java10.annotations.ToExport;
import lab.java10.models.CarShowroom;
import lab.java10.models.Vehicle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;


public class CSVImportExport {

    public static void importSalon(File file) {

    }

    public static void exportSalon(CarShowroom showroom) {
        try {
            final FileWriter csvWriter = new FileWriter("src/main/java/data/salons/" + showroom.getName() + ".csv");
            csvWriter.append(showroom.getName() + ";" + showroom.getMaxCarAmount().toString() + ";" + showroom.getFav().toString() + "\n");
            csvWriter.flush();
            for (Vehicle vehicle : showroom.getVehicles()) {
                try {
                    Class<?> Vehicle = vehicle.getClass();

                    for (Field f : Vehicle.getDeclaredFields()) {
                        if (f.isAnnotationPresent(ToExport.class)) {
                            ToExport annotation = f.getAnnotation(ToExport.class);
                            if(!annotation.collection() && !annotation.object()){
                                f.setAccessible(true);
                                String s = f.get(vehicle).toString();
                                csvWriter.append(s + ";");
                                csvWriter.flush();
                            }
                        }
                    }

                    csvWriter.append("\n");
                } catch (IOException | IllegalAccessException e) {
                    e.printStackTrace();
                    System.out.println("Error during saving to csv file");
                }
            }
            csvWriter.flush();
            csvWriter.close();
            System.out.println("File saved successfully");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot save file to csv");
        }
    }
}
