package info.reliefinder.messenger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class MessageResponse {

    private String recipient_id;

    private String message_id;

}
