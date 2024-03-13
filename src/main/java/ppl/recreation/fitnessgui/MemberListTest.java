package ppl.recreation.fitnessgui;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Junit Test class for MemberList Class
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */

public class MemberListTest {
    // add() method – 3 true cases and 3 false cases for adding Basic, Family and Premium.

    @Test
    public void testAddBasic() {
        MemberList memberList = new MemberList();
        Member bMember = new Basic(new Profile("Adeola", "Asimolowo", new Date(2003, 5 , 8)), new Date(2025, 8, 25), Location.SOMERVILLE);
        assertTrue(memberList.add(bMember));
        assertFalse(memberList.add((bMember)));

    }

    @Test
    public void testAddPremium(){
        MemberList memberList = new MemberList();
        Member pMember = new Premium(new Profile("Danny","Phantom", new Date(2003, 9, 12 )), new Date(2024 , 12, 29), Location.EDISON);
        assertTrue(memberList.add(pMember));
        assertFalse(memberList.add((pMember)));

    }

    @Test
    public void testAddFamily(){
        MemberList memberList = new MemberList();
        Member fMember = new Family(new Profile("John", "Doe", new Date(1987, 4, 13)),  new Date(2024 , 5, 7), Location.BRIDGEWATER);
        assertTrue(memberList.add(fMember));
        assertFalse(memberList.add((fMember)));
    }

    @Test
    public void testRemove(){
        MemberList memberList = new MemberList();
        Member bMember = new Basic(new Profile("Adeola", "Asimolowo", new Date(2003, 5 , 8)), new Date(2025, 8, 25), Location.SOMERVILLE);

        assertFalse(memberList.remove(bMember));

        memberList.add(bMember);

        assertTrue(memberList.remove(bMember));
    }


}
