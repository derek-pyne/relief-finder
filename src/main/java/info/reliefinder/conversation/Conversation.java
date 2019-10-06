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
/*
A Conversation represents a complete set of interactions back and forth with the user.
Conversations do not go on indefinetly.
A Conversation has a type and goes through multiple defined stages.
When it is completed a completed time is recorded.
Conversations can involve many ConversationMessages going back and forth between the user and the service.
 */

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String userId;

    @Enumerated(EnumType.STRING)
    private ConversationType conversationType;

    private String conversationStage;

//    @Singular("datum")
//    private Map<String, String> data;

    private Instant completedAt;

}
