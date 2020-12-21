package ruffe972.publisher;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Thread-safe. Used for serialization to json.
 */
@SuppressWarnings("unused")
public class Message {
    @JsonProperty("id")
    public final long messageId;

    public final int msisdn;
    public final String action;

    /**
     * In seconds.
     */
    @SuppressWarnings("unused")
    public final long timestamp = Instant.now().getEpochSecond();

    Message(long messageId) {
        var random = ThreadLocalRandom.current();
        this.messageId = messageId;
        msisdn = random.nextInt(Integer.MAX_VALUE);
        action = random.nextBoolean() ? "PURCHASE" : "SUBSCRIPTION";
    }
}
