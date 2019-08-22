package lk.mazarin.demo.hexagonal.registration.domain;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.vavr.control.Either;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.api.UserService;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.NotificationService;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.UserRepo;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.*;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegistrationStepDef {
    private UserService userService;
    private UserRepo userRepo;
    NotificationService notificationService;
    Either<List<Failure>, Boolean> resp;

    @Given("(.*) is not already registered with demo web app")
    public void userIsNotAlreadyRegisteredWithDemoWebApp(String user) {
        userService = new lk.mazarin.demo.hexagonal.registration.domain.user.service.UserService();
        userRepo = Mockito.mock(UserRepo.class);
        Mockito.when(userRepo.addUser(Mockito.any(PendingUser.class))).thenReturn(Either.right(new PendingUserId(1)));
        notificationService = Mockito.mock(NotificationService.class);
        Mockito.when(notificationService.notifyEmailVerification(Mockito.any(Email.class), Mockito.any(VerificationCode.class))).thenReturn(Either.right(Boolean.TRUE));
    }

    @When("(.*) submits email - (.*) and password - (.*) as registration details")
    public void userSubmitsEmailAndPasswordAsRegistrationDetails(String user, String email, String password) {
        resp = userService.register(
                userRepo,
                notificationService,
                new PendingUser(
                        new Email(email),
                        new Password(password),
                        new VerificationCode("wejkljsdf"))
        );
    }

    @Then("John's account should be created in a pending state")
    public void johnSAccountShouldBeCreatedInAPendingState() {
        assertEquals(Boolean.TRUE, resp.get());
    }

    @And("A confirmation email should be sent to John")
    public void aConfirmationEmailShouldBeSentToJohn() {
        //verify email sent logs updated
    }

    @Given("{string} is already registered with demo web app using email {string}")
    public void johnIsAlreadyRegisteredWithDemoWebAppUsingEmail(String user, String email) {
        userService = new lk.mazarin.demo.hexagonal.registration.domain.user.service.UserService();
        userRepo = Mockito.mock(UserRepo.class);
        Mockito.when(userRepo.addUser(Mockito.any(PendingUser.class))).thenReturn(Either.left(List.of(new Failure(Failure.Type.RegistrationErrorEmailTaken, "email is already taken."))));
        notificationService = Mockito.mock(NotificationService.class);
        Mockito.when(notificationService.notifyEmailVerification(Mockito.any(Email.class), Mockito.any(VerificationCode.class))).thenReturn(Either.right(Boolean.TRUE));
    }

    @And("John is notified that his email already got registered")
    public void johnIsNotifiedThatHisEmailAlreadyGotRegistered() {

    }

    @Then("(.*)'s account should not be created")
    public void userSAccountShouldNotBeCreated(String user) {
        assertEquals(Boolean.FALSE, resp.getLeft().isEmpty());
    }

    @And("(.*) is notified that email is with wrong format")
    public void userIsNotifiedThatEmailIsWithWrongFormat(String user) {
        assertEquals(Failure.Type.EmailWithInvalidFormat, resp.getLeft().get(0).getType());
    }

    @Then("he should be informed with message code (.*)")
    public void heShouldBeInformedWithMessageCodeCode(String code) {
        assertTrue(resp.getLeft().stream().map(Failure::getType).collect(Collectors.toList()).contains(Failure.Type.valueOf(code)));
    }




//    @Given("^I have (\\d+) cukes in my belly$")
//    public void I_have_cukes_in_my_belly(int cukes) throws Throwable {
//
//    }
//
//    @Given("I have {int} cukes in my bellys")
//    public void iHaveCukesInMyBellys(int arg0) {
//    }
//
//    @Given("a valid user email")
//    public void aValidUserEmail() {
//        Email email = new Email("chal@mazarin.lk");
//    }
//
//    @And("a strong password with more than {int} chars, and at least contain {int} number, a uppercase letter and {int} lowercase letter")
//    public void aStrongPasswordWithMoreThanCharsAndAtLeastContainNumberAUppercaseLetterAndLowercaseLetter(int arg0, int arg1, int arg2) {
//        Password password = new Password("12345");
//        Password.validatePassword(password);
//    }
}
