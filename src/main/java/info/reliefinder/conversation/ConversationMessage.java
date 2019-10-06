package info.reliefinder.conversation;

import info.reliefinder.shared.Auditable;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class ConversationMessage extends Auditable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String conversationId;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String text;

    private Instant sentAt;

    public ConversationMessage(String text, UserType userType, Instant sentAt) {
        this.text = text;
        this.userType = userType;
        this.sentAt = sentAt;
    }

    public ConversationMessage(String text, UserType userType, Instant sentAt, String conversationId) {
        this.conversationId = conversationId;
        this.userType = userType;
        this.text = text;
        this.sentAt = sentAt;
    }
}
