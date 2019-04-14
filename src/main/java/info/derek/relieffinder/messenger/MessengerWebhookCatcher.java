package info.derek.relieffinder.messenger;

import info.derek.relieffinder.contact.Contact;
import info.derek.relieffinder.contact.ContactRepository;
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

import java.time.Instant;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "messenger")
@AllArgsConstructor
class MessengerWebhookCatcher {

    private final ShiftRepository shiftRepository;
    private final ContactRepository contactRepository;

//    @PostMapping
//    ResponseEntity<Shift> catchMessengerWebhook(@RequestBody MessengerWebhook webhook) {
//        log.info("Entering catchFormSubmission, webhook: {}", webhook);
//        try {
//
//            ShiftCreationForm shiftCreationForm = webhook.getData();
//            Optional<Contact> posterOptional = contactRepository.findByEmail(shiftCreationForm.getEmail());
//
//            Contact poster;
//            if (posterOptional.isPresent()) {
//                poster = posterOptional.get();
//                log.info("Found existing contact for poster: {}", poster);
//            } else {
//                Contact newContact = Contact.builder()
//                        .email(shiftCreationForm.getEmail())
//                        .fullName(shiftCreationForm.getName())
//                        .build();
//                publisher.publishEvent(new BeforeCreateEvent(newContact));
//                Contact newContactSaved = contactRepository.save(newContact);
//                publisher.publishEvent(new AfterCreateEvent(newContactSaved));
//
//                log.info("Created new contact for poster: {}", newContactSaved);
//                poster = newContactSaved;
//            }
//
//            Shift shift = Shift.builder()
//                    .poster(poster)
//                    .startTime(Instant.now())
//                    .endTime(Instant.now())
//                    .build();
//
//            publisher.publishEvent(new BeforeCreateEvent(shift));
//            Shift savedShift = shiftRepository.save(shift);
//            publisher.publishEvent(new AfterCreateEvent(shift));
//
//            ShiftCMS shiftCMS = cmsManager.postShift(savedShift);
//            log.info("Saved shiftCMS: {}", shiftCMS);
//
//            return ResponseEntity.ok(savedShift);
//        } finally {
//            log.info("Exiting catchFormSubmission, webhook: {}", webhook);
//        }
//    }

}
