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

    @JsonProperty("time")
    private Instant startTime;

    @JsonProperty("end-time")
    private Instant endTime;

    private String name;

    private String slug;

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_draft")
    private boolean draft;

    @JsonProperty("_archived")
    private boolean archived;

    static ShiftCMS fromShift(Shift shift) {
        return ShiftCMS.builder()
                .posterEmail(shift.getPoster().getEmail())
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .name("Epic test shift")
                .build();
    }
}
