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

import static info.reliefinder.conversation.UserType.SERVICE;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Slf4j
@Service
public class ConversationService {

    static final String HOME_RESPONSE = "What would you like to do next?";
    static final String ERROR_MESSAGE = "Something went wrong... oops :)";
    static final String CONFUSED_MESSAGE = "I didn't understand that. Please select another option.";
    static final String CANCEL_MESSAGE = "Cancelling";
    static final String FINISHED_CONVERSATION_MESSAGE = "All done here :)";
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

    @Autowired
    private ConversationMessageRepository conversationMessageRepository;

    ConversationMessage possibleConversationTypesConversationResponse() {
        /*
        This returns a message outlining all the possible Conversation paths the user can take.
        It is like a home screen message.
         */
        List<String> conversationTypes = Arrays.stream(ConversationType.values())
                .map(ConversationType::getLabel)
                .collect(Collectors.toList());
        String responseText = String.join(",", conversationTypes);
        return conversationMessageRepository.save(new ConversationMessage(responseText, SERVICE, Instant.now()));
    }

    public List<ConversationMessage> handleConversationResponse(String messengerId, ConversationMessage conversationMessage) {
        /*
        This handles any message coming from a User.
        This function will check if they are in an existing conversation and continue it, start a new conversation,
        or do some global actions.
         */

//        Save incoming ConversationMessage
        List<ConversationMessage> respondingConversationMessages = parseConversationResponse(messengerId, conversationMessage);

        conversationMessage.setConversationId(respondingConversationMessages.get(0).getConversationId());
        conversationMessageRepository.save(conversationMessage);
        return conversationMessageRepository.saveAll(respondingConversationMessages);
    }

    private List<ConversationMessage> parseConversationResponse(String messengerId, ConversationMessage conversationMessage) {
        //        Checking for global keywords
        if ("Cancel".equals(conversationMessage.getText())) {
            return asList(
                    new ConversationMessage(CANCEL_MESSAGE, SERVICE, Instant.now()),
                    possibleConversationTypesConversationResponse()
            );
        }

//        Checking for existing conversation
        Optional<Conversation> existingConversationOptional = conversationRepository.findTopByUserIdAndCompletedAtIsNullOrderByCreatedDateDesc(messengerId);

//        If there is an existing conversation, continue it
        if (existingConversationOptional.isPresent()) {
            Conversation existingConversation = existingConversationOptional.get();

//            Checking if last message in conversation
            if (existingConversation.getConversationStage().equals(FINAL_CONVERSATION_STAGE)) {
                existingConversation.setCompletedAt(Instant.now());
                return asList(
                        new ConversationMessage(FINISHED_CONVERSATION_MESSAGE, SERVICE, Instant.now(), existingConversation.getId()),
                        possibleConversationTypesConversationResponse()
                );
            }
            ConversationStageResponse conversationStageResponse = conversationMappings.get(existingConversation.getConversationType())
                    .get(existingConversation.getConversationStage());

            existingConversation.setConversationStage(conversationStageResponse.getNextConversationStage());

//            If conversation is finished, save completedAt timestamp to mark as finished
            Conversation savedConversation = conversationRepository.save(existingConversation);
            return singletonList(
                    new ConversationMessage(conversationStageResponse.getResponseText(), SERVICE, Instant.now(), savedConversation.getId())
            );
        }

//        Checking for start of conversation
        ConversationType conversationType = ConversationType.valueOfLabel(conversationMessage.getText());

//        If a valid conversation is matched, start a conversation of that type
        if (conversationType != null) {

            ConversationStageResponse initialConversationStageResponse = conversationMappings.get(conversationType).get(INITIAL_CONVERSATION_STAGE);

            Conversation conversation = Conversation.builder()
                    .userId(messengerId)
                    .conversationType(conversationType)
                    .conversationStage(initialConversationStageResponse.getNextConversationStage())
                    .build();

            Conversation savedConversation = conversationRepository.save(conversation);
            return singletonList(
                    new ConversationMessage(initialConversationStageResponse.getResponseText(), SERVICE, Instant.now(), savedConversation.getId())
            );
        }

//        Exiting with confused
        return asList(
                new ConversationMessage(CONFUSED_MESSAGE, SERVICE, Instant.now()),
                possibleConversationTypesConversationResponse()
        );
    }
}
