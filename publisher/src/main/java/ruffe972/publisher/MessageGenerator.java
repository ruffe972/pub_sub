package ruffe972.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Generates random json messages according to the specification
 */
@Component
public class MessageGenerator {
    private final Random random = new Random();
    private final ObjectMapper objectMapper = new ObjectMapper();

    String generate() {
        try {
            return objectMapper.writeValueAsString(new Message(random));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
