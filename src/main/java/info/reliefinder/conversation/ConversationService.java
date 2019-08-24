package info.reliefinder.conversation;

import info.reliefinder.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConversationService {

    static final String HOME_RESPONSE = "What would you like to do next?";
    static final String ERROR_MESSAGE = "Something went wrong... oops :)";
    static final String CANCEL_MESSAGE = "Cancelling";


    public ConversationResponse getPossibleConversationsResponse() {
        List<String> conversationTypes = Arrays.stream(ConversationType.values())
                .map(ConversationType::getLabel)
                .collect(Collectors.toList());
        String responseText = String.join(",", conversationTypes);
        return new ConversationResponse(UserType.SERVICE, responseText, Instant.now());
    }

    public List<ConversationResponse> handleConversationResponse(User user, ConversationResponse conversationResponse) {

        switch (conversationResponse.getText()) {
            case "Cancel":
                return Arrays.asList(new ConversationResponse(UserType.SERVICE, CANCEL_MESSAGE, Instant.now()),
                        getPossibleConversationsResponse());
        }
        return null;
    }
}
