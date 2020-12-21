package ruffe972.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * Thread-safe. Generates random json messages according to the specification.
 */
@Component
public class MessageGenerator implements Supplier<String> {
    private final AtomicLong messageId = new AtomicLong();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String get() {
        try {
            return objectMapper.writeValueAsString(
                    new Message(messageId.getAndIncrement()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
