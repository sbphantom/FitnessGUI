package ppl.recreation.fitnessgui;

/**
 * Profile object storing customer information
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */
public class Profile implements Comparable<Profile> {
    private final String fname;
    private final String lname;
    private final Date dob;
    private final boolean isMinor;


    /**
     * Constructs a Profile with the specified first name, last name, and date of birth.
     *
     * @param fname the first name of the person.
     * @param lname the last name of the person.
     * @param dob   the date of birth of the person.
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;

        Date dateOfMajor = dob.addYears(18);

        if (dateOfMajor.getYear() < Date.todayDate().getYear() ||
                (dateOfMajor.getYear() == Date.todayDate().getYear() && dateOfMajor.getMonth() <= Date.todayDate().getMonth())) {
            this.isMinor = false;
        } else {
            this.isMinor = true;
        }
    }

    /**
     * Checks if the person is a minor.
     *
     * @return true if the person is a minor, false otherwise.
     */
    public boolean isMinor() {
        return isMinor;
    }

    /**
     * Returns the first name of the person.
     *
     * @return a string representing the first name of the person.
     */
    public String getFname() {
        return fname;
    }

    /**
     * Returns the last name of the person.
     *
     * @return a string representing the last name of the person.
     */
    public String getLname() {
        return lname;
    }

    /**
     * Returns the birthdate of the person.
     *
     * @return a Date object containing the date.
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Compares this profile with the specified profile for order.
     *
     * @param profile the Profile to be compared.
     * @return a negative integer, zero, or a positive integer as this profile is less than, equal to, or greater than the specified profile.
     */
    @Override
    public int compareTo(Profile profile) {
        if (this.lname.compareTo(profile.lname) != 0) {
            return this.lname.compareTo(profile.lname);
        } else if (this.fname.compareTo(profile.fname) != 0) {
            return this.fname.compareTo(profile.fname);
        } else return this.dob.compareTo(profile.dob);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile profile) {
            return this.fname.equalsIgnoreCase(profile.fname) && this.lname.equalsIgnoreCase(profile.lname);
        }
        return false;
    }

    /**
     * Returns a string representation of the profile.
     *
     * @return a string representation of the profile.
     */
    @Override
    public String toString() {
        return String.format("%s:%s:%s", this.fname, this.lname, this.dob);
    }


    public static void main(String[] args) {

    }

}
