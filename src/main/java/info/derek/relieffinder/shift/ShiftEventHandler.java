package info.derek.relieffinder.shift;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@Slf4j
@RepositoryEventHandler
class ShiftEventHandler {

    @HandleBeforeCreate
    void handleBeforeCreate(Shift shift) {
        log.info("Creating new shift: {}", shift);
    }
}
