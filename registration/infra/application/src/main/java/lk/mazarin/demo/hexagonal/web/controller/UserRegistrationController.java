package lk.mazarin.demo.hexagonal.web.controller;

import lk.mazarin.demo.hexagonal.registration.domain.user.service.api.UserService;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.NotificationService;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.UserRepo;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.*;
import lk.mazarin.demo.hexagonal.web.resource.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@RestController
@RequestMapping("user")
public class UserRegistrationController {
    private static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class);

    private final UserRepo userRepo;

    private final UserService userService;

    private final NotificationService notificationService;

    public UserRegistrationController(UserRepo userRepo, UserService userService, NotificationService notificationService1) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.notificationService = notificationService1;
    }

    @PostMapping("/create")
    public ResponseEntity register(@Valid @RequestBody User userResource) {
        var result = userService.register(userRepo, notificationService,
                new PendingUser(
                        new Email(userResource.getEmail()),
                        new Password(userResource.getPassword()),
                        new VerificationCode(userResource.getVerificationCode())
                )
        );
        if (result.isRight()) {
            ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(result.get());
        }
        result.getLeft().forEach(s -> log.error(s.getMessage()));
        return ResponseEntity
                .status(SC_BAD_REQUEST)
                .body(Map.of(
                        "errors", result.getLeft()
                                .stream().map(Failure::getMessage)
                                .collect(Collectors.toList())
                ));
    }

}
