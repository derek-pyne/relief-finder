package info.derek.relieffinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class ReliefFinderApplication {
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World5!";
    }
	public static void main(String[] args) {
		SpringApplication.run(ReliefFinderApplication.class, args);
	}
}
