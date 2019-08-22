package lk.mazarin.demo.hexagonal.registration.domain.user.types;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InactiveUser extends User {
    @Builder(builderMethodName = "inactiveUser")
    public InactiveUser(Email email, Password password) {
        super(email, password);
    }
}
