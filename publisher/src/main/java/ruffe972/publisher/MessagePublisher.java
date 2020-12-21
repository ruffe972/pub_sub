package ruffe972.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * Periodically publishes messages asynchronously.
 */
public class MessagePublisher {
    private static final int THREADS_COUNT = 5;
    private static final Duration PERIOD = Duration.ofSeconds(15);

    private static final Logger logger = LoggerFactory.getLogger(MessagePublisher.class);
    private final WebClient webClient;
    private final Supplier<String> messageGenerator;

    MessagePublisher(WebClient webClient, Supplier<String> messageGenerator) {
        this.webClient = webClient;
        this.messageGenerator = messageGenerator;
    }

    Flux<Unit> publishMessages() {
        return Flux.range(0, THREADS_COUNT)
                .flatMap(it -> publishOneMessagePeriodically());
    }

    private Flux<Unit> publishOneMessagePeriodically() {
        return Flux.interval(PERIOD)
                .map(it -> messageGenerator.get())
                .doOnNext(message -> logger.trace("Sending message to server: {}.", message))
                .flatMap(this::publishSingleMessage);
    }

    private Mono<Unit> publishSingleMessage(String message) {
        return webClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(message))
                .retrieve()
                .toBodilessEntity()
                .map(it -> Unit.INSTANCE);
    }
}
