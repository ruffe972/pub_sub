package ruffe972.subscriber;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;

/**
 * Thread-safe. DAO for purchase and subscription actions.
 */
public class Dao {
    private final JdbcTemplate jdbcTemplate;

    public Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Data for writing to the databases.
     */
    static class Message {
        public final int msisdn;
        public final String tableName;

        /**
         * In seconds.
         */
        public final long timestamp;

        Message(int msisdn, String tableName, long timestamp) {
            this.msisdn = msisdn;
            this.tableName = tableName;
            this.timestamp = timestamp;
        }
    }

    /**
     * @param message writes data from message to the databases.
     */
    void create(Message message) {
        var sql = String.format("insert into %s values (default, ?, ?)", message.tableName);
        jdbcTemplate.update(
                sql,
                message.msisdn,
                new Timestamp(message.timestamp * 1000));
    }
}
