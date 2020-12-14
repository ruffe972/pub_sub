package ruffe972.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Sends messages to the server.
 */
public class MessagePublisher {
    private static final Logger logger = LoggerFactory.getLogger(MessagePublisher.class);
    private final WebClient webClient;

    MessagePublisher(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Sends a message to the server. Blocks until response.
     * @return false on success.
     */
    boolean publish(String message) {
        logger.info("Sending message to server: {}.", message);
        final var failureCode = true;  // todo вопрос о стиле. Надо ли выносить как поле? Если да, то зачем?
        var request = webClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(message));
        return request
                .retrieve()
                .toBodilessEntity()
                .doOnError(e -> {
                    logger.info("Message sending error: {}", e.getMessage());
                })
                .doOnNext(it -> logger.info("HTTP status code of the response: {}.", it.getStatusCode()))
                .map(it -> it.getStatusCode() != HttpStatus.OK)
                .onErrorReturn(failureCode)
                .blockOptional()
                .orElse(failureCode);
    }
}
