package info.reliefinder.conversation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static info.reliefinder.conversation.ConversationType.POST_SHIFT;
import static info.reliefinder.conversation.UserType.USER;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ConversationServiceTests {

    private static String testMessengerId = "123456789";

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private ConversationRepository conversationRepository;

    @Before
    public void setUp() throws Exception {
        conversationRepository.deleteAll();
    }

    @Test
    public void handleConversationResponse_withCancel_shouldReturnCancelMessageAndHomeResponse() throws Exception {
        ConversationMessage cancelResponse = new ConversationMessage("Cancel", USER, Instant.now());
        List<ConversationMessage> messages = conversationService.handleConversationResponse(testMessengerId, cancelResponse);
        assertThat(messages.get(0).getText()).isEqualTo(ConversationService.CANCEL_MESSAGE);
        assertThat(messages.get(1).getText()).isEqualTo(conversationService.possibleConversationTypesConversationResponse().getText());
    }

    @Test
    public void getPossibleConversationsResponse_shouldReturnAllConversationTypes() throws Exception {
        ConversationMessage possibleConversationsResponse = conversationService.possibleConversationTypesConversationResponse();
        assertThat(possibleConversationsResponse.getText()).contains(Arrays.asList("Post Shift", "See Shifts"));
    }

    @Test
    public void handleConversationResponse_withoutExistingConversationAndInValidConversationType_shouldReturnConfusedMessageAndHomeResponse() throws Exception {
        ConversationMessage conversationMessage = new ConversationMessage("random", USER, Instant.now());
        List<ConversationMessage> messages = conversationService.handleConversationResponse(testMessengerId, conversationMessage);
        assertThat(messages.get(0).getText()).isEqualTo(ConversationService.CONFUSED_MESSAGE);
        assertThat(messages.get(1).getText()).isEqualTo(conversationService.possibleConversationTypesConversationResponse().getText());
    }

    @Test
    public void handleConversationResponse_withoutExistingConversationAndValidConversationType_shouldStartConversation() throws Exception {
        ConversationMessage conversationMessage = new ConversationMessage(POST_SHIFT.getLabel(), USER, Instant.now());
        List<ConversationMessage> messages = conversationService.handleConversationResponse(testMessengerId, conversationMessage);

        assertThat(messages).isNotEmpty();
        assertThat(messages.get(0).getConversationId()).isNotBlank();

//        TODO should also check that converastion object was persisted
        //        assertThat(interaction.getConversation()).isNotNull();
//        assertThat(interaction.getConversation().getConversationType()).isEqualTo(POST_SHIFT);
    }

    @Test
    public void handleConversationResponse_withExistingConversation_shouldContinueConversation() throws Exception {
        Map<String, ConversationStageResponse> postShiftMappings = ConversationService.conversationMappings.get(POST_SHIFT);
        ConversationStageResponse initialResponse = postShiftMappings
                .get(ConversationService.INITIAL_CONVERSATION_STAGE);

        Conversation existingConversation = Conversation.builder()
                .conversationType(POST_SHIFT)
                .conversationStage(initialResponse.getNextConversationStage())
                .userId(testMessengerId)
                .build();

        conversationRepository.save(existingConversation);

        ConversationMessage conversationMessage = new ConversationMessage("Test response", USER, Instant.now());
        List<ConversationMessage> messages = conversationService.handleConversationResponse(testMessengerId, conversationMessage);
//        assertThat(messages.get(0).getConversationId()).isNotBlank();
//        assertThat(interaction.getConversation()).isNotNull();
//        assertThat(interaction.getConversation().getConversationType()).isEqualTo(POST_SHIFT);
//        assertThat(interaction.getConversation().getConversationStage()).isEqualTo(postShiftMappings.get(initialResponse.getNextConversationStage()).getNextConversationStage());
    }

}
