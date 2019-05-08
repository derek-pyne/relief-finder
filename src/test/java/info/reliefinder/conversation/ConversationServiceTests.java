package info.reliefinder.conversation;

import info.reliefinder.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConversationServiceTests {

    private static User testUser = new User("test_id", "joe@coffee.com", "123-456-7890", "Joe Coffee");
    @Autowired
    private ConversationService conversationService;

    @Test
    public void handleUserResponse_shouldReturnUserResponse() {

    }

    @Test
    public void handleUserResponse_withExit_shouldReturnExitResponse() {

        UserResponse quitResponse = UserResponse.builder().response("quit").user(testUser).build();
        UserResponse response = conversationService.handleUserResponse(quitResponse);
        assertThat(response.getResponse()).isEqualTo("quit");

    }
}
