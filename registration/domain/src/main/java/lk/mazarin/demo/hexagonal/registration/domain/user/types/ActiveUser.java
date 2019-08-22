package lk.mazarin.demo.hexagonal.registration.domain.user.types;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ActiveUser extends User {

    @Builder(builderMethodName = "activeUser")
    public ActiveUser(Email email, Password password) {
        super(email, password);
    }
}
