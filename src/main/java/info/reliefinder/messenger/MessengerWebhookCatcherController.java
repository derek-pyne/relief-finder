package info.reliefinder.messenger;

import info.reliefinder.conversation.ConversationMessage;
import info.reliefinder.conversation.ConversationService;
import info.reliefinder.conversation.UserType;
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
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/messenger")
class MessengerWebhookCatcherController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${facebook-messenger.messaging-url}")
    private String messengerUrl;

    @Value("${facebook-messenger.access-token}")
    private String messengerAccessToken;

    @PostMapping("/webhook")
    public Map<String, Object> catchMessengerWebhook(@RequestBody MessengerWebhook webhook) {

        log.info("Received webhook: {}", webhook);
        RestTemplate restTemplate = restTemplateBuilder.build();
        Messaging receivedMessaging = webhook.getEntry().get(0).getMessaging().get(0);

        ConversationMessage userResponse = new ConversationMessage(
                receivedMessaging.getMessage().getText(), UserType.USER, receivedMessaging.getTimestamp());
        List<ConversationMessage> conversationMessages = conversationService.handleConversationResponse(receivedMessaging.getSender().getId(), userResponse);

        log.info("Returning responses: {}", conversationMessages);
        conversationMessages.forEach(
                serviceResponse -> {
                    Messaging message = Messaging.builder()
                            .message(new Message(serviceResponse.getText()))
                            .recipient(new MessengerUser(receivedMessaging.getSender().getId()))
                            .build();

                    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(messengerUrl)
                            .queryParam("access_token", messengerAccessToken);

                    restTemplate.postForEntity(uriComponentsBuilder.toUriString(), message, MessageResponse.class);
                }
        );

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
