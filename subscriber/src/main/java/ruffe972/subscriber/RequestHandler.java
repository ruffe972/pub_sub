package ruffe972.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Thread-safe. Handles requests with purchase/subscription actions from the client.
 */
public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final ObjectMapper objectMapper;
    private final Dao dao;

    RequestHandler(ObjectMapper objectMapper, Dao dao) {
        this.objectMapper = objectMapper;
        this.dao = dao;
    }

    Mono<ServerResponse> handle(ServerRequest request) {
        return request
                .bodyToMono(String.class)
                .doOnNext(it -> logger.info("Got a client request. Body: {}.", it))
                .map(this::messageFromString)
                .doOnNext(it -> it.ifPresent(
                        clientMessage -> dao.create(clientMessage.toDaoMessage())
                ))
                .map(message -> message.isEmpty()
                        ? ServerResponse.badRequest()
                        : ServerResponse.ok())
                .flatMap(ServerResponse.HeadersBuilder::build)
                .doOnNext(it -> logger.info("Responding with a status code: {}.", it.statusCode()));
    }

    /**
     * Transforms Json into ClientMessage
     * @return 'empty' for incorrect input
     */
    private Optional<ClientMessage> messageFromString(String string) {
        try {
            var builder = Optional.ofNullable(
                    objectMapper.readValue(string, ClientMessage.Builder.class));
            return builder.flatMap(ClientMessage.Builder::build);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}

