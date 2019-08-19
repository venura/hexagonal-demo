package lk.mazarin.demo.hexagonal.web.configuration;

import lk.mazarin.demo.hexagonal.persistence.respository.UserDBRepository;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.api.UserService;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.NotificationService;
import lk.mazarin.demo.hexagonal.web.controller.UserRegistrationController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfiguration {

    @Bean
    public UserRegistrationController userRegistrationController(UserDBRepository userDBRepository, UserService userService, NotificationService notificationService) {
        return new UserRegistrationController(userDBRepository, userService, notificationService);
    }
}
