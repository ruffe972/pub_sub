package ruffe972.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 * Generates and sends json messages to the server.
 */
@SpringBootApplication
public class PublisherApplication implements CommandLineRunner {
	private final static String QUIT_COMMAND = "q";
	private static final Logger logger = LoggerFactory.getLogger(PublisherApplication.class);
    private final MessageGenerator messageGenerator;
    private final MessagePublisher messagePublisher;

    PublisherApplication(MessageGenerator messageGenerator, MessagePublisher messagePublisher) {
        this.messageGenerator = messageGenerator;
        this.messagePublisher = messagePublisher;
    }

    public static void main(String[] args) {
        SpringApplication.run(PublisherApplication.class, args);
    }

    /**
     * Message is sent on user request.
     */
    @Override
    public void run(String... args) {
        System.out.printf(
                "%sEnter command 'p' to publish a message. 'q' - quit.%s",
                System.lineSeparator(),
                System.lineSeparator());
        try (var scanner = new Scanner(System.in)) {
            String command;
            do {
                command = scanner.nextLine();
                runCommand(command);
            } while (!command.equals(QUIT_COMMAND));
        }
    }

    /**
     * @param command the command entered by user. For example, "p".
     */
    private void runCommand(String command) {
        if (command.equals(QUIT_COMMAND)) {
            System.out.println("Goodbye.");
        } else if (command.equals("p")) {
			runPublishCommand();
        } else {
            System.out.println("Command is not recognized.");
        }
    }

	/**
	 * Sends a message to the server.
	 */
	private void runPublishCommand() {
		String message = messageGenerator.generate();
		logger.info("Sending message to server: {}.", message);
		messagePublisher.publish(message).subscribe(
				it -> System.out.println("Success."),
				e -> {
					System.out.println("Failure.");
					logger.error(e.getMessage());
				});
	}
}