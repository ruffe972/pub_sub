package ruffe972.publisher;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class MessagePublisher {
    private final WebClient webClient;

    MessagePublisher(WebClient webClient) {
        this.webClient = webClient;
    }

    // Returns false on success.
    boolean publish(String message) {
        var request = webClient
                .post()
                .uri("/abc")
                .body(BodyInserters.fromValue(message));
        final var FAILURE_CODE = true;
        return request
                .retrieve()
                .toBodilessEntity()
                .map(it -> it.getStatusCode() != HttpStatus.OK)
                .onErrorReturn(FAILURE_CODE)
                .blockOptional()
                .orElse(FAILURE_CODE);
    }
}
