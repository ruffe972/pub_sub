package ruffe972.publisher;

import java.time.Instant;
import java.util.Random;

/**
 * Used for serialization to json.
 */
@SuppressWarnings("unused")
public class Message {
    public final int msisdn;
    public final String action;

    /**
     * In seconds.
     */
    @SuppressWarnings("unused")
    public final long timestamp = Instant.now().getEpochSecond();

    Message(Random random) {
        msisdn = random.nextInt(Integer.MAX_VALUE);
        action = random.nextBoolean() ? "PURCHASE" : "SUBSCRIPTION";
    }
}
