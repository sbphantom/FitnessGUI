package ppl.recreation.fitnessgui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is an array-based implementation of a linear data structure to hold a list of member
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */
public class MemberList {
    private ArrayList<Member> members = new ArrayList<>();
    public static final int NOT_FOUND = -1;
    public boolean GUEST_LIST;

    /**
     * Default constructor for MemberList.
     */
    public MemberList() {
    }

    /**
     * Constructs a MemberList as GuestList.
     *
     * @param type a Boolean value indicating guest list.
     */
    public MemberList(Boolean type) {
        GUEST_LIST = type;
    }

    /**
     * Returns the members of the MemberList.
     *
     * @return an array of Member objects representing the members of the MemberList.
     */
    public ArrayList<Member> getMembers() {
        return members;
    }

    /**
     * Returns the size of the MemberList.
     *
     * @return an integer representing the size of the MemberList.
     */
    public int getSize() {
        return this.members.size();
    }

    /**
     * Finds a member in the MemberList.
     *
     * @param member the Member object to be found.
     * @return an integer representing the index of the member in the MemberList. Returns NOT_FOUND if the member is not in the list.
     */
    private int find(Member member) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).equals(member)) {
                return i;
            }
        }
        return NOT_FOUND;
    }


    /**
     * Checks if a member is in the MemberList.
     *
     * @param member the Member object to be checked.
     * @return true if the member is in the MemberList, false otherwise.
     */
    public boolean contains(Member member) {
        return find(member) != NOT_FOUND;
    }

    /**
     * Returns a member from the MemberList.
     *
     * @param member the Member object to be returned.
     * @return the Member object if it is in the MemberList, null otherwise.
     */
    public Member getMember(Member member) {
        if (contains(member))
            return members.get(find(member));
        else
            return null;
    }

    /**
     * Adds a member to the MemberList.
     *
     * @param member the Member object to be added.
     * @return true if the member was successfully added, false otherwise.
     */
    public boolean add(Member member) {
        if (members.isEmpty()) {
            members.add(member);
            return true;
        } else if (contains(member) && !GUEST_LIST) return false;
        else {
            members.add(member);
            return true;
        }
    }

    /**
     * Removes a member from the MemberList.
     *
     * @param member the Member object to be removed.
     * @return true if the member was successfully removed, false otherwise.
     */
    public boolean remove(Member member) {
        if (members.isEmpty() || !contains(member)) return false;
        else {
            members.remove(find(member));
            return true;
        }
    }

    /**
     * Sorts the collection based on a given parameter
     *
     * @param sortBy sorts collection by given string
     *               options are "release", "genre", and "rating"
     *               defaults to album title
     */
    private void sort(String sortBy) {
        members.sort((Member m1, Member m2) -> compareMember(m1, m2, sortBy));
    }


    /**
     * Compares two member based on a given param
     *
     * @param m1        first member to compare to
     * @param m2        second member to compare against
     * @param compareBy what to compare by
     *                  options are "county" and "member
     *                  invalid option does not sort
     */
    private int compareMember(Member m1, Member m2, String compareBy) {
        return switch (compareBy) {
            case "county" -> {
                if (m1.getHomeStudio().getCounty().compareTo(m2.getHomeStudio().getCounty()) == 0) {
                    yield m1.getHomeStudio().getZipCode().compareTo(m2.getHomeStudio().getZipCode());
                }
                yield m1.getHomeStudio().getCounty().compareTo(m2.getHomeStudio().getCounty());

            }
            case "member" -> m1.getProfile().compareTo(m2.getProfile());
            default -> 0;
        };
    }

    /**
     * Loads members from file
     */
    public void load(File file) throws IOException {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.isEmpty() || line.split(" ").length != 6) {
                    //throw exception?
                    continue;
                }
                String[] args = line.split(" ");
                String type = args[0];
                String fname = args[1];
                String lname = args[2];
                Date dob = new Date(args[3]);
                Date expiration = new Date(args[4]);
                Location location = Location.getLocation(args[5]);
                Profile profile = new Profile(fname, lname, dob);
                switch (type) {
                    case "B" -> add(new Basic(profile, expiration, location));
                    case "F" -> add(new Family(profile, expiration, location));
                    case "P" -> add(new Premium(profile, expiration, location));
                }
            }
        }
    }

    /**
     * Prints the memberlist sorted by location
     */
    public String printByCounty() {
        sort("county");
        StringBuilder sb = new StringBuilder();
        sb.append("\n-list of members sorted by county then zipcode-\n");

        for (Member m : members) {
            sb.append(m).append("\n");
        }

        sb.append("-end of list-");
        return sb.toString();
//        System.out.println(sb);
    }

    /**
     * Prints the memberlist sorted by members
     */
    public String printByMember() {
        sort("member");
        StringBuilder sb = new StringBuilder();
        sb.append("\n-list of members sorted by member profiles-\n");

        for (Member m : members) {
            sb.append(m).append("\n");
        }
        sb.append("-end of list-");
        return sb.toString();
//        System.out.println(sb);
    }

    /**
     * Prints the memberlist along with their fees
     */
    public String printByFees() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n-list of members with next dues-\n");

        for (Member m : members) {
            sb.append(m).append(String.format(" [next due: $%.02f]\n", m.bill()));
        }
        sb.append("-end of list-");
        return sb.toString();
//        System.out.println(sb);
}

}