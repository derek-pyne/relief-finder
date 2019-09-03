package info.reliefinder.conversation;

import info.reliefinder.shared.Auditable;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;


@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Conversation extends Auditable {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String userId;

    @Enumerated(EnumType.STRING)
    private ConversationType conversationType;

    private String conversationStage;

//    private List<ConversationResponse> conversationResponses;

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
