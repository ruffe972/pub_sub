package ruffe972.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Thread-safe. Handles requests with purchase/subscription actions from the client.
 */
@Component
public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Dao dao;

    RequestHandler(Dao dao) {
        this.dao = dao;
    }

    Mono<ServerResponse> handle(ServerRequest request) {
        return request
                .bodyToMono(String.class)
                .doOnNext(it -> logger.info("Got a client request. Body: {}.", it))
                .map(this::messageFromString)
                .doOnError(e -> logger.error(e.getMessage()))
                .doOnNext(messageDto -> logger.info("Request is valid."))
                .flatMap(dao::create)
                .map(it -> ServerResponse.ok())
                .onErrorReturn(e -> e instanceof InvalidRequestException,
                        ServerResponse.badRequest())
                .flatMap(ServerResponse.HeadersBuilder::build);
    }

    /**
     * Transforms Json into MessageDto.
     * @throws InvalidRequestException if the request body is invalid.
     */
    private MessageDto messageFromString(String string) {
        try {
            var messageDto = objectMapper.readValue(string, MessageDto.class);
            if (!isValid(messageDto)) {
                throw new InvalidRequestException(null);
            }
            return messageDto;
        } catch (JsonProcessingException e) {
            throw new InvalidRequestException(e);
        }
    }

    private static boolean isValid(MessageDto messageDto) {
        return messageDto.msisdn >= 0
                && messageDto.action.equals("PURCHASE") || messageDto.action.equals("SUBSCRIPTION")
                && messageDto.timestamp >= 0;
    }
}

