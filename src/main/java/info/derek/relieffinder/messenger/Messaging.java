package info.derek.relieffinder.messenger;

import lombok.Value;

@Value
class Messaging {

    private MessengerUser sender;

    private MessengerUser recipient;

    private Integer timestamp;

    private Message message;
}
