package lk.mazarin.demo.hexagonal.registration.domain.user.service.api;

import io.vavr.control.Either;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.NotificationService;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.UserRepo;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.Failure;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.PendingUser;

import java.util.List;

public interface UserService {
    default Either<List<Failure>, Boolean> register(UserRepo repo, NotificationService notificationService, PendingUser user) {
        var pendingUser = PendingUser.validatePendingUser(user);
        return pendingUser.isRight() ?
                repo.addUser(pendingUser.get())
                        .flatMap(verificationCode -> notificationService
                                .notifyEmailVerification(user.getEmail(), verificationCode)
                        )
                :
                Either.left(pendingUser.getLeft());
    }
}
