package lk.mazarin.demo.hexagonal.registration.domain.user.types;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class PendingUserId {

    private final Number id;

}
