package lk.mazarin.demo.hexagonal.registration.domain.user.types;

import lombok.Getter;

@Getter
public class Failure {
    public enum Type {
        UserRegistrationError,
        EmailVerificationErrorInvalidCode,
        RegistrationErrorEmailTaken,
        LoginError,
        EmailMustNotBeNull,
        EmailMustNotBeEmpty,
        EmailWithInvalidFormat,
        InvalidRange,
        NoUppercase,
        NoLowercase,
        NoDigit,
        VerificationCodeMustNotBeNull,
        PersistenceError
    }

    private Type type;
    private String message;

    public Failure(Type type, String message) {
        this.type = type;
        this.message = message;
    }
}
