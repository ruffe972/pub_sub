package ruffe972.subscriber;

import java.util.Optional;

/**
 * Represents a purchase/subscription message recived from client.
 */
public class ClientMessage {
    private final int msisdn;
    private final String action;

    /**
     * In seconds.
     */
    private final long timestamp;

    /**
     * @param timestamp in seconds.
     */
    ClientMessage(int msisdn, String action, long timestamp) {
        this.msisdn = msisdn;
        this.action = action;
        this.timestamp = timestamp;
    }

    Dao.Message toDaoMessage() {
        return new Dao.Message(msisdn, action.toLowerCase(), timestamp);
    }

    /**
     * Checks fields for correctness.
     */
    boolean isValid() {
        return msisdn >= 0
                && action.equals("PURCHASE") || action.equals("SUBSCRIPTION")
                && timestamp >= 0;
    }

    // todo Не слишком ли мудрёно билдер заводить?
    /**
     * This class exists to keep ClientMessage's fields private and final. Mapped from json client message.
     * Making fields final here causes a bug (bug in Jackson?):  // todo in Jackson?
     * see https://stackoverflow.com/questions/1615163/modifying-final-fields-in-java.
     */
    static class Builder {
        public int msisdn = 0;
        public String action = "";

        /**
         * In seconds.
         */
        public long timestamp = 0;

        /**
         * @return 'empty' for incorrect input
         */
        Optional<ClientMessage> build() {
            return Optional.of(new ClientMessage(msisdn, action, timestamp))
                    .map(it -> it.isValid() ? it : null);
        }
    }
}
