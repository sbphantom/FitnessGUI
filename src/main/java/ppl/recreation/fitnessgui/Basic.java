package ppl.recreation.fitnessgui;

/**
 * Subclass of Member for Basic tier customers
 *
 * @author Danny Onuorah , Adeola Asimolowo
 */
public class Basic extends Member {
    private int numClasses;
    private final double BASIC_PRICE = 39.99;
    private final int CLASS_LIMIT = 4;
    private final double EXTRA_CLASS_FEE = 10;

    /**
     * Constructs a basic tier member.
     *
     * @param profile    The profile of the customer.
     * @param expire     The expiration date of the customer's plan.
     * @param homeStudio The signup location of the costumer.
     */
    public Basic(Profile profile, Date expire, Location homeStudio) {
        super(profile, expire, homeStudio);
    }

    /**
     * Returns the number of classes the member has attended
     *
     * @return the number of classes attended by the member
     */
    public int getNumClasses() {
        return this.numClasses;
    }

    /**
     * Increases the number of classes attended by one
     */
    public void incrementClassCount() {
        this.numClasses += 1;
    }

    /**
     * Returns the amount of money due for the billing cycle.
     *
     * @return amount due
     */
    @Override
    public double bill() {
        double bill = BASIC_PRICE;

        if (numClasses > CLASS_LIMIT) {
            bill += ((numClasses - CLASS_LIMIT) * EXTRA_CLASS_FEE);
        }

        return bill;
    }

    /**
     * Returns a formatted string containing member info
     *
     * @return member string
     */
    @Override
    public String toString() {
        return String.format("%s, Membership expires %s, Home Studio: %s, (Basic) number of classes attended: %d", getProfile(), getExpire(), getHomeStudio(), numClasses);
    }
}