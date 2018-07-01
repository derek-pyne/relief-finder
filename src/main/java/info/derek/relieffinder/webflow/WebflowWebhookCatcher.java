package info.derek.relieffinder.webflow;

import info.derek.relieffinder.shift.Shift;
import info.derek.relieffinder.shift.ShiftRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.rest.core.event.AfterCreateEvent;
import org.springframework.data.rest.core.event.BeforeCreateEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "webflow")
@AllArgsConstructor
class WebflowWebhookCatcher implements ApplicationEventPublisherAware {

    private final ShiftRepository shiftRepository;
    private ApplicationEventPublisher publisher;

    @PostMapping
    ResponseEntity<Shift> catchFormSubmission(@RequestBody WebflowWebhook webhook) {
        log.info("Entering catchFormSubmission, webhook: {}", webhook);
        try {
            Shift shift = new Shift();

            publisher.publishEvent(new BeforeCreateEvent(shift));
            Shift savedShift = shiftRepository.save(shift);
            publisher.publishEvent(new AfterCreateEvent(shift));

            return ResponseEntity.ok(savedShift);
        } finally {
            log.info("Exiting catchFormSubmission, webhook: {}", webhook);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        publisher = applicationEventPublisher;
    }
}
