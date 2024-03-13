package ppl.recreation.fitnessgui;

/**
 * Subclass of Member for Family tier customers
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */
public class Family extends Member {
    private boolean guest;
    private final double FAMILY_PRICE = 49.99;

    private final int billingCycleLength = 3;

    /**
     * Constructs a family tier member.
     *
     * @param profile    The profile of the customer.
     * @param expire     The expiration date of the customer's plan.
     * @param homeStudio The signup location of the costumer.
     */
    public Family(Profile profile, Date expire, Location homeStudio) {
        super(profile, expire, homeStudio);
        this.guest = !expire.isExpired();
    }

    /**
     * Returns the amount of money due for the billing cycle.
     */
    @Override
    public double bill() {
        return FAMILY_PRICE * billingCycleLength;
    }

    /**
     * Returns whether the member is able to guess.
     *
     * @return true if the member is able to guess.
     */
    @Override
    public boolean canGuest() {
        return !isExpired() && guest;
    }

    /**
     * Use of a guest pass of the user.
     *
     * @return true if a guest pass was successfully used.
     */
    @Override
    public boolean useGuestPass() {
        if (canGuest()) {
            guest = false;
            return true;
        }
        return false;
    }

    /**
     * Adds a guest pass to the user.
     *
     * @return true if a guest pass was successfully added.
     */
    @Override
    public boolean addGuestPass() {
        guest = true;
        return true;
    }

    /**
     * Returns a string contain member
     *
     * @return member string
     */
    @Override
    public String toString() {
        if (isExpired()) {
            return String.format("%s, Membership expired %s, Home Studio: %s, (Family) guest-pass remaining: not eligible", getProfile(), getExpire(), getHomeStudio());
        } else if (guest) {
            return String.format("%s, Membership expires %s, Home Studio: %s, (Family) guest-pass remaining: 1", getProfile(), getExpire(), getHomeStudio());
        } else {
            return String.format("%s, Membership expires %s, Home Studio: %s, (Family) guest-pass remaining: 0", getProfile(), getExpire(), getHomeStudio());
        }
    }

}
