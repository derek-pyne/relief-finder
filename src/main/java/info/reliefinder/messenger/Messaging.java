package info.reliefinder.messenger;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
class Messaging {

    private MessengerUser sender;

    private MessengerUser recipient;

    private Instant timestamp;

    private Message message;

}
