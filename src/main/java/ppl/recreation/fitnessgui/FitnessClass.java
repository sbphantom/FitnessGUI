package ppl.recreation.fitnessgui;

/**
 * Class representing a fitness classes
 *
 * @author Danny Onuorah , Adeola Asimolowo
 */

public class FitnessClass {
    private Offer classInfo;
    private Instructor instructor;
    private Location studio;
    private Time time;
    private MemberList members = new MemberList();
    public boolean GUEST_LIST = true;
    private MemberList guests = new MemberList(GUEST_LIST);

    /**
     * Creates a fitness class
     *
     * @param classInfo  The type of class.
     * @param instructor The instructor teaching the class.
     * @param studio     The location of the class.
     * @param time       the time of the class
     */

    public FitnessClass(Offer classInfo, Instructor instructor, Location studio, Time time) {
        this.classInfo = classInfo;
        this.instructor = instructor;
        this.studio = studio;
        this.time = time;
    }

    /**
     * Creates a temporary fitness class used during searching
     *
     * @param classInfo  The type of class.
     * @param instructor The instructor teaching the class.
     * @param studio     The location of the class.
     */
    public FitnessClass(Offer classInfo, Instructor instructor, Location studio) {
        this.classInfo = classInfo;
        this.instructor = instructor;
        this.studio = studio;
    }

    public Offer getClassInfo() {
        return classInfo;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Returns the location of the class.
     *
     * @return class location
     */
    public Location getStudio() {
        return studio;
    }

    /**
     * Returns the time of the class.
     *
     * @return class time
     */
    public Time getTime() {
        return time;
    }


    public MemberList getMembers() {
        return members;
    }

    public MemberList getGuests() {
        return guests;
    }

    /**
     * Marks the attendance of a member.
     */
    public void addMember(Member member) {
        members.add(member);
        if (member instanceof Basic basic) {
            basic.incrementClassCount();
        }
    }

    /**
     * Marks the attendance of a guest.
     */
    public void addGuest(Member guest) {
        guests.add(guest);
    }

    /**
     * Removes a member from attendance.
     */
    public void removeMember(Member member) {
        members.remove(member);
    }

    /**
     * Removes a guest from attendance.
     */
    public void removeGuest(Member member) {
        member.addGuestPass();
        guests.remove(member);
    }

    /**
     * Returns whether if members have attended the class
     *
     * @return true if members have attended
     */
    public boolean hasAttendance() {
        return members.getSize() > 0;
    }

    /**
     * Returns whether if guests have attended the class
     *
     * @return true if guests have attended
     */
    public boolean hasGuestAttendance() {
        return guests.getSize() > 0;
    }

    /**
     * Returns a string of the class's attendance
     *
     * @return formatted string of current class attendance
     */
    public String attendanceList() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s - %s\n", classInfo, toString()));
        if (hasAttendance())
            sb.append("[Attendees]\n");
        {
            for (Member m : members.getMembers()) {
                sb.append("   " + m + "\n");
            }
        }
        if (hasGuestAttendance()) {
            sb.append("[Guests]\n");
            {
                Member prev = null;
                for (Member m : guests.getMembers()) {
                    if (!m.equals(prev))
                        sb.append("   " + m + "\n");
                    prev = m;
                }
            }
        }
        return sb.toString();
    }

    /**
     * Returns a string contain the fitness class
     *
     * @return class string
     */
    public String getMenuString() {
        return String.format("%s - %s (%s, %s)", classInfo.getName(), instructor.getName(), time.name().charAt(0), studio.getName());
    }




    /**
     * Returns a string contain the fitness class
     *
     * @return class string
     */
    @Override
    public String toString() {
        return String.format("%s, %d:%02d, %s", instructor, time.getHour(), time.getMinute(), studio.name());
    }

    /**
     * Returns whether two class are the same
     *
     * @return true if offer, studio and instructor are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FitnessClass c) {
            return this.studio.equals(c.studio) && this.instructor.equals(c.instructor) && this.classInfo.equals(c.classInfo);
        }
        return false;
    }

    public static void main(String[] args) {
        Offer offer = Offer.PILATES;
        Instructor instructor = Instructor.EMMA;
        Location studio = Location.PISCATAWAY;
        Time time = Time.MORNING;
        Member member1 = new Basic(new Profile("Adeola", "Asimolowo", new Date(2003, 5, 8)), new Date(2025, 8, 25), Location.SOMERVILLE);

        FitnessClass classOne = new FitnessClass(offer, instructor, studio, time);
        classOne.addMember(member1);
    }
}
