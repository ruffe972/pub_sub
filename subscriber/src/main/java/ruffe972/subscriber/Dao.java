package ruffe972.subscriber;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Thread-safe. DAO for purchase and subscription actions.
 */
@Component
public class Dao {
    private final JdbcTemplate jdbcTemplate;

    public Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @param message data from message is written to the databases.
     */
    void create(MessageDto message) {
        String tableName = message.action.toLowerCase();
        var sql = String.format("insert into %s values (default, ?, ?)", tableName);
        jdbcTemplate.update(
                sql,
                message.msisdn,
                new Timestamp(message.timestamp * 1000));
    }
}
