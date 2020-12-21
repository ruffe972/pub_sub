package ruffe972.publisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    MessagePublisher messagePublisher(MessageGenerator messageGenerator) {
        return new MessagePublisher(WebClient.create("http://localhost:8080"), messageGenerator);
    }
}
