package ppl.recreation.fitnessgui;

/**
 * Enum class of various studio location
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */
public enum Location {
    BRIDGEWATER("08807", "SOMERSET"),
    EDISON("08837", "MIDDLESEX"),
    FRANKLIN("08873", "SOMERSET"),
    PISCATAWAY("08854", "MIDDLESEX"),
    SOMERVILLE("08876", "SOMERSET");

    private final String zipCode;
    private final String county;

    /**
     * Constructor location enum containing zipcode and county
     */
    Location(String zipcode, String county) {
        this.zipCode = zipcode;
        this.county = county;
    }

    /**
     * Returns the city name of the location
     *
     * @return city name
     */
    public String getName() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }

    /**
     * Returns the zipcode of the location
     *
     * @return zipCode
     */
    public String getZipCode() {
        return this.zipCode;
    }

    /**
     * Returns the county of the county
     *
     * @return county
     */
    public String getCounty() {
        return this.county.charAt(0) + this.county.substring(1).toLowerCase();
    }

    /**
     * Returns location enum corresponding to string.
     *
     * @return corresponding location
     */
    public static Location getLocation(String name) {
        if (name.toUpperCase().equals(BRIDGEWATER.name())) {
            return BRIDGEWATER;
        } else if (name.toUpperCase().equals(EDISON.name())) {
            return EDISON;
        } else if (name.toUpperCase().equals(FRANKLIN.name())) {
            return FRANKLIN;
        } else if (name.toUpperCase().equals(PISCATAWAY.name())) {
            return PISCATAWAY;
        } else if (name.toUpperCase().equals(SOMERVILLE.name())) {
            return SOMERVILLE;
        } else {
            return null;
        }
    }

    /**
     * Returns the of the location
     *
     * @return name, zipcode, county
     */
    @Override
    public String toString() {
        return this.name() + ", " + this.zipCode + ", " + this.county;
    }
}

