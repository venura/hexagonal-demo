package lk.mazarin.demo.hexagonal.web;

import lk.mazarin.demo.hexagonal.web.configuration.RestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Import({RestConfiguration.class})
public class TestApplication {

    public static void main(String args[]) {
        SpringApplication.run(TestApplication.class, args);
    }

}