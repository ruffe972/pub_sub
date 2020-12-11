package ruffe972.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Random;

@Configuration
public class Config {

    @Bean
    MessageGenerator messageGenerator() {
        return new MessageGenerator(new Random(), new ObjectMapper());
    }

    @Bean
    MessagePublisher messagePublisher() {
        return new MessagePublisher(WebClient.create());
    }
}
