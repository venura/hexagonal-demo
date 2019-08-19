package lk.mazarin.demo.hexagonal.registration.domain.user.types;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class UserId {

    @NonNull
    private final Long id;

}
