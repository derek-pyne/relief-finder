package info.derek.relieffinder.shift;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ShiftRepositoryConfiguration {

    @Bean
    ShiftEventHandler shiftEventHandler() {
        return new ShiftEventHandler();
    }
}
