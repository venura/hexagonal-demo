package lk.mazarin.demo.hexagonal.registration.domain.user.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    protected final Email email;
    protected final Password password;
}
