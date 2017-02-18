package es.org.ganchix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class SlackCommandLaunchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlackCommandLaunchApplication.class, args);
    }

    @RequestMapping("/")
    public String hello() {
        return "Hello world";
    }

}
