package info.derek.relieffinder.webflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import java.time.Instant;

@Data
class WebflowWebhook {

    @JsonProperty("_id")
    private String id;

    private String name;

    private String site;

    @Valid
    private ShiftCreationForm data;

    @JsonProperty("d")
    private Instant time;
}
