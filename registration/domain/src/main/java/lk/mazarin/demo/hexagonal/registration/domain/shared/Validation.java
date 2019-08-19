package lk.mazarin.demo.hexagonal.registration.domain.shared;

import lk.mazarin.demo.hexagonal.registration.domain.user.types.Failure;

import java.util.Optional;

@FunctionalInterface
public interface Validation<T> {
    Optional<Failure> validate(T t);

}
