package info.reliefinder.conversation;

import info.reliefinder.user.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConversationServiceTests {

    private static String testMessengerId = "123456789";

    @Autowired
    private ConversationService conversationService;

    @Test
    public void handleConversationResponse_withCancel_shouldReturnCancelMessageAndHomeResponse() throws Exception {
        ConversationResponse cancelResponse = new ConversationResponse(UserType.USER, "Cancel", Instant.now());
        List<ConversationResponse> responses = conversationService.handleConversationResponse(testMessengerId, cancelResponse);
        assertThat(responses.get(0).getText()).isEqualTo(ConversationService.CANCEL_MESSAGE);
        assertThat(responses.get(responses.size() - 1).getText()).isEqualTo(conversationService.getPossibleConversationsResponse().getText());
    }

    @Test
    public void getPossibleConversationsResponse_shouldReturnAllConversationTypes() throws Exception {
        ConversationResponse possibleConversationsResponse = conversationService.getPossibleConversationsResponse();
        assertThat(possibleConversationsResponse.getText()).contains(Arrays.asList("Post Shift", "See Shifts"));
    }

    @Test
    public void handleConversationResponse_withoutExistingConversationAndInValidConversationType_shouldReturnConfusedMessageAndHomeResponse() throws Exception {
        ConversationResponse conversationResponse = new ConversationResponse(UserType.USER, "random", Instant.now());
        List<ConversationResponse> responses = conversationService.handleConversationResponse(testMessengerId, conversationResponse);
        assertThat(responses.get(0).getText()).isEqualTo(ConversationService.CONFUSED_MESSAGE);
        assertThat(responses.get(responses.size() - 1).getText()).isEqualTo(conversationService.getPossibleConversationsResponse().getText());
    }

    @Test
    @Ignore
    public void handleConversationResponse_withoutExistingConversationAndValidConversationType_shouldStartConversation() throws Exception {
        ConversationResponse conversationResponse = new ConversationResponse(UserType.USER, ConversationType.POST_SHIFT.getLabel(), Instant.now());
        List<ConversationResponse> conversationResponses = conversationService.handleConversationResponse(testMessengerId, conversationResponse);

    }

    @Test
    @Ignore
    public void handleConversationResponse_withExistingConversation_shouldContinueConversation() throws Exception {
    }

}
