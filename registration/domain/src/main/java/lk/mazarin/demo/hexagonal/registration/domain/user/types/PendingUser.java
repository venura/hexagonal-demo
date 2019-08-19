package lk.mazarin.demo.hexagonal.registration.domain.user.types;

import io.vavr.control.Either;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;


@Getter
public class PendingUser extends User {
    private final VerificationCode verificationCode;

    public PendingUser(Email email, Password password, VerificationCode verificationCode) {
        super(email, password);
        this.verificationCode = verificationCode;
    }

    public static Either<List<Failure>, PendingUser> validatePendingUser(PendingUser pendingUser) {
        var inputParams = List.of(
                Email.validateEmail(pendingUser.getEmail()),
                Password.validatePassword(pendingUser.getPassword()),
                VerificationCode.validateVerificationCode(pendingUser.getVerificationCode()));


        var leftCollection = inputParams.stream()
                .filter( x -> x.isLeft())
                .map(x -> x.getLeft())
                .flatMap(x -> x.stream())
                .distinct()
                .collect(Collectors.toList());

        return leftCollection.size() > 0 ? Either.left(leftCollection) : Either.right(pendingUser);
    }
}
