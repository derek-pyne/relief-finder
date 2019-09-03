package info.reliefinder.conversation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
//@EqualsAndHashCode(callSuper = true)
@Builder
//@Entity
//@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
public class Interaction {
    private Conversation conversation;
    private List<ConversationResponse> responses;

    public Interaction(List<ConversationResponse> responses) {
        this.responses = responses;
    }
}
