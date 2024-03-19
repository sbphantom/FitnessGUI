package ppl.recreation.fitnessgui;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * An Object representation of a Date.
 * Range of valid date values are from 1/1/1900
 * to the date of compiled time.
 * <p>
 * Provides methods to create, compare, and validate Date objects.
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */
public class Date implements Comparable<Date> {
    public final int NUM_OF_MONTHS = 12;
    public final int MAX_DAYS = 31;
    public final int MIN_YEAR = 1900;

    private int year;
    private int month;
    private int day;

    /**
     * Constructs a Date object with the specified year, month, and day.
     *
     * @param year  The year number.
     * @param month The month number.
     * @param day   The day number.
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Constructs a Date object with the specified year, month, and day.
     *
     * @param date date formatted as MM/DD/YYYY
     */
    public Date(String date) {
        String[] nums = date.split("/");
        this.month = Integer.parseInt(nums[0]);
        this.day = Integer.parseInt(nums[1]);
        this.year = Integer.parseInt(nums[2]);
    }

    /**
     * Returns the year of the current date.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the month of the current date.
     *
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns the day of the current date.
     *
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Adds a number of months to the current Data object.
     *
     * @param months number of months to add
     * @return newly created sum date
     */
    public Date addMonths(int months) {
        GregorianCalendar calendar = new GregorianCalendar(this.year, this.month - 1, this.day);
        calendar.add(Calendar.MONTH, months);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return new Date(year, month, day);
    }

    /**
     * Adds a number of years to the current Data object.
     *
     * @param years number of years to add
     * @return newly created sum date
     */
    public Date addYears(int years) {
        return new Date(this.year + years, this.month, this.day);
    }


    /**
     * Returns the current date as a Date object.
     *
     * @return A Date object representing the current date.
     */
    public static Date todayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Note: Calendar months are 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new Date(year, month, day);
    }

    /**
     * Compares this Date object with another Date object.
     *
     * @param date The Date object to be compared.
     * @return A negative integer, zero, or a positive integer as this Date is before, equal to, or after the specified Date.
     */
    @Override
    public int compareTo(Date date) {
        if (this.year != date.year) {
            return this.year - date.year;
        }
        if (this.month != date.month) {
            return this.month - date.month;
        }
        return this.day - date.day;
    }

    /**
     * Returns a string representation of this Date object.
     *
     * @return A string representation of this Date object in the format "month/day/year".
     */
    @Override
    public String toString() {
        return String.format("%d/%d/%d", this.month, this.day, this.year);
    }

    /**
     * Checks if this Date object is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal; otherwise, false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date date) {
            return compareTo(date) == 0;
        }
        return false;
    }

    /**
     * Checks if the given year, month, and day form a valid date.
     *
     * @return True if the date is valid; otherwise, false.
     */
    public boolean isValid() {
        if (year < MIN_YEAR || month < 1 || day < 1 || month > NUM_OF_MONTHS || day > MAX_DAYS) {
            return false;
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setLenient(false);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        try {
            calendar.get(Calendar.YEAR); // This will trigger any potential exceptions
            calendar.get(Calendar.MONTH);
            calendar.get(Calendar.DAY_OF_MONTH);
            return true; // If no exception occurred, date is valid
        } catch (Exception e) {
            return false; // Exception means date is invalid
        }
    }

    /**
     * Returns whether the date is expired
     *
     * @return true if expired, else false
     */
    public boolean isExpired() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return compareTo(new Date(year, month, dayOfMonth)) <= 0;
    }

    /**
     * A main method for testing the Date class.
     *
     * @param args The command line arguments (unused).
     */
    public static void main(String[] args) {
        // Test cases for Date class
    }
}
