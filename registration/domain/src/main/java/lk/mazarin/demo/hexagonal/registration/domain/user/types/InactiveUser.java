package lk.mazarin.demo.hexagonal.registration.domain.user.types;

public class InactiveUser extends User {
    public InactiveUser(Email email, Password password) {
        super(email, password);
    }
}
