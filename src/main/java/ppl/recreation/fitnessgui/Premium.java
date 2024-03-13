package ppl.recreation.fitnessgui;

/**
 * Subclass of Member for Premium tier customers
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */
public class Premium extends Member {
    private int guestPass;
    private final double PREMIUM_PRICE = 59.99;
    private final int billingCycleLength = 12;
    private final int complementaryMonth = 1;

    /**
     * Constructs a premium tier member.
     *
     * @param profile The profile of the customer.
     * @param expire The expiration date of the customer's plan.
     * @param homeStudio The signup location of the costumer.
     */
    public Premium(Profile profile, Date expire, Location homeStudio) {
        super(profile, expire, homeStudio);
        if (expire.isExpired()) {
            this.guestPass = 0;
        } else {
            this.guestPass = 3;
        }
    }

    /**
     * Returns the amount of money due for the billing cycle.
     *
     * @return the amount due
     */
    @Override
    public double bill() {
        return PREMIUM_PRICE * (billingCycleLength - complementaryMonth);
    }

    /**
     * Returns whether the member is able to guess.
     *
     * @return true if the member is able to guess.
     */
    @Override
    public boolean canGuest() {
        return (!isExpired() && guestPass > 0);
    }

    /**
     * Use of a guest pass of the user.
     *
     * @return true if a guest pass was successfully used.
     */
    @Override
    public boolean useGuestPass() {
        if (canGuest()) {
            guestPass--;
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
        guestPass++;
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
            return String.format("%s, Membership expired %s, Home Studio: %s, (Premium) guest-pass remaining: not eligible", getProfile(), getExpire(), getHomeStudio());
        } else {
            return String.format("%s, Membership expires %s, Home Studio: %s, (Premium) guest-pass remaining: %d", getProfile(), getExpire(), getHomeStudio(), guestPass);
        }
    }

}
