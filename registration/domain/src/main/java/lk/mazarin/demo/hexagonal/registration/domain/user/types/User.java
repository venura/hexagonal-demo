package lk.mazarin.demo.hexagonal.registration.domain.user.types;

public class User {
    private Email email;
    private Password password;

    protected User(Email email, Password password) {
        this.email = email;
        this.password = password;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }
}
