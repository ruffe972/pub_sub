package ruffe972.subscriber;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.sql.Timestamp;

/**
 * Thread-safe. DAO for purchase and subscription actions.
 */
@Component
public class Dao {
    private final JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unused")
    Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @param message data from message is written to the databases.
     */
    Mono<Void> create(MessageDto message) {
        String tableName = message.action.toLowerCase();
        var sql = String.format("insert into %s values (default, ?, ?, ?)", tableName);
        var mono = Mono.<Void>fromRunnable(() -> jdbcTemplate.update(
                sql,
                message.messageId,
                message.msisdn,
                new Timestamp(message.timestamp * 1000)));
        mono.subscribeOn(Schedulers.boundedElastic());
        return mono;
    }
}
