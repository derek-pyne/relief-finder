package info.reliefinder.conversation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConversationService {

    public UserResponse handleUserResponse(UserResponse userResponse) {
        return userResponse;
    }

}
