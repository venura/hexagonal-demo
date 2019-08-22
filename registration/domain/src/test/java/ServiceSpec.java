import io.vavr.control.Either;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.UserService;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.NotificationService;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.UserRepo;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.*;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ServiceSpec {

    @Test
    public void should_notify_email_upon_successful_registration() {
        lk.mazarin.demo.hexagonal.registration.domain.user.service.api.UserService userService = new UserService();
        var pendingUser = new PendingUser(
                new Email("chal@sd.lk"),
                new Password("sdfd34"),
                new VerificationCode("wejkljsdf"));
        UserRepo userRepo = Mockito.mock(UserRepo.class);
        Mockito.when(userRepo.addUser(Mockito.any(PendingUser.class))).thenReturn(Either.right(new PendingUserId(1)));
        NotificationService notificationService = Mockito.mock(NotificationService.class);
        Mockito.when(notificationService.notifyEmailVerification(Mockito.any(Email.class), Mockito.any(VerificationCode.class))).thenReturn(Either.right(Boolean.TRUE));
        var resp = userService.register(
                userRepo,
                notificationService,pendingUser

                );
        assertEquals(Boolean.TRUE, resp.get());
    }


    @Test
    public void should_fail_when_email_is_duplicated() {
        lk.mazarin.demo.hexagonal.registration.domain.user.service.api.UserService userService = new UserService();
        UserRepo userRepo = Mockito.mock(UserRepo.class);
        Mockito.when(userRepo.addUser(Mockito.any(PendingUser.class))).thenReturn(Either.left(List.of(new Failure(Failure.Type.RegistrationErrorEmailTaken, "email is already taken."))));
        NotificationService notificationService = Mockito.mock(NotificationService.class);
        Mockito.when(notificationService.notifyEmailVerification(Mockito.any(Email.class), Mockito.any(VerificationCode.class))).thenReturn(Either.right(Boolean.TRUE));
        var resp = userService.register(userRepo, notificationService, new PendingUser(new Email("chal@sd.lk"), new Password("sdfd123"), new VerificationCode("wer")));
        assertEquals(Failure.Type.RegistrationErrorEmailTaken, resp.getLeft().get(0).getType());
    }


}
