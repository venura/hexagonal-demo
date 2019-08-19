package lk.mazarin.demo.hexagonal.web;

import lk.mazarin.demo.hexagonal.persistence.configuration.H2DBConfiguration;
import lk.mazarin.demo.hexagonal.persistence.respository.UserDBRepository;
import lk.mazarin.demo.hexagonal.web.configuration.RestConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@Import({H2DBConfiguration.class, RestConfiguration.class})
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

}