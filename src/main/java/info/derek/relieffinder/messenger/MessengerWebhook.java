package info.derek.relieffinder.messenger;

import lombok.Value;

import java.util.List;

@Value
class MessengerWebhook {

    private String object;

    private List<Event> entry;

}
