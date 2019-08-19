package lk.mazarin.demo.hexagonal.persistence.service;

import io.vavr.control.Either;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.NotificationService;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.Email;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.Failure;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.VerificationCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockEmailNotificationService implements NotificationService {
    @Override
    public Either<List<Failure>, Boolean> notifyEmailVerification(Email email, VerificationCode verificationCode) {
        System.out.println("sendng mail to " + email.getRawEmail());
        return Either.right(Boolean.TRUE);
    }
}
