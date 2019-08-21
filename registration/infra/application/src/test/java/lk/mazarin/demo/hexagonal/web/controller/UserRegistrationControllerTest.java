package lk.mazarin.demo.hexagonal.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import lk.mazarin.demo.hexagonal.persistence.respository.UserDBRepository;
import lk.mazarin.demo.hexagonal.persistence.service.MockEmailNotificationService;
import lk.mazarin.demo.hexagonal.persistence.service.UserServiceImpl;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.api.UserService;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.NotificationService;
import lk.mazarin.demo.hexagonal.registration.domain.user.service.spi.UserRepo;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.Failure;
import lk.mazarin.demo.hexagonal.registration.domain.user.types.PendingUser;
import lk.mazarin.demo.hexagonal.web.TestApplication;
import lk.mazarin.demo.hexagonal.web.resource.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserRegistrationControllerTest.Configuration.class, TestApplication.class})
@AutoConfigureMockMvc
public class UserRegistrationControllerTest {

    @org.springframework.context.annotation.Configuration
    static class Configuration {

        @MockBean
        private UserDBRepository userDBRepository;

        @MockBean
        private MockEmailNotificationService notificationService;

        @MockBean
        private UserServiceImpl userService;
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    public void test() throws Exception {
        Either<List<Failure>, Boolean> result = Either.left(Arrays.asList(new Failure(Failure.Type.NoUppercase, "No uppercase letters found")));
        doReturn(result)
                .when(userService)
                .register(any(UserDBRepository.class), any(NotificationService.class), any(PendingUser.class));


        mockMvc.perform(post("/user/create")
                .contentType("application/json")
                .content(
                        objectMapper.writeValueAsString(
                                new User(
                                        "chal@mazarin.lk",
                                        "jkl1234",
                                        "sfddf"))))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.errors")
                                .value("No uppercase letters found")
                )

        ;
    }

}
