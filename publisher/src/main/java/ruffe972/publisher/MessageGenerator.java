package ruffe972.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Random;

public class MessageGenerator {
    private final Random random;
    private final ObjectMapper objectMapper;

    MessageGenerator(Random random, ObjectMapper objectMapper) {
        this.random = random;
        this.objectMapper = objectMapper;
    }

    String generate() {
        try {
            return objectMapper.writeValueAsString(new Message(random));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

class Message {
    public final int msisdn;
    public final String action;
    public final long timestamp = Instant.now().getEpochSecond();

    Message(Random random) {
        msisdn = random.nextInt(Integer.MAX_VALUE);
        action = random.nextBoolean() ? "PURCHASE" : "SUBSCRIPTION";
    }
}