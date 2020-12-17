package ruffe972.subscriber;

/**
 * Mapped from json client message.
 * Making fields private final here makes deserialization buggy.
 * See https://stackoverflow.com/questions/1615163/modifying-final-fields-in-java.
 */
@SuppressWarnings("CanBeFinal")
public class MessageDto {
    public int msisdn = 0;
    public String action = "";

    /**
     * In seconds.
     */
    public long timestamp = 0;
}
