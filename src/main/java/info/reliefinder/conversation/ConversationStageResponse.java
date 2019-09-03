package info.reliefinder.conversation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConversationStageResponse {
    private String nextConversationStage;
    private String responseText;
}
