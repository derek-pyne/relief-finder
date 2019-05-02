package info.reliefinder.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Data
@MappedSuperclass
public abstract class Auditable {

    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Instant lastModifiedDate;

    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Instant createdDate;

}
