package info.reliefinder.shift;

import info.reliefinder.shared.Auditable;
import info.reliefinder.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
public class ShiftRequest extends Auditable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @ManyToOne
    private User requester;

    @ManyToOne
    private Shift shift;

    private ShiftRequestState state;

}
