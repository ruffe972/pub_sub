package ruffe972.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class Config {
    @Bean
    DataSource dataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/messaging");
        dataSource.setUsername("postgres");
        dataSource.setPassword("1");  // Only for exercises :)
        return dataSource;
    }

    @Bean
    RequestHandler requestHandler(Dao dao) {
        return new RequestHandler(new ObjectMapper(), dao);
    }

    @Bean
    Dao dao(DataSource dataSource) {
        return new Dao(new JdbcTemplate(dataSource));
    }
}
