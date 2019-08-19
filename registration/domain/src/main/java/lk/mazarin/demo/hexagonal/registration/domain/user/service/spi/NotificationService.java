package lk.mazarin.demo.hexagonal.registration.domain.user.service.spi;

import io.vavr.control.Either;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.Email;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.Failure;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.VerificationCode;

import java.util.List;

public interface NotificationService {
    Either<List<Failure>, Boolean> notifyEmailVerification(Email email, VerificationCode verificationCode);
}
