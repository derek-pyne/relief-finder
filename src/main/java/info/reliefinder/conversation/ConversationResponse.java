package info.reliefinder.conversation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class ConversationResponse {

    private UserType respondingUserType;

    private String text;

    private Instant sentAt;

}
