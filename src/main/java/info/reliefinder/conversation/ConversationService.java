package info.reliefinder.conversation;

import info.reliefinder.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Slf4j
@Service
public class ConversationService {

    static final String HOME_RESPONSE = "What would you like to do next?";
    static final String ERROR_MESSAGE = "Something went wrong... oops :)";
    static final String CONFUSED_MESSAGE = "I didn't understand that. Please select another option.";
    static final String CANCEL_MESSAGE = "Cancelling";

    static final Map<ConversationType, Map<String, String>> conversationMappings =
            Map.of(
                    ConversationType.POST_SHIFT, Map.of(
                            "location", "Where is the shift?",
                            "when", "What time?"
                    )
            );

    @Autowired
    private ConversationRepository conversationRepository;

    public ConversationResponse getPossibleConversationsResponse() {
        List<String> conversationTypes = Arrays.stream(ConversationType.values())
                .map(ConversationType::getLabel)
                .collect(Collectors.toList());
        String responseText = String.join(",", conversationTypes);
        return new ConversationResponse(UserType.SERVICE, responseText, Instant.now());
    }

    public List<ConversationResponse> handleConversationResponse(String messengerId, ConversationResponse conversationResponse) {

//        Checking for global keywords
        switch (conversationResponse.getText()) {
            case "Cancel":
                return asList(new ConversationResponse(UserType.SERVICE, CANCEL_MESSAGE, Instant.now()),
                        getPossibleConversationsResponse());
        }
        
//        Checking for existing conversation
        Optional<Conversation> existingConversationOptional = conversationRepository.findByUserId(messengerId);

        if (existingConversationOptional.isPresent()) {
            Conversation existingConversation = existingConversationOptional.get();

            String responseText = conversationMappings.get(existingConversation.getConversationType())
                    .get(existingConversation.getConversationStage());
            return singletonList(new ConversationResponse(UserType.SERVICE, responseText, Instant.now()));
        }

//        Checking for start of conversation
        ConversationType conversationType = ConversationType.valueOfLabel(conversationResponse.getText());

        if (conversationType != null) {
            return singletonList(new ConversationResponse(UserType.SERVICE,
                    "Starting conversation for: " + conversationType.getLabel(), Instant.now()));
        }
        
//        Exiting with confused
        return asList(new ConversationResponse(UserType.SERVICE, CONFUSED_MESSAGE, Instant.now()),
                getPossibleConversationsResponse());
    }
}
