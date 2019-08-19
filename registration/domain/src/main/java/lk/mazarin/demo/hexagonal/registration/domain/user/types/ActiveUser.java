package lk.mazarin.demo.hexagonal.registration.domain.user.types;

public class ActiveUser extends User {

    public ActiveUser(Email email, Password password) {
        super(email, password);
    }
}
