package ruffe972.publisher;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Prepares sending messages to the server.
 */
public class MessagePublisher {
    private final WebClient webClient;

    MessagePublisher(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Prepares sending a message to the server.
     */
    Mono<Void> publish(String message) {
        return webClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(message))
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
