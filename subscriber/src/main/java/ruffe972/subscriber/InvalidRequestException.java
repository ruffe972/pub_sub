package ruffe972.subscriber;

import reactor.util.annotation.Nullable;

public class InvalidRequestException extends RuntimeException {
    InvalidRequestException(@Nullable Throwable cause) {
        super("Invalid request.", cause);
    }
}
