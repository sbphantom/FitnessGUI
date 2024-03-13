package ppl.recreation.fitnessgui;

/**
 * Enum class of various class instructors
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */
public enum Offer {
    PILATES,
    SPINNING,
    CARDIO;

    /**
     * Returns the name of the offer
     *
     * @return offer name
     */
    public String getName() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }

    /**
     * Returns offer enum corresponding to string.
     *
     * @return corresponding offer
     */
    public static Offer getOffer(String name) {
        if (name.toUpperCase().equals(PILATES.name())) {
            return PILATES;
        } else if (name.toUpperCase().equals(SPINNING.name())) {
            return SPINNING;
        } else if (name.toUpperCase().equals(CARDIO.name())) {
            return CARDIO;
        } else {
            return null;
        }
    }

}
