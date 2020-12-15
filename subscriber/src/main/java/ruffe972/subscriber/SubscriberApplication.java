package ruffe972.subscriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Listens to the client requests and reacts by writing data to the databases.
 */
@SpringBootApplication
public class SubscriberApplication {
    public static void main(String[] args) {
        SpringApplication.run(SubscriberApplication.class, args);
    }
}
