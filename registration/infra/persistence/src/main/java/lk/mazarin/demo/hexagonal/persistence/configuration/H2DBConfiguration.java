package lk.mazarin.demo.hexagonal.persistence.configuration;

import lk.mazarin.demo.hexagonal.persistence.respository.UserDBRepository;
import lk.mazarin.demo.hexagonal.persistence.service.MockEmailNotificationService;
import lk.mazarin.demo.hexagonal.persistence.service.UserServiceImpl;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.api.UserService;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class H2DBConfiguration {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:jdbc/schema.sql")
                .addScript("classpath:jdbc/test-data.sql")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public UserDBRepository userDBRepository(JdbcTemplate jdbcTemplate) {
        return new UserDBRepository(jdbcTemplate);
    }

    @Bean
    public NotificationService notificationService() {
        return new MockEmailNotificationService();
    }

    @Bean
    public UserService userService(){
        return new UserServiceImpl();
    }
}
