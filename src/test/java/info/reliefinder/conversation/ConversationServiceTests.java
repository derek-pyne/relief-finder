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
        ConversationResponse cancelResponse = new ConversationResponse(UserType.USER, "Cancel", Instant.now());
        Interaction interaction = conversationService.handleConversationResponse(testMessengerId, cancelResponse);
        List<ConversationResponse> responses = interaction.getResponses();
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
        Interaction interaction = conversationService.handleConversationResponse(testMessengerId, conversationResponse);
        List<ConversationResponse> responses = interaction.getResponses();
        assertThat(responses.get(0).getText()).isEqualTo(ConversationService.CONFUSED_MESSAGE);
        assertThat(responses.get(responses.size() - 1).getText()).isEqualTo(conversationService.getPossibleConversationsResponse().getText());
    }

    @Test
    public void handleConversationResponse_withoutExistingConversationAndValidConversationType_shouldStartConversation() throws Exception {
        ConversationResponse conversationResponse = new ConversationResponse(UserType.USER, POST_SHIFT.getLabel(), Instant.now());
        Interaction interaction = conversationService.handleConversationResponse(testMessengerId, conversationResponse);
        assertThat(interaction.getConversation()).isNotNull();
        assertThat(interaction.getConversation().getConversationType()).isEqualTo(POST_SHIFT);
        assertThat(interaction.getResponses()).isNotEmpty();
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

        ConversationResponse conversationResponse = new ConversationResponse(UserType.USER, "Test response", Instant.now());
        Interaction interaction = conversationService.handleConversationResponse(testMessengerId, conversationResponse);
        assertThat(interaction.getConversation()).isNotNull();
        assertThat(interaction.getConversation().getConversationType()).isEqualTo(POST_SHIFT);
        assertThat(interaction.getConversation().getConversationStage()).isEqualTo(postShiftMappings.get(initialResponse.getNextConversationStage()).getNextConversationStage());
    }

}
