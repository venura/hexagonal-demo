package lk.mazarin.demo.hexagonal.persistence;


import lk.mazarin.demo.hexagonal.persistence.respository.UserDBRepository;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {H2DBConfiguration.class})
public class UserDBRepositoryTest {

    @Autowired
    private UserDBRepository userDBRepository;

    @Test
    public void it_should_save_user() {
        var pendingUser = userDBRepository.addUser(new PendingUser(new Email("chal2@mazarin.lk"), new Password("s3cr3T"), new VerificationCode("vikdfkcGjklsd78dfk")));
        var user = userDBRepository.findUser(new Email("chal2@mazarin.lk"));
        assertEquals(pendingUser.get().getId(), 2);
    }

    @Test
    public void it_should_read_user() {
        var user = userDBRepository.findUser(new UserId(1L));
        assertNotNull("User should not null or should retrieved by id", user.get());
    }

    @Test
    public void it_should_read_pending_user_by_id() {
        var user = userDBRepository.findPendingUser(new PendingUserId(1L));
        assertNotNull("Pending user with id: 1 should be available", user.get());
    }

    @Test
    public void it_should_read_pending_user_by_email() {
        var user = userDBRepository.findUser(new Email("john@mazarin.lk"));
        assertNotNull("Pending user with id: 1 should be available", user.get());
    }

}