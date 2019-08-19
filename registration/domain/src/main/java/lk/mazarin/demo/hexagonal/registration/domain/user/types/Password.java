package lk.mazarin.demo.hexagonal.registration.domain.user.types;

import io.vavr.control.Either;
import lk.mazarin.demo.hexagonal.registration.domain.shared.Validation;
import lombok.Getter;

import java.util.List;

import static lk.mazarin.demo.hexagonal.registration.domain.shared.ValidationService.*;

@Getter
public class Password {
    private final String rawPassword;

    public Password(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public static Either<List<Failure>, Password> validatePassword(Password password) {
        List<Validation<String>> validations = List.of(
                s -> rangeBetween(5, 50, new Failure(Failure.Type.InvalidRange, "Should between 5 and 50"), s.length()),
                s -> containLowercase(new Failure(Failure.Type.NoLowercase, "No lowercase letters found"), password.getRawPassword()),
                s -> containUppercase(new Failure(Failure.Type.NoUppercase, "No uppercase letters found"), password.getRawPassword()),
                s -> containDigit(new Failure(Failure.Type.NoDigit, "No digit found"), password.getRawPassword())
        );
        return validate(Password::new, validations, password.getRawPassword());
    }
}
