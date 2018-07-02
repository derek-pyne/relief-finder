package info.derek.relieffinder.webflow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.derek.relieffinder.shift.Shift;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class ShiftCMS {

    @JsonProperty("poster-email")
    private String posterEmail;

    private Instant time;

    private String name;

    private String slug;

    @JsonProperty("_id")
    private String id;

    static ShiftCMS fromShift(Shift shift) {
        return ShiftCMS.builder()
                .posterEmail(shift.getPoster().getEmail())
                .name("Epic test shift")
                .build();
    }
}
