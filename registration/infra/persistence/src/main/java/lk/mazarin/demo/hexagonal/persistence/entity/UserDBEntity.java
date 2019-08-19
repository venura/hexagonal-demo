package lk.mazarin.demo.hexagonal.persistence.entity;

import lk.mazarin.demo.hexagonal.registration.domain.user.types.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.vavr.API.*;

@NoArgsConstructor
@Data
public class UserDBEntity {
    public enum UserState {
        Active,
        Inactive
    }

    Long userId;
    String email;
    String password;
    String userState;

    public User toDomainModel() {
        return Match(userState).of(
                Case($(UserState.Active.name()), this::toActiveUser),
                Case($(UserState.Inactive.name()), this::toInactiveUser)
        );
    }

    private InactiveUser toInactiveUser() {
        return new InactiveUser(Email.validateEmail(new Email(email)).get(), Password.validatePassword(new Password(password)).get());
    }

    private ActiveUser toActiveUser() {
        return new ActiveUser(Email.validateEmail(new Email(email)).get(), Password.validatePassword(new Password(password)).get());
    }
}
