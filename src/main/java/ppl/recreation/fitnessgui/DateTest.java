package ppl.recreation.fitnessgui;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Junit Test class for Date Class
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */
public class DateTest {
    //isValid() method in the Date class, 5 invalid cases, 2 valid cases.
    @Test
    public void isValidTrueTest(){
        Date d1 = new Date(2024, 2, 1);
        Date d2 = new Date(1996, 3, 30);

        assertTrue(d1.isValid());
        assertTrue(d2.isValid());
    }


    @Test
    public void isValidFalseTest(){
        Date d1 = new Date(2023, 2, 29); //invalid non-leap year
        Date d2 = new Date(2024, 2, 30); // invalid out of bound day
        Date d3 = new Date(2024, 0, 1); // invalid month
        Date d4 = new Date(2024, 1, 0); //invalid day
        Date d5 = new Date(Integer.MIN_VALUE, 1, 1);// invalid

        assertFalse(d1.isValid());
        assertFalse(d2.isValid());
        assertFalse(d3.isValid());
        assertFalse(d4.isValid());
        assertFalse(d5.isValid());

    }

}