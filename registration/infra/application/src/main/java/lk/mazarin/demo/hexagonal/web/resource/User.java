package lk.mazarin.demo.hexagonal.web.resource;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;


public class User implements Serializable {
    private String email;
    private String password;
    private String verificationCode;

    public User() {
    }

    public User(String email, String password, String verificationCode) {
        this.email = email;
        this.password = password;
        this.verificationCode = verificationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
