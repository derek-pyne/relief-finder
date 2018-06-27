package info.derek.relieffinder;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
class Contact extends Auditable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(unique = true, nullable = false)
    private String id;

    @NotBlank
    private String email;

    private String phoneNumber;

    private String fullName;
}
