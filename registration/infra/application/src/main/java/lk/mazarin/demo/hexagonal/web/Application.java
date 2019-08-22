package lk.mazarin.demo.hexagonal.web;

import lk.mazarin.demo.hexagonal.persistence.H2DBConfiguration;
import lk.mazarin.demo.hexagonal.web.configuration.RestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Import({H2DBConfiguration.class, RestConfiguration.class})
public class Application {

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

}