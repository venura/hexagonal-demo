package lk.mazarin.demo.hexagonal.registration.domain.user.types;

import io.vavr.control.Either;
import lk.mazarin.demo.hexagonal.registration.domain.shared.Validation;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

import static lk.mazarin.demo.hexagonal.registration.domain.shared.ValidationService.validate;

@Getter
public class VerificationCode {
    private final String code;

    public VerificationCode(String code) {
        this.code = code;
    }

    public static Either<List<Failure>, VerificationCode> validateVerificationCode(VerificationCode code) {
        List<Validation<String>> validations = List.of(
                s -> s == null ? Optional.of(new Failure(Failure.Type.VerificationCodeMustNotBeNull, "verification code must not be null.")) : Optional.empty()
        );
        return validate(VerificationCode::new, validations, code.getCode());
    }
}
