package ruffe972.subscriber;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Mapped from json client message.
 * Making fields private final here makes deserialization buggy.
 * See https://stackoverflow.com/questions/1615163/modifying-final-fields-in-java.
 */
@SuppressWarnings("CanBeFinal")
public class MessageDto {
    @JsonProperty("id")
    public long messageId = 0;

    public int msisdn = 0;
    public String action = "";

    /**
     * In seconds.
     */
    public long timestamp = 0;
}
