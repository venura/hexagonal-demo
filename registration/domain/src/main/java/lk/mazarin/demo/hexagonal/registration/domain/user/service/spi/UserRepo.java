package lk.mazarin.demo.hexagonal.registration.domain.user.service.spi;

import io.vavr.control.Either;
import io.vavr.control.Option;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.*;

import java.util.List;


public interface UserRepo {
    Either<List<Failure>, PendingUserId> addUser(PendingUser user);

    Either<Failure, ActiveUser> setEmailAsVerified(Email email,VerificationCode code);

    Option<User> findUser(UserId userId);

    Option<User> findPendingUser(PendingUserId id);

}
