package info.reliefinder.shift;

import info.reliefinder.contact.Contact;
import info.reliefinder.shared.Auditable;
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
    private Contact requester;

    @ManyToOne
    private Shift shift;

    private ShiftRequestState state;

}
