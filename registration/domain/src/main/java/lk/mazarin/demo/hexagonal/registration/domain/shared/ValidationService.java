package lk.mazarin.demo.hexagonal.registration.domain.shared;

import io.vavr.API;
import io.vavr.control.Either;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.Email;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.Failure;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.Password;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.VerificationCode;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;
import static lk.mazarin.demo.hexagonal.registration.domain.user.types.Email.validateEmail;
import static lk.mazarin.demo.hexagonal.registration.domain.user.types.Password.validatePassword;
import static lk.mazarin.demo.hexagonal.registration.domain.user.types.VerificationCode.validateVerificationCode;

public class ValidationService {
    private static final Pattern[] inputRegexes = new Pattern[4];

    private static final Pattern upperCasePattern = Pattern.compile(".*[A-Z].*");
    private static final Pattern lowerCasePattern = Pattern.compile(".*[a-z].*");
    private static final Pattern digitPattern = Pattern.compile(".*\\d.*");
    private static final Pattern specialCharacter = Pattern.compile(".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*");
    public static Either<List<Failure>, ?> validate(Object value) {
        return
                API.Match(
                        value
                ).of(
                        Case($(instanceOf(Email.class)), validateEmail((Email) value)),
                        Case($(instanceOf(Password.class)), validatePassword((Password) value)),
                        Case($(instanceOf(VerificationCode.class)), validateVerificationCode((VerificationCode) value))
                );
    }

    public static <U, V> Either<List<Failure>, U> validate(Function<V, U> constructor, List<Validation<V>> validations, V value) {
        var validated = validations.stream()
                .map(validator -> validator.validate(value))
                .filter(Optional::isPresent)
                .collect(Collectors.toList());
        return API.Match(
                validated.size()
        ).of(
                Case($(0), Either.right(constructor.apply(value))),
                Case($(n -> n > 0), Either.left(validated.stream()
                        .map(Optional::get)
                        .collect(Collectors.toList()))
                )
        );

    }

    public static Optional<Failure> rangeBetween(int min, int max, Failure err, int value) {
        return value >= min && value <= max ? Optional.empty() : Optional.of(err);
    }

    public static Optional<Failure> containLowercase(Failure err, String value) {
        return lowerCasePattern.matcher(value).matches() ? Optional.empty() : Optional.of(err);
    }

    public static Optional<Failure> containUppercase(Failure err, String value) {
        return upperCasePattern.matcher(value).matches() ? Optional.empty() : Optional.of(err);
    }

    public static Optional<Failure> containDigit(Failure err, String value) {
        return digitPattern.matcher(value).matches() ? Optional.empty() : Optional.of(err);
    }

}
