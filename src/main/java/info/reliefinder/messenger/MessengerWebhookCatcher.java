package info.reliefinder.messenger;

import info.reliefinder.contact.ContactRepository;
import info.reliefinder.shift.ShiftRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/messenger")
class MessengerWebhookCatcher {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${facebook-messenger.messaging-url}")
    private String messengerUrl;

    @Value("${facebook-messenger.access-token}")
    private String messengerAccessToken;

    @PostMapping("/webhook")
    public Map<String, Object> catchMessengerWebhook(@RequestBody MessengerWebhook webhook) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        Messaging receivedMessaging = webhook.getEntry().get(0).getMessaging().get(0);

        Messaging message = Messaging.builder()
                .message(new Message(receivedMessaging.getMessage().getText()))
                .recipient(new MessengerUser(receivedMessaging.getSender().getId()))
                .build();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(messengerUrl)
                .queryParam("access_token", messengerAccessToken);

        restTemplate.postForEntity(uriComponentsBuilder.toUriString(), message, MessageResponse.class);

        return Collections.emptyMap();
    }

    @GetMapping("/webhook")
    public String handleValidationRequest(@RequestParam("hub.mode") String mode,
                                          @RequestParam("hub.challenge") String challenge,
                                          @RequestParam("hub.verify_token") String verifyToken) {
        if (mode.equals("subscribe") && verifyToken.equals("TOKEN")) {
            return challenge;
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

}
