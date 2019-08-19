package lk.mazarin.demo.hexagonal.web.controller;

import lk.mazarin.demo.hexagonal.persistence.respository.UserDBRepository;
import lk.mazarin.demo.hexagonal.persistence.service.UserServiceImpl;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.api.UserService;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.NotificationService;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserRegistrationController {

    private final UserDBRepository userDBRepository;

    private final UserService userService;

    private final NotificationService notificationService;

    public UserRegistrationController(UserDBRepository userDBRepository, UserService userService, NotificationService notificationService1) {
        this.userDBRepository = userDBRepository;
        this.userService = userService;
        this.notificationService = notificationService1;
    }

    @GetMapping("/create")
    public String getActiveUser(@RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String verificationCode) {
        //User user = userDBRepository.findUser(new UserId(id)).getOrElseThrow(() -> new RuntimeException(String.format("User with id: %d not found", id)));
        var result = userService.register(userDBRepository,notificationService, new PendingUser(new Email(email),new Password(password),new VerificationCode(verificationCode)));
        if(result.isLeft()){
            result.getLeft().forEach( s -> System.out.println(s.getMessage()));
        }
        return "";
    }
}
