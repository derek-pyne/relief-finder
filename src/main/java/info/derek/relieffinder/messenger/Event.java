package info.derek.relieffinder.messenger;

import lombok.Value;

import java.util.List;

@Value
class Event {

    private String id;

    private Integer time;

    private List<Messaging> messaging;
}
