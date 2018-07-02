package info.derek.relieffinder.shift;

import info.derek.relieffinder.contact.Contact;
import info.derek.relieffinder.shared.Auditable;
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
public class Shift extends Auditable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(unique = true, nullable = false)
    private String id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Contact poster;
}
