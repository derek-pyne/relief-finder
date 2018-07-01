package info.derek.relieffinder.webflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
class WebflowWebhook {

    @JsonProperty("_id")
    private String id;

    private String name;

    private String site;

    private Map<String, String> data;

    @JsonProperty("d")
    private Instant time;
}
