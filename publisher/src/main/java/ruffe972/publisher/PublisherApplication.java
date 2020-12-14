package ruffe972.publisher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 * Generates and sends json messages to the server.
 */
@SpringBootApplication
public class PublisherApplication implements CommandLineRunner {
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
		// todo di?
		try (var scanner = new Scanner(System.in)) {
			boolean shouldQuit;
			do {
				String command = scanner.nextLine();
				shouldQuit = runCommand(command);
			} while (!shouldQuit);
		}
	}

	/**
	 * @param command the command entered by user. For example, "p".
	 * @return true for "q" command.
	 */
	private boolean runCommand(String command) {
		if (command.equals("q")) {
			return true;
		} else if (command.equals("p")) {
			boolean status = messagePublisher.publish(messageGenerator.generate());
			System.out.println(status ? "Failure." : "Success.");
		} else {
			System.out.println("Command is not recognized.");
		}
		return false;
	}
}