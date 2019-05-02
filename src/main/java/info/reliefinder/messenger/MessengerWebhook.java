package info.reliefinder.messenger;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
class MessengerWebhook {

    private String object;

    private List<Entry> entry;

}
