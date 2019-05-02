package info.derek.relieffinder.messenger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class Message {

    private String mid;
    private String text;

    Message(String text) {
        this.text = text;
    }

}
