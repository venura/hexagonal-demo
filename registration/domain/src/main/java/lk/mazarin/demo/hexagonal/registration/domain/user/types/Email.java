package lk.mazarin.demo.hexagonal.registration.domain.user.types;

import io.vavr.control.Either;
import lk.mazarin.demo.hexagonal.registration.domain.shared.Validation;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static lk.mazarin.demo.hexagonal.registration.domain.shared.ValidationService.validate;

@EqualsAndHashCode
@Getter
public class Email {
    private static Pattern emailPattern =
            Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
    private final String rawEmail;

    public Email(String rawEmail) {
        this.rawEmail = rawEmail;
    }

    public static Either<List<Failure>, Email> validateEmail(Email email) {
        List<Validation<String>> validations = List.of(
                s -> s == null ? Optional.of(new Failure(Failure.Type.EmailMustNotBeNull, "email " + s + " is invalid.")) : Optional.empty(),
                s -> s.length() == 0 ? Optional.of(new Failure(Failure.Type.EmailMustNotBeEmpty, "email " + s + " is invalid.")) : Optional.empty(),
                s -> emailPattern.matcher(s).matches() ? Optional.empty() : Optional.of(new Failure(Failure.Type.EmailWithInvalidFormat, "email " + s + " is invalid."))

        );
        return validate(Email::new, validations, email.getRawEmail());
    }

}
