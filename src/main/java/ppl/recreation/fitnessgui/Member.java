package ppl.recreation.fitnessgui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Superclass of Member extended by Basic, Family, and Premium subclasses
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */
public class Member implements Comparable<Member> {
    private Profile profile;
    private Date expire;
    private Location homeStudio;

    private final BooleanProperty selected = new SimpleBooleanProperty();

    private FitnessClass[] attendance = new FitnessClass[Time.values().length];

    /**
     * Construct member from user and sets home studio and expiration date
     */
    public Member(Profile profile, Date expire, Location homeStudio) {
        this.profile = profile;
        this.expire = expire;
        this.homeStudio = homeStudio;
    }

    /**
     * Construct temporary member used for searching in memberlist
     */
    public Member(Profile profile) {
        this.profile = profile;
    }

    /**
     * Returns the amount of money due for the billing cycle.
     *
     * @return the amount due
     */
    public double bill() {
        return 0.0;
    }

    /**
     * Returns where the member has an empty timeslot to attend a fitness class
     *
     * @return true if member can attend a class
     */
    public boolean isFree() {
        boolean free = false;
        for (int i = 0; i < Time.values().length; i++) {
            if (attendance[i] == null) {
                free = true;
            }
        }
        return free;
    }

    /**
     * Returns whether the member is able to guess.
     *
     * @return true if the member is able to guess.
     */
    public boolean canGuest() {
        return false;
    }

    /**
     * Returns whether the number of guest passes.
     *
     * @return true if the member is able to guess.
     */
    public Integer guestPassCount() {
        return 0;
    }

    /**
     * Uses a guest pass of the member.
     *
     * @return true if a guest pass was successfully used.
     */
    public boolean useGuestPass() {
        return false;
    }

    /**
     * Adds a guest pass to the member.
     *
     * @return true if a guest pass was successfully added.
     */
    public boolean addGuestPass() {
        return false;
    }

    /**
     * Returns the attendance of the member.
     *
     * @return an array of FitnessClass representing the attendance of the member.
     */
    public FitnessClass[] getAttendance() {
        return attendance;
    }

    /**
     * Returns the profile of the member.
     *
     * @return a Profile object representing the member's profile.
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Returns the expiration date of the member's membership.
     *
     * @return a Date object representing the expiration date of the membership.
     */
    public Date getExpire() {
        return expire;
    }

    /**
     * Returns the home studio of the member.
     *
     * @return a Location object representing the home studio of the member.
     */
    public Location getHomeStudio() {
        return homeStudio;
    }

    /**
     * Checks if the membership of the member is expired.
     *
     * @return true if the membership is expired, false otherwise.
     */
    public boolean isExpired() {
        return (expire.compareTo(Date.todayDate()) < 0);
    }


    /**
     * Boolean Property used for selecting members
     *
     * @return BooleanProperty of the member.
     */
    public BooleanProperty selectedProperty() {
        return selected;
    }

    /**
     * Returns whether the member is selected
     *
     * @return true if selected.
     */
    public final boolean isSelected() {
        return selectedProperty().get();
    }

    /**
     * Toggles the selectedProperty of the user
     */
    public final void setSelected(boolean selected) {
        selectedProperty().set(selected);
    }

    /**
     * Compares this member with the specified member for order.
     *
     * @param member the Member to be compared.
     * @return a negative integer, zero, or a positive integer as this member is less than, equal to, or greater than the specified member.
     */
    @Override
    public int compareTo(Member member) {
        if (this.profile.compareTo(member.profile) != 0) {
            return this.profile.compareTo(member.profile);
        } else if (this.expire.compareTo(member.expire) != 0) {
            return this.expire.compareTo(member.expire);
        } else return this.homeStudio.compareTo(member.homeStudio);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Member member) {
            if (this.profile.equals(member.profile) && member.getClass().equals(Member.class)) {
                return true;
            }
            return this.profile.equals(member.profile) && this.getClass().equals(member.getClass());
        }
        return false;
    }

    /**
     * Returns a string representation of the member.
     *
     * @return a string representation of the member.
     */
    @Override
    public String toString() {
        String location = "";
        location = String.format("%s, %s, %s", homeStudio.name(), homeStudio.getZipCode(), homeStudio.getCounty());
        return String.format("%s, Membership expires %s, Home Studio: %s", this.profile.toString(), this.expire.toString(), location);
    }

    /**
     * Testbed main
     */
    public static void main(String[] args) {

        Member basicMember = new Basic(new Profile("Kate", "Lindsey", new Date(1989, 12, 1)), new Date(2024, 5, 31), Location.FRANKLIN);
        Member basicMember2 = new Basic(new Profile("William", "Black", new Date(2003, 6, 2)), new Date(2024, 5, 31), Location.PISCATAWAY);

        Member familyMember = new Family(new Profile("Carl", "Brown", new Date(1991, 10, 7)), new Date(2024, 3, 31), Location.PISCATAWAY);
        Member premuimMember = new Premium(new Profile("Saul", "Goodman", new Date(1999, 5, 1)), new Date(2024, 1, 25), Location.BRIDGEWATER);

        System.out.println(basicMember.equals(basicMember2)); //False

    }
}