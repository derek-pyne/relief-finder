package info.derek.relieffinder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ReliefFinderApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReliefFinderApplication.class, args);
	}
}
