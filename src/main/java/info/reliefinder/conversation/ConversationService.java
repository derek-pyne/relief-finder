package info.reliefinder.conversation;

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
    static final String INITIAL_CONVERSATION_STAGE = "INITIAL";
    static final String FINAL_CONVERSATION_STAGE = "FINAL";

    static final Map<ConversationType, Map<String, ConversationStageResponse>> conversationMappings =
            Map.of(
                    ConversationType.POST_SHIFT, Map.of(
                            INITIAL_CONVERSATION_STAGE, new ConversationStageResponse("WHEN", "Where is the shift?"),
                            "WHEN", new ConversationStageResponse(FINAL_CONVERSATION_STAGE, "What time?")
                    )
            );

    @Autowired
    private ConversationRepository conversationRepository;

    ConversationResponse getPossibleConversationsResponse() {
        List<String> conversationTypes = Arrays.stream(ConversationType.values())
                .map(ConversationType::getLabel)
                .collect(Collectors.toList());
        String responseText = String.join(",", conversationTypes);
        return new ConversationResponse(UserType.SERVICE, responseText, Instant.now());
    }

    public Interaction handleConversationResponse(String messengerId, ConversationResponse conversationResponse) {

//        Checking for global keywords
        switch (conversationResponse.getText()) {
            case "Cancel":
                return new Interaction(asList(new ConversationResponse(UserType.SERVICE, CANCEL_MESSAGE, Instant.now()),
                        getPossibleConversationsResponse()));
        }

//        Checking for existing conversation
        Optional<Conversation> existingConversationOptional = conversationRepository.findTopByUserIdOrderByCreatedDateDesc(messengerId);

        if (existingConversationOptional.isPresent()) {
            Conversation existingConversation = existingConversationOptional.get();

            ConversationStageResponse conversationStageResponse = conversationMappings.get(existingConversation.getConversationType())
                    .get(existingConversation.getConversationStage());

            existingConversation.setConversationStage(conversationStageResponse.getNextConversationStage());
            if (conversationStageResponse.getNextConversationStage().equals(FINAL_CONVERSATION_STAGE)) {
                existingConversation.setCompletedAt(Instant.now());
            }
            Conversation savedConversation = conversationRepository.save(existingConversation);

            return new Interaction(savedConversation, singletonList(new ConversationResponse(UserType.SERVICE, conversationStageResponse.getResponseText(), Instant.now())));
        }

//        Checking for start of conversation
        ConversationType conversationType = ConversationType.valueOfLabel(conversationResponse.getText());

        if (conversationType != null) {

            ConversationStageResponse initialConversationStageResponse = conversationMappings.get(conversationType).get(INITIAL_CONVERSATION_STAGE);

            Conversation conversation = Conversation.builder()
                    .userId(messengerId)
                    .conversationType(conversationType)
                    .conversationStage(initialConversationStageResponse.getNextConversationStage())
                    .build();

            Conversation savedConversation = conversationRepository.save(conversation);
            return new Interaction(savedConversation, singletonList(new ConversationResponse(UserType.SERVICE,
                    initialConversationStageResponse.getResponseText(), Instant.now())));
        }

//        Exiting with confused
        return new Interaction(asList(new ConversationResponse(UserType.SERVICE, CONFUSED_MESSAGE, Instant.now()),
                getPossibleConversationsResponse()));
    }
}
