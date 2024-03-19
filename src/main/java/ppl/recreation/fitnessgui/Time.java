package ppl.recreation.fitnessgui;

/**
 * Enum class of various times
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */
public enum Time {
    MORNING(9, 30), AFTERNOON(14, 0), EVENING(18, 30);
    private final int hour;
    private final int minute;

    /**
     * Constructor time enum containing hour and minutes
     */
    Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Returns the hour of the time
     *
     * @return hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Returns the minutes of the time
     *
     * @return minutes
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Returns offer enum corresponding to string.
     *
     * @return corresponding time
     */
    public static Time getTime(String name) {
        if (name.toUpperCase().equals(MORNING.name())) {
            return MORNING;
        } else if (name.toUpperCase().equals(AFTERNOON.name())) {
            return AFTERNOON;
        } else if (name.toUpperCase().equals(EVENING.name())) {
            return EVENING;
        } else {
            return null;
        }
    }

    /**
     * Returns time string
     *
     * @return HH:MM
     */
    @Override
    public String toString() {
        return String.format("%d:%02d", hour, minute);
    }
}
