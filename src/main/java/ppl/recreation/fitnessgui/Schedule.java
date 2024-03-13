package ppl.recreation.fitnessgui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The class holds all the fitness classes offered by the franchise
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */
public class Schedule {
    private ArrayList<FitnessClass> classes = new ArrayList<>();
    private int numClasses;
    public static final int PARTITION_SIZE = 4;

    /**
     * Adds a new class to the class list
     *
     * @return the class array
     */
    public ArrayList<FitnessClass> getClasses() {
        return classes;
    }

    /**
     * Adds a new class to the class list
     *
     * @return true if class was successfully added
     */
    private boolean add(FitnessClass fitnessClass) {
        classes.add(fitnessClass);
        return true;
    }


    /**
     * Searches for a class in the class list
     *
     * @return the real class in the class array
     */
    public FitnessClass findClass(FitnessClass target) {
        for (FitnessClass fitnessClass : classes) {
            if (fitnessClass.equals(target)) {
                return fitnessClass;
            }
        }
        return null;
    }

    /**
     * Loads class schedule from file
     */
    public void load(File file) throws IOException {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.isEmpty() || line.split(" ").length != 4) {
                    //throw exception?
                    continue;
                }
                String[] args = line.split(" ");

                Offer classInfo = Offer.getOffer(args[0]);
                Instructor instructor = Instructor.getInstructor(args[1]);
                Time time = Time.getTime(args[2]);
                Location studio = Location.getLocation(args[3]);
                FitnessClass fitnessClass = new FitnessClass(classInfo, instructor, studio, time);
                add(fitnessClass);
            }
        }
    }

    /**
     * Returns a string of the class list
     *
     * @return formatted string of class list
     */
    public String listString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-Fitness classes-\n");
        for (FitnessClass fitnessClass : classes) {
            sb.append(fitnessClass.attendanceList());
        }
        sb.append("-end of class list.\n");
        return sb.toString();
    }
}
