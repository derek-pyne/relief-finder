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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/messenger")
@AllArgsConstructor
class MessengerWebhookCatcher {

    private final ShiftRepository shiftRepository;
    private final ContactRepository contactRepository;


    @PostMapping("/webhook")
    ResponseEntity<Map<String, Object>> catchMessengerWebhook(@RequestBody Map<String, Object> webhook) {
        log.info("Entering catchFormSubmission, webhook: {}", webhook);
        try {
            return ResponseEntity.ok(Collections.emptyMap());
        } finally {
            log.info("Exiting catchFormSubmission, webhook: {}", webhook);
        }
    }

    @GetMapping("/webhook")
    ResponseEntity<String> handleValidationRequest(@RequestParam("hub.mode") String mode,
                                                   @RequestParam("hub.challenge") String challenge,
                                                   @RequestParam("hub.verify_token") String verifyToken) {
        log.info("Entering handleValidationRequest, mode: {}, challenge: {}, verifyToken: {}", mode, challenge, verifyToken);
        try {
            if (mode.equals("subscribe") && verifyToken.equals("TOKEN")) {
                return ResponseEntity.ok(challenge);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } finally {
            log.info("Exiting handleValidationRequest, mode: {}, challenge: {}, verifyToken: {}", mode, challenge, verifyToken);
        }
    }

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
