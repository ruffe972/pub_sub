package ruffe972.subscriber;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * For purchase and subscription actions.
 */
@Configuration
public class Router {
    @Bean
    RouterFunction<ServerResponse> route(RequestHandler handler) {
        var predicate = RequestPredicates.POST("")
                .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON));
        return RouterFunctions.route(predicate, handler::handle);
    }
}
