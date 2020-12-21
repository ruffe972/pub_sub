package ruffe972.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Generates and sends json messages to the server.
 */
@SpringBootApplication
public class PublisherApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(PublisherApplication.class);
    private final MessagePublisher messagePublisher;

    PublisherApplication(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    public static void main(String[] args) {
        SpringApplication.run(PublisherApplication.class, args);
    }

    @Override
    public void run(String... args) {
        messagePublisher
                .publishMessages()
                .onErrorContinue((e, it) -> logger.error(e.getMessage()))
                .subscribe(it -> logger.trace("A request has been sent successfully."));
        waitIndefinitely();
    }

    /**
     * Introduces a deliberate deadlock.
     */
    private static void waitIndefinitely() {
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}