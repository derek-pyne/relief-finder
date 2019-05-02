package info.derek.relieffinder.messenger;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
class Entry {

    private String id;

    private Instant time;

    private List<Messaging> messaging;
}
