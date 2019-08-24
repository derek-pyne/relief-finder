package info.reliefinder.conversation;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;
import java.util.List;


@Data
@Builder
//@Entity
//@EntityListeners(AuditingEntityListener.class)
//public class Conversation extends Auditable {
public class Conversation {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String userId;

    @Enumerated(EnumType.STRING)
    private ConversationType conversationType;

    private String conversationStage;

    private List<ConversationResponse> conversationResponses;

//    private String response;

//    @Singular("datum")
//    private Map<String, String> data;

    private Instant completedAt;

//    public ConversationResponse getMostRecentUserResponse() {
//        if (!conversationRespons.isEmpty()) {
//            return conversationRespons.get(conversationRespons.size()-1);
//        }
//        return null;
//    }
}
